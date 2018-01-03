package com.example.admin.scall.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.admin.scall.R;

import java.util.List;

/**
 * Created by Admin on 11/30/2017.
 */

public class SelectedAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<Integer> list;
    private onClick onClick;

    public SelectedAdapter(Context context, List<Integer> list, onClick onClick) {
        this.context = context;
        this.list = list;
        this.onClick = onClick;
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
        final int valuesIcon = list.get(position);
        myViewHolder.imgIcon.setImageResource(valuesIcon);
        myViewHolder.imgIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                list.remove(position);
                notifyDataSetChanged();
                onClick.click(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (list != null) {
            return list.size();
        } else {
            return 0;
        }
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView imgIcon;

        public MyViewHolder(View itemView) {
            super(itemView);
            imgIcon = (ImageView) itemView.findViewById(R.id.img_icon);
        }
    }

    public interface onClick {
        void click(int pos);
    }
}
