package com.karacraft.ribsncuts;

import com.karacraft.ribsncuts.model.Item;

public interface ICartOperations {

    /**
     * We will use this interface
     * to communicate between the MainActivity.class
     * and ShowProductActivity.class / CartActivity.class (Fragment)
     *
     * Steps:
     * 1- Define Interface
     * 2- Implement it on MainActivity.class
     * 3- Implement the Method & Do whatever you want to
     * 4- Create Interface variable
     * 5- in onActivityCreated() add the following lines to instantiate the interface
     *          if ( getActivity() instanceof ICartOperations)
     *              {
     *                  myInterface = (ICartOperations) getActivity();
     *              }
     * 6- Call the method from Fragment and get the response in MainActivity
     * */

    void OnCartUpdate(int cartSize);
    void OnItemAddedToCart(Item item);

}
