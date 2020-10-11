package com.example.foodrecipe.viewmodels;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.foodrecipe.models.Recipe;
import com.example.foodrecipe.repositories.RecipeRepository;

import java.util.List;

public class RecipeListViewModel extends ViewModel {

    RecipeRepository mRecipeRepository;
    public RecipeListViewModel() {
            mRecipeRepository = RecipeRepository.getInstance();

    }
    public LiveData<List<Recipe>> getRecipes(){
        return mRecipeRepository.getRecipes();
    }

    public void searchRecipesApi(String query, int pageNumber){
        if(pageNumber == 0)
            pageNumber = 1;
        mRecipeRepository.searchRecipesApi(query, pageNumber);
    }
}
