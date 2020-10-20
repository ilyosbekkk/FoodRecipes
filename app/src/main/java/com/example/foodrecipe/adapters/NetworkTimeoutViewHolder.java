package com.example.foodrecipe.adapters;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodrecipe.R;

public class NetworkTimeoutViewHolder extends RecyclerView.ViewHolder {
    TextView timeout;
    public NetworkTimeoutViewHolder(@NonNull View itemView) {
        super(itemView);
        timeout = itemView.findViewById(R.id.timeout);
    }
}
