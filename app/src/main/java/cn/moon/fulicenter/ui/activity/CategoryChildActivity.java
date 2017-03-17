package cn.moon.fulicenter.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.moon.fulicenter.R;
import cn.moon.fulicenter.application.I;
import cn.moon.fulicenter.ui.fragment.NewGoodsFragment;
import cn.moon.fulicenter.ui.view.MFGT;

public class CategoryChildActivity extends AppCompatActivity {

    boolean sortPrice;
    boolean sortAddTime;
    int sortBy = I.SORT_BY_PRICE_DESC;

    NewGoodsFragment mNewGoodsFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_child);
        ButterKnife.bind(this);
        mNewGoodsFragment = new NewGoodsFragment();

        getSupportFragmentManager().beginTransaction()
                .add(R.id.layout_category_child, mNewGoodsFragment)
                .commit();
    }

    @OnClick(R.id.ivBack)
    public void onClick() {
        MFGT.finish(this);
    }

    @OnClick({R.id.btnOrderByPrice, R.id.btnOrderByTime})
    public void sortBy(View view) {
        switch (view.getId()) {
            case R.id.btnOrderByPrice:
                sortBy = sortPrice?I.SORT_BY_PRICE_ASC:I.SORT_BY_PRICE_DESC;
                sortPrice = ! sortPrice;
                break;
            case R.id.btnOrderByTime:
                sortBy = sortAddTime?I.SORT_BY_ADDTIME_ASC:I.SORT_BY_ADDTIME_DESC;
                sortAddTime = !sortAddTime;
                break;
        }
        mNewGoodsFragment.sortBy(sortBy);

    }

}
