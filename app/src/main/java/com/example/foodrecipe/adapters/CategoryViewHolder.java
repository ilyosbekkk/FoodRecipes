package com.example.foodrecipe.adapters;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodrecipe.R;

import de.hdodenhof.circleimageview.CircleImageView;


public class CategoryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    OnRecipeClickListener listener;
    CircleImageView categoryImageView;
    TextView categoryTitle;
    public CategoryViewHolder(@NonNull View itemView, OnRecipeClickListener listener) {
        super(itemView);
        this.listener = listener;
        categoryImageView = itemView.findViewById(R.id.circular_image);
        categoryTitle = itemView.findViewById(R.id.category_title);

        itemView.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
       listener.onCategoryClick(categoryTitle.getText().toString());
    }
}
