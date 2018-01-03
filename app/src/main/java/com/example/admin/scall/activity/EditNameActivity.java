package com.example.admin.scall.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.admin.scall.R;
import com.example.admin.scall.adapter.EffectAdapter;
import com.example.admin.scall.adapter.FontAdapter;
import com.example.admin.scall.adapter.IconAdapter;
import com.example.admin.scall.adapter.SelectedAdapter;
import com.example.admin.scall.dialog.ConfirmDialog;
import com.example.admin.scall.dialog.ConfirmQuitDialog;
import com.example.admin.scall.model.Contact;
import com.example.admin.scall.model.InfoStyle;
import com.example.admin.scall.utils.DBManager;
import com.example.admin.scall.utils.SqliteHelper;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.gson.Gson;
import com.waynell.library.DropAnimationView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import yuku.ambilwarna.AmbilWarnaDialog;

public class EditNameActivity extends BaseActivity implements View.OnClickListener {
    private TextView tvName;
    private EditText edtName;
    private RecyclerView rvFont;
    private RelativeLayout rlColor;
    private CircleImageView civColor;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.LayoutManager layoutManagerEffect;
    private RecyclerView.LayoutManager layoutManagerSelected;
    private RecyclerView.LayoutManager layoutManagerIcon;
    private String[] list;
    private FontAdapter adapter;
    private int currentColor;
    private Toolbar toolbar;
    private TextView tvTitleToolbar;
    private ImageView imgToolbar;
    private SeekBar sbTextSize;
    private ImageView imgEffect;
    private AdView adView;
    private TextView tvChangeBackground;
    private RecyclerView rvListSelected;
    private RecyclerView rvListIcon;
    private CheckBox checkBox;
    private CheckBox checkFull;
    private int isFull = 1;
    //    private InfoStyle infoStyle;
    private String fontStyle;
    private int size = 24;
    private Contact contact;
    private Animation animation;
    private RecyclerView rvEffect;
    private int[] listEffect = {0, R.anim.bounce, R.anim.rotate, R.anim.custom_anim1, R.anim.pump_top, R.anim.fade_in, R.anim.grow_from_bottom};
    private EffectAdapter effectAdapter;
    //    private SqliteHelper db;
    private List<InfoStyle> list1 = new ArrayList<>();
    private InfoStyle infoStyle;
    private String imagePath;
    private TextView tvAnotherEffect;
    public static final int IMAGE_GALLERY = 1;
    public static final int IMAGE_CAMERA = 2;
    private int animation1;
    private IconAdapter adapterIcon;
    private SelectedAdapter adapterSelected;
    private int[] arraySelected;
    private List<Integer> listSelected = new ArrayList<>();
    private int[] listIcon = new int[70];
    private int posSelected;
    private DropAnimationView dropView;
    private TextView tvPreview;
    private String listIconString;
    private Gson gson;
    private boolean isAnother;
    private InfoStyle infoStyle2;
    private int[] listImage;
    private DBManager manager;
    private String name;
    private int id;
    private String phone;
//    private SqliteHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        setContentView(R.layout.activity_edit_name);
        MobileAds.initialize(this, getString(R.string.admob_app_id));
        iniUI();
    }

    protected void iniUI() {
//        db = new SqliteHelper(this);
        gson = new Gson();
        listIcon = new int[]{R.mipmap.ic_love, R.mipmap.apple_1_15, R.mipmap.apple_42, R.mipmap.bananas_48, R.mipmap.bananas_50,
                R.mipmap.cherry_2, R.mipmap.cherry_25, R.mipmap.coffee_cup_1_5, R.mipmap.coffee_cup_41, R.mipmap.cookie_1_6, R.mipmap.cookie_36,
                R.mipmap.corn_28, R.mipmap.cupcake_30, R.mipmap.cupcake_47, R.mipmap.donut_37, R.mipmap.donut_49,
                R.mipmap.eggplant_26, R.mipmap.french_fries_19, R.mipmap.french_fries_40, R.mipmap.fried_egg_23, R.mipmap.grapes_31,
                R.mipmap.hamburger_20, R.mipmap.hamburger_43, R.mipmap.hot_dog_8, R.mipmap.ice_cream_24, R.mipmap.ice_cream_38, R.mipmap.ketchup_11,
                R.mipmap.meatball_17, R.mipmap.milk_32, R.mipmap.milkshake_18, R.mipmap.mushroom_7, R.mipmap.onigiri_4, R.mipmap.onigiri_35,
                R.mipmap.orange_33, R.mipmap.pea_14, R.mipmap.peach_22, R.mipmap.peach_3, R.mipmap.pear_12, R.mipmap.piece_of_cake_1_9, R.mipmap.piece_of_cake_34,
                R.mipmap.pineapple_1_13, R.mipmap.pineapple_39, R.mipmap.pizza_29, R.mipmap.pizza_45, R.mipmap.popsicle_16, R.mipmap.pudding_1, R.mipmap.pudding_21,
                R.mipmap.strawberry_27, R.mipmap.strawberry_44, R.mipmap.taco_10, R.mipmap.taco_46,
                R.mipmap.emo1, R.mipmap.emo2, R.mipmap.emo3, R.mipmap.emo4, R.mipmap.emo5, R.mipmap.emo6, R.mipmap.emo7,
                R.mipmap.emo8, R.mipmap.emo9, R.mipmap.emo10, R.mipmap.emo11, R.mipmap.emo12, R.mipmap.emo13, R.mipmap.emo14, R.mipmap.emo15,
                R.mipmap.emo16, R.mipmap.emo17, R.mipmap.emo18, R.mipmap.emo19, R.mipmap.emo20, R.mipmap.emo21, R.mipmap.emo22, R.mipmap.emo23,
                R.mipmap.emo24, R.mipmap.emo25, R.mipmap.emo26, R.mipmap.emo27, R.mipmap.emo28, R.mipmap.emo29, R.mipmap.emo30, R.mipmap.emo31, R.mipmap.emo32,
                R.mipmap.emo33, R.mipmap.emo34, R.mipmap.emo35, R.mipmap.emo36, R.mipmap.emo37, R.mipmap.emo38, R.mipmap.emo39};
        animation = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.bounce);
        currentColor = ContextCompat.getColor(this, R.color.black);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(R.mipmap.ic_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        sbTextSize = findViewById(R.id.sb_text_size);
        tvTitleToolbar = findViewById(R.id.tv_title_toolbar_2);
        dropView = findViewById(R.id.drop_animation_view);
        tvPreview = findViewById(R.id.tv_preview);
        tvTitleToolbar.setText(getString(R.string.make_your_style));
        imgToolbar = findViewById(R.id.img_menu_toolbar);
        imgToolbar.setImageResource(R.mipmap.ic_save);
        tvName = findViewById(R.id.tv_name);
        edtName = findViewById(R.id.edt_name);
        rvFont = findViewById(R.id.rv_font);
        rvEffect = findViewById(R.id.rv_effect);
        rvListIcon = findViewById(R.id.rv_list_icon);
        rvListSelected = findViewById(R.id.rv_list_selected);
        rlColor = findViewById(R.id.rl_color);
        civColor = findViewById(R.id.civ_color);
        imgEffect = findViewById(R.id.img_effect);
        tvAnotherEffect = findViewById(R.id.tv_another_effect);
        checkBox = findViewById(R.id.checkbox);
        checkFull = findViewById(R.id.check_full);
        adView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);
        tvChangeBackground = findViewById(R.id.tv_change_background);
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        layoutManagerSelected = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        layoutManagerIcon = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        layoutManagerEffect = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rvFont.setLayoutManager(layoutManager);
        rvListIcon.setLayoutManager(layoutManagerIcon);
        rvListSelected.setLayoutManager(layoutManagerSelected);
        rvEffect.setLayoutManager(layoutManagerEffect);
        tvName.measure(0, 0);
        checkFull.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    isFull = 2;
                    imgEffect.setScaleType(ImageView.ScaleType.FIT_XY);
                } else {
                    isFull = 1;
                    imgEffect.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                }
            }
        });
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                isAnother = b;
            }
        });
        if (getIntent() != null) {
            try {
                infoStyle = (InfoStyle) getIntent().getSerializableExtra("Style");
                id = infoStyle.getId();
                phone = infoStyle.getPhone();
                name = infoStyle.getName();
                tvName.setText(infoStyle.getName());
                edtName.setText(infoStyle.getName());
                if (infoStyle.getFont() != null) {
                    Typeface font = Typeface.createFromAsset(getAssets(), "fonts/" + infoStyle.getFont());
                    tvName.setTypeface(font);
                    fontStyle = infoStyle.getFont();
                }
                if (infoStyle.getUrlImage() != null) {
                    imagePath = infoStyle.getUrlImage();
                    Glide.with(EditNameActivity.this).load(infoStyle.getUrlImage()).into(imgEffect);
                }
                tvName.setTextSize(infoStyle.getSize());
                tvName.setTextColor(infoStyle.getColor());
                sbTextSize.setProgress(infoStyle.getSize());
                edtName.setText(infoStyle.getName());
                if (infoStyle.getAnimation() != 0) {
                    Animation animation = AnimationUtils.loadAnimation(EditNameActivity.this, infoStyle.getAnimation());
                    tvName.startAnimation(animation);
                    animation1 = infoStyle.getAnimation();
                }
                if (infoStyle.getListIcon() != null) {
                    listImage = gson.fromJson(infoStyle.getListIcon(), int[].class);
                    dropView.setDrawables(listImage);
                    dropView.startAnimation();
                    listIconString = infoStyle.getListIcon();
                }
                if (infoStyle.getIsFull() == 1) {
                    imgEffect.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                } else {
                    imgEffect.setScaleType(ImageView.ScaleType.FIT_XY);
                }

//                imagePath = infoStyleList.get
            } catch (Exception e) {

                contact = (Contact) getIntent().getSerializableExtra("Contact");
                name = contact.getName();
                id = contact.getId();
                phone = contact.getPhoneNumber();
                tvName.setText(contact.getName());
                size = 24;
                edtName.setText(contact.getName());
                tvName.setTextSize(24);
                sbTextSize.setProgress(24);
                Log.e("iniUI: ", "iniUI: " + e.getMessage());
            }
        }
        AssetManager assetManager = getAssets();
        try {
            list = assetManager.list("fonts");
        } catch (IOException e) {
            e.printStackTrace();
        }
        adapter = new FontAdapter(this, list, new FontAdapter.clickItem() {
            @Override
            public void click(String value) {
                fontStyle = value;
                Typeface font = Typeface.createFromAsset(getAssets(), "fonts/" + value);
                Log.d("click:", "click: " + value);
                tvName.setTypeface(font);
            }
        }, name);
        effectAdapter = new EffectAdapter(this, listEffect, new EffectAdapter.clickItem() {
            @Override
            public void click(int value, int pos) {
                if (pos == 0) {
                    if (animation1 != 0) {
                        tvName.clearAnimation();
                        animation1 = 0;
                    }
                } else {
                    if (animation != null) {
                        animation.cancel();
                        animation1 = value;
                        animation = AnimationUtils.loadAnimation(getApplicationContext(),
                                value);
                        tvName.startAnimation(animation);
                    } else {
                        animation1 = value;
                        animation = AnimationUtils.loadAnimation(getApplicationContext(),
                                value);
                        tvName.startAnimation(animation);
                    }
                }
            }
        });
        effectAdapter.notifyDataSetChanged();
        rvEffect.setAdapter(effectAdapter);
        adapter.notifyDataSetChanged();
        rvFont.setAdapter(adapter);
        rlColor.setOnClickListener(this);
        imgToolbar.setOnClickListener(this);
        tvChangeBackground.setOnClickListener(this);
        tvAnotherEffect.setOnClickListener(this);
        tvPreview.setOnClickListener(this);
        final Handler handler = new Handler();
        adapterIcon = new IconAdapter(this, listIcon, new IconAdapter.onClickItem() {
            @Override
            public void click(int values) {
                if (isAnother) {
                    if (listSelected.size() < 11) {
                        listSelected.add(values);
//                    arraySelected[posSelected] = values;
//                    adapterSelected.notifyDataSetChanged();
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        adapterSelected.notifyDataSetChanged();
                                    }
                                });
                            }
                        }).start();

