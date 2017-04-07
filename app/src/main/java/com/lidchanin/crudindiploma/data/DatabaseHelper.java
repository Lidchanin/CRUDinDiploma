package com.lidchanin.crudindiploma.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Class {@link DatabaseHelper} extends {@link SQLiteOpenHelper}. This class need to work with
 * SQLite database.
 *
 * @author Lidchanin
 * @see SQLiteOpenHelper
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    // FIXME: 03.04.2017 Log constant
    private static final String LOG = "DatabaseHelper";

    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_NAME = "personal_shopping_lists";
    private static final String TABLE_SHOPPING_LISTS = "shopping_lists";
    private static final String TABLE_PRODUCTS = "products";
    private static final String TABLE_SHOPPING_LISTS_PRODUCTS = "shopping_lists_products";
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_COST = "cost";
    private static final String KEY_POPULARITY = "popularity";
    private static final String KEY_LIST_ID = "list_id";
    private static final String KEY_PRODUCT_ID = "product_id";

    private static final String CREATE_TABLE_SHOPPING_LISTS = "CREATE TABLE " + TABLE_SHOPPING_LISTS
            + "("
            + KEY_ID + " INTEGER PRIMARY KEY, "
            + KEY_NAME + " TEXT NOT NULL"
            + ")";
    private static final String CREATE_TABLE_PRODUCTS = "CREATE TABLE " + TABLE_PRODUCTS
            + "("
            + KEY_ID + " INTEGER PRIMARY KEY, "
            + KEY_NAME + " TEXT NOT NULL, "
            + KEY_COST + " REAL, "
            + KEY_POPULARITY + " INTEGER"
            + ")";
    private static final String CREATE_TABLE_SHOPPING_LISTS_PRODUCTS = "CREATE TABLE " + TABLE_SHOPPING_LISTS_PRODUCTS
            + "("
            + KEY_ID + " INTEGER PRIMARY KEY, "
            + KEY_LIST_ID + " INTEGER, "
            + KEY_PRODUCT_ID + " INTEGER"
            + ")";

    /**
     * Simple constructor for create <code>DatabaseHelper</code> exemplar.
     *
     * @param context is the context, where you want to use <code>DatabaseHelper</code>
     */
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_SHOPPING_LISTS);
        db.execSQL(CREATE_TABLE_PRODUCTS);
        db.execSQL(CREATE_TABLE_SHOPPING_LISTS_PRODUCTS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO: 03.04.2017 Sanya, method for you, if you want to upgrade project)
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SHOPPING_LISTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SHOPPING_LISTS_PRODUCTS);
        onCreate(db);
    }

    /**
     * Method <code>getShoppingList</code> reads one shopping list in the database.
     *
     * @param shoppingListId is the shopping list id, which you want to read.
     * @return shopping list, which you need, or null.
     */
    public ShoppingList getShoppingList(long shoppingListId) {
        SQLiteDatabase database = this.getWritableDatabase();
        String selectQuery = "SELECT * FROM " + TABLE_SHOPPING_LISTS + " WHERE "
                + KEY_ID + " = " + shoppingListId;
        Log.i(LOG, selectQuery);
        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor != null) {
            cursor.moveToFirst();
            ShoppingList shoppingList = new ShoppingList();
            shoppingList.setId(cursor.getLong(cursor.getColumnIndex(KEY_ID)));
            shoppingList.setName(cursor.getString(cursor.getColumnIndex(KEY_NAME)));
            cursor.close();
            return shoppingList;
        } else {
            return null;
        }
    }

    /**
     * Method <code>getAllShoppingLists</code> reads all shopping lists in the database.
     *
     * @return all shopping lists, which you need, or empty shopping lists array.
     */
    public List<ShoppingList> getAllShoppingLists() {
        List<ShoppingList> shoppingLists = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_SHOPPING_LISTS;
        Log.i(LOG, selectQuery);
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                ShoppingList shoppingList = new ShoppingList();
                shoppingList.setId(cursor.getLong(cursor.getColumnIndex(KEY_ID)));
                shoppingList.setName(cursor.getString(cursor.getColumnIndex(KEY_NAME)));
                shoppingLists.add(shoppingList);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return shoppingLists;
    }

    /**
     * Method <code>addShoppingList</code> adds a shopping list to database.
     *
     * @param shoppingList is shopping list, which you want to add to the database.
     * @return shopping list id in the database.
     */
    public long addShoppingList(ShoppingList shoppingList) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_NAME, shoppingList.getName());
        return database.insert(TABLE_SHOPPING_LISTS, null, contentValues);
    }

    /**
     * Method <code>updateShoppingList</code> update shopping list in the database.
     *
     * @param shoppingList is the shopping list, which you need to update.
     * @return updated shopping list id.
     */
    public long updateShoppingList(ShoppingList shoppingList) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_NAME, shoppingList.getName());
        return database.update(TABLE_SHOPPING_LISTS, contentValues, KEY_ID + " = ?",
                new String[]{String.valueOf(shoppingList.getId())});
    }

    /**
     * Method <code>deleteShoppingList</code> delete shopping list in the database.
     *
     * @param shoppingListId is the shopping list id, which you want to delete.
     */
    public void deleteShoppingList(long shoppingListId) {
        SQLiteDatabase database = this.getWritableDatabase();
        database.delete(TABLE_SHOPPING_LISTS, KEY_ID + " = ?",
                new String[]{String.valueOf(shoppingListId)});
    }

    /**
     * Method <code>getProductById</code> reads one product by id in the database.
     *
     * @param productId is the product id, which you want to read.
     * @return product, which you need, or empty product.
     */
    public Product getProductById(long productId) {
        SQLiteDatabase database = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + TABLE_PRODUCTS + " WHERE "
                + KEY_ID + " = " + productId;
        Log.i(LOG, selectQuery);
        Cursor cursor = database.rawQuery(selectQuery, null);
        Product product = new Product();
        if (cursor.moveToFirst()) {
            product.setId(cursor.getLong(cursor.getColumnIndex(KEY_ID)));
            product.setName(cursor.getString(cursor.getColumnIndex(KEY_NAME)));
            product.setCost(cursor.getDouble(cursor.getColumnIndex(KEY_COST)));
            product.setPopularity(cursor.getLong(cursor.getColumnIndex(KEY_POPULARITY)));
        }
        cursor.close();
        return product;
    }

    /**
     * Method <code>getProductByName</code> reads one product by name in the database.
     *
     * @param productName is the product name, which you want to read.
     * @return product, which you need, or null.
     */
    public Product getProductByName(String productName) {
        SQLiteDatabase database = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + TABLE_PRODUCTS + " WHERE "
                + KEY_NAME + " = " + "'" + productName + "'";
        Log.i(LOG, selectQuery);
        Cursor cursor = database.rawQuery(selectQuery, null);
        Product product = new Product();
        if (cursor.moveToFirst()) {
            product.setId(cursor.getLong(cursor.getColumnIndex(KEY_ID)));
            product.setName(cursor.getString(cursor.getColumnIndex(KEY_NAME)));
            product.setCost(cursor.getDouble(cursor.getColumnIndex(KEY_COST)));
            product.setPopularity(cursor.getLong(cursor.getColumnIndex(KEY_POPULARITY)));
            cursor.close();
            return product;
        } else {
            cursor.close();
            return null;
        }
    }

    /**
     * Method <code>getAllProducts</code> gets all products from the database.
     *
     * @return all products, which you need, or empty products array.
     */
    public List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_PRODUCTS;
        Log.i(LOG, selectQuery);
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                Product product = new Product();
                product.setId(cursor.getLong(cursor.getColumnIndex(KEY_ID)));
                product.setName(cursor.getString(cursor.getColumnIndex(KEY_NAME)));
                product.setCost(cursor.getDouble(cursor.getColumnIndex(KEY_COST)));
                product.setPopularity(cursor.getLong(cursor.getColumnIndex(KEY_POPULARITY)));
                products.add(product);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return products;
    }

    /**
     * Method <code>getAllProductsFromCurrentShoppingListById</code> gets all products in needed
     * shopping list from the database.
     *
     * @param shoppingListId is the shopping list id, which contains needed products.
     * @return all products in shopping list, which you need, or null.
     */
    public List<Product> getAllProductsFromCurrentShoppingListById(long shoppingListId) {
        List<Product> products = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_PRODUCTS + " tp, "
                + TABLE_SHOPPING_LISTS + " tl, " + TABLE_SHOPPING_LISTS_PRODUCTS + " tlp "
                + "WHERE tl." + KEY_ID + " = '" + shoppingListId + "'"
                + " AND " + "tp." + KEY_ID + " = " + "tlp." + KEY_PRODUCT_ID
                + " AND " + "tl." + KEY_ID + " = " + "tlp." + KEY_LIST_ID;
        Log.i(LOG, selectQuery);
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                Product product = new Product();
                product.setId(cursor.getLong(cursor.getColumnIndex(KEY_ID)));
                product.setName(cursor.getString(cursor.getColumnIndex(KEY_NAME)));
                product.setCost(cursor.getDouble(cursor.getColumnIndex(KEY_COST)));
                product.setPopularity(cursor.getLong(cursor.getColumnIndex(KEY_POPULARITY)));
                Log.d(LOG, "_________product id:" + product.getId()
                        + "\t\tname:" + product.getName()
                        + "\t\tcost:" + product.getCost()
                        + "\t\tpopularity:" + product.getPopularity()
                );
                products.add(product);
            } while (cursor.moveToNext());
            cursor.close();
            return products;
        } else {
            cursor.close();
            return null;
        }
    }

    /**
     * Method <code>addProductInCurrentShoppingList</code> adds product in the database and assign
     * it to shopping list.
     *
     * @param product        is the product, which you want to add to the database.
     * @param shoppingListId is the shopping list id.
     * @return added or updated product id.
     *
     * @see #assignProductToShoppingList(long, long)
     * @see #isExistRelationship(long, long) 
     */
    public long addProductInCurrentShoppingList(Product product, long shoppingListId) {
        long productId;
        if (getProductByName(product.getName()) == null) {
            Log.d(LOG, "Product is not exist.");
            SQLiteDatabase database = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(KEY_NAME, product.getName());
            contentValues.put(KEY_COST, product.getCost());
            contentValues.put(KEY_POPULARITY, product.getPopularity());
            productId = database.insert(TABLE_PRODUCTS, null, contentValues);
            assignProductToShoppingList(shoppingListId, productId);
        } else {
            Log.d(LOG, "Product is already exist.");
            product.setId(getProductByName(product.getName()).getId());
            product.setPopularity(product.getPopularity() + 1);
            productId = updateProduct(product);
            if (!isExistRelationship(shoppingListId, productId)) {
                assignProductToShoppingList(shoppingListId, productId);
            }
        }
        return productId;
    }

    /**
     * Method <code>updateProduct</code> update product in the database.
     *
     * @param product is the product, which you need to update.
     * @return updated product id.
     */
    private long updateProduct(Product product) {
        SQLiteDatabase database = this.getWritableDatabase();
        Log.d(LOG, "_________UPDATED product id:" + product.getId()
                + "\t\tname:" + product.getName()
                + "\t\tcost:" + product.getCost()
                + "\t\tpopularity:" + product.getPopularity()
        );
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_NAME, product.getName());
        contentValues.put(KEY_COST, product.getCost());
        contentValues.put(KEY_POPULARITY, product.getPopularity());
        return database.update(TABLE_PRODUCTS, contentValues, KEY_ID + " = ?",
                new String[]{String.valueOf(product.getId())});
    }

    /**
     * Method <code>deleteProduct</code> delete product in the database.
     *
     * @param productId is the product id, which you want to delete.
     */
    public void deleteProduct(long productId) {
        SQLiteDatabase database = this.getWritableDatabase();
        database.delete(TABLE_PRODUCTS, KEY_ID + " = ?",
                new String[]{String.valueOf(productId)});
    }

    /**
     * Method <code>assignProductToShoppingList</code> assign product to shopping list.
     *
     * @param shoppingListId is the shopping list id.
     * @param productId      is the product id, which you want to assign to shopping list.
     */
    private void assignProductToShoppingList(long shoppingListId, long productId) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_LIST_ID, shoppingListId);
        contentValues.put(KEY_PRODUCT_ID, productId);
        database.insert(TABLE_SHOPPING_LISTS_PRODUCTS, null, contentValues);
    }

    /**
     * Method <code>isExistRelationship</code> checks the relationship exists or not.
     *
     * @param shoppingListId is the shopping list id.
     * @param productId is the product id.
     * @return there is relationship or not.
     */
    private boolean isExistRelationship(long shoppingListId, long productId) {
        SQLiteDatabase database = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + TABLE_SHOPPING_LISTS_PRODUCTS + " WHERE "
                + KEY_LIST_ID + " = " + shoppingListId + " AND "
                + KEY_PRODUCT_ID + " = " + productId;
        Log.i(LOG, selectQuery);
        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            cursor.close();
            return true;
        } else {
            cursor.close();
            return false;
        }
    }

    // FIXME: 07.04.2017 delete method, because it's need for test only.
    public List<Long[]> getRelationships() {
        List<Long[]> relationships = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_SHOPPING_LISTS_PRODUCTS;
        Log.i(LOG, selectQuery);
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                Long[] ids = new Long[3];
                ids[0] = cursor.getLong(cursor.getColumnIndex(KEY_ID));
                ids[1] = cursor.getLong(cursor.getColumnIndex(KEY_LIST_ID));
                ids[2] = cursor.getLong(cursor.getColumnIndex(KEY_PRODUCT_ID));
                relationships.add(ids);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return relationships;
    }
}