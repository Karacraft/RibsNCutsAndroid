package com.karacraft.ribsncuts.cart;

import android.util.Log;

import com.karacraft.ribsncuts.model.Item;

import java.util.ArrayList;

import static com.karacraft.ribsncuts.helper.Constants.TAG;

public class Cart
{

    //    private ArrayList<Product> products;
    private ArrayList<Item> items;

    public Cart()
    {
//        products = new ArrayList<Product>();
        items = new ArrayList<Item>();
    }

    public Item getItem(int position)
    {
        return items.get(position);
    }

    public void addItem(Item item)
    {
        if (items.contains(item))
        {
            increaseItem(item);
            Log.d(TAG, "Increasing already added item...");
        } else
        {
            items.add(item);
            Log.d(TAG, "New Item added...");
        }

    }

    public void removeItem(Item item)
    {

        int rowId = item.getId();

        for (Item i :
                items)
        {
            if (i.getId() == rowId)
            {
                items.remove(i);
            }
        }
    }

    public void increaseItem(Item item)
    {
        int rowId = item.getId();

        for (Item i :
                items)
        {
            if (i.getId() == rowId)
            {
                int qty = i.getQuantity();
                //Set the new quantity & price
                i.setQuantity(qty + 1);
            }
        }

    }

    public void decreaseItem(Item item)
    {
        int rowId = item.getId();

        for (Item i :
                items)
        {
            if (i.getId() == rowId)
            {
                if (i.getQuantity() > 1)
                {
                    int qty = i.getQuantity();
                    i.setQuantity(qty - 1);
                } else if (i.getQuantity() == 1)
                {
                    removeItem(i);
                }

            }
        }
    }

    public int getCartSize()
    {
        return items.size();
    }

    public int getTotalPrice()
    {
        int Price = 0;
        for (Item item :
                items)
        {
            Price = Price + (item.getQuantity() * item.getPrice());
        }

        return Price;
    }

    public int getTotalItems()
    {
        int tItems = 0;

        for (Item item :
                items)
        {
            tItems = tItems + item.getQuantity();
        }

        return tItems;
    }

    public int getUniqueItems()
    {
        int tItems = 0;
        for (Item item :
                items)
        {
            tItems++;
        }

        return tItems;
    }

    public void clearCart()
    {
        items.clear();
    }

    public ArrayList<Item> CartContents()
    {
        return items;
    }
}
