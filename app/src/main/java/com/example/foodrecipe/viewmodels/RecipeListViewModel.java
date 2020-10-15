package com.example.foodrecipe.viewmodels;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.foodrecipe.models.Recipe;
import com.example.foodrecipe.repositories.RecipeRepository;

import java.util.List;

public class RecipeListViewModel extends ViewModel {


    //region Vars&Constructor
    RecipeRepository mRecipeRepository;
    private boolean mViewingRecipes;
    private boolean mIsPerformingQuery;

    public RecipeListViewModel() {
        mViewingRecipes = false;
        mIsPerformingQuery = false;
            mRecipeRepository = RecipeRepository.getInstance();

    }
    //endregion
    //region getRecipes
    public LiveData<List<Recipe>> getRecipes(){
        return mRecipeRepository.getRecipes();
    }
    //endregion
    //region search recipe api
    public void searchRecipesApi(String query, int pageNumber){
        mViewingRecipes = true;
        if(pageNumber == 0)
            pageNumber = 1;
        mRecipeRepository.searchRecipesApi(query, pageNumber);
    }
    //endregion
    //region set/get viewng recipes
    public boolean ismViewingRecipes(){
        return mViewingRecipes;
    }
    public void setmViewingRecipes(boolean mViewingRecipes){
        this.mViewingRecipes = mViewingRecipes;
    }
    //endregion
    //region onBackPressed
    public boolean onBackPressed(){
        if(mIsPerformingQuery){
            mRecipeRepository.cancelRequest();
            mIsPerformingQuery= false;
        }
        if(mViewingRecipes){
               mViewingRecipes = false;
               return  false;
        }
        return true;

    }
    //endregion
    //region set/get ispreforming  query
    public  boolean ismIsPerformingQuery(){
        return mIsPerformingQuery;
    }
    public void setmIsPerformingQuery(boolean isPerformingQuery){
        this.mIsPerformingQuery = isPerformingQuery;
    }
    //endregion
    //region searchNextPage
    public void searchNextPage(){
        if(!mIsPerformingQuery && mViewingRecipes){
            mRecipeRepository.searchNextPage();
        }
    }
    //endregion
}

