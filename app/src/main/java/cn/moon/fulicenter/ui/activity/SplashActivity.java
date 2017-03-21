package cn.moon.fulicenter.ui.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import cn.moon.fulicenter.R;
import cn.moon.fulicenter.application.FuLiCenterApplication;
import cn.moon.fulicenter.model.bean.User;
import cn.moon.fulicenter.model.dao.UserDao;
import cn.moon.fulicenter.model.utils.SharedPreferencesUtils;
import cn.moon.fulicenter.ui.view.MFGT;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
    }

    @Override
    protected void onStart() {
        super.onStart();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                String userName = SharedPreferencesUtils.getInstance().getUserName();
                if (userName != null) {
                    User user = UserDao.getInstance(SplashActivity.this).getUserInfo(userName);
                    FuLiCenterApplication.setCurrentUser(user);
                }
                MFGT.gotoMain(SplashActivity.this);
                MFGT.finish(SplashActivity.this);
            }
        }, 2000);
    }

}
