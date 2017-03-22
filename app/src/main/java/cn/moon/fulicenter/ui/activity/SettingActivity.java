package cn.moon.fulicenter.ui.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.moon.fulicenter.R;
import cn.moon.fulicenter.application.FuLiCenterApplication;
import cn.moon.fulicenter.application.I;
import cn.moon.fulicenter.model.bean.Result;
import cn.moon.fulicenter.model.bean.User;
import cn.moon.fulicenter.model.dao.UserDao;
import cn.moon.fulicenter.model.net.IUserModel;
import cn.moon.fulicenter.model.net.OnCompleteListener;
import cn.moon.fulicenter.model.net.UserModel;
import cn.moon.fulicenter.model.utils.CommonUtils;
import cn.moon.fulicenter.model.utils.FileUtils;
import cn.moon.fulicenter.model.utils.ImageLoader;
import cn.moon.fulicenter.model.utils.L;
import cn.moon.fulicenter.model.utils.OnSetAvatarListener;
import cn.moon.fulicenter.model.utils.ResultUtils;
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
    User user = null;
    OnSetAvatarListener mOnSetAvatarListener;
    IUserModel mModel;
    ProgressDialog mProgressDialog;
    String mAvatarName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);
        initData();
    }

    private void initData() {
        mTvTitle.setText(getString(R.string.user_profile));
        user = FuLiCenterApplication.getCurrentUser();
        if (user != null) {
            showUserInfo(user);
        } else {
            backArea();
        }
    }

    private void showUserInfo(User user) {
        mTvUserProfileName.setText(user.getMuserName());
        mTvUserProfileNick.setText(user.getMuserNick());
        ImageLoader.setAvatar(ImageLoader.getAvatarUrl(user), SettingActivity.this, mIvUserProfileAvatar);
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
        mAvatarName = getAvatarName();
        mOnSetAvatarListener = new OnSetAvatarListener(SettingActivity.this,
                R.id.layout_user_profile_avatar, mAvatarName, I.AVATAR_TYPE_USER_PATH);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        L.e(TAG, "requestCode=" + requestCode + ",data=" + data);

        mOnSetAvatarListener.setAvatar(requestCode, data, mIvUserProfileAvatar);

        L.e(TAG, "requestCode1111111=" + requestCode + ",data=" + data);

        if (requestCode == OnSetAvatarListener.REQUEST_CROP_PHOTO) {
            uploadAvatar();
        }
    }

    private void uploadAvatar() {
        showDialog();
        File file = FileUtils.getAvatarPath(SettingActivity.this,I.AVATAR_TYPE_USER_PATH,
                mAvatarName+I.AVATAR_SUFFIX_JPG);

        L.e(TAG, "file1111=" + file.getAbsolutePath());

        mModel = new UserModel();

        mModel.updateAvatar(SettingActivity.this, user.getMuserName(), file, new OnCompleteListener<String>() {
            @Override
            public void onSuccess(String s) {
                Result result = ResultUtils.getResultFromJson(s, User.class);
                if (result != null) {
                    if (result.isRetMsg()) {
                        User u = (User) result.getRetData();
                        updateSuccess(u);
                    } else {
                        if (result.getRetCode() == I.MSG_UPLOAD_AVATAR_FAIL) {
                            CommonUtils.showShortToast(R.string.update_user_avatar_fail);
                        }
                    }
                }
                mProgressDialog.dismiss();
            }

            @Override
            public void onError(String error) {
                mProgressDialog.dismiss();
                CommonUtils.showShortToast(R.string.update_user_avatar_fail);
            }
        });
    }

    private void showDialog() {
        mProgressDialog = new ProgressDialog(SettingActivity.this);
        mProgressDialog.setMessage(getString(R.string.update_user_avatar));
        mProgressDialog.show();
    }

    private void updateSuccess(final User u) {
        L.e(TAG, "user=" + u);
        CommonUtils.showShortToast(getString(R.string.update_user_avatar_success));
        FuLiCenterApplication.setCurrentUser(u);
        new Thread(new Runnable() {
            @Override
            public void run() {
                boolean b = UserDao.getInstance(SettingActivity.this).saveUserInfo(u);
                L.e(TAG, "updateSuccess=" + b);
            }
        }).start();
        initData();
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
