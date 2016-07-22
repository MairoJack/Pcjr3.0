package com.pcjinrong.pcjr;

import android.app.Application;
import com.orhanobut.logger.Logger;


/**
 * Created by Mario on 2016/7/8.
 */
public class App extends Application {

    private static App mInstance = new App();
    public boolean log = true;

    public static final long ONE_KB = 1024L;
    public static final long ONE_MB = ONE_KB * 1024L;
    public static final long CACHE_DATA_MAX_SIZE = ONE_MB * 3L;

    public static App getInstance(){
        return mInstance;
    }

    @Override public void onCreate() {
        super.onCreate();
        mInstance = this;
        Logger.init();

    }





}
