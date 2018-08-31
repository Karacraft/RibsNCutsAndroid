package com.karacraft.ribsncuts.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.karacraft.ribsncuts.R;
import com.karacraft.ribsncuts.helper.Constants;
import com.karacraft.ribsncuts.model.Product;
import com.squareup.picasso.Picasso;

import java.util.List;

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
    public View getView(int position, View view, ViewGroup viewGroup) {

        View v;

        v = View.inflate(context, R.layout.products_list,null);

        TextView tv_product_title = v.findViewById(R.id.tv_product_title);
        TextView tv_product_best_for = v.findViewById(R.id.tv_product_best_for);
        TextView tv_product_price = v.findViewById(R.id.tv_product_price);

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
}
