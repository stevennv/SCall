package com.example.admin.scall.activity;

import android.Manifest;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.astuetz.PagerSlidingTabStrip;
import com.example.admin.scall.R;
import com.example.admin.scall.adapter.ContactAdapter;
import com.example.admin.scall.dialog.ConfirmQuitDialog;
import com.example.admin.scall.fragment.ListContactFragment;
import com.example.admin.scall.fragment.ListCustomFragment;
import com.example.admin.scall.model.Contact;
import com.example.admin.scall.model.InfoStyle;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.gson.Gson;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends BaseActivity {
    private Toolbar toolbar;
    private TextView tvTitleToolbar;
    private List<Contact> list = new ArrayList<>();
    private List<InfoStyle> list1 = new ArrayList<>();
    private static final int RECORD_REQUEST_CODE = 101;
    private AdView adView;
    private ConfirmQuitDialog dialog;
    private PagerSlidingTabStrip tabs;
    private ViewPager viewPager;
    private Gson gson;
    private SwipeRefreshLayout refreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MobileAds.initialize(this, getString(R.string.admob_app_id));
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.READ_CONTACTS, Manifest.permission.READ_PHONE_STATE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE},
                RECORD_REQUEST_CODE);
        iniUI();
    }

    private void iniUI() {
        gson = new Gson();
//        toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        tvTitleToolbar = findViewById(R.id.tv_title_toolbar);
//        tvTitleToolbar.setText(getString(R.string.app_name));
        adView = (AdView) findViewById(R.id.adView);
        refreshLayout = findViewById(R.id.refresh);
        tabs = findViewById(R.id.tabs);
        tabs.setShouldExpand(true);
        tabs.setIndicatorColor(0xffffffff);
        tabs.setIndicatorHeight(10);
        tabs.setBackgroundColor(0xff4a2a71);
        tabs.setTextColor(0xffffffff);
        viewPager = findViewById(R.id.viewpager);

        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getContactList();
                list1 = db.getAllStyle();
                ListPagerAdapter adapter = new ListPagerAdapter(getSupportFragmentManager());
                viewPager.setAdapter(adapter);
                refreshLayout.setRefreshing(false);
            }
        });
    }

    private void getContactList() {
        ContentResolver cr = getContentResolver();
        Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI,
                null, null, null, Phone.DISPLAY_NAME + " ASC");
        if ((cur != null ? cur.getCount() : 0) > 0) {
            while (cur != null && cur.moveToNext()) {
                String id = cur.getString(
                        cur.getColumnIndex(ContactsContract.Contacts._ID));
                String name = cur.getString(cur.getColumnIndex(
                        ContactsContract.Contacts.DISPLAY_NAME));
                int id1 = cur.getInt(cur.getColumnIndex(ContactsContract.Contacts._ID));
                Log.d("getContactList: ", "getContactList: " + id1);
                if (cur.getInt(cur.getColumnIndex(
                        ContactsContract.Contacts.HAS_PHONE_NUMBER)) > 0) {
                    Cursor pCur = cr.query(
                            Phone.CONTENT_URI,
                            null,
                            Phone.CONTACT_ID + " = ?",
                            new String[]{id}, null);
                    while (pCur.moveToNext()) {
                        String phoneNo = pCur.getString(pCur.getColumnIndex(
                                Phone.NUMBER));
                        Log.i("GET_INFO", "Name: " + name);
                        Log.i("GET_INFO", "Phone Number: " + phoneNo);
                        Contact contact = new Contact(id1, phoneNo, name);
                        list.add(contact);
                    }
                    pCur.close();
                }
            }
        }
        if (cur != null) {
            cur.close();
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case RECORD_REQUEST_CODE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getContactList();
                    list1 = db.getAllStyle();
                    ListPagerAdapter adapter = new ListPagerAdapter(getSupportFragmentManager());
                    viewPager.setAdapter(adapter);
                    tabs.setViewPager(viewPager);
                    return;
                }
            }
        }
    }


    @Override
    public void onBackPressed() {
        dialog = new ConfirmQuitDialog(this, getString(R.string.confirm_quit), getString(R.string.ok),
                getString(R.string.cancel), new ConfirmQuitDialog.clickBtn1() {
            @Override
            public void click() {
                int pid = android.os.Process.myPid();
                android.os.Process.killProcess(pid);
            }
        }, new ConfirmQuitDialog.clickBtn2() {
            @Override
            public void click() {
                dialog.dismiss();
            }
        });
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setCancelable(true);
        dialog.show();
    }


    private class ListPagerAdapter extends FragmentPagerAdapter {

        private final String[] TITLES = {"Contact", "MyStyle"};

        public ListPagerAdapter(FragmentManager fm) {
            super(fm);
        }


        @Override
        public CharSequence getPageTitle(int position) {
            return TITLES[position];
        }

        @Override
        public Fragment getItem(int position) {
            Fragment listSongFragment = null;

            if (position == 0) {
                listSongFragment = new ListContactFragment();
                Bundle bundle = new Bundle();
                bundle.putString("ListContact", gson.toJson(list));
                String content = gson.toJson(list);
                listSongFragment.setArguments(bundle);
            } else {
                listSongFragment = new ListCustomFragment();
                Bundle bundle = new Bundle();
                bundle.putString("ListStyle", gson.toJson(list1));
                String content = gson.toJson(list);
                listSongFragment.setArguments(bundle);
            }
            return listSongFragment;
        }

        @Override
        public int getCount() {
            return TITLES.length;
        }
    }


}
