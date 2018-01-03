package com.example.admin.scall.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.example.admin.scall.R;
import com.example.admin.scall.activity.EditNameActivity;
import com.example.admin.scall.model.InfoStyle;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 12/8/2017.
 */

public class InfoStyleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements SectionIndexer {
    private Context context;
    private List<InfoStyle> list;
    private ArrayList<Integer> mSectionPositions;
    public InfoStyleAdapter(Context context, List<InfoStyle> list) {
        this.context = context;
        this.list = list;
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
        MyViewHolder myViewHolder = (MyViewHolder) holder;
        final InfoStyle infoStyle = list.get(position);
        myViewHolder.tvName.setText(infoStyle.getName() + "\n" + "\t \t" + infoStyle.getPhone());
        if (infoStyle.getFont() != null) {
            Typeface typeface = Typeface.createFromAsset(context.getAssets(), "fonts/" + infoStyle.getFont());
            myViewHolder.tvName.setTypeface(typeface);
        }
        myViewHolder.llItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, EditNameActivity.class);
                intent.putExtra("Style", infoStyle);
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
}
