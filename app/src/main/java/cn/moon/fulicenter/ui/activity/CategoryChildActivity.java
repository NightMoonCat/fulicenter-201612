package cn.moon.fulicenter.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.moon.fulicenter.R;
import cn.moon.fulicenter.ui.fragment.CategoryFragment;
import cn.moon.fulicenter.ui.fragment.NewGoodsFragment;
import cn.moon.fulicenter.ui.view.MFGT;

public class CategoryChildActivity extends AppCompatActivity {

    @BindView(R.id.ivBack)
    ImageView mIvBack;
    @BindView(R.id.tvTitle)
    TextView mTvTitle;
    @BindView(R.id.layout_category_child)
    FrameLayout mLayoutCategoryChild;
    @BindView(R.id.srl)
    LinearLayout mSrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_child);
        ButterKnife.bind(this);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.layout_category_child, new NewGoodsFragment())
                .commit();
    }

    @OnClick(R.id.ivBack)
    public void onClick() {
        MFGT.finish(this);
    }
}
