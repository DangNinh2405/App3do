package com.example.app3do.utils.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class BroadcastOrder extends BroadcastReceiver {
    public static final String ACTION_UPDATE_ORDERS = "ACTION_UPDATE_ORDERS";
    public static final String ACTION_UPDATE_SUB_ORDERS = "ACTION_UPDATE_SUB_ORDERS";
    private UpdateOrders updateOrders;

    public BroadcastOrder(UpdateOrders updateOrders) {
        this.updateOrders = updateOrders;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(ACTION_UPDATE_ORDERS)) {
            updateOrders.update();
        }

    }
}
