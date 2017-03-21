package cn.moon.fulicenter.model.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import cn.moon.fulicenter.model.bean.User;

/**
 * Created by Moon on 2017/3/21.
 */

public class DBManager {
    private static DBOpenHelper mHelper;
    static DBManager dbmgr = new DBManager();

    public static DBManager getInstance() {
        return dbmgr;
    }

    public synchronized void initDB(Context context) {
        mHelper = DBOpenHelper.getInstance(context);
    }

    public boolean saveUserInfo(User user) {
        SQLiteDatabase database = mHelper.getWritableDatabase();
        if (database.isOpen()) {
            ContentValues values = new ContentValues();
            values.put(UserDao.USER_COLUMN_NAME, user.getMuserName());
            values.put(UserDao.USER_COLUMN_NICK, user.getMuserNick());
            values.put(UserDao.USER_COLUMN_AVATAR, user.getMavatarId());
            values.put(UserDao.USER_COLUMN_AVATAR_PATH, user.getMavatarPath());
            values.put(UserDao.USER_COLUMN_AVATAR_TYPE, user.getMavatarType());
            values.put(UserDao.USER_COLUMN_AVATAR_SUFFIX, user.getMavatarSuffix());
            values.put(UserDao.USER_COLUMN_AVATAR_LASTUPDATE_TIME, user.getMavatarLastUpdateTime());
            return database.insert(UserDao.USER_TABLE_NAME, null, values) != -1;
        }
        return false;
    }

    public User getUserInfo(String userName) {
        SQLiteDatabase database = mHelper.getReadableDatabase();
        if (database.isOpen()) {
            String sql = "select * from " + UserDao.USER_TABLE_NAME +
                    " where " + UserDao.USER_COLUMN_NAME + "='" + userName + "'";
            Cursor cursor = database.rawQuery(sql, null);
            while (cursor.moveToNext()) {
                User user = new User();
                user.setMuserName(userName);
                user.setMuserNick(cursor.getString(cursor.getColumnIndex(UserDao.USER_COLUMN_NICK)));
                user.setMavatarId(cursor.getInt(cursor.getColumnIndex(UserDao.USER_COLUMN_AVATAR)));
                user.setMavatarPath(cursor.getString(cursor.getColumnIndex(UserDao.USER_COLUMN_AVATAR_PATH)));
                user.setMavatarType(cursor.getInt(cursor.getColumnIndex(UserDao.USER_COLUMN_AVATAR_TYPE)));
                user.setMavatarSuffix(cursor.getString(cursor.getColumnIndex(UserDao.USER_COLUMN_AVATAR_SUFFIX)));
                user.setMavatarLastUpdateTime(cursor.getString(cursor.getColumnIndex(UserDao.USER_COLUMN_AVATAR_LASTUPDATE_TIME)));
                return user;
            }
        }
        return null;
    }
}
