package com.karacraft.ribsncuts.cart;

import android.app.Application;

import com.karacraft.ribsncuts.model.Item;

import java.util.ArrayList;

public class Controller extends Application
{

    /**
     * Controller class extends with android.app.Application and defined in the
     * application tag in your AndroidManifest.xml file.
     * Android will create an instance of Controller class and make it available
     * for your entire application context.
     * You can get object of your class on any activity / broadcast receiver / service
     * in application context(environment) by Context.getApplicationContext() method.
     */

    private Cart cart = new Cart();

    public boolean isCartEmpty()
    {
        return cart.isEmpty();
    }

    public Item getItem(int position)
    {
        return cart.getItem(position);
    }

    public void addItem(Item item)
    {
        cart.addItem(item);
    }

    public void removeItem(Item item)
    {
        cart.removeItem(item);
    }

    public int getCartSize()
    {
        return cart.getCartSize();
    }

    public int getTotalPrice()
    {
        return cart.getTotalPrice();
    }

    public int getTotalItems()
    {
        return cart.getTotalItems();
    }

    public int getUniqueItems()
    {
        return cart.getUniqueItems();
    }

    public void clearCart()
    {
        cart.clearCart();
    }

    public ArrayList<Item> getCartContents()
    {
        return cart.CartContents();
    }
}
