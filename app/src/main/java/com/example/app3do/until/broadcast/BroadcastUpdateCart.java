package com.example.app3do.until.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class BroadcastUpdateCart extends BroadcastReceiver {
    public static String ACTION_UPDATE_CART = "ACTION_UPDATE_CART";
    private UpdateCart mUpdateCart;

    public BroadcastUpdateCart(UpdateCart mUpdateCart) {
        this.mUpdateCart = mUpdateCart;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(BroadcastUpdateCart.ACTION_UPDATE_CART)) {
            mUpdateCart.update();
        }
    }
}
