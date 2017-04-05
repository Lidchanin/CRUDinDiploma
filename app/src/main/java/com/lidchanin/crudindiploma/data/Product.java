package com.lidchanin.crudindiploma.data;

/**
 * Class {@link Product} ia an entity. Product contains id, name, cost and
 * popularity.
 *
 * @author Lidchanin
 */
public class Product {

    private long id;
    private String name;
    private double cost;
    // Product popularity increases every time, where product is used.
    private long popularity = 0;

    /**
     * Constructor for create an empty product.
     */
    public Product() {
    }

    /**
     * Constructor for create a product with name and cost.
     * @param name is the product name.
     * @param cost is the product cost.
     */
    public Product(String name, double cost) {
        this.name = name;
        this.cost = cost;
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

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public long getPopularity() {
        return popularity;
    }

    public void setPopularity(long popularity) {
        this.popularity = popularity;
    }

}
