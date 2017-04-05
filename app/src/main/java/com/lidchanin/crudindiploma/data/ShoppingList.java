package com.lidchanin.crudindiploma.data;

/**
 * Class {@link ShoppingList} is an entity. Shopping list contains id, name.
 *
 * @author Lidchanin
 */
public class ShoppingList {

    private long id;
    private String name;

    /**
     * Constructor for create an empty shopping list.
     */
    public ShoppingList() {
    }

    /**
     * Constructor for create a shopping list.
     * @param name is the shopping list name.
     */
    public ShoppingList(String name) {
        this.name = name;
    }

    // getters and setters

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
