package com.example.foodrecipe;



import android.os.Bundle;

import android.util.Log;
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

public class RecipeListActivity extends BaseActivity{



    private RecipeListViewModel mRecipeListViewModel;

    Button button;
    private static final String TAG = "RecipeListActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);
        mRecipeListViewModel = new ViewModelProvider(this).get(RecipeListViewModel.class);
        subscribeObservers();
    }



    //region observers
    private void subscribeObservers(){
        mRecipeListViewModel.getRecipes().observe(this, new Observer<List<Recipe>>() {
            @Override
            public void onChanged(List<Recipe> recipes) {

            }
        });
    }
    //endregion
    //region recipe test for get single recipe
    private void testGetRetrofit(){
        RecipeApi recipeApi = ServiceGenerator.getRecipeApi();
        Call<RecipeResponse> responceCall = recipeApi.getRecipe("49421");
        responceCall.enqueue(new Callback<RecipeResponse>() {
            @Override
            public void onResponse(Call<RecipeResponse> call, Response<RecipeResponse> response) {
                if(response.code() == 200){
                    assert response.body() != null;
                    Log.d(TAG, "onResponse: " +response.body().getRecipe().getTitle());
                    Log.d(TAG, "onResponse: " + response.body().getRecipe().getImage_url());
                }
            }

            @Override
            public void onFailure(Call<RecipeResponse> call, Throwable t) {

            }
        });
    }
    //endregion
}

