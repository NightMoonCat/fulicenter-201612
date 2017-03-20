package cn.moon.fulicenter.model.net;

import android.content.Context;

import cn.moon.fulicenter.application.I;
import cn.moon.fulicenter.model.utils.OkHttpUtils;

/**
 * Created by Moon on 2017/3/20.
 */

public class UserModel implements IUserModel {
    @Override
    public void login(Context context, String userName, String password,
                      OnCompleteListener<String> listener) {
        OkHttpUtils<String> utils = new OkHttpUtils<>(context);
        utils.setRequestUrl(I.REQUEST_LOGIN)
                .addParam(I.User.USER_NAME, userName)
                .addParam(I.User.PASSWORD, password)
                .targetClass(String.class)
                .execute(listener);

    }

    @Override
    public void register(Context context, String userName, String nickName, String password,
                         OnCompleteListener<String> listener) {
        OkHttpUtils<String> utils = new OkHttpUtils<>(context);
        utils.setRequestUrl(I.REQUEST_REGISTER)
                .post()
                .addParam(I.User.USER_NAME, userName)
                .addParam(I.User.NICK,nickName)
                .addParam(I.User.PASSWORD, password)
                .targetClass(String.class)
                .execute(listener);
    }
}
