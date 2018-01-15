package com.example.vsood.masterdetail.repository;

import android.arch.lifecycle.LiveData;
import android.arch.paging.LivePagedListBuilder;
import android.arch.paging.PagedList;
import android.arch.paging.PagedList.Config.Builder;
import android.support.annotation.MainThread;

import com.example.vsood.masterdetail.AppExecutors;
import com.example.vsood.masterdetail.common.DataLoadState;
import com.example.vsood.masterdetail.model.Product;

import java.util.concurrent.Executor;

import static android.arch.lifecycle.Transformations.switchMap;

/**
 * Created by vsood on 1/12/18.
 */

public class ProductRepositoryImpl implements  ProductRepository{

    //private Executor networkExecutor;
    ProductDataSourceFactory dataSourceFactory;
    private static final int PAGE_SIZE = 30;
    private LiveData<PagedList<Product>> products;

    public ProductRepositoryImpl() {
        dataSourceFactory = new ProductDataSourceFactory();
        //this.networkExecutor = networkExecutor;
    }

    @Override
    @MainThread
    public LiveData<PagedList<Product>> getProducts() {

        PagedList.Config config = new PagedList.Config.Builder()
                                        .setInitialLoadSizeHint(PAGE_SIZE)
                                        .setPageSize(PAGE_SIZE)
                                        .build();


        products = new LivePagedListBuilder(dataSourceFactory, config)
                        .setInitialLoadKey(1)
                        .setBackgroundThreadExecutor(AppExecutors.networkIO())
                        .build();

        return products;
    }

    public LiveData<DataLoadState> getDataLoadStatus(){
        return switchMap(dataSourceFactory.datasourceLiveData,
                            dataSource -> dataSource.loadState);
    }
}
