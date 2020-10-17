package com.example.foodrecipe;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.foodrecipe.models.Recipe;


public class RecipeActivity extends BaseActivity {

    //region UI componenets
    private ImageView mImageView;
    private TextView mRecipeTitles, mRecipeRank;
    private LinearLayout mRecipeIngredientsLinearLayout;
    private ScrollView mScrollView;
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



        getIncomingIntent();
    }


    private void getIncomingIntent() {
        if (getIntent().hasExtra("recipe")) {
            Recipe recipe = getIntent().getParcelableExtra("recipe");
            Log.d(TAG, "getIncomingIntent: " + recipe.getTitle());


        }
    }
}



