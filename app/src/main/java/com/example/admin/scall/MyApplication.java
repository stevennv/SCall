package com.example.admin.scall;

import android.app.Application;

import com.google.android.gms.ads.MobileAds;

/**
 * Created by Admin on 1/3/2018.
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        MobileAds.initialize(this, getString(R.string.admob_app_id));
    }
}
