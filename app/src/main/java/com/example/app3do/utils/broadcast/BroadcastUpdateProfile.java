package com.example.app3do.utils.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class BroadcastUpdateProfile extends BroadcastReceiver {
    public static String ACTION_PROFILE = "ACTION_PROFILE";
    private UpdateProfile updateProfile;

    public BroadcastUpdateProfile(UpdateProfile updateProfile) {
        this.updateProfile = updateProfile;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(ACTION_PROFILE)) {
            updateProfile.updateProfile();
        }
    }
}
