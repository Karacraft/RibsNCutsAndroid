package com.karacraft.ribsncuts;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.karacraft.ribsncuts.cart.Controller;
import com.karacraft.ribsncuts.helper.Constants;
import com.karacraft.ribsncuts.helper.CustomToast;
import com.karacraft.ribsncuts.model.Item;
import com.karacraft.ribsncuts.model.Product;
import com.squareup.picasso.Picasso;

import static com.karacraft.ribsncuts.helper.Constants.TAG;


/**
 * A simple {@link Fragment} subclass.
 */
public class ShowProductActivity extends Fragment
{

    ICartOperations myInterface;

    Button btnAddToCart;
    ImageView ivProduct;
    TextView tvProductTitle;
    TextView tvDescription;
    TextView tvPrice;

    public ShowProductActivity()
    {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.show_product_fragment, container, false);
        //Setup Title
        getActivity().setTitle(R.string.title_show_product_details);

        /** Get Global Controller Class object (See applicaiton tag in anrdroidmanifest.xml )*/
        final Controller controller = (Controller) getActivity().getApplicationContext();

        final Bundle bundle = getArguments();
        final Product product = new Product(
                bundle.getInt(Constants.KEY_ROWID),
                bundle.getString(Constants.KEY_TITLE),
                bundle.getString(Constants.KEY_CUTSOURCE),
                bundle.getString(Constants.KEY_BESTFOR),
                bundle.getString(Constants.KEY_DESCRIPTION),
                bundle.getString(Constants.KEY_IMAGE),
                bundle.getInt(String.valueOf(Constants.KEY_PRICEPERKG)),
                bundle.getString(Constants.KEY_CATEGORY),
                bundle.getString(Constants.KEY_SLUG)
                );

        tvProductTitle = view.findViewById(R.id.tv_product_title_full);
        ivProduct = view.findViewById(R.id.iv_product_image_full);
        tvDescription = view.findViewById(R.id.tv_description_full);
        tvPrice = view.findViewById(R.id.tv_price_full);
        btnAddToCart = view.findViewById(R.id.btnAddToCart);

        Picasso
                .get()
                .load(Constants.IMAGE_URL + product.getImage())
                .placeholder(R.drawable.ic_ribsncuts_white)
//                .resize(960,617)
//                .resize(1080,694)
                .into(ivProduct);

        tvProductTitle.setText(product.getTitle());
        tvDescription.setText(product.getDescription());
        tvPrice.setText(String.valueOf(product.getPrice()) + " /Kg");


        //Create Item and Add to Cart
        final Item item  = new Item();
        item.setId(product.getId());
        item.setName(product.getTitle());
        item.setImage(product.getImage());
        item.setPrice(product.getPrice());
        item.setQty(1);

        btnAddToCart.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
//                controller.addItem(item);
                myInterface.OnItemAddedToCart(item);
                myInterface.OnCartUpdate(controller.getCartSize());
                CustomToast.showToastMessage(item.getName() +" added to cart.", getContext(), Toast.LENGTH_SHORT);

                if(BuildConfig.DEBUG)
                    Log.d(TAG, "Cart Items[Unique]: " + controller.getUniqueItems() + " Items[Total]: " + controller.getTotalItems() + " Price: " + controller.getTotalPrice());

            }
        });

        return view;
    }

    /**
     * Called when the fragment's activity has been created and this
     * fragment's view hierarchy instantiated.  It can be used to do final
     * initialization once these pieces are in place, such as retrieving
     * views or restoring state.  It is also useful for fragments that use
     * {@link #setRetainInstance(boolean)} to retain their instance,
     * as this callback tells the fragment when it is fully associated with
     * the new activity instance.  This is called after {@link #onCreateView}
     * and before {@link #onViewStateRestored(Bundle)}.
     *
     * @param savedInstanceState If the fragment is being re-created from
     *                           a previous saved state, this is the state.
     */
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        if ( getActivity() instanceof ICartOperations)
        {
            myInterface = (ICartOperations) getActivity();
        }
    }
}
