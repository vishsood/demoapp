package com.example.vsood.masterdetail.api;

import okhttp3.OkHttpClient;
//import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by vsood on 1/12/18.
 */

public class WalmartService {

    private static final WalmartService service = new WalmartService();
    private static WalmartApi api;

    private static final String API_URL
            = "https://walmartlabs-test.appspot.com/_ah/api/walmart/v1/walmartproducts/"+
                "56330880-82c2-47eb-9d5d-03db3a49e3bf/";

    private WalmartService() {

        /*HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder okHttpBuilder = new OkHttpClient.Builder();
        okHttpBuilder.addInterceptor(loggingInterceptor);
        OkHttpClient client = okHttpBuilder.build();*/

        api = new Retrofit.Builder()
                .baseUrl(API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                //.client(client)
                .build()
                .create(WalmartApi.class);
    }

    public static WalmartApi get() {
        return service.api;
    }
}
