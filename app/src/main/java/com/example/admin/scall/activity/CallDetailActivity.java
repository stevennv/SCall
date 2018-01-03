package com.example.admin.scall.activity;

import android.Manifest;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.net.Uri;
import android.provider.Contacts;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.admin.scall.R;
import com.example.admin.scall.dialog.MessageDialog;
import com.example.admin.scall.model.Contact;
import com.example.admin.scall.model.InfoStyle;
import com.google.android.gms.ads.MobileAds;
import com.google.gson.Gson;
import com.hanks.htextview.rainbow.RainbowTextView;
import com.waynell.library.DropAnimationView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

public class CallDetailActivity extends BaseActivity implements View.OnClickListener {
    private ImageView imgEffect;
    private RainbowTextView tvPreview;
    private TextView tvName;
    private DropAnimationView dropView;
    private TextView tvCallBack;
    private TextView tvDetail;
    private TextView tvMessage;
    private TextView tvEdit;
    private TextView tvContentDetail;
    private RelativeLayout rlDetail;
    private InfoStyle infoStyle;
    private int[] listImage = new int[6];
    private Gson gson;
    private String phone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call_detail);
        iniUI();
    }

    private void iniUI() {
        MobileAds.initialize(this, getString(R.string.admob_app_id));
        gson = new Gson();
        imgEffect = findViewById(R.id.img_effect);
        tvPreview = findViewById(R.id.tv_preview);
        tvName = findViewById(R.id.tv_name);
        dropView = findViewById(R.id.drop_animation_view);
        tvCallBack = findViewById(R.id.tv_call_back);
        tvDetail = findViewById(R.id.tv_detail);
        tvMessage = findViewById(R.id.tv_message);
        tvEdit = findViewById(R.id.tv_edit);
        rlDetail = findViewById(R.id.rl_detail);
        tvContentDetail = findViewById(R.id.tv_content_detail);

        if (getIntent() != null) {
            try {
                infoStyle = (InfoStyle) getIntent().getSerializableExtra("Info");
                phone = infoStyle.getPhone();
                tvName.setText(infoStyle.getName());
                if (infoStyle.getFont() != null) {
                    Typeface font = Typeface.createFromAsset(getAssets(), "fonts/" + infoStyle.getFont());
                    tvName.setTypeface(font);
                }
                tvName.setTextSize(infoStyle.getSize());
                tvName.setTextColor(infoStyle.getColor());
                if (infoStyle.getAnimation() != 0) {
                    Animation animation = AnimationUtils.loadAnimation(CallDetailActivity.this, infoStyle.getAnimation());
                    tvName.startAnimation(animation);
                }
                Glide.with(CallDetailActivity.this).load(infoStyle.getUrlImage()).into(imgEffect);
                if (infoStyle.getListIcon() != null) {
                    listImage = gson.fromJson(infoStyle.getListIcon(), int[].class);
                    dropView.setDrawables(listImage);
                    dropView.startAnimation();
                }
                if (infoStyle.getIsFull() == 1) {
                    imgEffect.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                } else {
                    imgEffect.setScaleType(ImageView.ScaleType.FIT_XY);
                }
            } catch (Exception e) {
                rlDetail.setVisibility(View.GONE);
                phone = getIntent().getStringExtra("Phone_Number");
                tvName.setText(phone);
                tvName.setTextSize(24);
            }
            long timeStart = getIntent().getLongExtra("Time_Start", 0);
            long timeEnd = getIntent().getLongExtra("Time_End", 0);
            long second = timeEnd - timeStart;
            Date dateStart = new Date(timeStart);
            Date dateEnd = new Date(timeEnd);
            tvContentDetail.setText("Start at :     " + dateStart + "\n" +
                    "End at   :     " + dateEnd + "\n" +
                    "Length   :     " + convertTime((int) second));
        }
        tvDetail.setOnClickListener(this);
        tvCallBack.setOnClickListener(this);
        tvMessage.setOnClickListener(this);
        tvEdit.setOnClickListener(this);
    }

    private String convertTime(int miliSeconds) {
        int hrs = (int) TimeUnit.MILLISECONDS.toHours(miliSeconds) % 24;
        int min = (int) TimeUnit.MILLISECONDS.toMinutes(miliSeconds) % 60;
        int sec = (int) TimeUnit.MILLISECONDS.toSeconds(miliSeconds) % 60;
        return String.format("%02d:%02d:%02d", hrs, min, sec);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_detail:
                Intent intent = new Intent(this, DetailContactActivity.class);
                intent.putExtra("Info", infoStyle);
                startActivity(intent);
                break;
            case R.id.tv_call_back:
                Intent intent1 = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phone));
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                startActivity(intent1);
                break;
            case R.id.tv_message:
                if (infoStyle != null) {
                    MessageDialog dialog = new MessageDialog(this, infoStyle.getPhone(), infoStyle.getName());
                    dialog.show();
                } else {
                    MessageDialog dialog = new MessageDialog(this, phone, phone);
                    dialog.show();
                }
                break;
            case R.id.tv_edit:
                Intent intent2 = new Intent(this, EditNameActivity.class);
                if (infoStyle != null) {
                    intent2.putExtra("Style", infoStyle);
                } else {
                    intent2.putExtra("Main", "Abc");
                    Contact contact = new Contact(phone, phone);
                    intent2.putExtra("Contact", contact);
                }
                startActivity(intent2);
                break;
        }
    }


}

