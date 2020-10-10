package com.example.foodrecipe.requests;

import com.example.foodrecipe.responses.RecipeResponce;
import com.example.foodrecipe.responses.RecipeSearchResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RecipeApi {

    //region SEARCH
    @GET("api/search")
    Call<RecipeSearchResponse> searchRecipe(
            @Query("q") String query,
            @Query("page") String page
    );
    //endregion
    //region GET RECIPE REQUEST
    @GET("api/get")
    Call<RecipeResponce> getRecipe(
            @Query("rId") String recipe_id
    );
    //endregion

}
