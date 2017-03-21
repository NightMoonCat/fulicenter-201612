package cn.moon.fulicenter.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.moon.fulicenter.R;
import cn.moon.fulicenter.application.FuLiCenterApplication;
import cn.moon.fulicenter.model.bean.User;
import cn.moon.fulicenter.model.utils.ImageLoader;
import cn.moon.fulicenter.ui.view.MFGT;

/**
 * Created by Moon on 2017/3/21.
 */

public class SettingActivity extends AppCompatActivity {
    @BindView(R.id.tvTitle)
    TextView mTvTitle;
    @BindView(R.id.iv_user_profile_avatar)
    ImageView mIvUserProfileAvatar;
    @BindView(R.id.tv_user_profile_name)
    TextView mTvUserProfileName;
    @BindView(R.id.tv_user_profile_nick)
    TextView mTvUserProfileNick;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);
        initData();
    }

    private void initData() {
        mTvTitle.setText(getString(R.string.user_profile));
        User user = FuLiCenterApplication.getCurrentUser();
        if (user != null) {
            showUserInfo(user);
        } else {
            backArea();
        }
    }

    private void showUserInfo(User user) {
        mTvUserProfileName.setText(user.getMuserName());
        mTvUserProfileNick.setText(user.getMuserNick());
        ImageLoader.downloadImg(SettingActivity.this,mIvUserProfileAvatar,user.getAvatar());
    }

    @OnClick(R.id.ivBack)
    public void backArea() {
        MFGT.finish(SettingActivity.this);
    }

    @OnClick(R.id.btn_logout)
    public void onClick() {
    }
}
