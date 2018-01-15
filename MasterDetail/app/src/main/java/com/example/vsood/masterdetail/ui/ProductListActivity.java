package com.example.vsood.masterdetail.ui;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import com.example.vsood.masterdetail.R;
import com.example.vsood.masterdetail.common.DataLoadState;
import com.example.vsood.masterdetail.dummy.DummyContent;
import com.example.vsood.masterdetail.repository.ProductRepository;
import com.example.vsood.masterdetail.ui.ProductDetailActivity;
import com.example.vsood.masterdetail.ui.ProductDetailFragment;

import java.util.List;

import timber.log.Timber;

/**
 * An activity representing a list of Products. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link ProductDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class ProductListActivity extends AppCompatActivity {

    private static final String TAG = "ProductListActivity";
    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;
    private ProductListViewModel mProductsViewModel;
    private ProductListAdaptor mAdapter;

    private ProgressBar mLoadProgressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Timber.v("ON CREATE");
        Log.d(TAG, "onCreate()");

        setContentView(R.layout.activity_product_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        mLoadProgressBar = findViewById(R.id.progress_bar);

        View recyclerView = findViewById(R.id.product_list);
        assert recyclerView != null;

        if (findViewById(R.id.product_detail_container) != null) {
            mTwoPane = true;
        }
        setupRecyclerView((RecyclerView) recyclerView);

        mProductsViewModel = ViewModelProviders.of(this).get(ProductListViewModel.class);
        mProductsViewModel.dataLoadStatus().observe(this, loadStatus -> {
            switch (loadStatus) {
                case LOADING:
                    mLoadProgressBar.setVisibility(View.VISIBLE);
                    break;
                case LOADED:
                    mLoadProgressBar.setVisibility(View.GONE);
                    break;
                case FAILED:
                    mLoadProgressBar.setVisibility(View.GONE);
                    Toast.makeText(this,"Failed to connect to Walmart Service",
                                        Toast.LENGTH_LONG);
                    break;
            }
        });
        mProductsViewModel.getProducts().observe(this, pagedList -> {
                mAdapter.setList(pagedList);

        });


    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        mAdapter = new ProductListAdaptor(this, mTwoPane);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setHasFixedSize(true);

        recyclerView.addItemDecoration(new DividerItemDecoration(this,
                                                DividerItemDecoration.VERTICAL));
    }

}
