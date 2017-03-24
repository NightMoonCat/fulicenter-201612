package cn.moon.fulicenter.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.moon.fulicenter.R;
import cn.moon.fulicenter.application.FuLiCenterApplication;
import cn.moon.fulicenter.application.I;
import cn.moon.fulicenter.model.bean.CollectBean;
import cn.moon.fulicenter.model.bean.User;
import cn.moon.fulicenter.model.net.IUserModel;
import cn.moon.fulicenter.model.net.OnCompleteListener;
import cn.moon.fulicenter.model.net.UserModel;
import cn.moon.fulicenter.model.utils.CommonUtils;
import cn.moon.fulicenter.model.utils.ImageLoader;
import cn.moon.fulicenter.model.utils.L;
import cn.moon.fulicenter.model.utils.ResultUtils;
import cn.moon.fulicenter.ui.adpter.CollectsAdapter;
import cn.moon.fulicenter.ui.view.SpaceItemDecoration;

public class CollectsActivity extends AppCompatActivity {

    IUserModel mModel;
    @BindView(R.id.tvRefreshHint)
    TextView mTvRefreshHint;
    @BindView(R.id.rvGoods)
    RecyclerView mRvGoods;
    @BindView(R.id.srl)
    SwipeRefreshLayout mSwipeRefreshLayout;
    int mPageId = 1;
    CollectsAdapter mAdapter;
    List<CollectBean> mList = new ArrayList<>();
    GridLayoutManager mLayoutManager;
    User mUser;
    private String TAG = CollectsActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_new_goods);
        ButterKnife.bind(this);
        mModel = new UserModel();
        initView();
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

    private void initData(final int action) {
        mUser = FuLiCenterApplication.getCurrentUser();
        mModel.loadCollect(CollectsActivity.this, mUser.getMuserName(), mPageId, I.PAGE_SIZE_DEFAULT,
                new OnCompleteListener<CollectBean[]>() {
                    @Override
                    public void onSuccess(CollectBean[] result) {
                        setRefresh(false);
                        mAdapter.setMore(true);
                        if (result != null && result.length > 0) {
                            Log.e(TAG, result.length + "");
                            ArrayList<CollectBean> list = ResultUtils.array2List(result);
                            if (action == I.ACTION_DOWNLOAD || action == I.ACTION_PULL_DOWN) {
                                mList.clear();
                            }
                            mList.addAll(list);
                            if (list.size() < I.PAGE_SIZE_DEFAULT) {
                                mAdapter.setMore(false);
                            }
                            mAdapter.notifyDataSetChanged();
                        } else if (mPageId==1 && result!=null && result.length==0) {
                            mList.clear();
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

    private void initView() {
        mLayoutManager = new GridLayoutManager(CollectsActivity.this, I.COLUM_NUM);
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
        mAdapter = new CollectsAdapter(CollectsActivity.this, mList);
        mRvGoods.addItemDecoration(new SpaceItemDecoration(12));
        mRvGoods.setAdapter(mAdapter);
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == I.REQUEST_CODE_COLLECTED) {
            boolean isCollected = data.getBooleanExtra(I.GoodsDetails.KEY_IS_COLLECTED, true);
            int goodsId = data.getIntExtra(I.GoodsDetails.KEY_GOODS_ID, 0);
            L.e(TAG,"onActivityResult,isCollected="+isCollected+"goodsId"+goodsId);
            if (!isCollected) {
                mList.remove(new CollectBean(goodsId));
                mAdapter.notifyDataSetChanged();
            }
        }
    }
}
