package com.example.foodrecipe.requests;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.foodrecipe.AppExecutors;
import com.example.foodrecipe.models.Recipe;
import com.example.foodrecipe.responses.RecipeSearchResponse;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Response;

import static com.example.foodrecipe.utils.Constants.NETWORK_TIMEOUT;

public class RecipeApiClient {
    private static final String TAG = "RecipeApiClient";
    private static RecipeApiClient instance;
    private MutableLiveData<List<Recipe>> mRecipes;
    RetrieveRecipesRunnable mRetrieveRecipesRunnable;

    public static RecipeApiClient getInstance() {
        if (instance == null) {
            instance = new RecipeApiClient();
        }
        return instance;
    }

    private RecipeApiClient() {
        mRecipes = new MutableLiveData<>();

    }

    public LiveData<List<Recipe>> getRecipes() {
        return mRecipes;
    }

    public void searchRecipesApi(String query, int pageNumber) {
        if(mRetrieveRecipesRunnable != null)
            mRetrieveRecipesRunnable = null;
        mRetrieveRecipesRunnable = new RetrieveRecipesRunnable(query, pageNumber);
        final Future handler = AppExecutors.getInstance().networkIO().submit(mRetrieveRecipesRunnable);
        AppExecutors.getInstance().networkIO().schedule(() -> {
            //Let the User know it is timeout
            handler.cancel(true);
        }, NETWORK_TIMEOUT, TimeUnit.MILLISECONDS);
    }

    private class RetrieveRecipesRunnable implements Runnable {


        private String query;
        private int pageNumber;
        boolean cancelRequest = false;

        public RetrieveRecipesRunnable(String query,  int pageNumber){
            this.query = query;
            this.pageNumber = pageNumber;
        }
        @Override
        public void run() {
            try {
                Response<RecipeSearchResponse> response = getRecipes(query, pageNumber).execute();

                if (cancelRequest) {
                    return;
                }
                if (response.code() == 200) {
                    assert response.body() != null;
                    List<Recipe> list = new ArrayList<>(response.body().getRecipes());
                    if (pageNumber == 1) {
                        mRecipes.postValue(list);

                    } else {
                        List<Recipe> currentRecipes = mRecipes.getValue();
                        assert currentRecipes != null;
                        currentRecipes.addAll(list);
                        mRecipes.postValue(currentRecipes);
                    }
                } else {
                    assert response.errorBody() != null;
                    String error = response.errorBody().string();
                    Log.e(TAG, "run: " + error);
                    mRecipes.postValue(null);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        private Call<RecipeSearchResponse> getRecipes(String query, int pageNumber) {
            return ServiceGenerator.getRecipeApi().searchRecipe(
                    query,
                    String.valueOf(pageNumber)
            );
        }

        private void cancelRequest() {
            Log.d(TAG, "cancelRequest: Cancelling Request");
            cancelRequest = true;
        }
    }
    public void cancelRequest(){
        if(mRetrieveRecipesRunnable !=null){
            mRetrieveRecipesRunnable.cancelRequest();
        }
    }

}
