package com.example.vsood.masterdetail;

import android.app.Application;
import android.util.Log;

import timber.log.Timber;

/**
 * Created by vsood on 1/13/18.
 */

public class MasterDetailDemo extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Log.d("MasterDetailDemo", "Application onCreate ....creating Timber");
        //if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        //}
    }
}
