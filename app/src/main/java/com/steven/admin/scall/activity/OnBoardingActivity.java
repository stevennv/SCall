package com.steven.admin.scall.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.steven.admin.scall.R;
import com.steven.admin.scall.fragment.FragmentSpash;
import com.steven.admin.scall.utils.SharedPreferencesUtils;
import com.layer_net.stepindicator.StepIndicator;

public class OnBoardingActivity extends AppCompatActivity {
    public ViewPager viewPager;
    private StepIndicator stepView;
    private SharedPreferencesUtils utils;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_boarding);
        iniUI();
    }

    private void iniUI() {
        utils = new SharedPreferencesUtils(this);
        checkFirstOpenApp();
        stepView = findViewById(R.id.step_view);
        viewPager = findViewById(R.id.viewpager);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        stepView.setupWithViewPager(viewPager);
        stepView.setClickable(false);
    }

    private class ViewPagerAdapter extends FragmentPagerAdapter {
        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
//            if (position == 0) {
            Fragment fragment = new FragmentSpash();
            Bundle bundle = new Bundle();
            bundle.putInt("edttext", position);
            fragment.setArguments(bundle);
            return fragment;
//            } else if (position==1){
//                Fragment fragment = new FragmentSpash2();
//                Bundle bundle = new Bundle();
//                bundle.putInt("edttext", position);
//                fragment.setArguments(bundle);
//                return fragment;
//            } else {
//                Fragment fragment = new FragmentSpash3();
//                Bundle bundle = new Bundle();
//                bundle.putInt("edttext", position);
//                fragment.setArguments(bundle);
//                return fragment;
//            }

        }

        @Override
        public int getCount() {
            return 4;
        }
    }

    private void checkFirstOpenApp() {
        if (!utils.isFirstOpenApp()) {
            Intent intent = new Intent(OnBoardingActivity.this, MainActivity.class);
            startActivity(intent);
        }
    }
}
