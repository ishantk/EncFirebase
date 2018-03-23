package com.auribises.encfirebase.adapter;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.auribises.encfirebase.R;
import com.auribises.encfirebase.holder.UserHolder;
import com.auribises.encfirebase.listener.OnRecyclerItemClickListener;
import com.auribises.encfirebase.listener.RecyclerAdapterClickListener;
import com.auribises.encfirebase.model.User;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

/**
 * Created by ishantkumar on 23/03/18.
 */

public class UserAdapter extends FirestoreRecyclerAdapter<User,UserHolder> implements OnRecyclerItemClickListener{

    RecyclerAdapterClickListener recyclerAdapterClickListener;

    public void setRecyclerAdapterClickListener(RecyclerAdapterClickListener recyclerAdapterClickListener){
        this.recyclerAdapterClickListener = recyclerAdapterClickListener;
    }

    public UserAdapter(FirestoreRecyclerOptions<User> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(UserHolder holder, int position, User model) {

        holder.txtName.setText(model.name);
        holder.txtEmail.setText(model.email);
    }

    @NonNull
    @Override
    public UserHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item,parent,false);

        UserHolder userHolder = new UserHolder(view);
        userHolder.setOnRecyclerItemClickListener(this);

        return userHolder;
    }

    // If any data shall be changed on server, this method will be executed
    @Override
    public void onDataChanged() {
        super.onDataChanged();

    }

    @Override
    public void onRecyclerItemClicked() {
        recyclerAdapterClickListener.onRecyclerAdapterClicked(0);
    }
}
