package com.example.foodrecipe.adapters;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodrecipe.R;

public class RecipeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    TextView title,  publisher,  social_score;
    AppCompatImageView image;
    OnRecipeClickListener onRecipeClickListener;



    public RecipeViewHolder(@NonNull View itemView,  OnRecipeClickListener onRecipeClickListener) {
        super(itemView);
        this.onRecipeClickListener = onRecipeClickListener;
        title = itemView.findViewById(R.id.title);
        publisher = itemView.findViewById(R.id.publisher);
        social_score = itemView.findViewById(R.id.social_rank);
        image = itemView.findViewById(R.id.image);

        itemView.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
            onRecipeClickListener.onRecipeClick(getAdapterPosition());
    }
}
