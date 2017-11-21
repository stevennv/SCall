package com.example.admin.scall.dialog;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.TextView;

import com.astuetz.PagerSlidingTabStrip;
import com.example.admin.scall.R;
import com.example.admin.scall.fragment.FontFragment;
import com.example.admin.scall.fragment.PickColorFragment;

/**
 * Created by Admin on 11/21/2017.
 */

public class EditFontDialog extends AppCompatActivity {
    private String name;
    private PagerSlidingTabStrip tabs;
    private ViewPager viewPager;
    private TextView tvNamePreview;
    private EditText edtName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_edit_font);
        iniUI();
    }

    private void iniUI() {
        tabs = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        tabs.setShouldExpand(true);
        tabs.setIndicatorColor(0xffffffff);
        tabs.setIndicatorHeight(10);
        tabs.setBackgroundColor(0xff3F51B5);
        tabs.setTextColor(0xffffffff);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        ListPagerAdapter adapter = new ListPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        tabs.setViewPager(viewPager);
        tvNamePreview = (TextView) findViewById(R.id.tv_name_preview);
        edtName = (EditText) findViewById(R.id.edt_name);
        if (getIntent() != null) {
            name = getIntent().getStringExtra("Name");
            edtName.setText(name);
        }
    }

    private class ListPagerAdapter extends FragmentPagerAdapter {
        private final String[] TITLES = {getString(R.string.font), getString(R.string.color)};

        public ListPagerAdapter(FragmentManager fm) {
            super(fm);
        }


        @Override
        public CharSequence getPageTitle(int position) {
            return TITLES[position];
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = null;
            if (position == 0) {
                fragment = new FontFragment();
                return fragment;
            } else {
                fragment = new PickColorFragment();
                return fragment;
            }
        }

        @Override
        public int getCount() {
            return TITLES.length;
        }
    }
}
