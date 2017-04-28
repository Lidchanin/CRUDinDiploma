package com.lidchanin.crudindiploma.data.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.lidchanin.crudindiploma.data.DatabaseHelper;
import com.lidchanin.crudindiploma.data.model.ShoppingList;

import java.util.ArrayList;
import java.util.List;

import static com.lidchanin.crudindiploma.data.DatabaseHelper.TABLE_SHOPPING_LISTS;
import static com.lidchanin.crudindiploma.data.DatabaseHelper.TABLE_SHOPPING_LISTS_PRODUCTS;

/**
 * Class <code>ShoppingListDAO</code> extends {@link DatabaseDAO} and implements database operations
 * such as add, update, deleteInDatabase, get {@link com.lidchanin.crudindiploma.data.model.ShoppingList}.
 *
 * @author Lidchanin
 */
public class ShoppingListDAO extends DatabaseDAO {

    private static final String WHERE_ID_EQUALS = DatabaseHelper.COLUMN_ID + " =?";
    private static final String WHERE_LIST_ID_EQUALS = DatabaseHelper.COLUMN_LIST_ID + " =?";

    public ShoppingListDAO(Context context) {
        super(context);
    }

    /**
     * Method <code>add</code> add shopping list to database.
     *
     * @param shoppingList is shopping list, which you want to add to the database.
     * @return added shopping list id in the database.
     */
    public long add(ShoppingList shoppingList) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.COLUMN_NAME, shoppingList.getName());
        return database.insert(DatabaseHelper.TABLE_SHOPPING_LISTS, null, contentValues);
    }

    /**
     * Method <code>update</code> update shopping list in the database.
     *
     * @param shoppingList is the shopping list, which you need to update.
     * @return the number of rows affected.
     */
    public long update(ShoppingList shoppingList) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.COLUMN_NAME, shoppingList.getName());
        return database.update(TABLE_SHOPPING_LISTS, contentValues, WHERE_ID_EQUALS,
                new String[]{String.valueOf(shoppingList.getId())});
    }

    /**
     * Method <code>deleteInDatabase</code> deleteInDatabase shopping list in the database.
     *
     * @param shoppingList is the shopping list, which you want to deleteInDatabase.
     */
    public void delete(ShoppingList shoppingList) {
        deleteRelationships(shoppingList.getId());
        database.delete(DatabaseHelper.TABLE_SHOPPING_LISTS, WHERE_ID_EQUALS,
                new String[]{String.valueOf(shoppingList.getId())});
    }

    /**
     * Method <code>getOne</code> get shopping list by id from the database.
     *
     * @param shoppingListId is the shopping list id, which you want to get.
     * @return needed shopping list.
     */
    public ShoppingList getOne(long shoppingListId) {
        Cursor cursor = database.query(DatabaseHelper.TABLE_SHOPPING_LISTS,
                new String[]{DatabaseHelper.COLUMN_ID, DatabaseHelper.COLUMN_NAME}, WHERE_ID_EQUALS,
                new String[]{String.valueOf(shoppingListId)}, null, null, null, String.valueOf(1));
        ShoppingList shoppingList = new ShoppingList();
        shoppingList.setId(cursor.getLong(cursor.getColumnIndex(DatabaseHelper.COLUMN_ID)));
        shoppingList.setName(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_NAME)));
        cursor.close();
        return shoppingList;
    }

    /**
     * Method <code>getAll</code> get all shopping lists in the database.
     *
     * @return all shopping lists, which you need, or empty shopping lists array.
     */
    public List<ShoppingList> getAll() {
        List<ShoppingList> shoppingLists = new ArrayList<>();
        Cursor cursor = database.query(DatabaseHelper.TABLE_SHOPPING_LISTS,
                new String[]{DatabaseHelper.COLUMN_ID, DatabaseHelper.COLUMN_NAME}, null, null,
                null, null, null);
        if (cursor.moveToFirst()) {
            do {
                ShoppingList shoppingList = new ShoppingList();
                shoppingList.setId(cursor.getLong(0));
                shoppingList.setName(cursor.getString(1));
                shoppingLists.add(shoppingList);
            } while (cursor.moveToNext());
            cursor.close();
        }
        return shoppingLists;
    }

    /**
     * Method <code>deleteRelationships</code> need to deleteInDatabase relationships, i.e. deleteInDatabase all
     * products from the current shopping list.
     *
     * @param shoppingListId is the current shopping list id.
     */
    private void deleteRelationships(long shoppingListId) {
        database.delete(TABLE_SHOPPING_LISTS_PRODUCTS, WHERE_LIST_ID_EQUALS,
                new String[]{String.valueOf(shoppingListId)});
    }

    // FIXME: 11.04.2017 only for test
    public List<Long[]> getAllRelationships() {
        List<Long[]> relationships = new ArrayList<>();
        Cursor cursor = database.query(DatabaseHelper.TABLE_SHOPPING_LISTS_PRODUCTS,
                new String[]{DatabaseHelper.COLUMN_ID, DatabaseHelper.COLUMN_LIST_ID,
                        DatabaseHelper.COLUMN_PRODUCT_ID},
                null, null, null, null, null);
        if ((cursor.moveToFirst())) {
            do {
                Long[] relationship = new Long[3];
                relationship[0] = cursor.getLong(cursor.getColumnIndex(DatabaseHelper.COLUMN_ID));
                relationship[1] = cursor.getLong(cursor.getColumnIndex(DatabaseHelper.COLUMN_LIST_ID));
                relationship[2] = cursor.getLong(cursor.getColumnIndex(DatabaseHelper.COLUMN_PRODUCT_ID));
                relationships.add(relationship);
            } while (cursor.moveToNext());
            cursor.close();
        }
        return relationships;
    }

}