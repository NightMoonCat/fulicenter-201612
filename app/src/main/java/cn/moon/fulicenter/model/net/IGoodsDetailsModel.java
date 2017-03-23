package cn.moon.fulicenter.model.net;

import android.content.Context;

import cn.moon.fulicenter.model.bean.GoodsDetailsBean;
import cn.moon.fulicenter.model.bean.MessageBean;

/**
 * Created by Moon on 2017/3/16.
 */

public interface IGoodsDetailsModel {
    void loadData(Context context, int goodId, OnCompleteListener<GoodsDetailsBean> listener);

    void collectAction(Context context,int action, int goodsId, String userName,
                       OnCompleteListener<MessageBean> listener);

}
