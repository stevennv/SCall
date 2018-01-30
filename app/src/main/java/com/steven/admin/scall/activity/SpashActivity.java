package com.steven.admin.scall.activity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.BounceInterpolator;
import android.widget.Button;
import android.widget.TextView;

import com.steven.admin.scall.R;
import com.steven.admin.scall.utils.SharedPreferencesUtils;
import com.waynell.library.DropAnimationView;

public class SpashActivity extends AppCompatActivity implements View.OnClickListener {
    private DropAnimationView dropView;
    private Button btnStart;
    private Button btnNext;
    private SharedPreferencesUtils utils;
    private TextView tvName;
    private TextView tvPhone;
    private int[] imageArray = new int[6];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_spash);
        iniUI();
    }

    private void iniUI() {
        utils = new SharedPreferencesUtils(this);
        dropView = findViewById(R.id.drop_view);
        btnNext = findViewById(R.id.btn_next);
        btnStart = findViewById(R.id.btn_start);
        tvName = findViewById(R.id.tv_name);
        tvPhone = findViewById(R.id.tv_phone_number);
        Typeface font = Typeface.createFromAsset(getAssets(), "fonts/Larch Shaded.otf");
        tvName.setTypeface(font);
        tvPhone.setTypeface(font);
        tvName.setText("My Love");
        tvPhone.setText("+44 123 456 789 Calling");
        btnStart.setVisibility(View.VISIBLE);
        imageArray = new int[]{R.mipmap.ic_love, R.mipmap.ic_love, R.mipmap.ic_love, R.mipmap.ic_love, R.mipmap.ic_love, R.mipmap.ic_love};
        dropView.setDrawables(imageArray);
        dropView.startAnimation();
        int bottomOfScreen = getResources().getDisplayMetrics()
                .heightPixels / 2;
        btnStart.animate()
                .translationY(bottomOfScreen)
                .setInterpolator(new AccelerateInterpolator())
                .setInterpolator(new BounceInterpolator())
                .setDuration(2000);
        btnNext.setOnClickListener(this);
        btnStart.setOnClickListener(this);
        if (!utils.isFirstOpenApp()) {
            Intent intent = new Intent(this, MainActivity.class);
//            utils.saveFirstOpenApp(false);
            startActivity(intent);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_start:
                Intent intent = new Intent(this, MainActivity.class);
                utils.saveFirstOpenApp(false);
                startActivity(intent);
                break;
            case R.id.btn_next:
                break;
        }
    }
}
