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
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.moon.fulicenter.R;
import cn.moon.fulicenter.application.FuLiCenterApplication;
import cn.moon.fulicenter.model.bean.CartBean;
import cn.moon.fulicenter.model.bean.User;
import cn.moon.fulicenter.model.net.CartModel;
import cn.moon.fulicenter.model.net.ICartModel;
import cn.moon.fulicenter.model.net.OnCompleteListener;
import cn.moon.fulicenter.model.utils.ResultUtils;
import cn.moon.fulicenter.ui.adpter.CartAdapter;
import cn.moon.fulicenter.ui.view.SpaceItemDecoration;

/**
 * A simple {@link Fragment} subclass.
 */
public class CartFragment extends Fragment {
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
    }

    private void initData() {
        mUser = FuLiCenterApplication.getCurrentUser();
        if (mUser != null) {
            loadCartList();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        initData();
    }

    private void loadCartList() {
        mModel.loadData(getActivity(), mUser.getMuserName(), new OnCompleteListener<CartBean[]>() {
            @Override
            public void onSuccess(CartBean[] result) {
                if (result != null) {
                    if (result.length > 0) {
                        ArrayList<CartBean> cartList = ResultUtils.array2List(result);
                        mList.addAll(cartList);
                        mAdapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onError(String error) {

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
    }
}
