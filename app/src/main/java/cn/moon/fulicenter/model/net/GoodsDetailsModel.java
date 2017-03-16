package cn.moon.fulicenter.model.net;

import android.content.Context;

import cn.moon.fulicenter.application.I;
import cn.moon.fulicenter.model.bean.GoodsDetailsBean;
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
}
