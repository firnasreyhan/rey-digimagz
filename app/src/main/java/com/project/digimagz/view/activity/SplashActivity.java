package com.project.digimagz.view.activity;

import android.Manifest;
import android.app.Activity;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.project.digimagz.BuildConfig;
import com.project.digimagz.Constant;
import com.project.digimagz.R;
import com.project.digimagz.adapter.RecyclerViewNewsAdapter;
import com.project.digimagz.api.ApiClient;
import com.project.digimagz.api.ApiInterface;
import com.project.digimagz.api.InitRetrofit;
import com.project.digimagz.api.NotifApi;
import com.project.digimagz.model.DefaultStructureNewsSearch;
import com.project.digimagz.model.DefaultStructureVersion;
import com.project.digimagz.model.DefaultStructureVideo;
import com.project.digimagz.model.NotifValue;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SplashActivity extends Activity {

    private ApiInterface apiInterface;
    private TextView textViewVersion;
    private String id_news;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        apiInterface = ApiClient.getRetrofit().create(ApiInterface.class);
        textViewVersion = findViewById(R.id.textViewVersion);

        textViewVersion.setText("Version " + BuildConfig.VERSION_NAME);

        if (getIntent().getExtras() != null){
            int id = getIntent().getIntExtra("id", 0);
            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.cancel(id);
            id_news = getIntent().getStringExtra("id_news");
            if (id_news != null) {
                Log.e("id_news_notif", id_news);
            }
        }

        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(new OnSuccessListener<InstanceIdResult>() {
            @Override
            public void onSuccess(InstanceIdResult instanceIdResult) {
                Retrofit retrofit = new Retrofit.Builder().baseUrl(Constant.URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                NotifApi notifApi = retrofit.create(NotifApi.class);
                Call<NotifValue> call = notifApi.sendToken(instanceIdResult.getToken());
                call.enqueue(new Callback<NotifValue>() {
                    @Override
                    public void onResponse(Call<NotifValue> call, Response<NotifValue> response) {
                        String value = response.body().getValue();
                        String message = response.body().getMessage();
                        Log.d("val", value);
                        Log.d("mes", message);
                    }

                    @Override
                    public void onFailure(Call<NotifValue> call, Throwable t) {
                        Log.d("ERROR", "Network error!");
                    }
                });
            }
        });

        String[] PERMISSIONS = {
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA,
        };

        if(!hasPermissions(PERMISSIONS)){
            ActivityCompat.requestPermissions(this, PERMISSIONS, 0);
        }else {
            apiInterface.getVersion().enqueue(new Callback<DefaultStructureVersion>() {
                @Override
                public void onResponse(Call<DefaultStructureVersion> call, Response<DefaultStructureVersion> response) {
                    assert response.body() != null;
                    if (response.body().getData().getVersion().equalsIgnoreCase(BuildConfig.VERSION_NAME)) {
                        goToMain();
                    } else {
                        startActivity(new Intent(SplashActivity.this, UpdateActivity.class));
                    }
                }

                @Override
                public void onFailure(Call<DefaultStructureVersion> call, Throwable t) {
                    Log.e("version", t.getMessage());
                }
            });
        }
    }

    private boolean hasPermissions(String... permissions) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && permissions != null) {
            for (String permission : permissions) {
                if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 0: {
                if(grantResults[0] != PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(this, "Izin diperlukan untuk menggunakan aplikasi", Toast.LENGTH_SHORT).show();
                    //finish();
                }else {
                    goToMain();
                }
            }
        }
    }

    private void goToMain() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                apiInterface.getVideo().enqueue(new Callback<DefaultStructureVideo>() {
                    @Override
                    public void onResponse(Call<DefaultStructureVideo> call, Response<DefaultStructureVideo> response) {
                        if (id_news == null) {
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                            finish();
                        } else {
                            apiInterface.getNewsParam(id_news).enqueue(new Callback<DefaultStructureNewsSearch>() {
                                @Override
                                public void onResponse(Call<DefaultStructureNewsSearch> call, Response<DefaultStructureNewsSearch> response) {
                                    Intent intent = new Intent(getApplicationContext(), DetailNewsActivity.class);
                                    intent.putExtra(RecyclerViewNewsAdapter.INTENT_PARAM_KEY_NEWS_DATA, response.body().getData());
                                    intent.putExtra("notif", true);
                                    startActivity(intent);
                                    finish();
                                }

                                @Override
                                public void onFailure(Call<DefaultStructureNewsSearch> call, Throwable t) {
                                    Log.e("ErrorGetSearch", t.getMessage());
                                }
                            });
                        }
                    }

                    @Override
                    public void onFailure(Call<DefaultStructureVideo> call, Throwable t) {
                        startActivity(new Intent(getApplicationContext(), ErrorActivity.class));
                        finish();
                    }
                });
            }
        }, 5000);
    }
}
