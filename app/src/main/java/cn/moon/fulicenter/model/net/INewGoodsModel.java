package cn.moon.fulicenter.model.net;

import android.content.Context;

import cn.moon.fulicenter.model.bean.NewGoodsBean;

/**
 * Created by Moon on 2017/3/15.
 */

public interface INewGoodsModel {
    void loadData(Context context,int catId,int pageId,OnCompleteListener<NewGoodsBean[]> listener);
}
