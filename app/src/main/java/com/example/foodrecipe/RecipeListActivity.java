package com.example.foodrecipe;


import android.os.Bundle;

import android.util.Log;


import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodrecipe.adapters.OnRecipeClickListener;
import com.example.foodrecipe.adapters.RecipeRecyclerAdapter;
import com.example.foodrecipe.models.Recipe;
import com.example.foodrecipe.viewmodels.RecipeListViewModel;




public class RecipeListActivity extends BaseActivity implements OnRecipeClickListener {



    //region UI components
    RecyclerView mRecyclerView;
    //endregion
    //region Vars
    RecipeRecyclerAdapter mAdapter;
    private RecipeListViewModel mRecipeListViewModel;
    private static final String TAG = "RecipeListActivity";
    //endregion
    //region Override(s)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);
        mRecyclerView = findViewById(R.id.recipe_list);
        mRecipeListViewModel = new ViewModelProvider(this).get(RecipeListViewModel.class);


        subscribeObservers();
        initRecyclerView();
        initSearchView();

    }
    //endregion
    //region Subscribe Observers
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
    //region Initialize Recycler View
     private  void initRecyclerView(){
        mAdapter = new RecipeRecyclerAdapter(this);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
     }
    //endregion
    //region onClickListener override methods
    @Override
    public void onRecipeClick(int position) {

    }

    @Override
    public void onCategoryClick(String category) {

    }
    //endregion
    //region Initialize Search View
    private void initSearchView(){
        final SearchView searchView = findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                mAdapter.displayLoading();
                mRecipeListViewModel.searchRecipesApi(query, 1);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }
    //endregion


}

