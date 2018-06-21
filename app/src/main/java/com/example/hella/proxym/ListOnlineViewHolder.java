package com.example.hella.proxym;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;


public class ListOnlineViewHolder extends RecyclerView.ViewHolder {

    public TextView txt_email;


    public ListOnlineViewHolder(View itemView) {
        super(itemView);
        txt_email=(TextView) itemView.findViewById(R.id.txt_email);
    }
}
