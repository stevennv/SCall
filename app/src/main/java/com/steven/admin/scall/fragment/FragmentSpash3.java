package com.steven.admin.scall.fragment;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.BounceInterpolator;
import android.widget.Button;
import android.widget.TextView;

import com.steven.admin.scall.R;
import com.steven.admin.scall.activity.MainActivity;
import com.steven.admin.scall.activity.OnBoardingActivity;
import com.steven.admin.scall.utils.SharedPreferencesUtils;

/**
 * Created by Admin on 12/13/2017.
 */

public class FragmentSpash3 extends Fragment implements View.OnClickListener {
    private Button btnStart;
    private Button btnNext;
    private int check;
    private OnBoardingActivity mActivity;
    private SharedPreferencesUtils utils;
    private TextView tvName;
    private TextView tvPhone;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_spash3, container, false);
        mActivity = (OnBoardingActivity) getContext();
        utils = new SharedPreferencesUtils(getContext());
        btnNext = view.findViewById(R.id.btn_next);
        btnStart = view.findViewById(R.id.btn_start);
        tvName = view.findViewById(R.id.tv_name);
        tvPhone = view.findViewById(R.id.tv_phone_number);
        Typeface font = Typeface.createFromAsset(getContext().getAssets(), "fonts/We Youth.otf");
        tvName.setTypeface(font);
        tvPhone.setTypeface(font);
        tvName.setText("My Pet");
        tvPhone.setText("+44 987 654 321 Calling");
//        if (getArguments() != null) {
//            check = getArguments().getInt("edttext");
//            if (check == 3) {
//                btnStart.setVisibility(View.VISIBLE);
//                int bottomOfScreen = getResources().getDisplayMetrics()
//                        .heightPixels / 2;
//                btnStart.animate()
//                        .translationY(bottomOfScreen)
//                        .setInterpolator(new AccelerateInterpolator())
//                        .setInterpolator(new BounceInterpolator())
//                        .setDuration(2000);
//            } else {
                btnNext.setVisibility(View.VISIBLE);
//            }
//        }
        btnNext.setOnClickListener(this);
        btnStart.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_start:
                Intent intent = new Intent(getContext(), MainActivity.class);
                utils.saveFirstOpenApp(false);
                startActivity(intent);
                break;
            case R.id.btn_next:
                mActivity.viewPager.setCurrentItem(mActivity.viewPager.getCurrentItem() + 1);
                break;
        }
    }
}
