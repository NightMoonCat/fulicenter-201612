package cn.moon.fulicenter.ui.adpter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.moon.fulicenter.R;
import cn.moon.fulicenter.model.bean.CartBean;
import cn.moon.fulicenter.model.bean.GoodsDetailsBean;
import cn.moon.fulicenter.model.net.CartModel;
import cn.moon.fulicenter.model.net.ICartModel;
import cn.moon.fulicenter.model.utils.ImageLoader;
import cn.moon.fulicenter.ui.view.MFGT;

/**
 * Created by Moon on 2017/3/15.
 */

public class CartAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context mContext;
    List<CartBean> mList;
    ICartModel mModel;
    CompoundButton.OnCheckedChangeListener mOnCheckedListener;
    View.OnClickListener mOnClickUpdateListener;

    public void setOnClickDelListener(View.OnClickListener onClickDelListener) {
        mOnClickUpdateListener = onClickDelListener;
    }

    public CartAdapter(Context mContext, List<CartBean> mList) {
        this.mContext = mContext;
        this.mList = mList;
        mModel = new CartModel();
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layout = View.inflate(mContext, R.layout.item_cart, null);
        return new CartViewHolder(layout);

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder parentHolder, int position) {
        CartViewHolder holder = (CartViewHolder) parentHolder;
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return mList != null ? mList.size() : 0;
    }

    public void setCheckedChangeListener(CompoundButton.OnCheckedChangeListener listener) {
        mOnCheckedListener = listener;
    }


    class CartViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.cb_cart_selected)
        CheckBox mCbCartSelected;
        @BindView(R.id.iv_cart_thumb)
        ImageView mIvCartThumb;
        @BindView(R.id.tv_cart_good_name)
        TextView mTvCartGoodName;
        @BindView(R.id.iv_cart_add)
        ImageView mIvCartAdd;
        @BindView(R.id.tv_cart_count)
        TextView mTvCartCount;
        @BindView(R.id.iv_cart_del)
        ImageView mIvCartDel;
        @BindView(R.id.tv_cart_price)
        TextView mTvCartPrice;

        CartViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }


        public void bind(final int position) {
            mCbCartSelected.setOnCheckedChangeListener(null);

            final CartBean bean = mList.get(position);
            mTvCartCount.setText("(" + bean.getCount() + ")");
            mCbCartSelected.setChecked(bean.isChecked());//是否被选中

            final GoodsDetailsBean goods = bean.getGoods();
            if (goods != null) {
                mTvCartGoodName.setText(goods.getGoodsName());
                ImageLoader.downloadImg(mContext, mIvCartThumb, goods.getGoodsThumb());
                mTvCartPrice.setText( goods.getCurrencyPrice()+"");
            }

            //将OnCheckedChangeListener的listener对象从CartFragment传到适配器CartAdapter,再传到viewHolder
            mCbCartSelected.setTag(position);
            mCbCartSelected.setOnCheckedChangeListener(mOnCheckedListener);

            mIvCartThumb.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    MFGT.gotoGoodsDetails(mContext, bean.getGoodsId());
                }
            });

            mIvCartAdd.setTag(position);
            mIvCartAdd.setTag(R.id.cart_add_tag,1);
            mIvCartAdd.setOnClickListener(mOnClickUpdateListener);
            mIvCartDel.setTag(position);
            mIvCartDel.setTag(R.id.cart_del_tag,-1);
            mIvCartDel.setOnClickListener(mOnClickUpdateListener);
        }
    }
}
