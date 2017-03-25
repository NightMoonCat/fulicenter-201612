package cn.moon.fulicenter.model.net;

import android.content.Context;

import cn.moon.fulicenter.application.I;
import cn.moon.fulicenter.model.bean.CartBean;
import cn.moon.fulicenter.model.bean.MessageBean;
import cn.moon.fulicenter.model.utils.OkHttpUtils;

/**
 * Created by Moon on 2017/3/24.
 */

public class CartModel implements ICartModel {
    @Override
    public void loadData(Context context, String username, OnCompleteListener<CartBean[]> listener) {
        OkHttpUtils<CartBean[]> utils = new OkHttpUtils<>(context);
        utils.setRequestUrl(I.REQUEST_FIND_CARTS)
                .addParam(I.Cart.USER_NAME,username)
                .targetClass(CartBean[].class)
                .execute(listener);
    }

    @Override
    public void cartAction(Context context, int action, String cartId, String goodsId, String userName,
                           int count, OnCompleteListener<MessageBean> listener) {
        OkHttpUtils<MessageBean> utils = new OkHttpUtils<>(context);
        if (action == I.ACTION_CART_ADD) {
            addCart(utils,userName,goodsId,count,listener);
        }
        if (action == I.ACTION_CART_DEL) {
            deleteCart(utils, cartId, listener);
        }
        if (action == I.ACTION_CART_UPDATE) {
            updateCart(utils, cartId, count, listener);
        }

    }

    private void updateCart(OkHttpUtils<MessageBean> utils, String cartId,
                            int count, OnCompleteListener<MessageBean> listener) {
        utils.setRequestUrl(I.REQUEST_UPDATE_CART)
                .addParam(I.Cart.ID,cartId)
                .addParam(I.Cart.COUNT,String.valueOf(count))
                .targetClass(MessageBean.class)
                .execute(listener);
    }

    private void deleteCart(OkHttpUtils<MessageBean> utils, String cartId,
                            OnCompleteListener<MessageBean> listener) {
        utils.setRequestUrl(I.REQUEST_DELETE_CART)
                .addParam(I.Cart.ID,cartId)
                .targetClass(MessageBean.class)
                .execute(listener);
    }

    private void addCart(OkHttpUtils<MessageBean> utils, String userName,
                         String goodsId, int count,
                         OnCompleteListener<MessageBean> listener) {
        utils.setRequestUrl(I.REQUEST_ADD_CART)
                .addParam(I.Cart.USER_NAME,userName)
                .addParam(I.Cart.GOODS_ID,goodsId)
                .addParam(I.Cart.COUNT,String.valueOf(1))
                .addParam(I.Cart.IS_CHECKED,String.valueOf(0))
                .targetClass(MessageBean.class)
                .execute(listener);
    }
}
