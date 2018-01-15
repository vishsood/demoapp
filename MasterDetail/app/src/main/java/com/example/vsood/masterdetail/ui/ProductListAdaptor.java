package com.example.vsood.masterdetail.ui;

import android.arch.paging.PagedListAdapter;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.recyclerview.extensions.DiffCallback;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.vsood.masterdetail.R;
import com.example.vsood.masterdetail.model.Product;

/**
 * Created by vsood on 1/12/18.
 */

public class ProductListAdaptor extends PagedListAdapter<Product, ProductListAdaptor.ProductViewHolder> {

    private Context mContext;
    private boolean mTwoPane;

    public ProductListAdaptor(Context context, boolean twoPane) {
        super(DIFF_CALLBACK);
        mContext = context;
        mTwoPane = twoPane;
    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.product_list_content, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ProductViewHolder holder, int position) {
        Product product = getItem(position);
        if (product != null) {
            holder.bindTo(product);
        } else {
            // Null defines a placeholder item - PagedListAdapter will automatically invalidate
            // this row when the actual object is loaded from the database
            holder.clear();
        }
    }
    public static final DiffCallback<Product> DIFF_CALLBACK = new DiffCallback<Product>() {
        @Override
        public boolean areItemsTheSame(@NonNull Product oldProduct, @NonNull Product newProduct) {

            return oldProduct.productId == newProduct.productId;
        }
        @Override
        public boolean areContentsTheSame(@NonNull Product oldProduct, @NonNull Product newProduct) {

            return oldProduct.equals(newProduct);
        }
    };

    public class ProductViewHolder extends RecyclerView.ViewHolder {
        private View itemView;
        private ImageView productImage;
        private TextView title;
        private TextView price;
        private TextView inStock;
        private RatingBar rating;
        private Product productItem;

        public ProductViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            this.productImage = (ImageView) itemView.findViewById(R.id.productImage);
            this.title = (TextView) itemView.findViewById(R.id.titleLbl);
            this.price = (TextView) itemView.findViewById(R.id.priceLbl);
            this.inStock = (TextView) itemView.findViewById(R.id.inStoreLbl);
            this.rating = (RatingBar) itemView.findViewById(R.id.ratingBar);

        }

        public void bindTo(Product product) {

            productItem = product;
            productImage.setImageURI(Uri.parse(product.productImage));
            title.setText(product.productName);
            price.setText(product.price);
            inStock.setText(product.inStock ? "In Stock" : "Not Available");
            rating.setRating(product.reviewRating);

            Glide.with(mContext).load(product.productImage)
                    .thumbnail(0.5f)
                    .crossFade()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(productImage);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mTwoPane) {
                        Bundle arguments = new Bundle();
                        //TODO - check if have to create copy of product
                        arguments.putParcelable(ProductDetailFragment.ARG_PRODUCT, productItem);
                        ProductDetailFragment fragment = new ProductDetailFragment();
                        fragment.setArguments(arguments);
                        ((AppCompatActivity) mContext).getSupportFragmentManager().beginTransaction()
                                .replace(R.id.product_detail_container, fragment)
                                .commit();
                    } else {
                        Context context = v.getContext();
                        Intent intent = new Intent(context, ProductDetailActivity.class);
                        intent.putExtra(ProductDetailFragment.ARG_PRODUCT, productItem);

                        context.startActivity(intent);
                    }
                }


            });
        }

        public void clear() {
                //TODO
            productItem = null;
            productImage.setImageURI(null);
            title.setText(null);
            price.setText(null);
            inStock.setText(null);
            rating.setRating(0);

        }
    }


}
