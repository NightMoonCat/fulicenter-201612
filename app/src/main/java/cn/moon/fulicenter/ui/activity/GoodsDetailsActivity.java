package cn.moon.fulicenter.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.moon.fulicenter.R;
import cn.moon.fulicenter.application.I;
import cn.moon.fulicenter.model.bean.GoodsDetailsBean;
import cn.moon.fulicenter.model.net.GoodsDetailsModel;
import cn.moon.fulicenter.model.net.IGoodsDetailsModel;
import cn.moon.fulicenter.model.net.OnCompleteListener;
import cn.moon.fulicenter.ui.view.MFGT;

public class GoodsDetailsActivity extends AppCompatActivity {

    int goodId;
    IGoodsDetailsModel mModel;
    @BindView(R.id.tvGoodsEnglishName)
    TextView mTvGoodsEnglishName;
    @BindView(R.id.tvCurrentPrice)
    TextView mTvCurrentPrice;
    @BindView(R.id.tvGoodsChineseName)
    TextView mTvGoodsChineseName;
    @BindView(R.id.tvSalePrice)
    TextView mTvSalePrice;

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
            public void onSuccess(GoodsDetailsBean result) {
                if (result != null) {
                    mTvGoodsEnglishName.setText(result.getGoodsEnglishName());
                    mTvGoodsChineseName.setText(result.getGoodsName());
                    mTvCurrentPrice.setText(result.getCurrencyPrice());
                    mTvSalePrice.setText(result.getShopPrice());
                }
            }

            @Override
            public void onError(String error) {

            }
        });
    }
}

