package com.example.foodrecipe.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.foodrecipe.models.Recipe;
import com.example.foodrecipe.repositories.RecipeRepository;

public class IndividualRecipeViewModel extends ViewModel {
    //region vars&constructor
    RecipeRepository mRecipeRepository;


    public IndividualRecipeViewModel() {
        mRecipeRepository = RecipeRepository.getInstance();
    }

    public void getRecipeApi(String recipe_id){
     mRecipeRepository.getRecipeApi(recipe_id);
    }

    //endregion

    //region getIndividualRecipe
    public LiveData<Recipe> getIndividualRecipe() {
        return mRecipeRepository.getRecipe();
    }
    //endreigon
}
