package com.example.hella.proxym;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hella.proxym.Util.ItemClickListener;

public class ListQuestsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener  {

    public TextView txt_username;
    public ImageView quest_profile_pic;
    public ItemClickListener itemClickListener;

    public ListQuestsViewHolder(View itemView) {
        super(itemView);
        txt_username = (TextView) itemView.findViewById(R.id.txt_username);
        quest_profile_pic=(ImageView) itemView.findViewById(R.id.quest_profile_pic);
        itemView.setOnClickListener(this);
    }
    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View v) {
        itemClickListener.onClick(v, getAdapterPosition());
    }
}
