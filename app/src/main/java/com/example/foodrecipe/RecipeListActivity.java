package com.example.foodrecipe;


import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;


import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodrecipe.adapters.OnRecipeClickListener;
import com.example.foodrecipe.adapters.RecipeRecyclerAdapter;
import com.example.foodrecipe.utils.VerticalSpacingItemDecorator;
import com.example.foodrecipe.viewmodels.RecipeListViewModel;


public class RecipeListActivity extends BaseActivity implements OnRecipeClickListener {


    //region UI components
    RecyclerView mRecyclerView;
    //endregion
    //region Vars
    RecipeRecyclerAdapter mAdapter;
    private RecipeListViewModel mRecipeListViewModel;
    private static final String TAG = "RecipeListActivity";
    private SearchView mSearchView;

    //endregion
    //region Override(s)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);
        mRecyclerView = findViewById(R.id.recipe_list);
        mRecipeListViewModel = new ViewModelProvider(this).get(RecipeListViewModel.class);
        mSearchView = findViewById(R.id.searchView);

        subscribeObservers();
        initRecyclerView();
        initSearchView();
        if (!mRecipeListViewModel.ismViewingRecipes())
            displaySearchCategories();

        setSupportActionBar(findViewById(R.id.toolbar));
    }


    //endregion
    //region Subscribe Observers
    private void subscribeObservers() {
        mRecipeListViewModel.getRecipes().observe(this, recipes -> {
            if (recipes != null && mRecipeListViewModel.ismViewingRecipes())
                mRecipeListViewModel.setmIsPerformingQuery(false);
            mAdapter.setmRecipes(recipes);
        });
    }

    //endregion
    //region Initialize Recycler View
    private void initRecyclerView() {
        mAdapter = new RecipeRecyclerAdapter(this);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        VerticalSpacingItemDecorator verticalSpacingItemDecorator = new VerticalSpacingItemDecorator(30);
        mRecyclerView.addItemDecoration(verticalSpacingItemDecorator);
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {

               if(!mRecyclerView.canScrollVertically(1)){
                mRecipeListViewModel.searchNextPage();
               }
                super.onScrollStateChanged(recyclerView, newState);
            }
        });
    }

    //endregion
    //region onClickListener override methods
    @Override
    public void onRecipeClick(int position) {

    }

    @Override
    public void onCategoryClick(String category) {
        mAdapter.displayLoading();
        mRecipeListViewModel.searchRecipesApi(category, 1);
        mSearchView.clearFocus();
    }

    //endregion
    //region Initialize Search View
    private void initSearchView() {

        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                mAdapter.displayLoading();
                mRecipeListViewModel.searchRecipesApi(query, 1);
                mSearchView.clearFocus();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    //endregion
    //region Display Search Categories
    private void displaySearchCategories() {
        mRecipeListViewModel.setmViewingRecipes(false);
        mAdapter.displaySearchCategories();
    }
    //endregion
    //region onbackpressed

    @Override
    public void onBackPressed() {

        if (mRecipeListViewModel.onBackPressed()) {
            super.onBackPressed();
        } else {
            displaySearchCategories();
        }
    }

    //endregion
    //region menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.recipe_search_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.action_categories) {
            displaySearchCategories();

        }
        return super.onOptionsItemSelected(item);
    }
    //endregion
}

