package com.lidchanin.crudindiploma.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import com.lidchanin.crudindiploma.R;
import com.lidchanin.crudindiploma.adapter.InsideShoppingListRecyclerViewAdapter;
import com.lidchanin.crudindiploma.data.dao.ProductDAO;
import com.lidchanin.crudindiploma.data.dao.ShoppingListDAO;
import com.lidchanin.crudindiploma.data.model.Product;

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

    private RecyclerView recyclerViewAllProducts;

    private List<Product> products;
    private long shoppingListId;

    private ShoppingListDAO shoppingListDAO;
    private ProductDAO productDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inside_shopping_list);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        shoppingListId = getIntent().getLongExtra("shoppingListId", -1);

        shoppingListDAO = new ShoppingListDAO(this);
        productDAO = new ProductDAO(this);

        initializeViewsAndButtons(shoppingListId);
        initializeData(shoppingListId);
        initializeRecyclerViews();
        initializeAdapters();
    }

    @Override
    protected void onResume() {
        super.onResume();
        shoppingListDAO.open();
        productDAO.open();
    }

    @Override
    protected void onPause() {
        super.onPause();
        shoppingListDAO.close();
        productDAO.close();
    }

    /**
     * Method <code>initializeData</code> reads and receives all products in current shopping list
     * from the database.
     *
     * @param shoppingListId is the current shopping list id.
     */
    private void initializeData(long shoppingListId) {
        products = productDAO.getAllFromCurrentShoppingList(shoppingListId);
        if (products == null) {
            products = new ArrayList<>();
        }
    }

    /**
     * Method <code>initializeViewsAndButtons</code> initialize views and buttons and add them an
     * actions or other properties.
     *
     * @param shoppingListId is the current shopping list id.
     */
    private void initializeViewsAndButtons(final long shoppingListId) {
        FloatingActionButton floatingActionButtonAddProduct = (FloatingActionButton)
                findViewById(R.id.inside_shopping_list_floating_action_button);
        // FIXME: 06.04.2017 fab is need to fix?
        floatingActionButtonAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(InsideShoppingListActivity.this,
                        InsideShoppingListAddProductPopUpWindowActivity.class);
                intent.putExtra("shoppingListId", shoppingListId);
                startActivity(intent);
            }
        });

        TextView textViewShoppingListName = (TextView)
                findViewById(R.id.inside_shopping_list_text_view_shopping_list_name);
        textViewShoppingListName.setText("id = " + String.valueOf(shoppingListId));
    }

    /**
     * Method <code>initializeRecyclerViews</code> initializes {@link RecyclerView}s.
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
                = new InsideShoppingListRecyclerViewAdapter(products, getApplicationContext(),
                shoppingListId);
        recyclerViewAllProducts.setAdapter(adapter);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent intent = new Intent(InsideShoppingListActivity.this, MainScreenActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
