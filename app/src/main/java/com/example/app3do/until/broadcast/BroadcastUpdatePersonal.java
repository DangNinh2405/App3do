package com.example.app3do.until.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class BroadcastUpdatePersonal extends BroadcastReceiver {
    public static String ACTION_UPDATE_ADDRESSES = "ACTION_UPDATE_ADDRESSES";
    public static String ACTION_UPDATE_BANKS = "ACTION_UPDATE_ADDRESS";
    private UpdatePersonal personal;
    public BroadcastUpdatePersonal(UpdatePersonal personal) {
        this.personal = personal;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(ACTION_UPDATE_ADDRESSES)) {
            personal.updateAddresses();
        }

        if (intent.getAction().equals(ACTION_UPDATE_BANKS)) {
            personal.updateBanks();
        }
    }
}
