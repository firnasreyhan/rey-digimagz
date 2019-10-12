package com.project.digimagz.view.activity;

import android.app.Activity;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.project.digimagz.Constant;
import com.project.digimagz.R;
import com.project.digimagz.api.NotifApi;
import com.project.digimagz.model.NotifValue;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SplashActivity extends Activity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

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
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
            }
        }, 2500);
    }
}
