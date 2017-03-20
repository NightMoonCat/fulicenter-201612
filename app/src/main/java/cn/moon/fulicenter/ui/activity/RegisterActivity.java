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
import cn.moon.fulicenter.application.I;
import cn.moon.fulicenter.model.bean.Result;
import cn.moon.fulicenter.model.bean.User;
import cn.moon.fulicenter.model.net.IUserModel;
import cn.moon.fulicenter.model.net.OnCompleteListener;
import cn.moon.fulicenter.model.net.UserModel;
import cn.moon.fulicenter.model.utils.CommonUtils;
import cn.moon.fulicenter.model.utils.MD5;
import cn.moon.fulicenter.model.utils.ResultUtils;
import cn.moon.fulicenter.ui.view.MFGT;

public class RegisterActivity extends AppCompatActivity {

    @BindView(R.id.tvTitle)
    TextView mTvTitle;
    @BindView(R.id.username)
    EditText mUsername;
    @BindView(R.id.nick)
    EditText mNick;
    @BindView(R.id.password)
    EditText mPassword;
    @BindView(R.id.confirm_password)
    EditText mConfirmPassword;

    String userName;
    String nickName;
    String password;

    IUserModel mModel;

    ProgressDialog mProgressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        mModel = new UserModel();
        initView();
    }

    private void initView() {
        mTvTitle.setText("用户注册");
    }

    @OnClick({R.id.ivBack, R.id.btn_register})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivBack:
                MFGT.finish(RegisterActivity.this);
                break;
            case R.id.btn_register:
                register();
                break;
        }
    }

    private void showDialog() {
        mProgressDialog = new ProgressDialog(RegisterActivity.this);
        mProgressDialog.setMessage(getString(R.string.registering));
        mProgressDialog.show();

    }

    private void register() {
        if (checkInput()) {
            showDialog();
            mModel.register(RegisterActivity.this, userName, nickName,
                    MD5.getMessageDigest(password), new OnCompleteListener<String>() {
                @Override
                public void onSuccess(String s) {
                    Result result = ResultUtils.getResultFromJson(s, User.class);
                    if (result != null) {
                        if (result.isRetMsg()) {
                            registerSuccess();
                        } else {
                            if (result.getRetCode() == I.MSG_REGISTER_USERNAME_EXISTS) {
                                CommonUtils.showShortToast(R.string.register_fail_exists);
                            } else {
                                CommonUtils.showShortToast(R.string.register_fail);
                            }
                        }
                    }
                    mProgressDialog.dismiss();
                }

                @Override
                public void onError(String error) {
                    CommonUtils.showShortToast(R.string.register_fail);
                    mProgressDialog.dismiss();

                }
            });
        }
    }

    private void registerSuccess() {
        //设置返回结果
        setResult(RESULT_OK,new Intent().putExtra(I.User.USER_NAME,userName));
        CommonUtils.showShortToast(R.string.register_success);
        MFGT.finish(RegisterActivity.this);
    }

    private boolean checkInput() {
        userName = mUsername.getText().toString().trim();
        nickName = mNick.getText().toString().trim();
        password = mPassword.getText().toString().trim();
        String confirm = mConfirmPassword.getText().toString().trim();
        if (TextUtils.isEmpty(userName)) {
            mUsername.requestFocus();
            mUsername.setError(getString(R.string.user_name_connot_be_empty));
            return false;
        }
        if (!userName.matches("[a-zA-Z]\\w{5,15}")) {
            mUsername.requestFocus();
            mUsername.setError(getString(R.string.illegal_user_name));
        }
        if (TextUtils.isEmpty(nickName)) {
            mNick.requestFocus();
            mNick.setError(getString(R.string.nick_name_connot_be_empty));
            return false;
        }
        if (TextUtils.isEmpty(password)) {
            mPassword.requestFocus();
            mPassword.setError(getString(R.string.password_connot_be_empty));
            return false;
        }
        if (TextUtils.isEmpty(confirm)) {
            mConfirmPassword.requestFocus();
            mConfirmPassword.setError(getString(R.string.confirm_password_connot_be_empty));
            return false;
        }
        if (!password.equals(confirm)) {
            mConfirmPassword.requestFocus();
            mConfirmPassword.setError(getString(R.string.two_input_password));
            return false;
        }

        return true;
    }
}
