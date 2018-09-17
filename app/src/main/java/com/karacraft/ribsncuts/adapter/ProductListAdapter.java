package com.karacraft.ribsncuts.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.karacraft.ribsncuts.BuildConfig;
import com.karacraft.ribsncuts.ICartOperations;
import com.karacraft.ribsncuts.R;
import com.karacraft.ribsncuts.helper.Constants;
import com.karacraft.ribsncuts.model.Item;
import com.karacraft.ribsncuts.model.Product;
import com.squareup.picasso.Picasso;

import java.util.List;

import static com.karacraft.ribsncuts.helper.Constants.TAG;

/**
 * This ProductLIstAdapter uses ICartOperations Interface
 */
public class ProductListAdapter extends BaseAdapter {

    private Context context;
    private List<Product> products;
    private String type;

    /** Constructor*/
    public ProductListAdapter(Context context, List<Product> products, String type) {
        this.context = context;
        this.products = products;
        this.type = type;
    }

    @Override
    public int getCount() {
        return products.size();
    }

    @Override
    public Object getItem(int position) {
        return products.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View view, ViewGroup viewGroup) {

        View v;

        v = View.inflate(context, R.layout.products_list,null);

        TextView tv_product_title = v.findViewById(R.id.tv_product_title);
        TextView tv_product_best_for = v.findViewById(R.id.tv_product_best_for);
        TextView tv_product_price = v.findViewById(R.id.tv_product_price);
        Button btnAddToCartProductList = v.findViewById(R.id.btnAddToCartProductList);
        btnAddToCartProductList.setTag(position);
        btnAddToCartProductList.setOnClickListener(myButtonOnClickListener);

        /** Set Image & Text */
        tv_product_title.setText(products.get(position).getTitle());
        tv_product_best_for.setText(products.get(position).getBestFor());
        tv_product_price.setText(String.valueOf(products.get(position).getPrice()) + " / Kg");

        ImageView img_product_image = v.findViewById(R.id.img_product_image);

        /** Setup Image */
        Picasso
                .get()
                .load(Constants.IMAGE_URL + products.get(position).getImage())
                .placeholder(R.drawable.ic_ribsncuts_white)
//                .resize(960,617)
//                .resize(1080,694)
                .into(img_product_image);
        
        v.setTag(products.get(position).getId());


//        return null;
        return v;
    }

    private View.OnClickListener myButtonOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
        int position = (int) view.getTag();

        if(BuildConfig.DEBUG)
            Log.d(TAG, "myButtonOnClickListener() invoked");

        if(context instanceof ICartOperations)
        {
            Item item = new Item();
            item.setId(products.get(position).getId());
            item.setName(products.get(position).getTitle());
            item.setImage(products.get(position).getImage());
            item.setPrice(products.get(position).getPrice());
            item.setQty(1);
            //Now Call the Method in Listener
            ((ICartOperations)context).OnItemAddedToCart(item);
        }

        }
    };

}
