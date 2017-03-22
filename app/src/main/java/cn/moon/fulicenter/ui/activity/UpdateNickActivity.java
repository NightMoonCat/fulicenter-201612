package cn.moon.fulicenter.ui.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
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
import cn.moon.fulicenter.model.utils.ResultUtils;
import cn.moon.fulicenter.ui.view.MFGT;

public class UpdateNickActivity extends AppCompatActivity {

    @BindView(R.id.tvTitle)
    TextView mTvTitle;
    @BindView(R.id.etNewNick)
    EditText mEtNewNick;
    User mUser;
    IUserModel mModel;
    String newNick;
    private String TAG = UpdateNickActivity.class.getSimpleName();
    ProgressDialog mProgressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_nick);
        ButterKnife.bind(this);
        mModel = new UserModel();
        initData();
    }

    private void initData() {
        mTvTitle.setText(getString(R.string.update_user_nick));
        mUser = FuLiCenterApplication.getCurrentUser();
        if (mUser == null) {
            backArea();
        } else {
            mEtNewNick.setText(mUser.getMuserNick());
            mEtNewNick.selectAll();
        }
    }

    @OnClick(R.id.ivBack)
    public void backArea() {
        MFGT.finish(UpdateNickActivity.this);
    }
    private void showDialog() {
        mProgressDialog = new ProgressDialog(UpdateNickActivity.this);
        mProgressDialog.setMessage(getString(R.string.update_user_nick));
        mProgressDialog.show();
    }

    @OnClick(R.id.btnSaveNick)
    public void saveNick() {
        if (checkInput()) {
            showDialog();
            mModel.updateNick(UpdateNickActivity.this, mUser.getMuserName(), newNick,
                    new OnCompleteListener<String>() {
                        @Override
                        public void onSuccess(String s) {
                            Result result = ResultUtils.getResultFromJson(s, User.class);
                            if (result != null) {
                                if (result.isRetMsg()) {
                                    User user = (User) result.getRetData();
                                    updateNickSuccess(user);
                                } else {
                                    if (result.getRetCode() == I.MSG_USER_SAME_NICK) {
                                        CommonUtils.showShortToast(R.string.update_nick_fail_unmodify);
                                    }
                                    if (result.getRetCode() == I.MSG_USER_UPDATE_NICK_FAIL) {
                                        CommonUtils.showShortToast(R.string.update_fail);
                                    }
                                }
                            }
                            mProgressDialog.dismiss();
                        }

                        @Override
                        public void onError(String error) {
                            CommonUtils.showShortToast(R.string.update_fail);
                            mProgressDialog.dismiss();
                        }
                    });
        }
    }

    private void updateNickSuccess(final User user) {
        //保存在内存中
        FuLiCenterApplication.setCurrentUser(user);
        L.e(TAG,"updateNickSuccess_user"+user);
        //保存到数据库中
        new Thread(new Runnable() {
            @Override
            public void run() {
                boolean b = UserDao.getInstance(UpdateNickActivity.this).saveUserInfo(user);
                L.e(TAG,"updateNickSuccess:"+b);
            }
        }).start();

//        setResult(RESULT_OK);

        MFGT.finish(UpdateNickActivity.this);
    }

    private boolean checkInput() {
        newNick = mEtNewNick.getText().toString().trim();
        if (TextUtils.isEmpty(newNick)) {
            mEtNewNick.requestFocus();
            mEtNewNick.setError(getString(R.string.nick_name_connot_be_empty));
            return false;
        }
        if (newNick.equals(mUser.getMuserNick())) {
            mEtNewNick.requestFocus();
            mEtNewNick.setError(getString(R.string.update_nick_fail_unmodify));
            return false;
        }
        return true;
    }
}
