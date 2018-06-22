package com.example.hella.proxym;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.hella.proxym.Util.ItemClickListener;


public class ListOnlineViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView txt_email;
    public ItemClickListener itemClickListener;


    public ListOnlineViewHolder(View itemView) {
        super(itemView);
        txt_email = (TextView) itemView.findViewById(R.id.txt_email);
        itemView.setOnClickListener(this);
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }


    @Override
    public void onClick(View view) {
        itemClickListener.onClick(view, getAdapterPosition());
    }
}

