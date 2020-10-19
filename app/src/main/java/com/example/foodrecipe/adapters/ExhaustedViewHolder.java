package com.example.foodrecipe.adapters;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodrecipe.R;

public class ExhaustedViewHolder extends RecyclerView.ViewHolder {
    TextView exhausted;
    public ExhaustedViewHolder(@NonNull View itemView) {
        super(itemView);
        exhausted = itemView.findViewById(R.id.exhausted);
    }
}
