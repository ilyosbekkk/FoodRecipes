package com.example.foodrecipe.requests;


import com.example.foodrecipe.utils.Constants;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ServiceGenerator {

    private static final Retrofit.Builder retrofitBuilder =
            new Retrofit.Builder()
                    .baseUrl(Constants.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create());

    /*
      RetrofitBuilder -> baseUrl -> Converting `data to json -> create or build
     */



    private static final Retrofit retrofit = retrofitBuilder.build();

    private static final RecipeApi recipeApi = retrofit.create(RecipeApi.class);

    public static RecipeApi getRecipeApi(){
        return recipeApi;
    }
}