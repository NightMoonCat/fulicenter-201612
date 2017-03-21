package cn.moon.fulicenter.model.utils;

import android.content.Context;
import android.content.SharedPreferences;

import cn.moon.fulicenter.application.FuLiCenterApplication;

/**
 * Created by Moon on 2017/3/21.
 */

public class SharedPreferencesUtils {
    private static final String SHARED_PREFERENCES_NAME = "cn.moon.fulicenter_save_userinfo";
    private static final String SAVE_USERINFO_USERNAME = "m_user_name";
    static SharedPreferencesUtils instance;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    public SharedPreferencesUtils() {
        sharedPreferences = FuLiCenterApplication.getInstance()
                .getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

    }
    public static SharedPreferencesUtils getInstance() {
        if (instance == null) {
            instance = new SharedPreferencesUtils();
        }
        return instance;
    }

    public void setUserName(String userName) {
        editor.putString(SAVE_USERINFO_USERNAME, userName).commit();
    }
    public String  getUserName() {
         return sharedPreferences.getString(SAVE_USERINFO_USERNAME,null);
    }
    public void removeUser() {
        editor.remove(SAVE_USERINFO_USERNAME).commit();
    }

}
