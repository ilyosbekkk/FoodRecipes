package com.example.foodrecipe;


import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.Button;


import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.foodrecipe.models.Recipe;
import com.example.foodrecipe.requests.RecipeApi;
import com.example.foodrecipe.requests.ServiceGenerator;
import com.example.foodrecipe.responses.RecipeResponse;
import com.example.foodrecipe.responses.RecipeSearchResponse;
import com.example.foodrecipe.viewmodels.RecipeListViewModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecipeListActivity extends BaseActivity {


    private RecipeListViewModel mRecipeListViewModel;

    Button button;
    private static final String TAG = "RecipeListActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);
        button = findViewById(R.id.test);

        mRecipeListViewModel = new ViewModelProvider(this).get(RecipeListViewModel.class);
        subscribeObservers();
        button.setOnClickListener(
                v -> testRetrofitRequest()
        );
    }


    //region observers
    private void subscribeObservers() {
        mRecipeListViewModel.getRecipes().observe(this, new Observer<List<Recipe>>() {
            @Override
            public void onChanged(List<Recipe> recipes) {
                for (int i = 0; i < recipes.size(); i++) {
                    Log.d(TAG, "onChanged: " + recipes.get(i).getTitle());
                }
            }
        });
    }

    //endregion
    //region searchRecipesApi
    private void searchRecipesApi(String query, int pageNumber) {
        mRecipeListViewModel.searchRecipesApi(query, pageNumber);
    }
    //endregion

    //region test retrofit request
    private void testRetrofitRequest() {
        searchRecipesApi("chicken breast", 1);
    }
    //endregion


}

