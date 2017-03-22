package cn.moon.fulicenter.ui.activity;

import android.content.Intent;
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
import cn.moon.fulicenter.application.I;
import cn.moon.fulicenter.model.bean.User;
import cn.moon.fulicenter.model.dao.UserDao;
import cn.moon.fulicenter.model.utils.CommonUtils;
import cn.moon.fulicenter.model.utils.ImageLoader;
import cn.moon.fulicenter.model.utils.L;
import cn.moon.fulicenter.model.utils.OnSetAvatarListener;
import cn.moon.fulicenter.ui.view.MFGT;

/**
 * Created by Moon on 2017/3/21.
 */

public class SettingActivity extends AppCompatActivity {
    private static final String TAG = SettingActivity.class.getSimpleName();
    @BindView(R.id.tvTitle)
    TextView mTvTitle;
    @BindView(R.id.iv_user_profile_avatar)
    ImageView mIvUserProfileAvatar;
    @BindView(R.id.tv_user_profile_name)
    TextView mTvUserProfileName;
    @BindView(R.id.tv_user_profile_nick)
    TextView mTvUserProfileNick;
    String avatarName;
    User user = FuLiCenterApplication.getCurrentUser();
    OnSetAvatarListener mOnSetAvatarListener;

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
        ImageLoader.downloadImg(SettingActivity.this, mIvUserProfileAvatar, user.getAvatar());
    }

    @OnClick(R.id.ivBack)
    public void backArea() {
        MFGT.finish(SettingActivity.this);
    }

    @OnClick(R.id.btn_logout)
    public void logout() {
        UserDao.getInstance(SettingActivity.this).logout();
        MFGT.gotoLogin(SettingActivity.this, I.REQUEST_CODE_LOGIN);
        MFGT.finish(SettingActivity.this);
    }

    @OnClick(R.id.layout_user_profile_nickname)
    public void nickOnClick() {
        MFGT.gotoUpdateNick(SettingActivity.this);
    }

    @OnClick(R.id.layout_user_profile_username)
    public void userNameOnClick() {
        CommonUtils.showShortToast(R.string.username_connot_be_modify);
    }

    @OnClick(R.id.layout_user_profile_avatar)
    public void avatarOnClick() {
        mOnSetAvatarListener = new OnSetAvatarListener(SettingActivity.this,
                R.id.layout_user_profile_avatar, getAvatarName(), I.AVATAR_TYPE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_OK) {
            L.e(TAG, "requestCode=" + requestCode + ",data=" + data);
            mOnSetAvatarListener.setAvatar(requestCode, data, mIvUserProfileAvatar);
            if (requestCode == OnSetAvatarListener.REQUEST_CROP_PHOTO) {
                uploadAvatar();
            }
        }
    }

    private void uploadAvatar() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }

    public String getAvatarName() {
        avatarName = user.getMuserName() + System.currentTimeMillis();
        L.e(TAG, "avatarName" + avatarName);
        return avatarName;
    }
}
