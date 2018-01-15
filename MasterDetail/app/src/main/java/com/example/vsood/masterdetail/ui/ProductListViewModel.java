package com.example.vsood.masterdetail.ui;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.paging.PagedList;
import android.support.annotation.NonNull;

import com.example.vsood.masterdetail.common.DataLoadState;
import com.example.vsood.masterdetail.model.Product;
import com.example.vsood.masterdetail.repository.ProductDataSourceFactory;
import com.example.vsood.masterdetail.repository.ProductRepository;
import com.example.vsood.masterdetail.repository.ProductRepositoryImpl;

/**
 * Created by vsood on 1/12/18.
 */

public class ProductListViewModel  extends AndroidViewModel {

    private ProductRepository repository;

    public ProductListViewModel(@NonNull Application application) {
        super(application);
        repository = new ProductRepositoryImpl();
    }

    public LiveData<PagedList<Product>> getProducts() {
        return repository.getProducts();
    }

    public LiveData<DataLoadState> dataLoadStatus() {
        return repository.getDataLoadStatus();
    }

}
