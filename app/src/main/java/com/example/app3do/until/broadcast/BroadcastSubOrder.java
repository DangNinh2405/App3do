package com.example.app3do.until.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class BroadcastSubOrder extends BroadcastReceiver {
    public static final String ACTION_UPDATE_SUB_ORDERS = "ACTION_UPDATE_SUB_ORDERS";

    private UpdateSubOrders updateOrders;

    public BroadcastSubOrder(UpdateSubOrders updateOrders) {
        this.updateOrders = updateOrders;
    }
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(ACTION_UPDATE_SUB_ORDERS)) {
            updateOrders.update();
        }
    }
}
