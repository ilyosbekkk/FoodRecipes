package com.example.foodrecipe;



import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.Button;


import com.example.foodrecipe.models.Recipe;
import com.example.foodrecipe.requests.RecipeApi;
import com.example.foodrecipe.requests.ServiceGenerator;
import com.example.foodrecipe.responses.RecipeSearchResponse;

import java.io.IOError;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecipeListActivity extends BaseActivity{


    Button button;
    private static final String TAG = "RecipeListActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);
        button = findViewById(R.id.test);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              testRetrofitRequest();
            }
        });


    }


    //region test retrofit request
    private void testRetrofitRequest(){
        RecipeApi recipeApi = ServiceGenerator.getRecipeApi();
        Call<RecipeSearchResponse> responseCall = recipeApi.searchRecipe("chicken breas", "1");

        responseCall.enqueue(new Callback<RecipeSearchResponse>() {


            @Override
            public void onResponse(Call<RecipeSearchResponse> call, Response<RecipeSearchResponse> response) {
                Log.d(TAG, "onResponse: Server Response" + response.toString());
                if(response.code() == 200){
                    assert response.body() != null;
                    Log.d(TAG, "onResponse: " + response.body().toString());
                    List<Recipe> recipes = new ArrayList<>(response.body().getRecipes());
                    for(Recipe recipe: recipes){
                        Log.d(TAG, "onResponse: " + recipe.getTitle());
                    }
                }
                else{
                    try {
                        assert response.errorBody() != null;
                        Log.d(TAG, "onResponse: " + response.errorBody().toString());

                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<RecipeSearchResponse> call, Throwable t) {

            }
        });
    }
}

