package cn.moon.fulicenter.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.moon.fulicenter.R;
import cn.moon.fulicenter.application.FuLiCenterApplication;
import cn.moon.fulicenter.application.I;
import cn.moon.fulicenter.model.bean.AlbumsBean;
import cn.moon.fulicenter.model.bean.GoodsDetailsBean;
import cn.moon.fulicenter.model.bean.MessageBean;
import cn.moon.fulicenter.model.bean.User;
import cn.moon.fulicenter.model.net.GoodsDetailsModel;
import cn.moon.fulicenter.model.net.IGoodsDetailsModel;
import cn.moon.fulicenter.model.net.OnCompleteListener;
import cn.moon.fulicenter.model.utils.CommonUtils;
import cn.moon.fulicenter.model.utils.AntiShake;
import cn.moon.fulicenter.ui.view.FlowIndicator;
import cn.moon.fulicenter.ui.view.MFGT;
import cn.moon.fulicenter.ui.view.SlideAutoLoopView;

public class GoodsDetailsActivity extends AppCompatActivity {

    int goodId;
    IGoodsDetailsModel mModel;

    @BindView(R.id.tv_good_name_english)
    TextView mTvGoodNameEnglish;
    @BindView(R.id.tv_good_name)
    TextView mTvGoodName;
    @BindView(R.id.tv_good_price_shop)
    TextView mTvGoodPriceShop;
    @BindView(R.id.tv_good_price_current)
    TextView mTvGoodPriceCurrent;
    @BindView(R.id.salv)
    SlideAutoLoopView mSalv;
    @BindView(R.id.indicator)
    FlowIndicator mIndicator;
    @BindView(R.id.wv_good_brief)
    WebView mWvGoodBrief;
    @BindView(R.id.iv_good_collect)
    ImageView mIvGoodCollect;

    GoodsDetailsBean mBean;
    User user;

    boolean isCollect = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_details);
        ButterKnife.bind(this);

        goodId = getIntent().getIntExtra(I.Goods.KEY_GOODS_ID, 0);
        if (goodId == 0) {
            MFGT.finish(GoodsDetailsActivity.this);
            return;
        }
        mModel = new GoodsDetailsModel();
        initData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }

    private void initData() {
        if (mBean == null) {
            mModel.loadData(GoodsDetailsActivity.this, goodId, new OnCompleteListener<GoodsDetailsBean>() {
                @Override
                public void onSuccess(GoodsDetailsBean bean) {
                    if (bean != null) {
                        mBean = bean;
                        showDetails(bean);
                    }
                }

                @Override
                public void onError(String error) {
                    CommonUtils.showShortToast("加载商品失败");
                }
            });
        }
        loadCollectStatus();
    }

    private void loadCollectStatus() {
        User user = FuLiCenterApplication.getCurrentUser();
        if (user != null) {
            collectionAction(I.ACTION_IS_COLLECT,user);
        }
    }

    private void setCollectStatus() {
        mIvGoodCollect.setImageResource(isCollect? R.mipmap.bg_collect_out :
        R.mipmap.bg_collect_in);
    }

    private void showDetails(GoodsDetailsBean bean) {
        mTvGoodNameEnglish.setText(bean.getGoodsEnglishName());
        mTvGoodName.setText(bean.getGoodsName());
        mTvGoodPriceCurrent.setText(bean.getCurrencyPrice());
        mTvGoodPriceShop.setText(bean.getShopPrice());
        mSalv.startPlayLoop(mIndicator, getAlbumUrl(), getAlbumCount());
        mWvGoodBrief.loadDataWithBaseURL(null, bean.getGoodsBrief(), I.TEXT_HTML, I.UTF_8, null);

    }

    private String[] getAlbumUrl() {
        if (mBean.getPromotePrice() != null && mBean.getPromotePrice().length() > 0) {
            AlbumsBean[] albums = mBean.getProperties()[0].getAlbums();
            if (albums != null && albums.length > 0) {
                String[] urls = new String[albums.length];
                for (int i = 0; i < albums.length; i++) {
                    urls[i] = albums[0].getImgUrl();
                    return urls;
                }
            }
        }
        return null;
    }

    private int getAlbumCount() {
        if (mBean.getPromotePrice() != null && mBean.getPromotePrice().length() > 0) {
            return mBean.getProperties()[0].getAlbums().length;
        }
        return 0;
    }


    @OnClick(R.id.ivBack)
    public void clickBack() {
        MFGT.finish(GoodsDetailsActivity.this);
    }

    AntiShake util = new AntiShake();

    @OnClick(R.id.iv_good_collect)
    public void collectGoods() {
        //添加防抖检查，防止多次快速点击
        if (util.check()) {
            return;
        }

        User user = FuLiCenterApplication.getCurrentUser();
        if (user == null) {
            MFGT.gotoLogin(GoodsDetailsActivity.this, 0);
        } else {
            if (isCollect) {
                //已收藏，点击取消收藏
                collectionAction(I.ACTION_DELETE_COLLECT, user);
            } else {
                //添加收藏
                collectionAction(I.ACTION_ADD_COLLECT,user);
            }

        }
    }

    private void collectionAction(final int action, User user) {
        mModel.collectAction(GoodsDetailsActivity.this,action, goodId,
                user.getMuserName(), new OnCompleteListener<MessageBean>() {
                    @Override
                    public void onSuccess(MessageBean msg) {
                        if (msg != null && msg.isSuccess()) {
//                            isCollect = true;
//                            if (action == I.ACTION_DELETE_COLLECT) {
//                                isCollect = false;
//                            }
                            isCollect = action==I.ACTION_DELETE_COLLECT?false:true;
                            if (action != I.ACTION_IS_COLLECT) {
                                CommonUtils.showShortToast(msg.getMsg());
                            }
                        } else {
//                            isCollect = false;
//                            if (action == I.ACTION_DELETE_COLLECT) {
//                                isCollect = true;
//                            }
                            isCollect = action==I.ACTION_IS_COLLECT?false:isCollect;
                        }
                        setCollectStatus();

                    }

                    @Override
                    public void onError(String error) {
                        isCollect = false;
                        setCollectStatus();
                    }
                });
    }
}

