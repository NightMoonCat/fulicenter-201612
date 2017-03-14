package cn.moon.fulicenter.application;

import android.app.Application;

/**
 * Created by Moon on 2017/3/14.
 */

public class FuLiCenterApplication extends Application {
    static FuLiCenterApplication instance;

    public static FuLiCenterApplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }
}
