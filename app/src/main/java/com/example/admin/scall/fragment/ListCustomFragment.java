package com.example.admin.scall.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.example.admin.scall.R;
import com.example.admin.scall.adapter.ContactAdapter;
import com.example.admin.scall.adapter.InfoStyleAdapter;
import com.example.admin.scall.model.Contact;
import com.example.admin.scall.model.InfoStyle;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import in.myinnos.alphabetsindexfastscrollrecycler.IndexFastScrollRecyclerView;

/**
 * Created by admin on 12/8/2017.
 */

public class ListCustomFragment extends Fragment {
    private IndexFastScrollRecyclerView rvCustom;
    private RecyclerView.LayoutManager layoutManager;
    private List<InfoStyle> list;
    private InfoStyleAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contact, container, false);
        rvCustom = view.findViewById(R.id.rv_contact);
        layoutManager = new LinearLayoutManager(getContext());
        rvCustom.setLayoutManager(layoutManager);
        rvCustom.setIndexBarTextColor("#2ac119");
        if (getArguments() != null) {
            String content = getArguments().getString("ListStyle");
            Type listType = new TypeToken<ArrayList<InfoStyle>>() {
            }.getType();
            list = new Gson().fromJson(content, listType);
            Collections.sort(list, new Comparator<InfoStyle>() {
                @Override
                public int compare(final InfoStyle object1, final InfoStyle object2) {
                    return object1.getName().compareTo(object2.getName());
                }
            });
            adapter = new InfoStyleAdapter(getContext(), list);
            adapter.notifyDataSetChanged();
            rvCustom.setAdapter(adapter);
        }
        return view;
    }
}
