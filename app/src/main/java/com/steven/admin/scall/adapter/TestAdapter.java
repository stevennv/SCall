package com.steven.admin.scall.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.steven.admin.scall.R;
import com.steven.admin.scall.model.InfoStyle;

import java.util.List;

/**
 * Created by Admin on 11/24/2017.
 */

public class TestAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<InfoStyle> list;

    public TestAdapter(Context context, List<InfoStyle> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_font, parent, false);
        viewHolder = new Example(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Example example = (Example) holder;
        InfoStyle style = list.get(position);
        example.tvFont.setText(style.getFont());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class Example extends RecyclerView.ViewHolder {
        public TextView tvFont;
        public LinearLayout llItem;

        public Example(View itemView) {
            super(itemView);
            tvFont = itemView.findViewById(R.id.tv_name);
            llItem = itemView.findViewById(R.id.ll_item);
        }
    }
}
