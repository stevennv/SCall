package com.example.admin.scall.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.admin.scall.R;
import com.example.admin.scall.utils.SqliteHelper;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

/**
 * Created by Admin on 11/30/2017.
 */

public class BaseActivity extends AppCompatActivity {
    public static int countActivity = 0;
    InterstitialAd mInterstitialAd;
    public static boolean isCalling = false;
    public static SqliteHelper db;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = new SqliteHelper(this);
        AdListener adListener = new AdListener() {
            @Override
            public void onAdLoaded() {
                super.onAdLoaded();

                mInterstitialAd.show();
            }

            @Override
            public void onAdClosed() {
                super.onAdClosed();
            }

            @Override
            public void onAdFailedToLoad(int i) {
                super.onAdFailedToLoad(i);
            }

            @Override
            public void onAdLeftApplication() {
                super.onAdLeftApplication();
            }

            @Override
            public void onAdOpened() {
                super.onAdOpened();
            }
        };
        countActivity++;
        if (countActivity == 2) {
            mInterstitialAd = new InterstitialAd(this);
            mInterstitialAd.setAdUnitId(getResources().getString(R.string.interstitial_ads));
            mInterstitialAd.setAdListener(adListener);
            countActivity = 0;
            requestNewInterstitial();
        }
        Log.d("onCreate123:", "onCreate: " + countActivity);
    }

    private void requestNewInterstitial() {
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice("EE6061F76E1A5A87DCD512521BC294F9")
                .build();

        mInterstitialAd.loadAd(adRequest);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
