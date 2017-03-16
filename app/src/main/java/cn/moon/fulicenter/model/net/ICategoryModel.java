package cn.moon.fulicenter.model.net;

import android.content.Context;

import cn.moon.fulicenter.model.bean.CategoryChildBean;
import cn.moon.fulicenter.model.bean.CategoryGroupBean;
import cn.moon.fulicenter.model.bean.NewGoodsBean;

/**
 * Created by Moon on 2017/3/15.
 */

public interface ICategoryModel {
    void loadGroupData(Context context, OnCompleteListener<CategoryGroupBean[]> listener);
    void loadChildData(Context context, int parentId,OnCompleteListener<CategoryChildBean[]> listener);
}
