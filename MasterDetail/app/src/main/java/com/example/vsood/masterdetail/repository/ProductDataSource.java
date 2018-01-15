package com.example.vsood.masterdetail.repository;

import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.paging.PageKeyedDataSource;
import android.support.annotation.NonNull;

import com.example.vsood.masterdetail.api.ProductResponse;
import com.example.vsood.masterdetail.api.WalmartApi;
import com.example.vsood.masterdetail.common.DataLoadState;
import com.example.vsood.masterdetail.model.Product;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Response;
import timber.log.Timber;

/**
 * Created by vsood on 1/12/18.
 */

public class ProductDataSource extends PageKeyedDataSource<Integer, Product> {

    private WalmartApi walmartApi;

    public final MutableLiveData loadState;

    public ProductDataSource(WalmartApi walmartApi) {
        Timber.d("CREATING ProductDataSource..api="+walmartApi);
        this.walmartApi = walmartApi;
        loadState = new MutableLiveData<DataLoadState>();

    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull LoadInitialCallback<Integer, Product> callback) {
        Timber.d("loadInitial params="+params.requestedLoadSize);
        loadState.postValue(DataLoadState.LOADING);

        Call<ProductResponse> request = walmartApi.getProducts(1, params.requestedLoadSize);
        Timber.d("Retrofit Request="+request.request());
        Response<ProductResponse> response = null;
        try{
            response = request.execute();
            if(response != null) {
                callback.onResult(response.body().products, 1, 2);
            }else {
                callback.onResult(null, null,2);
            }
            loadState.postValue(DataLoadState.LOADED);
        }catch (IOException ex) {
            loadState.postValue(DataLoadState.FAILED);
        }

    }

    @Override
    public void loadBefore(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, Product> callback) {

        Call<ProductResponse> request = walmartApi.getProducts(params.key, params.requestedLoadSize);

        Response<ProductResponse> response = null;
        try{
            response = request.execute();
            if(response != null) {
                Integer adjacentKey = (params.key > 1) ? params.key - 1 : null;
                callback.onResult(response.body().products, adjacentKey);
            }else {
                callback.onResult(null, params.key - 1);
            }
        }catch (IOException ex) {
            //networkState.postValue();
        }
    }

    @Override
    public void loadAfter(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, Product> callback) {
        Call<ProductResponse> request = walmartApi.getProducts(params.key , params.requestedLoadSize);

        Response<ProductResponse> response = null;
        try{
            response = request.execute();
            if(response != null) {
                callback.onResult(response.body().products, params.key + 1);
            }else {
                callback.onResult(null, params.key + 1 );
            }
        }catch (IOException ex) {
            //networkState.postValue();
        }
    }
}
