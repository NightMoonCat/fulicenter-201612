package cn.moon.fulicenter.ui.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.moon.fulicenter.R;
import cn.moon.fulicenter.application.I;
import cn.moon.fulicenter.ui.fragment.NewGoodsFragment;
import cn.moon.fulicenter.ui.view.MFGT;

public class BoutiqueChildActivity extends AppCompatActivity {


    @BindView(R.id.layout_boutique)
    FrameLayout mLayoutBoutique;
    @BindView(R.id.srl)
    LinearLayout mSrl;
    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
    NewGoodsFragment newGoodsFragment = new NewGoodsFragment();
    @BindView(R.id.btnTitle)
    TextView mTvTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boutique_child);
        ButterKnife.bind(this);
        String title = getIntent().getStringExtra(I.Boutique.TITLE);
        mTvTitle.setText(title);
        ft.add(R.id.layout_boutique, newGoodsFragment).commit();
    }

    public void onBack(View view) {
        MFGT.finish(BoutiqueChildActivity.this);
    }
}
