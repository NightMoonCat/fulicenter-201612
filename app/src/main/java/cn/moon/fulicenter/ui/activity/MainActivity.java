package cn.moon.fulicenter.ui.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.moon.fulicenter.R;
import cn.moon.fulicenter.ui.fragment.BoutiqueFragment;
import cn.moon.fulicenter.ui.fragment.NewGoodsFragment;

public class MainActivity extends AppCompatActivity {


    @BindView(R.id.btnNewGoods)
    RadioButton mBtnNewGoods;
    @BindView(R.id.btnBoutique)
    RadioButton mBtnBoutique;
    @BindView(R.id.btnCategory)
    RadioButton mBtnCategory;
    @BindView(R.id.btnCollect)
    RadioButton mBtnCollect;
    @BindView(R.id.btnMe)
    RadioButton mBtnMe;
    @BindView(R.id.layout_menu)
    RadioGroup mLayoutMenu;
    @BindView(R.id.layout_content)
    FrameLayout mLayoutContent;
    Unbinder bind;
    int index = 0;
    int currentIndex = 0;
    Fragment[] mFragments;
    NewGoodsFragment mNewGoodsFragment;
    BoutiqueFragment mBoutiqueFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bind = ButterKnife.bind(this);
        initFragment();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.layout_content,mNewGoodsFragment)
                .add(R.id.layout_content,mBoutiqueFragment)
                .hide(mBoutiqueFragment)
                .show(mNewGoodsFragment)
                .commit();

    }

    private void initFragment() {
        mFragments = new Fragment[5];
        mNewGoodsFragment = new NewGoodsFragment();
        mBoutiqueFragment = new BoutiqueFragment();
        mFragments[0] = mNewGoodsFragment;
        mFragments[1] = mBoutiqueFragment;

    }

    public void onCheckedChange(View view) {
        switch (view.getId()) {
            case R.id.btnNewGoods:
                index = 0;
                break;
            case R.id.btnBoutique:
                index = 1;
                break;
        }
        setFragment();
    }

    private void setFragment() {
        if (currentIndex != index) {
            getSupportFragmentManager().beginTransaction()
                    .hide(mFragments[currentIndex])
                    .show(mFragments[index])
                    .commit();
            currentIndex = index;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (bind != null) {
            bind.unbind();
        }
    }
}
