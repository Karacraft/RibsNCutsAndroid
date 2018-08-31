package com.karacraft.ribsncuts;

public interface ICartUpdated
{

    /**
     * We will use this interface
     * to communicate between the MainActivity.class
     * and ShowProductActivity.class (Fragment)
     *
     * Steps:
     * 1- Define Interface
     * 2- Implement it on MainActivity.class
     * 3- Implement the Method & Do whatever you want to
     * 4- Create Interface variable
     * 5- in onActivityCreated() add the following lines to instantiate the interface
     *          if ( getActivity() instanceof ICartUpdated)
     *              {
     *                  myInterface = (ICartUpdated) getActivity();
     *              }
     * 6- Call the method from Fragment and get the response in MainActivity
     * */

    void OnCartUpdate(int cartSize);
}
