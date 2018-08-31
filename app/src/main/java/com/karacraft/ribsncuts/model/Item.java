package com.karacraft.ribsncuts.model;

public class Item
{

    private int Id;
    private String Title;
    private String Image;
    private int Price;
    private int Quantity;

    public Item()
    {

    }

    public Item(int id, String title, String image, int price, int quantity)
    {
        Id = id;
        Title = title;
        Image = image;
        Price = price;
        Quantity = quantity;
    }

    public int getId()
    {
        return Id;
    }

    public void setId(int id)
    {
        Id = id;
    }

    public String getTitle()
    {
        return Title;
    }

    public void setTitle(String title)
    {
        Title = title;
    }

    public String getImage()
    {
        return Image;
    }

    public void setImage(String image)
    {
        Image = image;
    }

    public int getPrice()
    {
        return Price;
    }

    public void setPrice(int price)
    {
        Price = price;
    }

    public int getQuantity()
    {
        return Quantity;
    }

    public void setQuantity(int quantity)
    {
        Quantity = quantity;
    }
}
