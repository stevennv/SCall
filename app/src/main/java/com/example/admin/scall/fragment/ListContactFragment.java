package com.example.admin.scall.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import com.example.admin.scall.R;
import com.example.admin.scall.adapter.ContactAdapter;
import com.example.admin.scall.model.Contact;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 12/8/2017.
 */

public class ListContactFragment extends Fragment {
    private ImageButton ibSearch;
    private EditText edtSearch;
    private RecyclerView rvContact;
    private RelativeLayout rlSearch;
    private RecyclerView.LayoutManager layoutManager;
    private ContactAdapter adapter;
    private List<Contact> list;
    private Gson gson;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_contact, container, false);
        gson = new Gson();
        ibSearch = view.findViewById(R.id.ib_search);
        edtSearch = view.findViewById(R.id.edt_search);
        rvContact = view.findViewById(R.id.rv_contact);
        rlSearch = view.findViewById(R.id.rl_search);
        rlSearch.setVisibility(View.VISIBLE);
        layoutManager = new LinearLayoutManager(getContext());
        rvContact.setLayoutManager(layoutManager);
        if (getArguments() != null) {
            String content = getArguments().getString("ListContact");
            Type listType = new TypeToken<ArrayList<Contact>>() {
            }.getType();
//            String venueStringRecvFromFragArg = getArguments().getString("VENUES");
            list = new Gson().fromJson(content, listType);
            adapter = new ContactAdapter(getContext(), list);
            adapter.notifyDataSetChanged();
            rvContact.setAdapter(adapter);
        }
        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                adapter.filter(edtSearch.getText().toString());
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        return view;
    }
}
