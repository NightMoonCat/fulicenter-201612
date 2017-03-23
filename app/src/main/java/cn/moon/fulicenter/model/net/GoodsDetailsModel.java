package cn.moon.fulicenter.model.net;

import android.content.Context;

import cn.moon.fulicenter.application.I;
import cn.moon.fulicenter.model.bean.GoodsDetailsBean;
import cn.moon.fulicenter.model.bean.MessageBean;
import cn.moon.fulicenter.model.utils.OkHttpUtils;

/**
 * Created by Moon on 2017/3/16.
 */

public class GoodsDetailsModel implements IGoodsDetailsModel {

    @Override
    public void loadData(Context context, int goodId, OnCompleteListener<GoodsDetailsBean> listener) {
        OkHttpUtils<GoodsDetailsBean> okHttpUtils = new OkHttpUtils<>(context);
        okHttpUtils.setRequestUrl(I.REQUEST_FIND_GOOD_DETAILS)
                .addParam(I.Goods.KEY_GOODS_ID,String.valueOf(goodId))
                .targetClass(GoodsDetailsBean.class)
                .execute(listener);
    }

    @Override
    public void collectAction(Context context, int action,int goodsId, String userName, OnCompleteListener<MessageBean> listener) {
        String request = I.REQUEST_IS_COLLECT;
        if (action == I.ACTION_ADD_COLLECT) {
            request = I.REQUEST_ADD_COLLECT;
        } else if (action == I.ACTION_DELETE_COLLECT) {
            request = I.REQUEST_DELETE_COLLECT;
        }
        OkHttpUtils<MessageBean> okHttpUtils = new OkHttpUtils<>(context);
        okHttpUtils.setRequestUrl(request)
                .addParam(I.Collect.USER_NAME,userName)
                .addParam(I.Goods.KEY_GOODS_ID,String.valueOf(goodsId))
                .targetClass(MessageBean.class)
                .execute(listener);
    }
}
