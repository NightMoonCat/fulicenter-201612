package cn.moon.fulicenter.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.moon.fulicenter.R;
import cn.moon.fulicenter.application.I;
import cn.moon.fulicenter.model.bean.NewGoodsBean;
import cn.moon.fulicenter.model.net.INewGoodsModel;
import cn.moon.fulicenter.model.net.NewGoodsModel;
import cn.moon.fulicenter.model.net.OnCompleteListener;
import cn.moon.fulicenter.model.utils.ImageLoader;
import cn.moon.fulicenter.model.utils.ResultUtils;
import cn.moon.fulicenter.ui.adpter.GoodsAdapter;
import cn.moon.fulicenter.ui.widget.SpaceItemDecoration;

/**
 * Created by Moon on 2017/3/15.
 */

public class NewGoodsFragment extends Fragment {
    private static final String TAG = "NewGoodsFragment";
    @BindView(R.id.rvGoods)
    RecyclerView mRvGoods;
    INewGoodsModel mModel;
    int mPageId = 1;
    Unbinder bind;
    List<NewGoodsBean> mList = new ArrayList<>();
    GridLayoutManager mLayoutManager;
    GoodsAdapter mAdapter;
    @BindView(R.id.srl)
    SwipeRefreshLayout mSwipeRefreshLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_new_goods, container, false);
        bind = ButterKnife.bind(this, layout);
        return layout;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mModel = new NewGoodsModel();
        initView();
        initData();
        setListener();
    }

    private void setListener() {
        setPullDownListener();
        setPullUpListener();
    }

    private void setPullUpListener() {
        mRvGoods.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                int lastLocation = mLayoutManager.findLastVisibleItemPosition();
                if (lastLocation == mAdapter.getItemCount() && newState == RecyclerView.SCROLL_STATE_IDLE) {
                    mPageId++;
                    mModel.loadData(getActivity(), mPageId, new OnCompleteListener<NewGoodsBean[]>() {
                        @Override
                        public void onSuccess(NewGoodsBean[] result) {
                            if (result != null && result.length > 0) {
                                ArrayList<NewGoodsBean> list = ResultUtils.array2List(result);
                                mList.addAll(list);
                                mAdapter.notifyDataSetChanged();
                            }
                        }

                        @Override
                        public void onError(String error) {

                        }
                    });
                }
            }
        });
    }

    private void setPullDownListener() {
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                ImageLoader.release();
                mSwipeRefreshLayout.setRefreshing(false);
                mPageId =1;
                mModel.loadData(getActivity(), mPageId, new OnCompleteListener<NewGoodsBean[]>() {
                    @Override
                    public void onSuccess(NewGoodsBean[] result) {
                        if (result != null && result.length > 0) {
                            ArrayList<NewGoodsBean> list = ResultUtils.array2List(result);
                            mList.clear();
                            mList.addAll(list);
                            mAdapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onError(String error) {

                    }
                });
            }
        });
    }


    private void initView() {
        mLayoutManager = new GridLayoutManager(getActivity(), I.COLUM_NUM);
        mRvGoods.setLayoutManager(mLayoutManager);
        mRvGoods.setHasFixedSize(true);
        mAdapter = new GoodsAdapter(getActivity(), mList);
        mRvGoods.addItemDecoration(new SpaceItemDecoration(12));
        mRvGoods.setAdapter(mAdapter);
    }

    private void initData() {
        mModel.loadData(getActivity(), mPageId, new OnCompleteListener<NewGoodsBean[]>() {
            @Override
            public void onSuccess(NewGoodsBean[] result) {
                if (result != null && result.length > 0) {
                    Log.e(TAG, result.length + "");
                    ArrayList<NewGoodsBean> list = ResultUtils.array2List(result);
                    mList.clear();
                    mList.addAll(list);
                    mAdapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onError(String error) {
                Log.e(TAG, error);
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (bind != null) {
            bind.unbind();
        }
    }
}
