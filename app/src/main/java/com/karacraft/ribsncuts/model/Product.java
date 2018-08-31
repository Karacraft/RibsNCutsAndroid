package com.karacraft.ribsncuts.model;

public class Product {

    private int id;
    private String title;
    private String cutSource;
    private String bestFor;
    private String description;
    private String image;
    private int price;
    private String category;
    private String slug;

    public Product()
    {

    }

    public Product(int id, String title, String cutSource, String bestFor, String description, String image, int price, String category, String slug) {
        this.id = id;
        this.title = title;
        this.cutSource = cutSource;
        this.bestFor = bestFor;
        this.description = description;
        this.image = image;
        this.price = price;
        this.category = category;
        this.slug = slug;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCutSource() {
        return cutSource;
    }

    public void setCutSource(String cutSource) {
        this.cutSource = cutSource;
    }

    public String getBestFor() {
        return bestFor;
    }

    public void setBestFor(String bestFor) {
        this.bestFor = bestFor;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }
}
