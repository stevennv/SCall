package com.steven.admin.scall.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.steven.admin.scall.R;

/**
 * Created by Admin on 11/22/2017.
 */

public class FontAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private String[] list;
    private clickItem clickItem;
    private String contentText;

    public FontAdapter(Context context, String[] list, clickItem clickItem, String contentText) {
        this.context = context;
        this.list = list;
        this.clickItem = clickItem;
        this.contentText = contentText;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_font, parent, false);
        viewHolder = new MyViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MyViewHolder myViewHolder = (MyViewHolder) holder;
        final String content = list[position];
        Typeface typeface = Typeface.createFromAsset(context.getAssets(), "fonts/" + content);
        myViewHolder.tvFont.setText(contentText + "\n" + content);
        myViewHolder.tvFont.setTypeface(typeface);
        myViewHolder.llItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickItem.click(content);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.length;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvFont;
        public LinearLayout llItem;

        public MyViewHolder(View itemView) {
            super(itemView);
            tvFont = itemView.findViewById(R.id.tv_name);
            llItem = itemView.findViewById(R.id.ll_item);
        }
    }

    public interface clickItem {
        void click(String value);
    }
}
