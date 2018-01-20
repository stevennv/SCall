package com.steven.admin.scall.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.BounceInterpolator;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.steven.admin.scall.R;
import com.steven.admin.scall.activity.MainActivity;
import com.steven.admin.scall.activity.OnBoardingActivity;
import com.steven.admin.scall.utils.SharedPreferencesUtils;

/**
 * Created by Admin on 12/5/2017.
 */

public class FragmentSpash extends Fragment implements View.OnClickListener {
    private Button btnStart;
    private Button btnNext;
    private int check;
    private OnBoardingActivity mActivity;
    private SharedPreferencesUtils utils;
    private TextView tvName;
    private TextView tvPhone;
    private RelativeLayout rlSpash;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_spash, container, false);
        mActivity = (OnBoardingActivity) getContext();
        utils = new SharedPreferencesUtils(getContext());
        btnNext = view.findViewById(R.id.btn_next);
        btnStart = view.findViewById(R.id.btn_start);
        tvName = view.findViewById(R.id.tv_name);
        tvPhone = view.findViewById(R.id.tv_phone_number);
        rlSpash = view.findViewById(R.id.rl_spash);
        Typeface font = Typeface.createFromAsset(getContext().getAssets(), "fonts/Billfont.otf");
        tvName.setTypeface(font);
        tvPhone.setTypeface(font);
        tvName.setText("New Way");
        tvPhone.setText("+44 666 666 666 Calling");
//        if (getArguments() != null) {
//            check = getArguments().getInt("edttext");
//            if (check == 0) {
////                rlSpash.setBackgroundDrawable(getContext().getDrawable(R.drawable.bg44));
//                rlSpash.setBackground(getResources().getDrawable(R.drawable.bg44));
                btnNext.setVisibility(View.VISIBLE);
//            } else if (check == 1) {
//                rlSpash.setBackgroundDrawable(getContext().getDrawable(R.drawable.anh1));
//                tvName.setText("My Love");
//                tvPhone.setText("+44 123 456 789 Calling");
//                tvName.setTextColor(Color.parseColor("#E77C7C"));
//                tvPhone.setTextColor(Color.parseColor("#E77C7C"));
//                btnNext.setVisibility(View.VISIBLE);
//            } else if (check == 2) {
//                rlSpash.setBackgroundDrawable(getContext().getDrawable(R.drawable.bg22));
//                tvName.setText("My Pet");
//                tvPhone.setText("+44 987 654 321 Calling");
//                tvName.setTextColor(Color.parseColor("#00FFFF"));
//                tvPhone.setTextColor(Color.parseColor("#00FFFF"));
//                btnNext.setVisibility(View.VISIBLE);
//            } else {
//                rlSpash.setBackgroundDrawable(getContext().getDrawable(R.drawable.bg33));
//                tvName.setText("Steven");
//                tvPhone.setText("+44 123 456 789 Calling");
//                tvName.setTextColor(Color.parseColor("#31bfed"));
//                tvPhone.setTextColor(Color.parseColor("#31bfed"));
//                btnStart.setVisibility(View.VISIBLE);
//                int bottomOfScreen = getResources().getDisplayMetrics()
//                        .heightPixels / 2;
//                btnStart.animate()
//                        .translationY(bottomOfScreen)
//                        .setInterpolator(new AccelerateInterpolator())
//                        .setInterpolator(new BounceInterpolator())
//                        .setDuration(2000);
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