//                    rvListSelected.setAdapter(adapterSelected);
                        posSelected++;

                    } else {
                        Toast.makeText(EditNameActivity.this, getString(R.string.max_choice), Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(EditNameActivity.this, getString(R.string.choice_another_effect), Toast.LENGTH_SHORT).show();
                }
            }
        });

        adapter.notifyDataSetChanged();

        adapterIcon.notifyDataSetChanged();
        rvListIcon.setAdapter(adapterIcon);

        adapterSelected = new SelectedAdapter(EditNameActivity.this, listSelected, new SelectedAdapter.onClick() {
            @Override
            public void click(int pos) {

            }
        });
        adapterSelected.notifyDataSetChanged();
        rvListSelected.setAdapter(adapterSelected);


        edtName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                tvName.setText(charSequence);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        sbTextSize.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                tvName.setTextSize(i);
                size = i;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_color:
                if (infoStyle != null) {
                    currentColor = infoStyle.getColor();
                }
                AmbilWarnaDialog dialog = new AmbilWarnaDialog(this, currentColor, true, new AmbilWarnaDialog.OnAmbilWarnaListener() {
                    @Override
                    public void onCancel(AmbilWarnaDialog dialog) {
                    }

                    @Override
                    public void onOk(AmbilWarnaDialog dialog, int color) {
                        currentColor = color;
                        civColor.setBackgroundColor(currentColor);
                        tvName.setTextColor(currentColor);
                    }
                });
                dialog.show();
                break;
            case R.id.img_menu_toolbar:

                if (isAnother) {
                    arraySelected = new int[listSelected.size()];
                    for (int i = 0; i < listSelected.size(); i++) {
                        arraySelected[i] = listSelected.get(i);
                    }
//                    if (!checkIcon(arraySelected)) {
                    listIconString = gson.toJson(arraySelected);
                    InfoStyle infoStyle = new InfoStyle(id, edtName.getText().toString(), formatNumber(phone),
                            fontStyle, imagePath, currentColor, size, animation1, listIconString, isFull);
                    saveAndUpdateStyle(infoStyle);
//                    }
                } else {
                    InfoStyle infoStyle = new InfoStyle(id, edtName.getText().toString(), formatNumber(phone),
                            fontStyle, imagePath, currentColor, size, animation1, listIconString, isFull);
                    saveAndUpdateStyle(infoStyle);
                }
                break;
            case R.id.tv_change_background:
                ConfirmQuitDialog dialog3 = new ConfirmQuitDialog(this, getString(R.string.choose_picture), getString(R.string.gallery),
                        getString(R.string.camera), new ConfirmQuitDialog.clickBtn1() {
                    @Override
                    public void click() {
                        Intent intent = new Intent(EditNameActivity.this, ImagePickerActivity.class);
                        intent.putExtra(ImagePickerActivity.EXTRA_TYPE_PICKER,
                                ImagePickerActivity.GALLERY_PICKER);
                        intent.putExtra(ImagePickerActivity.MODE, ImagePickerActivity.Mode.CROP);
                        startActivityForResult(intent, IMAGE_GALLERY);
                    }
                }, new ConfirmQuitDialog.clickBtn2() {
                    @Override
                    public void click() {
                        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(cameraIntent, IMAGE_CAMERA);
                    }
                });
                dialog3.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog3.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                dialog3.setCancelable(true);
                dialog3.show();
                break;
            case R.id.tv_another_effect:
                if (isAnother) {
                    arraySelected = new int[listSelected.size()];
                    for (int i = 0; i < listSelected.size(); i++) {
                        arraySelected[i] = listSelected.get(i);
                    }
                    dropView.setDrawables(arraySelected);
                    dropView.startAnimation();
                    dropView.setScrollBarFadeDuration(3000);
                }
                break;
            case R.id.tv_preview:
                if (isAnother) {
                    arraySelected = new int[listSelected.size()];
                    for (int i = 0; i < listSelected.size(); i++) {
                        arraySelected[i] = listSelected.get(i);
                    }
//                    if (!checkIcon(arraySelected)) {
                    listIconString = gson.toJson(arraySelected);
                    infoStyle2 = new InfoStyle(id, edtName.getText().toString(), phone, fontStyle,
                            imagePath, currentColor, size, animation1, listIconString, isFull);
//                    } else {
//                        Toast.makeText(this, "Failure", Toast.LENGTH_SHORT).show();
//                    }
                } else {
                    infoStyle2 = new InfoStyle(id, edtName.getText().toString(), phone, fontStyle,
                            imagePath, currentColor, size, animation1, listIconString, isFull);
                }
                Intent i = new Intent(this, DetailContactActivity.class);
                i.putExtra("Main", "Abc");
                i.putExtra("InfoStyle", infoStyle2);
                startActivity(i);

                break;
        }
    }

    private void saveAndUpdateStyle(InfoStyle info) {
        try {
            db.getStyleById(String.valueOf(id));
            db.updateStyle(info);
            Log.d("saveAndUpdateStyle: ", "saveAndUpdateStyle: " + db.updateStyle(info));
            Toast.makeText(this, "Update", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            db.addStyle(info);
            Toast.makeText(this, "Add", Toast.LENGTH_SHORT).show();
        }
    }

    private String formatNumber(String number) {
        String result1 = number.replace("-", "");
        String result2 = result1.replace("(", "");
        String result3 = result2.replace(")", "");
        String result4 = result3.replace("+84", "0");
        String result = result4.replace(" ", "");
        return result;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMAGE_GALLERY) {
            ImagePickerActivity.ImageReceiver receiver = new ImagePickerActivity.ImageReceiver(data);
            imagePath = receiver.getCroppedPath();
            Glide.with(this).load(imagePath).into(imgEffect);
        } else if (requestCode == IMAGE_CAMERA) {
            if (data.getData() == null) {
                Bitmap photo = (Bitmap) data.getExtras().get("data");
                if (photo != null) {
                    final File file = savebitmap(photo);
                    imagePath = file.getPath();
                    Glide.with(this).load(imagePath).into(imgEffect);
                }
            }
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


}


