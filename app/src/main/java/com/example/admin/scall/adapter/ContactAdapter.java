package com.example.admin.scall.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.example.admin.scall.R;
import com.example.admin.scall.activity.DetailContactActivity;
import com.example.admin.scall.activity.EditNameActivity;
import com.example.admin.scall.model.Contact;
import com.example.admin.scall.model.InfoStyle;
import com.example.admin.scall.utils.SqliteHelper;
import com.example.admin.scall.utils.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by Admin on 11/21/2017.
 */

public class ContactAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements SectionIndexer {
    private Context context;
    private List<Contact> list;
    private SqliteHelper db;
    private ArrayList<Contact> arraylist;
    private ArrayList<Integer> mSectionPositions;
    public ContactAdapter(Context context, List<Contact> list) {
        this.context = context;
        this.list = list;
        this.arraylist = new ArrayList<Contact>();
        this.arraylist.addAll(list);
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_contact, parent, false);
        viewHolder = new MyViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        db = new SqliteHelper(context);
        MyViewHolder myViewHolder = (MyViewHolder) holder;
        final Contact contact = list.get(position);
        myViewHolder.tvName.setText(contact.getName() + "\n" + "\t \t" + contact.getPhoneNumber());
        myViewHolder.llItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, EditNameActivity.class);
                intent.putExtra("Main", "Abc");
                intent.putExtra("Contact", contact);
                String phoneFormat = Utils.formatNumber(contact.getPhoneNumber());
                try {
                    InfoStyle infoStyle = db.getStyleByPhone(phoneFormat);
                    intent.putExtra("Style", infoStyle);
                } catch (Exception e) {
                    Log.d("onClick:", "onClick: " + e.getMessage());
                }
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public Object[] getSections() {
        List<String> sections = new ArrayList<>(26);
        mSectionPositions = new ArrayList<>(26);
        for (int i = 0, size = list.size(); i < size; i++) {
            String section = String.valueOf(list.get(i).getName().charAt(0)).toUpperCase();
            if (!sections.contains(section)) {
                sections.add(section);
                mSectionPositions.add(i);
            }
        }
        return sections.toArray(new String[0]);
    }

    @Override
    public int getPositionForSection(int i) {
        return mSectionPositions.get(i);
    }

    @Override
    public int getSectionForPosition(int i) {
        return 0;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout llItem;
        public TextView tvName;

        public MyViewHolder(View itemView) {
            super(itemView);
            llItem = itemView.findViewById(R.id.ll_item);
            tvName = itemView.findViewById(R.id.tv_name);
        }
    }

    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        list.clear();
        if (charText.length() == 0) {
            list.addAll(arraylist);
        } else {
            for (Contact wp : arraylist) {
                String[] listSearch = wp.getName().split(" ");
                for (int i = 0; i < listSearch.length; i++) {
                    if (listSearch[i].toLowerCase(Locale.getDefault()).contains(charText)) {
                        list.add(wp);
                        break;
                    }
                }
            }
        }
        notifyDataSetChanged();
    }
}
