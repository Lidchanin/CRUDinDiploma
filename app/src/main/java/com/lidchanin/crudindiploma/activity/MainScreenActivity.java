package com.lidchanin.crudindiploma.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.lidchanin.crudindiploma.R;
import com.lidchanin.crudindiploma.adapter.MainScreenRecyclerViewAdapter;
import com.lidchanin.crudindiploma.data.DatabaseHelper;
import com.lidchanin.crudindiploma.data.ShoppingList;

import java.util.ArrayList;
import java.util.List;

/**
 * Class <code>MainScreenActivity</code> is a activity and extends {@link AppCompatActivity}.
 * Here all shopping lists are displayed and there is a button for adding a new shopping list.
 *
 * @author Lidchanin
 * @see ShoppingList
 */
public class MainScreenActivity extends AppCompatActivity {

    private RecyclerView recyclerViewAllShoppingLists;
    private DatabaseHelper databaseHelper;
    private List<ShoppingList> shoppingLists;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        initializeButton();
        initializeRecyclerView();
        initializeData();
        initializeAdapter();
    }

    /**
     * Method <code>initializeButton</code> add an actions for {@link Button}.
     */
    public void initializeButton() {
        Button buttonAddShoppingList = (Button) findViewById(R.id.main_screen_button_add_shopping_list);
        buttonAddShoppingList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainScreenActivity.this, AddingShoppingListActivity.class);
                startActivity(intent);
            }
        });
    }

    /**
     * Method <code>initializeData</code> reads and receives all shopping lists from the database.
     */
    private void initializeData() {
        databaseHelper = new DatabaseHelper(this);
        shoppingLists = databaseHelper.getAllShoppingLists();
        if (shoppingLists == null) {
            shoppingLists = new ArrayList<>();
        }
    }

    /**
     * Method <code>initializeRecyclerView</code> initializes {@link RecyclerView}.
     */
    public void initializeRecyclerView() {
        recyclerViewAllShoppingLists
                = (RecyclerView) findViewById(R.id.main_screen_recycler_view_all_shopping_lists);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerViewAllShoppingLists.setLayoutManager(layoutManager);
    }

    /**
     * Method <code>initializeAdapter</code> initializes adapter for {@link RecyclerView}.
     */
    private void initializeAdapter() {
        MainScreenRecyclerViewAdapter adapter
                = new MainScreenRecyclerViewAdapter(shoppingLists, getApplicationContext());
        recyclerViewAllShoppingLists.setAdapter(adapter);
    }
}
