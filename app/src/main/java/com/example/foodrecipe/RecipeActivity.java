package com.example.foodrecipe;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.foodrecipe.models.Recipe;
import com.example.foodrecipe.viewmodels.IndividualRecipeViewModel;
import com.example.foodrecipe.viewmodels.RecipeListViewModel;

import java.util.Arrays;


public class RecipeActivity extends BaseActivity {

    //region UI componenets
    private ImageView mImageView;
    private TextView mRecipeTitles, mRecipeRank;
    private LinearLayout mRecipeIngredientsLinearLayout;
    private ScrollView mScrollView;
    //endregion

    //region vars
    IndividualRecipeViewModel mIndividualRecipeViewModel;

    //endregion
    private static final String TAG = "RecipeActivity";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_detail);
        mImageView = findViewById(R.id.recipe_image);
        mRecipeTitles = findViewById(R.id.recipe_title);
        mRecipeRank = findViewById(R.id.recipe_social_score);
        mRecipeIngredientsLinearLayout = findViewById(R.id.ingredients_container);
        mScrollView = findViewById(R.id.parent);
        mIndividualRecipeViewModel = new ViewModelProvider(this).get(IndividualRecipeViewModel.class);


        getIncomingIntent();
        subscribeobservers();
    }


    private void getIncomingIntent() {
        if (getIntent().hasExtra("recipe")) {
            Recipe recipe = getIntent().getParcelableExtra("recipe");
            Log.d(TAG, "getIncomingIntent: " + recipe.getTitle());
            getRecipeApi(recipe.getRecipe_id());

        }
    }

    private void subscribeobservers() {
        Log.d(TAG, "subscribeobservers:  Entered");
        mIndividualRecipeViewModel.getIndividualRecipe().observe(this, recipe -> {
            if(recipe != null)
            Log.d(TAG, "onChanged: "+ Arrays.toString(recipe.getIngredients()));
            else
                Log.d(TAG, "onChanged: Recipe is null");
        });
        Log.d(TAG, "subscribeobservers: Exit");

    }

    private void getRecipeApi(String recipe_id) {
        mIndividualRecipeViewModel.getRecipeApi(recipe_id);
    }
}



