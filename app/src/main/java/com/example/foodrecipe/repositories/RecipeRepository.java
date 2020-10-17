package com.example.foodrecipe.repositories;

import androidx.lifecycle.LiveData;


import com.example.foodrecipe.models.Recipe;
import com.example.foodrecipe.requests.RecipeApiClient;

import java.util.List;

public class RecipeRepository {

    private static RecipeRepository instance;
    RecipeApiClient mRecipeApiClient;
    private String mQuery;
    private int mPageNumber;


    public static RecipeRepository getInstance() {
        if (instance == null) {
            instance = new RecipeRepository();
        }
        return instance;
    }

    private RecipeRepository() {
        mRecipeApiClient = RecipeApiClient.getInstance();
    }

    public LiveData<List<Recipe>> getRecipes() {
        return mRecipeApiClient.getRecipes();
    }

    public LiveData<Recipe> getRecipe(){
        return  mRecipeApiClient.getRecipe();
    }

    public void searchRecipesApi(String query, int pageNumber) {
        if (pageNumber == 0) {
            pageNumber = 1;
        }
        mQuery = query;
        mPageNumber = pageNumber;
        mRecipeApiClient.searchRecipesApi(query, pageNumber);
    }

    public void getRecipeApi(String recipe_id){
        mRecipeApiClient.getRecipeApi(recipe_id);

    }


    public void searchNextPage(){
        searchRecipesApi(mQuery,  mPageNumber+1);
    }

    public void cancelRequest() {
        mRecipeApiClient.cancelRequest();
    }


}
