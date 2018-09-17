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
import com.squareup.picasso.Picasso;

import java.util.List;

import static com.karacraft.ribsncuts.helper.Constants.TAG;

public class CartItemListAdapter extends BaseAdapter {

    /** interface */
    public interface IDataChangeListener
    {
        void OnDataChanged();
    }

    public void setOnDataChangeListener(IDataChangeListener iDataChangeListener)
    {
        this.iDataChangeListener = iDataChangeListener;

        if(BuildConfig.DEBUG)
            Log.d(TAG, "setOnDataChangeListener: is Set");
    }
    /** End */



    private Context context;
    private List<Item> items;
    IDataChangeListener iDataChangeListener;  //Interface instance

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

        tv_item_title.setText(items.get(position).getName());
        tv_item_qty.setText (items.get(position).getQty() + " Kg");
        tv_item_price.setText("PKR / Kg : " + items.get(position).getPrice());

        ImageView iv_item_image = v.findViewById(R.id.iv_item_image);

        /** Setup Image */
        Picasso
                .get()
                .load(Constants.IMAGE_URL + items.get(position).getImage())
                .placeholder(R.drawable.ic_ribsncuts_white)
//                .resize(960,617)
//                .resize(1080,694)
                .into(iv_item_image);


        v.setTag(items.get(position).getId());

        Button btnIncreaseItem = v.findViewById(R.id.btnIncreaseItem);
        btnIncreaseItem.setTag(position);
        btnIncreaseItem.setOnClickListener(increaseItemButtonClickListener);


        Button btnDecreaseItem = v.findViewById(R.id.btnDecreaseItem);
        btnDecreaseItem.setTag(position);
        btnDecreaseItem.setOnClickListener(decreaseItemButtonClickListener);

        return v;
    }

    private View.OnClickListener increaseItemButtonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            int position = (int) view.getTag();

            if(BuildConfig.DEBUG)
                Log.d(TAG, "increaseItemButtonClickListener() invoked");

            if(context instanceof ICartOperations)
            {
                Item item = new Item();
                item.setId(items.get(position).getId());
                item.setName(items.get(position).getName());
                item.setImage(items.get(position).getImage());
                item.setPrice(items.get(position).getPrice());
                item.setQty(1);
                //Now Call the Method in Listener
                ((ICartOperations)context).OnIncreaseItemInCart(item);
                //Raise the Event
                if(iDataChangeListener != null){
                    iDataChangeListener.OnDataChanged();
                }
            }

        }
    };

    private View.OnClickListener decreaseItemButtonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            int position = (int) view.getTag();

            if(BuildConfig.DEBUG)
                Log.d(TAG, "decreaseItemButtonClickListener() invoked");

            if(context instanceof ICartOperations)
            {
                Item item = new Item();
                item.setId(items.get(position).getId());
                item.setName(items.get(position).getName());
                item.setImage(items.get(position).getImage());
                item.setPrice(items.get(position).getPrice());
                item.setQty(1);
                //Now Call the Method in Listener
                ((ICartOperations)context).OnDecreaseItemInCart(item);

                //Riase the event
                if(iDataChangeListener != null)
                {
                    iDataChangeListener.OnDataChanged();
                }
            }

        }
    };

}
