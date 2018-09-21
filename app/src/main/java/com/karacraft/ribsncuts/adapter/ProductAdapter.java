package com.karacraft.ribsncuts.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.karacraft.ribsncuts.R;
import com.karacraft.ribsncuts.helper.Constants;
import com.karacraft.ribsncuts.model.Product;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static com.karacraft.ribsncuts.helper.Constants.TAG;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder>
{

    ArrayList<Product> products;
    ItemClicked listener;
    Context context;
    int position;

    public ProductAdapter(Context context, ArrayList<Product> products , ItemClicked listener)
    {
        this.products = products;
        this.listener = listener;
        this.context = context;
    }

    /** Interface */
    public interface ItemClicked
    {
        void onItemClicked(int position); //card view
        void onAddButtonClicked(int position); //Add to Cart Green Little Button
    }

    /**
     * ViewHolder Class
     */
    public class ViewHolder extends RecyclerView.ViewHolder
    {
        Button btnAddToCartProductList;
        TextView tv_product_title,tv_product_best_for,tv_product_price;
        ImageView img_product_image;


        public ViewHolder(View itemView)
        {
            super(itemView);
            btnAddToCartProductList = itemView.findViewById(R.id.btnAddToCartProductList);
            tv_product_title = itemView.findViewById(R.id.tv_product_title);
            tv_product_best_for = itemView.findViewById(R.id.tv_product_best_for);
            tv_product_price = itemView.findViewById(R.id.tv_product_price);
            img_product_image = itemView.findViewById(R.id.img_product_image);
        }

    }


    @NonNull
    @Override
    public ProductAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.products_list,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder,final int position)
    {
        holder.itemView.setTag(products.get(position));
        this.position = position;
        Log.d(TAG, "onBindViewHolder: position is : " + position);

        holder.tv_product_title.setText(products.get(position).getTitle());
        holder.tv_product_best_for.setText(products.get(position).getBestFor());
        holder.tv_product_price.setText(String.valueOf(products.get(position).getPrice()));

        /** Setup Image */
        Picasso
                .get()
                .load(Constants.IMAGE_URL + products.get(position).getImage())
                .placeholder(R.drawable.ic_ribsncuts_white)
//                .resize(960,617)
//                .resize(1080,694)
                .into(holder.img_product_image);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null)
//                    listener.onItemClicked(products.indexOf(view.getTag()));
                        listener.onItemClicked(position);
            }
        });

        holder.btnAddToCartProductList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null)
//                        listener.onAddButtonClicked(products.indexOf(view.getTag()));
                    listener.onAddButtonClicked(position);
            }
        });


    }

    @Override
    public int getItemCount()
    {
        return products.size();
    }
}
