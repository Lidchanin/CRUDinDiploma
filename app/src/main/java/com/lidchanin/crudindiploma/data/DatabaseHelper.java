package com.lidchanin.crudindiploma.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

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

    /**
     * Simple constructor for create <code>DatabaseHelper</code> exemplar.
     *
     * @param context is the context, where you want to use <code>DatabaseHelper</code>
     */
    private DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
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
        loadDefaultProductsENG(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SHOPPING_LISTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SHOPPING_LISTS_PRODUCTS);
        onCreate(db);
    }

    /**
     * Method <code>loadDefaultProductsENG</code> loads products into the database.
     *
     * @param db is the SQLite database.
     */
    private void loadDefaultProductsENG(SQLiteDatabase db) {
        List<Product> defaultProductsENG = defaultProductsENG();
        for (int i = 0; i < defaultProductsENG.size(); i++) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(DatabaseHelper.COLUMN_NAME,
                    defaultProductsENG.get(i).getName());
            contentValues.put(DatabaseHelper.COLUMN_COST,
                    defaultProductsENG.get(i).getCost());
            contentValues.put(DatabaseHelper.COLUMN_POPULARITY,
                    defaultProductsENG.get(i).getPopularity());
            db.insert(DatabaseHelper.TABLE_PRODUCTS, null, contentValues);
        }
    }

    /**
     * Method <code>defaultProductsENG</code> fills the list with products. (ENG)
     *
     * @return default products list.
     */
    private List<Product> defaultProductsENG() {
        List<Product> defaultProducts = new ArrayList<>();
        defaultProducts.add(new Product("potatoes", 0, 3));
        defaultProducts.add(new Product("cabbages", 0, 2));
        defaultProducts.add(new Product("carrots", 0, 2));
        defaultProducts.add(new Product("tomatoes", 0, 2));
        defaultProducts.add(new Product("cucumbers", 0, 2));
        defaultProducts.add(new Product("garlics", 0, 2));
        defaultProducts.add(new Product("onions", 0, 2));
        defaultProducts.add(new Product("beetroots", 0, 2));
        defaultProducts.add(new Product("apples", 0, 2));
        defaultProducts.add(new Product("bananas", 0, 2));
        defaultProducts.add(new Product("oranges", 0, 2));
        defaultProducts.add(new Product("lemons", 0, 2));
        defaultProducts.add(new Product("butter", 0, 2));
        defaultProducts.add(new Product("milk", 0, 2));
        defaultProducts.add(new Product("fish", 0, 2));
        defaultProducts.add(new Product("peas", 0, 2));
        defaultProducts.add(new Product("corn", 0, 2));
        defaultProducts.add(new Product("mushrooms", 0, 2));
        defaultProducts.add(new Product("meat", 0, 3));
        defaultProducts.add(new Product("spaghetti", 0, 3));
        defaultProducts.add(new Product("pork", 0, 2));
        defaultProducts.add(new Product("beef", 0, 2));
        defaultProducts.add(new Product("rice", 0, 2));
        defaultProducts.add(new Product("buckwheat", 0, 2));
        defaultProducts.add(new Product("mustard", 0, 2));
        defaultProducts.add(new Product("eggs", 0, 2));
        defaultProducts.add(new Product("sugar", 0, 2));
        defaultProducts.add(new Product("salt", 0, 2));
        defaultProducts.add(new Product("coffee", 0, 2));
        defaultProducts.add(new Product("black tea", 0, 2));
        defaultProducts.add(new Product("green tea", 0, 2));
        defaultProducts.add(new Product("cocoa", 0, 2));
        return defaultProducts;
    }
}