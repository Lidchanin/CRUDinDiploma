package com.lidchanin.crudindiploma.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.lidchanin.crudindiploma.R;
import com.lidchanin.crudindiploma.adapter.InsideShoppingListRecyclerViewAdapter;
import com.lidchanin.crudindiploma.adapter.MainScreenRecyclerViewAdapter;
import com.lidchanin.crudindiploma.data.DatabaseHelper;
import com.lidchanin.crudindiploma.data.Product;

import java.util.ArrayList;
import java.util.List;

/**
 * Class <code>InsideShoppingListActivity</code> is a activity and extends
 * {@link AppCompatActivity}. Here all products in current shopping list are displayed and
 * there is a button for adding a new product in current shopping list.
 *
 * @author Lidchanin
 * @see android.app.Activity
 */
public class InsideShoppingListActivity extends AppCompatActivity {

    private DatabaseHelper databaseHelper;
    private FloatingActionButton floatingActionButtonAddProduct;
    private RecyclerView recyclerViewAllProducts;
    private List<Product> products;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inside_shopping_list);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        initializeButtons();
        initializeRecyclerViews();
        initializeData();
        initializeAdapters();
    }

    /**
     * Method <code>initializeData</code> reads and receives all products in current shopping list
     * from the database.
     */
    private void initializeData() {
        databaseHelper = new DatabaseHelper(this);
        long shoppingListId = getIntent().getLongExtra("shoppingListId", -1);
        Log.d("MyLog" + getLocalClassName(), "list id = " + shoppingListId);
        products = databaseHelper.getAllProductsFromCurrentShoppingListById(shoppingListId);
        if (products == null) {
            products = new ArrayList<>();
        }
    }

    /**
     * Method <code>initializeButtons</code> initialize button and add an actions for
     * {@link Button}s.
     */
    private void initializeButtons() {
        floatingActionButtonAddProduct
                = (FloatingActionButton) findViewById(R.id.inside_shopping_list_floating_action_button);
        // FIXME: 06.04.2017 fab is need to fix
        floatingActionButtonAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }


    /**
     * Method <code>initializeRecyclerViews</code> initializes {@link RecyclerView}.
     */
    public void initializeRecyclerViews() {
        recyclerViewAllProducts
                = (RecyclerView) findViewById(R.id.inside_shopping_list_recycler_view_all_products);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerViewAllProducts.setLayoutManager(layoutManager);
    }

    /**
     * Method <code>initializeAdapters</code> initializes adapter for {@link RecyclerView}.
     */
    private void initializeAdapters() {
        InsideShoppingListRecyclerViewAdapter adapter
                = new InsideShoppingListRecyclerViewAdapter(products, getApplicationContext());
        recyclerViewAllProducts.setAdapter(adapter);
    }

}
