package cn.moon.fulicenter.model.dao;

import android.content.Context;

import cn.moon.fulicenter.application.FuLiCenterApplication;
import cn.moon.fulicenter.model.bean.User;
import cn.moon.fulicenter.model.utils.SharedPreferencesUtils;

/**
 * Created by Moon on 2017/3/21.
 */

public class UserDao {
    public static final String USER_TABLE_NAME = "t_superwechat_user";
    public static final String USER_COLUMN_NAME = "m_user_name";
    public static final String USER_COLUMN_NICK = "m_user_nick";
    public static final String USER_COLUMN_AVATAR = "m_user_avatar_id";
    public static final String USER_COLUMN_AVATAR_PATH = "m_user_avatar_path";
    public static final String USER_COLUMN_AVATAR_SUFFIX = "m_user_avatar_suffix";
    public static final String USER_COLUMN_AVATAR_TYPE = "m_user_avatar_type";
    public static final String USER_COLUMN_AVATAR_LASTUPDATE_TIME =
            "m_user_avatar_lastupdate_time";

    private static UserDao instance;

    public static UserDao getInstance(Context context) {
        if (instance == null) {
            instance = new UserDao(context);
        }
        return instance;
    }

    public UserDao(Context context) {
        DBManager.getInstance().initDB(context);
    }

    public boolean saveUserInfo(User user) {
        return DBManager.getInstance().saveUserInfo(user);
    }

    public User getUserInfo(String userName) {
        if (userName == null) {
            return null;
        } else {
            return DBManager.getInstance().getUserInfo(userName);
        }
    }

    public void logout() {
        FuLiCenterApplication.setCurrentUser(null);
        SharedPreferencesUtils.getInstance().removeUser();
        DBManager.getInstance().closeDB();
    }
}
