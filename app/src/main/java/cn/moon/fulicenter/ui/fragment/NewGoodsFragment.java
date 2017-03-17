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
import android.widget.TextView;

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
import cn.moon.fulicenter.model.utils.CommonUtils;
import cn.moon.fulicenter.model.utils.ImageLoader;
import cn.moon.fulicenter.model.utils.ResultUtils;
import cn.moon.fulicenter.ui.adpter.GoodsAdapter;
import cn.moon.fulicenter.ui.view.SpaceItemDecoration;

/**
 * Created by Moon on 2017/3/15.
 */

public class NewGoodsFragment extends Fragment {

    private static final String TAG = "NewGoodsFragment";
    INewGoodsModel mModel;
    int mPageId = 1;
    Unbinder bind;
    List<NewGoodsBean> mList = new ArrayList<>();
    GridLayoutManager mLayoutManager;
    GoodsAdapter mAdapter;
    @BindView(R.id.srl)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.tvRefreshHint)
    TextView mTvRefreshHint;
    @BindView(R.id.rvGoods)
    RecyclerView mRvGoods;

    int catId = 0;

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
        catId = getActivity().getIntent().getIntExtra(I.NewAndBoutiqueGoods.CAT_ID,catId);
        initData(I.ACTION_DOWNLOAD);
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
                if (mAdapter.isMore() && lastLocation == mAdapter.getItemCount() - 1 && newState == RecyclerView.SCROLL_STATE_IDLE) {
                    mPageId++;
                    initData(I.ACTION_PULL_UP);
                }
            }
        });
    }

    private void setPullDownListener() {
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                ImageLoader.release();
                setRefresh(true);
                mPageId = 1;
                initData(I.ACTION_PULL_DOWN);
            }
        });
    }


    private void initView() {
        mLayoutManager = new GridLayoutManager(getActivity(), I.COLUM_NUM);
        //设置footer显示的文字居中
        mLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (position == mList.size()) {
                    return 2;
                }
                return 1;
            }
        });
        mRvGoods.setLayoutManager(mLayoutManager);
        mRvGoods.setHasFixedSize(true);
        mSwipeRefreshLayout.setColorSchemeColors(
                getResources().getColor(R.color.google_blue),
                getResources().getColor(R.color.google_red),
                getResources().getColor(R.color.google_green),
                getResources().getColor(R.color.google_yellow));
        mAdapter = new GoodsAdapter(getActivity(), mList);
        mRvGoods.addItemDecoration(new SpaceItemDecoration(12));
        mRvGoods.setAdapter(mAdapter);
    }

    private void initData(final int action) {
        mModel.loadData(getActivity(),catId, mPageId, new OnCompleteListener<NewGoodsBean[]>() {
            @Override
            public void onSuccess(NewGoodsBean[] result) {
                setRefresh(false);
                mAdapter.setMore(true);
                if (result != null && result.length > 0) {
                    Log.e(TAG, result.length + "");
                    ArrayList<NewGoodsBean> list = ResultUtils.array2List(result);
                    if (action == I.ACTION_DOWNLOAD || action == I.ACTION_PULL_DOWN) {
                        mList.clear();
                    }
                    mList.addAll(list);
                    if (list.size() < I.PAGE_SIZE_DEFAULT) {
                        mAdapter.setMore(false);
                    }
                    mAdapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onError(String error) {
                Log.e(TAG, error);
                CommonUtils.showShortToast(error);
                setRefresh(false);
            }
        });
    }

    private void setRefresh(boolean refresh) {
        if (mSwipeRefreshLayout != null) {
            mSwipeRefreshLayout.setRefreshing(refresh);
        }
        if (mTvRefreshHint != null) {
            mTvRefreshHint.setVisibility(refresh ? View.VISIBLE : View.GONE);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (bind != null) {
            bind.unbind();
        }
    }
}
