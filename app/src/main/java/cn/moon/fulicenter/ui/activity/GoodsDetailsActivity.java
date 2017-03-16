package cn.moon.fulicenter.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.moon.fulicenter.R;
import cn.moon.fulicenter.application.I;
import cn.moon.fulicenter.model.bean.AlbumsBean;
import cn.moon.fulicenter.model.bean.GoodsDetailsBean;
import cn.moon.fulicenter.model.net.GoodsDetailsModel;
import cn.moon.fulicenter.model.net.IGoodsDetailsModel;
import cn.moon.fulicenter.model.net.OnCompleteListener;
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

    GoodsDetailsBean mBean;


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

    private void initData() {
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

            }
        });
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
                for (int i=0;i<albums.length;i++) {
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
}

