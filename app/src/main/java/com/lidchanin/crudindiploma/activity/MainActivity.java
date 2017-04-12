package com.lidchanin.crudindiploma.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.lidchanin.crudindiploma.R;
import com.lidchanin.crudindiploma.data.DatabaseHelper;
import com.lidchanin.crudindiploma.data.dao.ProductDAO;
import com.lidchanin.crudindiploma.data.dao.ShoppingListDAO;
import com.lidchanin.crudindiploma.data.model.Product;
import com.lidchanin.crudindiploma.data.model.ShoppingList;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Button buttonGoToApp;
    private Button buttonShowLists;
    private Button buttonShowProducts;
    private Button buttonShowRelationship;

    private DatabaseHelper databaseHelper;
    private ShoppingListDAO shoppingListDAO;
    private ProductDAO productDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        shoppingListDAO = new ShoppingListDAO(this);
        productDAO = new ProductDAO(this);

        buttonGoToApp = (Button) findViewById(R.id.button_go_to_app);
        buttonGoToApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MainScreenActivity.class);
                startActivity(intent);
            }
        });

        buttonShowLists = (Button) findViewById(R.id.button_show_lists);
        buttonShowLists.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("MY_LOG", "__________Lists__________");
                List<ShoppingList> shoppingLists = shoppingListDAO.getAll();
                for (int i = 0; i < shoppingLists.size(); i++) {
                    Log.d("MY_LOG", "\tid: " + shoppingLists.get(i).getId()
                            + ",\tname: " + shoppingLists.get(i).getName());
                }
            }
        });

        buttonShowProducts = (Button) findViewById(R.id.button_show_products);
        buttonShowProducts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("MY_LOG", "__________Products__________");
                List<Product> products = productDAO.getAll();
                for (int i = 0; i < products.size(); i++) {
                    Log.d("MY_LOG", "\tid: " + products.get(i).getId()
                            + ",\tname: " + products.get(i).getName()
                            + ",\tcost: " + products.get(i).getCost()
                            + ",\tpopularity: " + products.get(i).getPopularity());
                }
            }
        });

        buttonShowRelationship = (Button) findViewById(R.id.button_show_relationship);
        buttonShowRelationship.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("MY_LOG", "__________Relationship__________");
                List<Long[]> products = shoppingListDAO.getAllRelationships();
                for (int i = 0; i < products.size(); i++) {
                    Log.d("MY_LOG", "\tid: " + products.get(i)[0]
                            + ",\tlist: " + products.get(i)[1]
                            + ",\tprod: " + products.get(i)[2]);
                }
            }
        });
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
}
