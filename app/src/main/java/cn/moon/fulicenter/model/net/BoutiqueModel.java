package cn.moon.fulicenter.model.net;

import android.content.Context;

import cn.moon.fulicenter.application.I;
import cn.moon.fulicenter.model.bean.BoutiqueBean;
import cn.moon.fulicenter.model.bean.NewGoodsBean;
import cn.moon.fulicenter.model.utils.OkHttpUtils;

/**
 * Created by Moon on 2017/3/15.
 */

public class BoutiqueModel implements IBoutiqueModel {

    @Override
    public void loadData(Context context,OnCompleteListener listener) {
        OkHttpUtils<BoutiqueBean[]> utils = new OkHttpUtils<>(context);
        utils.setRequestUrl(I.REQUEST_FIND_BOUTIQUES)
                .targetClass(BoutiqueBean[].class)
                .execute(listener);

    }
}
