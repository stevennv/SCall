package com.steven.admin.scall.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.steven.admin.scall.R;

/**
 * Created by Admin on 11/30/2017.
 */

public class IconAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private int[] listIcon;
    private onClickItem onClickItem;

    public IconAdapter(Context context, int[] listIcon, onClickItem onClickItem) {
        this.context = context;
        this.listIcon = listIcon;
        this.onClickItem = onClickItem;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_icon, parent, false);
        viewHolder = new MyViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        MyViewHolder myViewHolder = (MyViewHolder) holder;
        final int valuesIcon = listIcon[position];
        myViewHolder.imgIcon.setImageResource(valuesIcon);
        myViewHolder.imgIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickItem.click(valuesIcon);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listIcon.length;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView imgIcon;

        public MyViewHolder(View itemView) {
            super(itemView);
            imgIcon = (ImageView) itemView.findViewById(R.id.img_icon);
        }
    }

    public interface onClickItem {
        void click(int values);
    }
}
