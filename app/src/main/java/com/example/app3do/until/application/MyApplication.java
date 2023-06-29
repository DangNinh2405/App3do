package com.example.app3do.until.application;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;

public class MyApplication extends Application {
    public static final String CHANNEL_ID = "CHANNEL_ID";
    @Override
    public void onCreate() {
        super.onCreate();
        registerChannel();
    }

    private void registerChannel(){
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "PushNotification", NotificationManager.IMPORTANCE_DEFAULT);

            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }
    }
}
