package com.example.handsonfirebase;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

class RecyclerAdapter extends FirebaseRecyclerAdapter<loginhelperclass, RecyclerAdapter.ViewHolder> {

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView name, email, phoneNo;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.UserName);
            email = itemView.findViewById(R.id.UserEmail);
            phoneNo = itemView.findViewById(R.id.UserPhoneNo);
        }
    }

    public RecyclerAdapter(@NonNull FirebaseRecyclerOptions<loginhelperclass> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull loginhelperclass model) {
        holder.name.setText(model.getUserName());
        holder.email.setText(model.getUserEmail());
        holder.phoneNo.setText(model.getPhoneNo());
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.profile_design, parent, false);
        return new ViewHolder(view);
    }
}
