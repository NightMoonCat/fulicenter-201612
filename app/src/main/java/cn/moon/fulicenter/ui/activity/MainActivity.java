package cn.moon.fulicenter.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.moon.fulicenter.R;

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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bind = ButterKnife.bind(this);
    }

    public void onCheckedChange(View view) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (bind != null) {
            bind.unbind();
        }
    }
}
