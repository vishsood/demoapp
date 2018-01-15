package com.example.vsood.masterdetail.ui;

import android.app.Activity;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.design.widget.CollapsingToolbarLayout;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.vsood.masterdetail.R;
import com.example.vsood.masterdetail.dummy.DummyContent;
import com.example.vsood.masterdetail.model.Product;

/**
 * A fragment representing a single Product detail screen.
 * This fragment is either contained in a {@link ProductListActivity}
 * in two-pane mode (on tablets) or a {@link ProductDetailActivity}
 * on handsets.
 */
public class ProductDetailFragment extends Fragment {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_PRODUCT = "product";

    private Product mProduct;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ProductDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_PRODUCT)) {
            mProduct = (Product) getArguments().getParcelable(ARG_PRODUCT);

            Activity activity = this.getActivity();
            CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
            if (appBarLayout != null) {
                appBarLayout.setTitle("Product Detail");
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.product_detail, container, false);

        // Show the dummy content as text in a TextView.
        if (mProduct != null) {
            ImageView imageVw = (ImageView) rootView.findViewById(R.id.imageView);
            Glide.with(getActivity()).load(mProduct.productImage)
                    .into(imageVw);

            ((TextView) rootView.findViewById(R.id.nameLbl)).setText(mProduct.productName);
            ((TextView) rootView.findViewById(R.id.shortLbl)).setText(Html.fromHtml(mProduct.shortDescription));
            ((TextView) rootView.findViewById(R.id.longLbl)).setText(Html.fromHtml(mProduct.longDescription));
            ((TextView) rootView.findViewById(R.id.priceLbl)).setText(mProduct.price);
            ((TextView) rootView.findViewById(R.id.inStoreLbl)).setText(mProduct.inStock ? "In Stock"
                                                                                    : "Not Available");
            ((RatingBar) rootView.findViewById(R.id.ratingBar)).setRating(mProduct.reviewRating);
            ((TextView) rootView.findViewById(R.id.reviewCountLbl)).setText(String.valueOf(mProduct.reviewCount));
        }

        return rootView;
    }
}
