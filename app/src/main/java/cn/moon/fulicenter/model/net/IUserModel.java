package cn.moon.fulicenter.model.net;

import android.content.Context;

import java.io.File;

import cn.moon.fulicenter.model.bean.CollectBean;
import cn.moon.fulicenter.model.bean.MessageBean;

/**
 * Created by Moon on 2017/3/20.
 */

public interface IUserModel {
    void login(Context context, String userName, String password,
               OnCompleteListener<String> listener);

    void register(Context context, String userName, String nickName, String password,
                  OnCompleteListener<String> listener);

    void updateNick(Context context, String userName, String newNick,
                    OnCompleteListener<String> listener);

    void updateAvatar(Context context, String userName, File file,
                      OnCompleteListener<String> listener);

    void loadCollectCount(Context context, String userName,
                          OnCompleteListener<MessageBean> listener);
    void loadCollect(Context context, String userName,int pageId,int pageSize,
                          OnCompleteListener<CollectBean[]> listener);

}
