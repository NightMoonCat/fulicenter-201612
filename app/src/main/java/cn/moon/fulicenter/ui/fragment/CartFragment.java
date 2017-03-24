package cn.moon.fulicenter.ui.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.moon.fulicenter.R;
import cn.moon.fulicenter.application.FuLiCenterApplication;
import cn.moon.fulicenter.model.bean.CartBean;
import cn.moon.fulicenter.model.bean.GoodsDetailsBean;
import cn.moon.fulicenter.model.bean.User;
import cn.moon.fulicenter.model.net.CartModel;
import cn.moon.fulicenter.model.net.ICartModel;
import cn.moon.fulicenter.model.net.OnCompleteListener;
import cn.moon.fulicenter.model.utils.ImageLoader;
import cn.moon.fulicenter.model.utils.L;
import cn.moon.fulicenter.model.utils.ResultUtils;
import cn.moon.fulicenter.ui.adpter.CartAdapter;
import cn.moon.fulicenter.ui.view.SpaceItemDecoration;


/**
 * A simple {@link Fragment} subclass.
 */
public class CartFragment extends Fragment {
    private static final String TAG = "CartFragment";
    ICartModel mModel;
    ArrayList<CartBean> mList = new ArrayList<>();
    @BindView(R.id.tv_cart_sum_price)
    TextView mTvCartSumPrice;
    @BindView(R.id.tv_cart_save_price)
    TextView mTvCartSavePrice;
    @BindView(R.id.tv_cart_buy)
    TextView mTvCartBuy;
    @BindView(R.id.tv_nothing)
    TextView mTvNothing;
    @BindView(R.id.rv)
    RecyclerView mRv;
    @BindView(R.id.srl)
    SwipeRefreshLayout mSrl;
    CartAdapter mAdapter;
    LinearLayoutManager mLayoutManager;
    User mUser;
    @BindView(R.id.layout_cart)
    RelativeLayout mLayoutCart;

    public CartFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_cart, container, false);
        ButterKnife.bind(this, layout);
        return layout;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mModel = new CartModel();
        initView();
        initData();
        setListener();
    }

    CompoundButton.OnCheckedChangeListener mOnCheckedChangeListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
            int position = (int) compoundButton.getTag();
            L.e(TAG,"onCheckedChanged....checked="+checked+",position="+position);
            mList.get(position).setChecked(checked);
            setPriceText();
        }
    };
    private void setListener() {
        setPullDownListener();
        mAdapter.setListener(mOnCheckedChangeListener);
    }

    private void setPullDownListener() {
        mSrl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                ImageLoader.release();
                setRefresh(true);
                initData();
            }
        });
    }

    private void setRefresh(boolean refresh) {
        if (mSrl != null) {
            mSrl.setRefreshing(refresh);
        }
        mTvNothing.setVisibility(refresh ? View.VISIBLE : View.GONE);
    }

    private void setCartListLayout(boolean isShow) {
        mTvNothing.setVisibility(isShow ? View.GONE : View.VISIBLE);
        mLayoutCart.setVisibility(isShow ? View.VISIBLE : View.GONE);
    }

    private void initData() {
        mUser = FuLiCenterApplication.getCurrentUser();
        if (mUser != null) {
            showCartList();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        initData();
    }

    private void showCartList() {
        mModel.loadData(getActivity(), mUser.getMuserName(), new OnCompleteListener<CartBean[]>() {
            @Override
            public void onSuccess(CartBean[] result) {
                setRefresh(false);
                setCartListLayout(true);
                if (result != null) {
                    mList.clear();
                    if (result.length > 0) {

                        ArrayList<CartBean> cartList = ResultUtils.array2List(result);
                        mList.addAll(cartList);
                        mAdapter.notifyDataSetChanged();
                    } else {
                        setCartListLayout(false);
                    }
                }
            }

            @Override
            public void onError(String error) {
                setRefresh(false);
                L.e(TAG, "onError.error=" + error);
            }
        });
    }

    private void initView() {
        mLayoutManager = new LinearLayoutManager(getActivity());
        //设置footer显示的文字居中
        mRv.setLayoutManager(mLayoutManager);
        mRv.setHasFixedSize(true);
        mSrl.setColorSchemeColors(
                getResources().getColor(R.color.google_blue),
                getResources().getColor(R.color.google_red),
                getResources().getColor(R.color.google_green),
                getResources().getColor(R.color.google_yellow));
        mAdapter = new CartAdapter(getActivity(), mList);
        mRv.addItemDecoration(new SpaceItemDecoration(12));
        mRv.setAdapter(mAdapter);
        setCartListLayout(false);
        setPriceText();
    }
    private void setPriceText() {
        int sumPrice = 0;
        int rankPrice = 0;
        for (CartBean cartBean : mList) {
            if (cartBean.isChecked()) {
                GoodsDetailsBean goods = cartBean.getGoods();
                if (goods != null) {
                    sumPrice += getPrice(goods.getCurrencyPrice()) * cartBean.getCount();
                    rankPrice += getPrice(goods.getRankPrice())*cartBean.getCount();
                }
            }
            mTvCartSumPrice.setText("合计：￥"+sumPrice);
            mTvCartSavePrice.setText("节省：￥"+(sumPrice-rankPrice));
        }

    }
    private int getPrice(String p){
        String pStr = p.substring(p.indexOf("￥")+1);
        return Integer.valueOf(pStr);
    }

}
