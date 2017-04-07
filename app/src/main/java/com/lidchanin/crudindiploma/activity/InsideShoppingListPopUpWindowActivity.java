package com.lidchanin.crudindiploma.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.lidchanin.crudindiploma.R;
import com.lidchanin.crudindiploma.data.DatabaseHelper;
import com.lidchanin.crudindiploma.data.Product;

public class InsideShoppingListPopUpWindowActivity extends AppCompatActivity {

    private EditText editTextProductName;
    private EditText editTextProductCost;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inside_shopping_list_pop_up_window);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int width = displayMetrics.widthPixels;
        int height = displayMetrics.heightPixels;
        getWindow().setLayout((int) (width * .8), (int) (height * .6));

        long shoppingListId = getIntent().getLongExtra("shoppingListId", -1);

        initializeButtons(shoppingListId);
    }

    private void initializeButtons(final long shoppingListId) {
        editTextProductName = (EditText)
                findViewById(R.id.inside_shopping_list_pop_up_window_edit_text_product_name);
        editTextProductCost = (EditText)
                findViewById(R.id.inside_shopping_list_pop_up_window_edit_text_product_cost);

        ImageButton closeButton = (ImageButton)
                findViewById(R.id.inside_shopping_list_pop_up_window_image_button_close);
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InsideShoppingListPopUpWindowActivity.this,
                        InsideShoppingListActivity.class);
                intent.putExtra("shoppingListId", shoppingListId);
                startActivity(intent);
            }
        });

        ImageButton addButton = (ImageButton)
                findViewById(R.id.inside_shopping_list_pop_up_window_image_button_add);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editTextProductName.length() <= 1 && editTextProductCost.length() == 0) {
                    Toast.makeText(InsideShoppingListPopUpWindowActivity.this,
                            "Enter name and cost!", Toast.LENGTH_SHORT).show();
                } else if (editTextProductName.length() <= 1) {
                    Toast.makeText(InsideShoppingListPopUpWindowActivity.this,
                            "Enter name!", Toast.LENGTH_SHORT).show();
                } else if (editTextProductCost.length() == 0) {
                    Toast.makeText(InsideShoppingListPopUpWindowActivity.this,
                            "Enter cost!", Toast.LENGTH_SHORT).show();
                } else {
                    databaseHelper = new DatabaseHelper(getApplicationContext());
                    Product product = new Product();
                    product.setName(editTextProductName.getText().toString());
                    product.setCost(Double.valueOf(editTextProductCost.getText().toString()));
                    databaseHelper.addProductInCurrentShoppingList(product, shoppingListId);

                    Intent intent = new Intent(InsideShoppingListPopUpWindowActivity.this,
                            InsideShoppingListActivity.class);
                    intent.putExtra("shoppingListId", shoppingListId);
                    startActivity(intent);
                }
            }
        });
    }
}
