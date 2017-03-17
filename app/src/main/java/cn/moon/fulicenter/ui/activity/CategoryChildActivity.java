package cn.moon.fulicenter.ui.activity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.moon.fulicenter.R;
import cn.moon.fulicenter.application.I;
import cn.moon.fulicenter.ui.fragment.NewGoodsFragment;
import cn.moon.fulicenter.ui.view.CatFilterCategoryButton;
import cn.moon.fulicenter.ui.view.MFGT;

public class CategoryChildActivity extends AppCompatActivity {

    boolean sortPrice;
    boolean sortAddTime;
    int sortBy = I.SORT_BY_PRICE_DESC;

    NewGoodsFragment mNewGoodsFragment;
    @BindView(R.id.btnOrderByPrice)
    Button mBtnOrderByPrice;
    @BindView(R.id.btnOrderByTime)
    Button mBtnOrderByTime;
    String groupName;
    @BindView(R.id.btnTitle)
    CatFilterCategoryButton mBtnTitle;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_child);
        ButterKnife.bind(this);
        mNewGoodsFragment = new NewGoodsFragment();

        getSupportFragmentManager().beginTransaction()
                .add(R.id.layout_category_child, mNewGoodsFragment)
                .commit();

        groupName = getIntent().getStringExtra(I.CategoryGroup.NAME);
        initView();
    }

    private void initView() {
        mBtnTitle.setText(groupName);
    }

    @OnClick(R.id.ivBack)
    public void onClick() {
        MFGT.finish(this);
    }

    @OnClick({R.id.btnOrderByPrice, R.id.btnOrderByTime})
    public void sortBy(View view) {
        Drawable end = null;
        switch (view.getId()) {
            case R.id.btnOrderByPrice:
                sortBy = sortPrice ? I.SORT_BY_PRICE_ASC : I.SORT_BY_PRICE_DESC;
                sortPrice = !sortPrice;
                end = getResources().getDrawable(sortPrice ?
                        R.drawable.arrow_order_down : R.drawable.arrow_order_up);
                mBtnOrderByPrice.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null,
                        end, null);
                break;
            case R.id.btnOrderByTime:
                sortBy = sortAddTime ? I.SORT_BY_ADDTIME_ASC : I.SORT_BY_ADDTIME_DESC;
                sortAddTime = !sortAddTime;
                end = getResources().getDrawable(sortAddTime ?
                        R.drawable.arrow_order_down : R.drawable.arrow_order_up);
                mBtnOrderByTime.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null,
                        end, null);
                break;
        }
        mNewGoodsFragment.sortBy(sortBy);

    }

}
