package com.karacraft.ribsncuts.cart;

import android.util.Log;

import com.karacraft.ribsncuts.BuildConfig;
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

    public boolean isEmpty()
    {
        return  items.isEmpty();
    }

    public Item getItem(int position)
    {
        return items.get(position);
    }

    public void addItem(Item item)
    {
        if(inRecord(item))
        {
            increaseItem(item);
        }
        else
        {
            items.add(item);


            if(BuildConfig.DEBUG)
                Log.d(TAG, "Item Added.");
        }

    }

    public void increaseItem(Item item)
    {
        for (Item i : items)
        {
            if (i.getId() == item.getId())
            {
                int qty = i.getQty();
                i.setQty(qty + 1);
                return;
            }
        }

        if(BuildConfig.DEBUG)
            Log.d(TAG, "increaseItem: + 1");
    }

    public void removeItem(Item item)
    {
        if(inRecord(item))
            items.remove(item);


        if(BuildConfig.DEBUG)
            Log.d(TAG, "Item Removed.");
    }

    public void decreaseItem(Item item)
    {
        for (Item i : items)
        {
            if (i.getId() == item.getId())
            {
                int qty = i.getQty();
                if (qty == 1)
                {
                    removeItem(i);
                    return;
                }
                i.setQty(qty - 1);
            }
        }


        if(BuildConfig.DEBUG)
            Log.d(TAG, "decreaseItem: - 1");
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
            Price = Price + (item.getQty() * item.getPrice());
        }

        return Price;
    }

    public int getTotalItems()
    {
        int tItems = 0;

        for (Item item :
                items)
        {
            tItems = tItems + item.getQty();
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

    private boolean inRecord(Item item)
    {
        int rowId = item.getId();

        for (Item i :
                items)
        {
            if (i.getId() == rowId)
                return true;
        }

        return false;
    }

}
