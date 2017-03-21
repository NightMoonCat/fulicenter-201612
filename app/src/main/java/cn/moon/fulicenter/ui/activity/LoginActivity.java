package cn.moon.fulicenter.ui.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

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
import cn.moon.fulicenter.model.utils.L;
import cn.moon.fulicenter.model.utils.MD5;
import cn.moon.fulicenter.model.utils.ResultUtils;
import cn.moon.fulicenter.model.utils.SharedPreferencesUtils;
import cn.moon.fulicenter.ui.view.MFGT;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";
    @BindView(R.id.tvTitle)
    TextView mTvTitle;
    @BindView(R.id.etUserName)
    EditText mEtUserName;
    @BindView(R.id.etPassword)
    EditText mEtPassword;

    String userName, password;
    IUserModel mModel;
    ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        mModel = new UserModel();
        initView();
    }

    private void initView() {
        mTvTitle.setText("帐号登录");

    }

    @OnClick({R.id.ivBack, R.id.btnLogin, R.id.btnRegister})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivBack:
                MFGT.finish(LoginActivity.this);
                break;
            case R.id.btnLogin:
                login();
                break;
            case R.id.btnRegister:
                MFGT.gotoRegister(LoginActivity.this);
                break;
        }
    }

    private void login() {
        if (checkInput()) {
            showDialog();
            mModel.login(LoginActivity.this, userName, MD5.getMessageDigest(password),
                    new OnCompleteListener<String>() {
                        @Override
                        public void onSuccess(String s) {
                            Result result = ResultUtils.getResultFromJson(s, User.class);
                            if (result != null) {
                                if (result.isRetMsg()) {
                                    User user = (User) result.getRetData();
                                    loginSuccess(user);
                                }
                            } else {
                                if (result.getRetCode() == I.MSG_LOGIN_UNKNOW_USER) {
                                    CommonUtils.showShortToast(R.string.login_fail_unknow_user);
                                }
                                if (result.getRetCode() == I.MSG_LOGIN_ERROR_PASSWORD) {
                                    CommonUtils.showShortToast(R.string.login_fail_error_password);
                                }

                            }
                            mProgressDialog.dismiss();

                        }

                        @Override
                        public void onError(String error) {
                            CommonUtils.showShortToast(getString(R.string.login_fail));
                            mProgressDialog.dismiss();
                        }
                    });

        }
    }

    private void loginSuccess(final User user) {
        CommonUtils.showShortToast(R.string.login_success);
        //保存在内存中
        FuLiCenterApplication.setCurrentUser(user);
        L.e(TAG,"loginSuccess_user"+user);
        //保存在ShaSharedPreferences中
        SharedPreferencesUtils.getInstance().setUserName(user.getMuserName());
        //保存到数据库中
        new Thread(new Runnable() {
            @Override
            public void run() {
                boolean b = UserDao.getInstance(LoginActivity.this).saveUserInfo(user);
                L.e(TAG,"loginSuccess:"+b);
            }
        }).start();

        MFGT.finish(LoginActivity.this);
    }

    private void showDialog() {
        mProgressDialog = new ProgressDialog(LoginActivity.this);
        mProgressDialog.setMessage(getString(R.string.logining));
        mProgressDialog.show();
    }

    private boolean checkInput() {
        userName = mEtUserName.getText().toString().trim();
        password = mEtPassword.getText().toString().trim();
        if (TextUtils.isEmpty(userName)) {
            mEtUserName.requestFocus();
            mEtUserName.setError(getString(R.string.user_name_connot_be_empty));
        }
        if (!userName.matches("[a-zA-Z]\\w{5,15}")) {
            mEtUserName.requestFocus();
            mEtUserName.setError(getString(R.string.illegal_user_name));
        }
        if (TextUtils.isEmpty(password)) {
            mEtPassword.requestFocus();
            mEtPassword.setError(getString(R.string.password_connot_be_empty));
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == I.REQUEST_CODE_REGISTER) {
            String userName = data.getStringExtra(I.User.USER_NAME);
            mEtUserName.setText(userName);
        }
    }
}
