package com.steven.admin.scall.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.steven.admin.scall.R;

/**
 * Created by Admin on 11/23/2017.
 */

public class EffectAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private int[] list;
    private clickItem clickItem;

    public EffectAdapter(Context context, int[] list, clickItem clickItem) {
        this.context = context;
        this.list = list;
        this.clickItem = clickItem;
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
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        MyViewHolder myViewHolder = (MyViewHolder) holder;
        final int effect = list[position];
        myViewHolder.img.setVisibility(View.GONE);
        if (position == 0) {
            myViewHolder.tvFont.setText(context.getString(R.string.cancel));
        } else {
            myViewHolder.tvFont.setText((position + 1) + "");
        }

        myViewHolder.llItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickItem.click(effect, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.length;
    }

    public interface clickItem {
        void click(int value, int pos);
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvFont;
        public LinearLayout llItem;
        public ImageView img;

        public MyViewHolder(View itemView) {
            super(itemView);
            tvFont = itemView.findViewById(R.id.tv_name);
            llItem = itemView.findViewById(R.id.ll_item);
            img = itemView.findViewById(R.id.img);
        }
    }
}
