package org.example;

public class Item {
    private int id;
    private float quantity;
    private String name;
    private float cost;
    public Item(int id, String name, float quantity, float cost){
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.cost = cost;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getCost() {
        return cost;
    }

    public String getName() {
        return name;
    }

    public float getQuantity() {
        return quantity;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCost(float cost) {
        this.cost = cost;
    }

    public void setQuantity(float quantity) {
        this.quantity = quantity;
    }

    public void addToCost(float cost) {
        this.cost += cost;
    }

    public void addToQuantity(float quantity) {
        this.quantity += quantity;
    }

    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", quantity=" + quantity +
                ", name='" + name + '\'' +
                ", cost=" + cost +
                '}';
    }
}
