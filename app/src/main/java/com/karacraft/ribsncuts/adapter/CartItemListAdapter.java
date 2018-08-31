package com.karacraft.ribsncuts.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.karacraft.ribsncuts.R;
import com.karacraft.ribsncuts.helper.Constants;
import com.karacraft.ribsncuts.model.Item;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CartItemListAdapter extends BaseAdapter {

    private Context context;
    private List<Item> items;

    /** Constructor*/
    public CartItemListAdapter(Context context, List<Item> items) {
        this.context = context;
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {

        View v;

        v = View.inflate(context, R.layout.cart_list,null);

        TextView tv_item_title = v.findViewById(R.id.tv_item_title);
        TextView tv_item_qty = v.findViewById(R.id.tv_item_qty);
        TextView tv_item_price = v.findViewById(R.id.tv_item_price);

        /** Set Image & Text */

        tv_item_title.setText(items.get(position).getTitle());
        tv_item_qty.setText (String.valueOf(items.get(position).getQuantity()));
        tv_item_price.setText(String.valueOf(items.get(position).getPrice()));

        ImageView iv_item_image = v.findViewById(R.id.iv_item_image);

        /** Setup Image */
        Picasso
                .get()
                .load(Constants.IMAGE_URL + items.get(position).getImage())
                .placeholder(R.drawable.ic_ribsncuts)
//                .resize(960,617)
//                .resize(1080,694)
                .into(iv_item_image);


        v.setTag(items.get(position).getId());
        return v;
    }
}
