package com.example.foodrecipe;


import android.os.Bundle;

import android.util.Log;


import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodrecipe.adapters.OnRecipeClickListener;
import com.example.foodrecipe.adapters.RecipeRecyclerAdapter;
import com.example.foodrecipe.models.Recipe;
import com.example.foodrecipe.viewmodels.RecipeListViewModel;




public class RecipeListActivity extends BaseActivity implements OnRecipeClickListener {


    private RecipeListViewModel mRecipeListViewModel;

    //region UI components
    RecyclerView mRecyclerView;
    //endregion

    //region vars
    RecipeRecyclerAdapter mAdapter;
    //endregion
    private static final String TAG = "RecipeListActivity";
    //region override
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);
        mRecyclerView = findViewById(R.id.recipe_list);
        mRecipeListViewModel = new ViewModelProvider(this).get(RecipeListViewModel.class);


        subscribeObservers();
        testRetrofitRequest();
        initRecyclerView();

    }
    //endregion
    //region observers
    private void subscribeObservers() {
        mRecipeListViewModel.getRecipes().observe(this, recipes -> {
           if(recipes!=null)
               mAdapter.setmRecipes(recipes);
            assert recipes != null;
            for (int i = 0; i < recipes.size(); i++) {
                Log.d(TAG, "onChanged: " + recipes.get(i).getTitle());
            }

        });
    }

    //endregion
    //region searchRecipesApi
    private void searchRecipesApi(String query, int pageNumber) {
        mRecipeListViewModel.searchRecipesApi(query, pageNumber);
    }
    //endregion
    //region init recyclerview
     private  void initRecyclerView(){
        mAdapter = new RecipeRecyclerAdapter(this);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
     }
    //endregion
    //region test retrofit request
    private void testRetrofitRequest() {
        searchRecipesApi("chicken breast", 1);
    }

    @Override
    public void onRecipeClick(int position) {

    }

    @Override
    public void onCategoryClick(String category) {

    }
    //endregion


}

