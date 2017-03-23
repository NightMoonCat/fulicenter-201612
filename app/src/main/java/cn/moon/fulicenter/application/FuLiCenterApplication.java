package cn.moon.fulicenter.application;

import android.app.Application;

import cn.moon.fulicenter.model.bean.User;
import cn.moon.fulicenter.model.dao.UserDao;
import cn.moon.fulicenter.model.utils.SharedPreferencesUtils;

/**
 * Created by Moon on 2017/3/14.
 */

public class FuLiCenterApplication extends Application {
    static FuLiCenterApplication instance;
    static User currentUser;

    public static User getCurrentUser() {
        if (currentUser == null) {
            final String userName = SharedPreferencesUtils.getInstance().getUserName();
//            new Thread(new Runnable() {
//                @Override
//                public void run() {
                    currentUser = UserDao.getInstance(instance).getUserInfo(userName);
//                }
//            }).start();
        }
        return currentUser;
    }

    public static void setCurrentUser(User currentUser) {
        FuLiCenterApplication.currentUser = currentUser;
    }

    public static FuLiCenterApplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }
}
