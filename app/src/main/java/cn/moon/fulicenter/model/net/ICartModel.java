package cn.moon.fulicenter.model.net;

import android.content.Context;

import cn.moon.fulicenter.model.bean.CartBean;
import cn.moon.fulicenter.model.bean.MessageBean;

/**
 * Created by Moon on 2017/3/24.
 */

public interface ICartModel {
    void loadData(Context context, String userName, OnCompleteListener<CartBean[]> listener);

    void cartAction(Context context, int action, String cartId, String goodsId,
                    String userName, int count, OnCompleteListener<MessageBean> listener);
}
