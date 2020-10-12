package com.example.foodrecipe.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.foodrecipe.R;
import com.example.foodrecipe.models.Recipe;

import java.util.ArrayList;
import java.util.List;

public class RecipeRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    //region vars
    private List<Recipe> mRecipes;
    private OnRecipeClickListener mOnRecipeClickListener;
    private static final int RECIPE_TYPE = 1;
    private static final int LOADING_TYPE = 2;

    public RecipeRecyclerAdapter(OnRecipeClickListener mOnRecipeClickListener) {
        this.mOnRecipeClickListener = mOnRecipeClickListener;
    }

    //endregion
    //region overrides
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;

        if (viewType == LOADING_TYPE) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_loading_list_item, parent, false);
            return new LoadingViewHolder(view);
        }
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_recipe_list_item, parent, false);
        return new RecipeViewHolder(view, mOnRecipeClickListener);


    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        int itemViewType = getItemViewType(position);
        if (itemViewType == RECIPE_TYPE) {
            RequestOptions options = new RequestOptions().placeholder(R.drawable.ic_launcher_background);
            Glide.with(holder.itemView.getContext()).setDefaultRequestOptions(options).load(mRecipes.get(position).getImage_url()).into(((RecipeViewHolder) holder).image);
            ((RecipeViewHolder) holder).title.setText(mRecipes.get(position).getTitle());
            ((RecipeViewHolder) holder).publisher.setText(mRecipes.get(position).getPublisher());
            ((RecipeViewHolder) holder).social_score.setText(String.valueOf(Math.round(mRecipes.get(position).getSocial_rank())));
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (mRecipes.get(position).getTitle().equals("LOADING...")) {
            return LOADING_TYPE;
        }
        return RECIPE_TYPE;
    }

    @Override
    public int getItemCount() {
        if (mRecipes != null)
            return mRecipes.size();
        else
            return 0;
    }

    //endregion
    //region set Recipe List
    public void setmRecipes(List<Recipe> mRecipes) {
        this.mRecipes = mRecipes;
        notifyDataSetChanged();
    }

    //endregion
    //region display loading
    public void displayLoading() {
        if (!isLoading()) {
            Recipe recipe = new Recipe();
            recipe.setTitle("LOADING...");
            List<Recipe> loadingList = new ArrayList<>();
            loadingList.add(recipe);
            mRecipes = loadingList;
            notifyDataSetChanged();
        }
    }

    private boolean isLoading() {
        if (mRecipes != null) {
            if (mRecipes.size() > 0) {
                return mRecipes.get(mRecipes.size() - 1).getTitle().equals("LOADING...");
            }
        }

        return false;
    }

    //endregion
}
