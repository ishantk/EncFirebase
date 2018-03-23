package com.auribises.encfirebase.holder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.auribises.encfirebase.R;
import com.auribises.encfirebase.listener.OnRecyclerItemClickListener;

/**
 * Created by ishantkumar on 23/03/18.
 */

public class UserHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    public TextView txtName;
    public TextView txtEmail;

    OnRecyclerItemClickListener recyclerItemClickListener = null;

    public void setOnRecyclerItemClickListener(OnRecyclerItemClickListener recyclerItemClickListener){
        this.recyclerItemClickListener = recyclerItemClickListener;
    }

    public UserHolder(@NonNull View itemView) {
        super(itemView);

        txtName = itemView.findViewById(R.id.textViewName);
        txtEmail = itemView.findViewById(R.id.textViewEmail);

        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        recyclerItemClickListener.onRecyclerItemClicked();

    }
}
