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
import cn.moon.fulicenter.application.FuLiCenterApplication;
import cn.moon.fulicenter.ui.fragment.BoutiqueFragment;
import cn.moon.fulicenter.ui.fragment.CartFragment;
import cn.moon.fulicenter.ui.fragment.CategoryFragment;
import cn.moon.fulicenter.ui.fragment.NewGoodsFragment;
import cn.moon.fulicenter.ui.fragment.PersonalCenterFragment;
import cn.moon.fulicenter.ui.view.MFGT;

public class MainActivity extends AppCompatActivity {


    @BindView(R.id.btnNewGoods)
    RadioButton mBtnNewGoods;
    @BindView(R.id.btnBoutique)
    RadioButton mBtnBoutique;
    @BindView(R.id.btnCategory)
    RadioButton mBtnCategory;
    @BindView(R.id.btnCart)
    RadioButton mBtnCart;
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
    CategoryFragment mCategoryFragment;
    CartFragment mCartFragment;
    PersonalCenterFragment mPersonalCenterFragment;

    RadioButton[] mRadioButtons;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bind = ButterKnife.bind(this);
        initFragment();
        initRadioButton();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.layout_content,mNewGoodsFragment)
                .add(R.id.layout_content,mBoutiqueFragment)
                .add(R.id.layout_content,mCategoryFragment)
                .add(R.id.layout_content,mCartFragment)
                .add(R.id.layout_content,mPersonalCenterFragment)
                .hide(mBoutiqueFragment)
                .hide(mCategoryFragment)
                .hide(mPersonalCenterFragment)
                .hide(mCartFragment)
                .show(mNewGoodsFragment)
                .commit();

    }

    private void initRadioButton() {
        mRadioButtons = new RadioButton[5];
        mRadioButtons[0] =  mBtnNewGoods;
        mRadioButtons[1] =  mBtnBoutique;
        mRadioButtons[2] =  mBtnCategory;
        mRadioButtons[3] =  mBtnCart;
        mRadioButtons[4] =  mBtnMe;
    }

    private void initFragment() {
        mFragments = new Fragment[5];
        mNewGoodsFragment = new NewGoodsFragment();
        mBoutiqueFragment = new BoutiqueFragment();
        mCategoryFragment = new CategoryFragment();
        mPersonalCenterFragment = new PersonalCenterFragment();
        mCartFragment = new CartFragment();

        mFragments[0] = mNewGoodsFragment;
        mFragments[1] = mBoutiqueFragment;
        mFragments[2] = mCategoryFragment;
        mFragments[3] = mCartFragment;
        mFragments[4] = mPersonalCenterFragment;

    }

    @Override
    protected void onResume() {
        super.onResume();
        setRadioButton();
    }

    private void setRadioButton() {
        for (int i = 0;i<mRadioButtons.length;i++) {
            if (i == currentIndex) {
                mRadioButtons[i].setChecked(true);
            } else {
                mRadioButtons[i].setChecked(false);
            }
        }
    }

    public void onCheckedChange(View view) {
        switch (view.getId()) {
            case R.id.btnNewGoods:
                index = 0;
                break;
            case R.id.btnBoutique:
                index = 1;
                break;
            case R.id.btnCategory:
                index = 2;
                break;
            case R.id.btnCart:
                if (FuLiCenterApplication.getCurrentUser() != null) {
                    index = 3;
                } else {
                    MFGT.gotoLogin(MainActivity.this);
                }
                break;
            case R.id.btnMe:
                if (FuLiCenterApplication.getCurrentUser() != null) {
                    index = 4;
                } else {
                    MFGT.gotoLogin(MainActivity.this);
                }
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
