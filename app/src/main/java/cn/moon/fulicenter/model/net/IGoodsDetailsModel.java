package cn.moon.fulicenter.model.net;

import android.content.Context;

import cn.moon.fulicenter.model.bean.GoodsDetailsBean;

/**
 * Created by Moon on 2017/3/16.
 */

public interface IGoodsDetailsModel {
    void loadData(Context context,int goodId, OnCompleteListener<GoodsDetailsBean> listener);

}
