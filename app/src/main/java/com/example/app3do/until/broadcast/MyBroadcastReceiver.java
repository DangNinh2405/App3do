package com.example.app3do.until.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class MyBroadcastReceiver extends BroadcastReceiver {
    public static String ACTION_UPDATE_CART = "ACTION_UPDATE_CART";
    private UpdateCart mUpdateCart;

    public MyBroadcastReceiver(UpdateCart mUpdateCart) {
        this.mUpdateCart = mUpdateCart;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(MyBroadcastReceiver.ACTION_UPDATE_CART)) {
            mUpdateCart.update();
        }
    }
}
