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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.project.digimagz.Constant;
import com.project.digimagz.R;
import com.project.digimagz.api.InitRetrofit;
import com.project.digimagz.api.NotifApi;
import com.project.digimagz.model.NotifValue;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SplashActivity extends Activity {

    private InitRetrofit initRetrofit;
    private ArrayList<Integer> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        initRetrofit = new InitRetrofit();
        initRetrofit.getStatusCodeFromServer();

        if (getIntent().getExtras() != null){
            int id = getIntent().getIntExtra("id", 0);
            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.cancel(id);
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

        initRetrofit.setOnRetrofitSuccess(new InitRetrofit.OnRetrofitSuccess() {
            @Override
            public void onSuccessGetData(ArrayList arrayList) {
                list.addAll(arrayList);
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
            goToMain();
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
                if (!list.isEmpty()) {
                    if (list.get(0) != 0) {
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        finish();
                    } else {
                        startActivity(new Intent(getApplicationContext(), ErrorActivity.class));
                        finish();
                    }
                } else {
                    startActivity(new Intent(getApplicationContext(), ErrorActivity.class));
                    finish();
                }
            }
        }, 2500);
    }
}
