package com.lidchanin.crudindiploma.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.lidchanin.crudindiploma.R;
import com.lidchanin.crudindiploma.data.DatabaseHelper;
import com.lidchanin.crudindiploma.data.Product;
import com.lidchanin.crudindiploma.data.ShoppingList;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private final String LOG = "MainActivity";
    private Button buttonGoToApp;
    private Button buttonShowLists;
    private Button buttonShowProducts;
    private Button buttonShowRelationship;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        databaseHelper = new DatabaseHelper(getApplicationContext());

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
                Log.d(LOG, "__________Lists__________");
                List<ShoppingList> shoppingLists = databaseHelper.getAllShoppingLists();
                for (int i = 0; i < shoppingLists.size(); i++) {
                    Log.d(LOG, "\tid: " + shoppingLists.get(i).getId()
                            + ",\tname: " + shoppingLists.get(i).getName());
                }
            }
        });

        buttonShowProducts = (Button) findViewById(R.id.button_show_products);
        buttonShowProducts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(LOG, "__________Products__________");
                List<Product> products = databaseHelper.getAllProducts();
                for (int i = 0; i < products.size(); i++) {
                    Log.d(LOG, "\tid: " + products.get(i).getId()
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
                Log.d(LOG, "__________Relationships__________");
                List<Long[]> relationships = databaseHelper.getRelationships();
                for (int i = 0; i < relationships.size(); i++) {
                    Log.d(LOG, "\tid: " + relationships.get(i)[0].toString()
                            + ",\tlist id: " + relationships.get(i)[1].toString()
                            + ",\tproduct id: " + relationships.get(i)[2].toString()
                    );
                }
            }
        });
    }
}
