package com.karacraft.ribsncuts.model;

public class Item
{

    private int id;
    private String name;
    private String image;
    private int price;
    private int qty;

    public Item()
    {

    }

    public Item(int id, String name, String image, int price, int qty) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.price = price;
        this.qty = qty;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }
}
