package com.steven.admin.scall.activity;

import android.Manifest;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.astuetz.PagerSlidingTabStrip;
import com.steven.admin.scall.R;
import com.steven.admin.scall.dialog.ConfirmQuitDialog;
import com.steven.admin.scall.fragment.ListContactFragment;
import com.steven.admin.scall.fragment.ListCustomFragment;
import com.steven.admin.scall.model.Contact;
import com.steven.admin.scall.model.InfoStyle;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.gson.Gson;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.READ_CONTACTS;
import static android.Manifest.permission.READ_PHONE_STATE;
import static android.Manifest.permission.SEND_SMS;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

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
    //    private SwipeRefreshLayout refreshLayout;
    boolean permissionReadContact, permissionReadPhoneState,
            permissionWriteData,
            permissionSendSMS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.READ_CONTACTS
//                        , Manifest.permission.READ_PHONE_STATE,
//                        Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.SEND_SMS, Manifest.permission.PROCESS_OUTGOING_CALLS
                },
                RECORD_REQUEST_CODE);
        MobileAds.initialize(this, getString(R.string.admob_app_id));
        setContentView(R.layout.activity_main);


        iniUI();
    }

    private void iniUI() {
        gson = new Gson();
        List<InfoStyle> list2 = db.getAllStyle();
        for (int i = 0; i < list2.size(); i++) {
            Log.e("iniUI: ", "iniUI: " + list2.get(i).getId());
        }
        adView = (AdView) findViewById(R.id.adView);
//        adView.setAdUnitId(getString(R.string.admob_app_id));
//        adView.loadAd(adRequest);
//        refreshLayout = findViewById(R.id.refresh);
        tabs = findViewById(R.id.tabs);
        tabs.setShouldExpand(true);
        tabs.setIndicatorColor(0xff2ac119);
        tabs.setIndicatorHeight(10);
        tabs.setBackgroundColor(0xff4a2a71);
        tabs.setTextColor(0xff2ac119);
        viewPager = findViewById(R.id.viewpager);

        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);
//        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                getContactList();
//                list1 = db.getAllStyle();
//                ListPagerAdapter adapter = new ListPagerAdapter(getSupportFragmentManager());
//                viewPager.setAdapter(adapter);
//                refreshLayout.setRefreshing(false);
//            }
//        });
        File rootPath = new File(Environment.getExternalStorageDirectory(), "Scall");
        if (!rootPath.exists()) {
            rootPath.mkdirs();
            Toast.makeText(this, "Create Success", Toast.LENGTH_SHORT).show();
        }
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
                        Contact contact = new Contact(name, phoneNo);
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
                if (grantResults.length > 0) {
                    permissionReadContact = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    if (permissionReadContact) {
                        getContactList();
                        list1 = db.getAllStyle();
                        ListPagerAdapter adapter = new ListPagerAdapter(getSupportFragmentManager());
                        viewPager.setAdapter(adapter);
                        tabs.setViewPager(viewPager);
                    } else {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            if (shouldShowRequestPermissionRationale(READ_CONTACTS)) {
                                showMessageOKCancel("You need to allow access to both the permissions",
                                        new ConfirmQuitDialog.clickBtn1() {
                                            @Override
                                            public void click() {
                                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                    requestPermissions(new String[]{READ_CONTACTS},
                                                            RECORD_REQUEST_CODE);
                                                }
                                            }
                                        });
                                return;
                            }
                        }
                    }

//                    return;
                }
//                if (grantResults.length > 0) {
//                    permissionReadPhoneState = grantResults[0] == PackageManager.PERMISSION_GRANTED;
//
//                    if (permissionReadPhoneState) {
//                    } else {
//                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                            if (shouldShowRequestPermissionRationale(READ_PHONE_STATE)) {
//                                showMessageOKCancel("You need to allow access to both the permissions",
//                                        new ConfirmQuitDialog.clickBtn1() {
//                                            @Override
//                                            public void click() {
//                                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                                                    requestPermissions(new String[]{READ_PHONE_STATE},
//                                                            RECORD_REQUEST_CODE);
//                                                }
//                                            }
//                                        });
//                                return;
//                            }
//                        }
//                    }
//
////                    return;
//                }
//                if (grantResults.length > 0) {
//                    permissionWriteData = grantResults[0] == PackageManager.PERMISSION_GRANTED;
//
//                    if (permissionWriteData) {
//                    } else {
//                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                            if (shouldShowRequestPermissionRationale(WRITE_EXTERNAL_STORAGE)) {
//                                showMessageOKCancel("You need to allow access to both the permissions",
//                                        new ConfirmQuitDialog.clickBtn1() {
//                                            @Override
//                                            public void click() {
//                                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                                                    requestPermissions(new String[]{WRITE_EXTERNAL_STORAGE},
//                                                            RECORD_REQUEST_CODE);
//                                                }
//                                            }
//                                        });
//                                return;
//                            }
//                        }
//                    }
//
////                    return;
//                }
//                if (grantResults.length > 0) {
//                    permissionSendSMS = grantResults[0] == PackageManager.PERMISSION_GRANTED;
//
//                    if (permissionSendSMS) {
//                    } else {
//                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                            if (shouldShowRequestPermissionRationale(SEND_SMS)) {
//                                showMessageOKCancel("You need to allow access to both the permissions",
//                                        new ConfirmQuitDialog.clickBtn1() {
//                                            @Override
//                                            public void click() {
//                                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                                                    requestPermissions(new String[]{SEND_SMS},
//                                                            RECORD_REQUEST_CODE);
//                                                }
//                                            }
//                                        });
//                                return;
//                            }
//                        }
//                    }
//
////                    return;
//                }
//                if (permissionReadContact == true && permissionReadPhoneState == true && permissionSendSMS == true && permissionWriteData == true) {
//                    getContactList();
//                    list1 = db.getAllStyle();
//                    ListPagerAdapter adapter = new ListPagerAdapter(getSupportFragmentManager());
//                    viewPager.setAdapter(adapter);
//                    tabs.setViewPager(viewPager);
//                }
            }
        }
    }


    @Override
    public void onBackPressed() {
        dialog = new ConfirmQuitDialog(this, getString(R.string.confirm_quit), getString(R.string.ok),
                getString(R.string.cancel), new ConfirmQuitDialog.clickBtn1() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void click() {
                finishAffinity();
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

    private void showMessageOKCancel(String message, ConfirmQuitDialog.clickBtn1 okListener) {
        ConfirmQuitDialog dialog = new ConfirmQuitDialog(this, "You need to allow access to the permissions", "Ok", "Cancel"
                , okListener, new ConfirmQuitDialog.clickBtn2() {
            @Override
            public void click() {

            }
        });
        dialog.show();
//        new AlertDialog.Builder(MainActivity.this)
//                .setMessage(message)
//                .setPositiveButton("OK", okListener)
//                .setNegativeButton("Cancel", null)
//                .create()
//                .show();
    }

}
