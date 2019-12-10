package com.project.digimagz.service;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.project.digimagz.R;
import com.project.digimagz.model.Notif;
import com.project.digimagz.view.activity.SplashActivity;

import java.util.Map;
import java.util.Random;

public class FirebaseMessaging extends FirebaseMessagingService {

    public static final String CHANNEL_ID = "DIGIMAGZ";
    public static final String CHANNEL_NAME = "DigiMagz";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        if (remoteMessage.getData().size() > 0) {
            Notif notif = new Notif();
            notif.setTitle(remoteMessage.getData().get("title"));
            notif.setDescription(remoteMessage.getData().get("body"));
            notif.setIsRead(0);
            showMessages(remoteMessage);
        }
    }

    private void showMessages(RemoteMessage rm) {
        int rand = new Random().nextInt(9999) + 1;
        Map<String, String> data = rm.getData();
        String textTitle = data.get("title");
        String textContent = data.get("body");

        Intent activityIntent = new Intent(this, SplashActivity.class);
        activityIntent.putExtra("id", rand);
        PendingIntent pendingActivity = PendingIntent.getActivity(this, 1, activityIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = CHANNEL_NAME;
            String description = CHANNEL_NAME;
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            notificationManager.createNotificationChannel(channel);
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_launcher_digimagz)
                .setContentTitle(textTitle)
                .setContentText(textContent)
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText(textContent))
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(pendingActivity);
        builder.setVibrate(new long[] { 1000, 1000});
        notificationManager.notify(rand, builder.build());

    }

    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);
    }
}