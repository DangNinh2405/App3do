package com.example.app3do.firebases;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.example.app3do.R;
import com.example.app3do.until.application.MyApplication;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

public class FirebaseMessaging extends FirebaseMessagingService {
    private static int id = 1;
    @Override
    public void onNewToken(@NonNull String token) {
        Log.d("TokenFirebase", token);
        super.onNewToken(token);
    }

    @Override
    public void onMessageReceived(@NonNull RemoteMessage message) {
        super.onMessageReceived(message);

        Map<String, String> notification = message.getData();
        if (notification == null) {
            return;
        }

        String title = notification.get("title");
        String body = notification.get("body");

        sendNotification(title, body);
    }

    private void sendNotification(String title, String body){
        Notification notification = new NotificationCompat.Builder(this ,MyApplication.CHANNEL_ID)
                .setContentTitle(title)
                .setContentText(body)
                .setSmallIcon(R.drawable.logo_2x)
                .build();

        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(id, notification);
        id++;
    }
}
