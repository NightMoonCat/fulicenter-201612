package cn.moon.fulicenter.model.net;

import android.content.Context;

/**
 * Created by Moon on 2017/3/20.
 */

public interface IUserModel {
    void login(Context context, String userName, String password,
               OnCompleteListener<String> listener);

    void register(Context context, String userName, String nickName, String password,
                  OnCompleteListener<String> listener);
}
