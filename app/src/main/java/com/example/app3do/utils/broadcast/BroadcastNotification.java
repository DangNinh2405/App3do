package com.example.app3do.utils.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class BroadcastNotification extends BroadcastReceiver {
    public static final String ACTION_UPDATE_NOTIFICATION = "ACTION_UPDATE_NOTIFICATION";
    private UpdateNotification notification;

    public BroadcastNotification(UpdateNotification notification) {
        this.notification = notification;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(ACTION_UPDATE_NOTIFICATION)) {
            notification.update();
        }
    }
}
