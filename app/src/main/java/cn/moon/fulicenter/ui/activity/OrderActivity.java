package cn.moon.fulicenter.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.moon.fulicenter.R;
import cn.moon.fulicenter.application.I;
import cn.moon.fulicenter.ui.view.MFGT;

public class OrderActivity extends AppCompatActivity {

    @BindView(R.id.tvTitle)
    TextView mTvTitle;
    @BindView(R.id.ed_order_name)
    EditText mEdOrderName;
    @BindView(R.id.ed_order_phone)
    EditText mEdOrderPhone;
    @BindView(R.id.spin_order_province)
    Spinner mSpinOrderProvince;
    @BindView(R.id.ed_order_street)
    EditText mEdOrderStreet;

    int orderPrice = 0;
    @BindView(R.id.tv_order_price)
    TextView mTvOrderPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        ButterKnife.bind(this);
        orderPrice = getIntent().getIntExtra(I.ORDER_BUY_PRICE, 0);
        initView();

    }

    private void initView() {
        mTvTitle.setText("填写收货人信息");
        mTvOrderPrice.setText(String.valueOf("￥:" + orderPrice));
    }

    @OnClick({R.id.ivBack, R.id.tv_order_buy})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivBack:
                MFGT.finish(OrderActivity.this);
                break;
            case R.id.tv_order_buy:
                if (checkInput()) {

                }
                break;
        }
    }

    private boolean checkInput() {
        String receiveName = mEdOrderName.getText().toString();
        if (TextUtils.isEmpty(receiveName)) {
            mEdOrderName.setError("收货人姓名不能为空");
            mEdOrderName.requestFocus();
            return false;
        }
        String mobile = mEdOrderPhone.getText().toString();
        if (TextUtils.isEmpty(mobile)) {
            mEdOrderPhone.setError("手机号码不能为空");
            mEdOrderPhone.requestFocus();
            return false;
        }
        if (!mobile.matches("[\\d]{11}")) {
            mEdOrderPhone.setError("手机号码格式错误");
            mEdOrderPhone.requestFocus();
            return false;
        }
        String area = mSpinOrderProvince.getSelectedItem().toString();
        if (TextUtils.isEmpty(area)) {
            Toast.makeText(OrderActivity.this, "收货地区不能为空", Toast.LENGTH_SHORT).show();
            return false;
        }
        String address = mEdOrderStreet.getText().toString();
        if (TextUtils.isEmpty(address)) {
            mEdOrderStreet.setError("街道地址不能为空");
            mEdOrderStreet.requestFocus();
            return false;
        }
        return true;
    }
}
