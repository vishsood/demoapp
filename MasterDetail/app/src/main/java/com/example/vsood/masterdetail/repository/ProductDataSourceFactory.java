package com.example.vsood.masterdetail.repository;

import android.arch.lifecycle.MutableLiveData;
import android.arch.paging.DataSource;

import com.example.vsood.masterdetail.api.WalmartApi;
import com.example.vsood.masterdetail.api.WalmartService;
import com.example.vsood.masterdetail.model.Product;

import timber.log.Timber;

/**
 * Created by vsood on 1/12/18.
 */

public class ProductDataSourceFactory implements DataSource.Factory<Integer, Product> {

    public MutableLiveData<ProductDataSource> datasourceLiveData = new MutableLiveData<>();

    @Override
    public DataSource<Integer, Product> create() {
        Timber.d("ProductDataSourceFactory::create");
        ProductDataSource dataSource = new ProductDataSource(WalmartService.get());
        datasourceLiveData.postValue(dataSource);
        return dataSource;
    }
}
