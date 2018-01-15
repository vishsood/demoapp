package com.example.vsood.masterdetail.repository;

import android.arch.lifecycle.LiveData;
import android.arch.paging.PagedList;

import com.example.vsood.masterdetail.common.DataLoadState;
import com.example.vsood.masterdetail.model.Product;

/**
 * Created by vsood on 1/12/18.
 */

public interface ProductRepository {

    public LiveData<PagedList<Product>> getProducts();
    public LiveData<DataLoadState> getDataLoadStatus();
}
