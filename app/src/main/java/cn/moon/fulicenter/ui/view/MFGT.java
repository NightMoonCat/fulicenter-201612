package cn.moon.fulicenter.ui.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import java.util.ArrayList;
import java.util.List;

import cn.moon.fulicenter.R;
import cn.moon.fulicenter.application.I;
import cn.moon.fulicenter.model.bean.BoutiqueBean;
import cn.moon.fulicenter.model.bean.CategoryChildBean;
import cn.moon.fulicenter.ui.activity.BoutiqueChildActivity;
import cn.moon.fulicenter.ui.activity.CategoryChildActivity;
import cn.moon.fulicenter.ui.activity.GoodsDetailsActivity;
import cn.moon.fulicenter.ui.activity.MainActivity;

/**
 * Created by Moon on 2017/3/16.
 */

public class MFGT {
    public static void startActivity(Activity activity, Class cls) {
        activity.startActivity(new Intent(activity, cls));
        activity.overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
    }

    public static void startActivity(Activity activity, Intent intent) {
        activity.startActivity(intent);
        activity.overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
    }

    public static void finish(Activity activity) {
        activity.finish();
        activity.overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
    }

    public static void gotoMain(Activity activity) {
        startActivity(activity, MainActivity.class);
    }

    public static void gotoBoutiqueChild(Context activity, BoutiqueBean bean) {
        activity.startActivity(new Intent(activity, BoutiqueChildActivity.class)
                .putExtra(I.NewAndBoutiqueGoods.CAT_ID, bean.getId())
                .putExtra(I.Boutique.TITLE, bean.getTitle()));
    }

    public static void gotoGoodsDetails(Context activity, int goodsId) {
        activity.startActivity(new Intent(activity, GoodsDetailsActivity.class)
                .putExtra(I.Goods.KEY_GOODS_ID,goodsId));

    }


    public static void gotoCategoryChildActivity(Context context, int goodsId, String groupName,
                                                 ArrayList<CategoryChildBean> list) {
        context.startActivity(new Intent(context, CategoryChildActivity.class)
        .putExtra(I.NewAndBoutiqueGoods.CAT_ID,goodsId)
        .putExtra(I.CategoryGroup.NAME,groupName)
        .putExtra(I.CategoryChild.DATA,list));
    }
}
