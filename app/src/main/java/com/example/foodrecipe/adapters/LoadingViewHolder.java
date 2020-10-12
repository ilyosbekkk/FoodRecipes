package com.example.foodrecipe.adapters;

import android.view.View;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodrecipe.R;

public class LoadingViewHolder extends RecyclerView.ViewHolder {
    ProgressBar progressBar;
    public LoadingViewHolder(@NonNull View itemView) {
        super(itemView);
        progressBar = itemView.findViewById(R.id.loading);
    }
}
