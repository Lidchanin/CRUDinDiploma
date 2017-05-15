package com.lidchanin.crudindiploma.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.lidchanin.crudindiploma.R;
import com.lidchanin.crudindiploma.data.DatabaseHelper;
import com.lidchanin.crudindiploma.data.dao.DatabaseDAO;
import com.lidchanin.crudindiploma.data.dao.ExistingProductDAO;
import com.lidchanin.crudindiploma.data.dao.ProductDAO;
import com.lidchanin.crudindiploma.data.dao.ShoppingListDAO;
import com.lidchanin.crudindiploma.data.model.ExistingProduct;
import com.lidchanin.crudindiploma.data.model.Product;
import com.lidchanin.crudindiploma.data.model.ShoppingList;

import java.util.List;
import java.util.StringTokenizer;

public class MainActivity extends AppCompatActivity {

    final static private String PREF_KEY_SHORTCUT_ADDED = "PREF_KEY_SHORTCUT_ADDED";

    private Button buttonGoToApp;
    private Button buttonShowLists;
    private Button buttonShowProducts;
    private Button buttonShowRelationship;

    private DatabaseHelper databaseHelper;
    private ShoppingListDAO shoppingListDAO;
    private ProductDAO productDAO;
    private ExistingProductDAO existingProductDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        shoppingListDAO = new ShoppingListDAO(this);
        productDAO = new ProductDAO(this);
        existingProductDAO = new ExistingProductDAO(this);

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
                Log.d("MY_LOG", "__________ExistingProducts__________");
                List<String[]> products = existingProductDAO.getAllExistingProducts();
                for (int i = 0; i < products.size(); i++) {
                    Log.d("MY_LOG", "\n\tid: " + products.get(i)[0]
                            + ",\tlist_id: " + products.get(i)[1]
                            + ",\tprod_id: " + products.get(i)[2]
                            + ",\tquantity: " + products.get(i)[3]
                            + ",\ttotal_cost: " + products.get(i)[4]);
                }
            }
        });

        createShortcutIcon();
    }

    @Override
    protected void onResume() {
        super.onResume();
        shoppingListDAO.open();
        existingProductDAO.open();
        productDAO.open();
    }

    @Override
    protected void onPause() {
        super.onPause();
        shoppingListDAO.close();
        existingProductDAO.close();
        productDAO.close();
    }

    /**
     * Method <code>createShortcutIcon</code> creates shortcut icon on users mobile desktop.
     */
    private void createShortcutIcon() {
        SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE);
        boolean shortCutWasAlreadyAdded = sharedPreferences
                .getBoolean(PREF_KEY_SHORTCUT_ADDED, false);
        if (shortCutWasAlreadyAdded)
            return;
        Intent shortcutIntent = new Intent(getApplicationContext(), MainActivity.class);
        shortcutIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        shortcutIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        Intent addIntent = new Intent();
        addIntent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, shortcutIntent);
        addIntent.putExtra(Intent.EXTRA_SHORTCUT_NAME, getString(R.string.shortcut_name));
        addIntent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, Intent.ShortcutIconResource
                .fromContext(getApplicationContext(), R.mipmap.ic_launcher_round));
        addIntent.setAction("com.android.launcher.action.INSTALL_SHORTCUT");
        getApplicationContext().sendBroadcast(addIntent);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(PREF_KEY_SHORTCUT_ADDED, true);
        editor.apply();
    }
}
