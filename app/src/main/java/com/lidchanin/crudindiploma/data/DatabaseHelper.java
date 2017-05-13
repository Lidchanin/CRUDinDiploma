package com.lidchanin.crudindiploma.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.lidchanin.crudindiploma.R;
import com.lidchanin.crudindiploma.data.model.Product;

import java.util.ArrayList;
import java.util.List;

/**
 * Class <code>DatabaseHelper</code> extends {@link SQLiteOpenHelper}, which manages database
 * creation and version management. This class creates an “Personal shopping list” with two
 * tables “shopping_lists” and “products”.
 *
 * @author Lidchanin
 * @see SQLiteOpenHelper
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String TABLE_SHOPPING_LISTS = "shopping_lists";
    public static final String TABLE_SHOPPING_LISTS_PRODUCTS = "shopping_lists_products";
    public static final String TABLE_PRODUCTS = "products";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_COST = "cost";
    public static final String COLUMN_POPULARITY = "popularity";
    public static final String COLUMN_LIST_ID = "list_id";
    public static final String COLUMN_PRODUCT_ID = "product_id";
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "personal_shopping_lists";
    private static final String CREATE_TABLE_SHOPPING_LISTS = "CREATE TABLE " + TABLE_SHOPPING_LISTS
            + "("
            + COLUMN_ID + " INTEGER PRIMARY KEY NOT NULL, "
            + COLUMN_NAME + " TEXT NOT NULL"
            + ")";
    private static final String CREATE_TABLE_PRODUCTS = "CREATE TABLE " + TABLE_PRODUCTS
            + "("
            + COLUMN_ID + " INTEGER PRIMARY KEY NOT NULL, "
            + COLUMN_NAME + " TEXT NOT NULL, "
            + COLUMN_COST + " REAL, "
            + COLUMN_POPULARITY + " INTEGER"
            + ")";
    private static final String CREATE_TABLE_SHOPPING_LISTS_PRODUCTS
            = "CREATE TABLE " + TABLE_SHOPPING_LISTS_PRODUCTS
            + "("
            + COLUMN_ID + " INTEGER PRIMARY KEY NOT NULL, "
            + COLUMN_LIST_ID + " INTEGER REFERENCES " + TABLE_SHOPPING_LISTS + " (" + COLUMN_ID + "), "
            + COLUMN_PRODUCT_ID + " INTEGER REFERENCES " + TABLE_PRODUCTS + " (" + COLUMN_ID + ")"
            + ")";

    private static DatabaseHelper instance;

    private Context context;

    /**
     * Simple constructor for create <code>DatabaseHelper</code> exemplar.
     *
     * @param context is the context, where you want to use <code>DatabaseHelper</code>
     */
    private DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    public static synchronized DatabaseHelper getHelper(Context context) {
        if (instance == null)
            instance = new DatabaseHelper(context);
        return instance;
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        if (!db.isReadOnly()) {
            db.execSQL("PRAGMA foreign_keys = ON;");
        }
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_SHOPPING_LISTS);
        db.execSQL(CREATE_TABLE_PRODUCTS);
        db.execSQL(CREATE_TABLE_SHOPPING_LISTS_PRODUCTS);
        loadDefaultProducts(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SHOPPING_LISTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SHOPPING_LISTS_PRODUCTS);
        onCreate(db);
    }

    /**
     * Method <code>loadDefaultProducts</code> loads products into the database.
     *
     * @param db is the SQLite database.
     */
    private void loadDefaultProducts(SQLiteDatabase db) {
        List<Product> defaultProducts = defaultProducts();
        for (int i = 0; i < defaultProducts.size(); i++) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(DatabaseHelper.COLUMN_NAME,
                    defaultProducts.get(i).getName());
            contentValues.put(DatabaseHelper.COLUMN_COST,
                    defaultProducts.get(i).getCost());
            contentValues.put(DatabaseHelper.COLUMN_POPULARITY,
                    defaultProducts.get(i).getPopularity());
            db.insert(DatabaseHelper.TABLE_PRODUCTS, null, contentValues);
        }
    }

    /**
     * Method <code>defaultProducts</code> fills the list with products.
     *
     * @return default products list.
     */
    private List<Product> defaultProducts() {
        List<Product> defaultProducts = new ArrayList<>();
        defaultProducts.add(new Product(context.getString(R.string.potatoes), 0, 0));
        defaultProducts.add(new Product(context.getString(R.string.cabbage), 0, 0));
        defaultProducts.add(new Product(context.getString(R.string.carrot), 0, 0));
        defaultProducts.add(new Product(context.getString(R.string.tomatoes), 0, 0));
        defaultProducts.add(new Product(context.getString(R.string.cucumbers), 0, 0));
        defaultProducts.add(new Product(context.getString(R.string.garlic), 0, 0));
        defaultProducts.add(new Product(context.getString(R.string.onion), 0, 0));
        defaultProducts.add(new Product(context.getString(R.string.beetroot), 0, 0));
        defaultProducts.add(new Product(context.getString(R.string.apples), 0, 0));
        defaultProducts.add(new Product(context.getString(R.string.bananas), 0, 0));
        defaultProducts.add(new Product(context.getString(R.string.oranges), 0, 0));
        defaultProducts.add(new Product(context.getString(R.string.lemons), 0, 0));
        defaultProducts.add(new Product(context.getString(R.string.butter), 0, 0));
        defaultProducts.add(new Product(context.getString(R.string.olive_oil), 0, 0));
        defaultProducts.add(new Product(context.getString(R.string.sunflower_oil), 0, 0));
        defaultProducts.add(new Product(context.getString(R.string.milk), 0, 0));
        defaultProducts.add(new Product(context.getString(R.string.fish), 0, 0));
        defaultProducts.add(new Product(context.getString(R.string.peas), 0, 0));
        defaultProducts.add(new Product(context.getString(R.string.corn), 0, 0));
        defaultProducts.add(new Product(context.getString(R.string.mushrooms), 0, 0));
        defaultProducts.add(new Product(context.getString(R.string.chicken_meat), 0, 0));
        defaultProducts.add(new Product(context.getString(R.string.pork), 0, 0));
        defaultProducts.add(new Product(context.getString(R.string.beef), 0, 0));
        defaultProducts.add(new Product(context.getString(R.string.spaghetti), 0, 0));
        defaultProducts.add(new Product(context.getString(R.string.rice), 0, 0));
        defaultProducts.add(new Product(context.getString(R.string.buckwheat), 0, 0));
        defaultProducts.add(new Product(context.getString(R.string.mustard), 0, 0));
        defaultProducts.add(new Product(context.getString(R.string.eggs), 0, 0));
        defaultProducts.add(new Product(context.getString(R.string.sugar), 0, 0));
        defaultProducts.add(new Product(context.getString(R.string.salt), 0, 0));
        defaultProducts.add(new Product(context.getString(R.string.coffee), 0, 0));
        defaultProducts.add(new Product(context.getString(R.string.black_tea), 0, 0));
        defaultProducts.add(new Product(context.getString(R.string.green_tea), 0, 0));
        defaultProducts.add(new Product(context.getString(R.string.red_tea), 0, 0));
        defaultProducts.add(new Product(context.getString(R.string.cocoa), 0, 0));
        return defaultProducts;
    }
}