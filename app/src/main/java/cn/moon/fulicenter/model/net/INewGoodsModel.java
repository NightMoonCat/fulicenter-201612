package cn.moon.fulicenter.model.net;

import android.content.Context;

/**
 * Created by Moon on 2017/3/15.
 */

public interface INewGoodsModel {
    void loadData(Context context,int pageId,OnCompleteListener listener);
}
