package com.steven.admin.scall.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.internal.telephony.ITelephony;
import com.bumptech.glide.Glide;
import com.steven.admin.scall.R;
import com.steven.admin.scall.model.Contact;
import com.steven.admin.scall.model.InfoStyle;
import com.google.gson.Gson;
import com.hanks.htextview.rainbow.RainbowTextView;
import com.waynell.library.DropAnimationView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.util.Date;

public class DetailContactActivity extends AppCompatActivity implements View.OnClickListener {
    private Contact contact;
    public RainbowTextView tvPreview;
    private TextView tvName;
    private TextView tvPhoneNumber;
    private ImageView imgEffect;
    public static final int IMAGE_GALLERY = 1;
    public static final int IMAGE_CAMERA = 2;
    //    private SqliteHelper db;
    private InfoStyle infoStyle;
    private ImageView imgEndCall;
    private boolean isCalling;
    private int[] listImage = new int[10];
    private DropAnimationView dropView;
    private Gson gson;
    private ImageView imgAcceptCall;
    public static Activity myDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_detail_contact);
        iniUI();
    }

    private void iniUI() {

        gson = new Gson();
        myDialog = DetailContactActivity.this;
//        db = new SqliteHelper(this);
        tvName = findViewById(R.id.tv_name);
        tvPhoneNumber = findViewById(R.id.tv_phone_number);
        tvPreview = findViewById(R.id.tv_preview);
        imgEffect = findViewById(R.id.img_effect);
        imgEndCall = findViewById(R.id.img_reject_call);
        imgAcceptCall = findViewById(R.id.img_accept_call);
        dropView = findViewById(R.id.drop_animation_view);
        imgEndCall.setOnClickListener(this);
        imgEffect.setOnClickListener(this);
        imgAcceptCall.setOnClickListener(this);
        tvPreview.setOnClickListener(this);
        if (getIntent() != null) {
            String a = getIntent().getStringExtra("Main");
            if (a != null) {
                isCalling = true;
                infoStyle = (InfoStyle) getIntent().getSerializableExtra("InfoStyle");
                if (infoStyle.getFont() != null) {
                    Typeface font = Typeface.createFromAsset(getAssets(), "fonts/" + infoStyle.getFont());
                    tvName.setTypeface(font);
                    tvPhoneNumber.setTypeface(font);
                }
                tvName.setText(infoStyle.getName());
                tvName.setTextColor(infoStyle.getColor());
                tvName.setTextSize(infoStyle.getSize());
                if (infoStyle.getAnimation() != 0) {
                    Animation animation = AnimationUtils.loadAnimation(DetailContactActivity.this, infoStyle.getAnimation());
                    tvName.startAnimation(animation);
                }
                Glide.with(DetailContactActivity.this).load(infoStyle.getUrlImage()).into(imgEffect);
//                tvPreview.setVisibility(View.GONE);
                tvPhoneNumber.setText(infoStyle.getPhone() + "  " + getString(R.string.calling));
                tvPhoneNumber.setTextSize(24);
                tvPhoneNumber.setTextColor(infoStyle.getColor());
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
            } else {
                isCalling = false;
                InfoStyle infoStyle = (InfoStyle) getIntent().getSerializableExtra("Info");
                tvName.setText(infoStyle.getName());
                tvName.setTextColor(infoStyle.getColor());
                tvName.setTextSize(infoStyle.getSize());
                Glide.with(DetailContactActivity.this).load(infoStyle.getUrlImage()).into(imgEffect);
                tvPreview.setVisibility(View.GONE);
                tvPhoneNumber.setText(infoStyle.getPhone() + "  " + getString(R.string.calling));
                tvPhoneNumber.setTextSize(24);
                tvPhoneNumber.setTextColor(infoStyle.getColor());
                if (infoStyle.getFont() != null) {
                    Typeface font1 = Typeface.createFromAsset(getAssets(), "fonts/" + infoStyle.getFont());
                    tvName.setTypeface(font1);
                    tvPhoneNumber.setTypeface(font1);
                }
                if (infoStyle.getAnimation() != 0) {
                    Animation animation1 = AnimationUtils.loadAnimation(DetailContactActivity.this, infoStyle.getAnimation());
                    tvName.startAnimation(animation1);
                }
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
//                Uri uri = Uri.parse(infoStyle.getUrlImage());
//                imgEffect.setImageURI(uri);
//                imgEffect.setImageURI(infoStyle.getUrlImage());
                tvName.setClickable(false);
                imgEffect.setClickable(false);
                tvName.setEnabled(false);
                imgEffect.setEnabled(false);
            }
        }
        tvName.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_name:

                break;
            case R.id.img_effect:
                break;
            case R.id.img_reject_call:
                TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
                try {
                    Class c = Class.forName(tm.getClass().getName());
                    Method m = c.getDeclaredMethod("getITelephony");
                    m.setAccessible(true);
                    ITelephony telephonyService = (ITelephony) m.invoke(tm);
                    telephonyService.silenceRinger();
                    telephonyService.endCall();
                    int pid = android.os.Process.myPid();
                    android.os.Process.killProcess(pid);
                } catch (Exception e) {
                    Log.d("getITelephony", "onClick: " + e.getMessage());
                    e.printStackTrace();
                }
                finish();
                break;
            case R.id.img_accept_call:
//                acceptCall();
                finish();
                break;
            case R.id.tv_preview:
                Bitmap bitmap = takeScreenshot();
                saveBitmap(bitmap);
//                tvPreview.setVisibility(View.GONE);
//                takeScreenshot();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMAGE_GALLERY) {

        } else if (requestCode == IMAGE_CAMERA) {
            if (data.getData() == null) {
                Bitmap photo = (Bitmap) data.getExtras().get("data");
                if (photo != null) {
                    final File file = savebitmap(photo);
                    String mImagePath = file.getPath();
                    Glide.with(this).load(mImagePath).into(imgEffect);
                }
            }
        }
    }

    public void saveBitmap(Bitmap bitmap) {
        Date now = new Date();
        android.text.format.DateFormat.format("yyyy-MM-dd_hh:mm:ss", now);
        File imagePath = new File(Environment.getExternalStorageDirectory() + "/Scall/" + now + ".png");
        FileOutputStream fos;
        try {
            fos = new FileOutputStream(imagePath);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            Log.e("GREC", e.getMessage(), e);
        } catch (IOException e) {
            Log.e("GREC", e.getMessage(), e);
        }
    }

    public static File savebitmap(Bitmap bmp) {
        String extStorageDirectory = Environment.getExternalStorageDirectory().toString();
        OutputStream outStream = null;
        // String temp = null;
        File file = new File(extStorageDirectory, "temp.png");
        if (file.exists()) {
            file.delete();
            file = new File(extStorageDirectory, "temp.png");
        }

        try {
            outStream = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.PNG, 100, outStream);
            outStream.flush();
            outStream.close();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return file;
    }

    public Bitmap takeScreenshot() {
        View rootView = findViewById(android.R.id.content).getRootView();
        rootView.setDrawingCacheEnabled(true);
        return rootView.getDrawingCache();
    }


}
