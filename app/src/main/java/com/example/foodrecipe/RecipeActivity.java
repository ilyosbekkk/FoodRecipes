package com.example.foodrecipe;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
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
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.io.IOException;
import java.net.URL;
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


    //region get incoming intent
    private void getIncomingIntent() {
        if (getIntent().hasExtra("recipe")) {
            Recipe recipe = getIntent().getParcelableExtra("recipe");
            Log.d(TAG, "getIncomingIntent: " + recipe.getTitle());
            getRecipeApi(recipe.getRecipe_id());

        }
    }
    //endregion

    //region subscribeobservers
    private void subscribeobservers() {

        mIndividualRecipeViewModel.getIndividualRecipe().observe(this, recipe -> {
            if (recipe != null) {
                mScrollView.setVisibility(View.VISIBLE);
                try {
                    setUIproperties(recipe);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
    //endregion

    ///region getRecipeApi
    private void getRecipeApi(String recipe_id) {
        mIndividualRecipeViewModel.getRecipeApi(recipe_id);
    }

    //endregion
    //region set UI properties
    public void setUIproperties(Recipe recipe) throws IOException {


        mRecipeTitles.setText(recipe.getTitle());
        mRecipeRank.setText(String.valueOf(Math.round(recipe.getSocial_rank())));
        Picasso.get().load(recipe.getImage_url()).into(mImageView, new Callback() {
            @Override
            public void onSuccess() {

            }
            @Override
            public void onError(Exception e) {
                Log.d(TAG, "onError: ERROR occured");
            }
        });

        Log.d(TAG, "setUIproperties: " + Arrays.toString(recipe.getIngredients()));
        TextView textView = new TextView(this);
        mRecipeIngredientsLinearLayout.setOrientation(LinearLayout.VERTICAL);

        String[] ingredients = recipe.getIngredients();
        Log.d(TAG, "setUIproperties: Entering...");
        for (int i = 0; i < ingredients.length; i++) {
            Log.d(TAG, "setUIproperties: " + ingredients[i]);
            if (i == 0) {
                textView.setText(ingredients[i]);
            } else {
                textView.append("\n" + ingredients[i]);
            }
        }
        mRecipeIngredientsLinearLayout.addView(textView);


    }


}
//endregion



