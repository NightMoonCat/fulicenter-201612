package cn.moon.fulicenter.ui.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.moon.fulicenter.R;
import cn.moon.fulicenter.application.FuLiCenterApplication;
import cn.moon.fulicenter.model.bean.MessageBean;
import cn.moon.fulicenter.model.bean.User;
import cn.moon.fulicenter.model.net.IUserModel;
import cn.moon.fulicenter.model.net.OnCompleteListener;
import cn.moon.fulicenter.model.net.UserModel;
import cn.moon.fulicenter.model.utils.ImageLoader;
import cn.moon.fulicenter.ui.view.MFGT;

/**
 * A simple {@link Fragment} subclass.
 */
public class PersonalCenterFragment extends Fragment {


    @BindView(R.id.iv_user_avatar)
    ImageView mIvUserAvatar;
    @BindView(R.id.tv_user_name)
    TextView mTvUserName;
    @BindView(R.id.tv_collect_count)
    TextView mTvCollectCount;
    User user;
    IUserModel mModel;
    @BindView(R.id.center_user_order_lis)
    GridView mCenterUserOrderLis;

    public PersonalCenterFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_personal_center, container, false);
        ButterKnife.bind(this, layout);
        return layout;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initOrderList();
        initData();
    }

    private void initOrderList() {
        ArrayList<HashMap<String, Object>> data = new ArrayList<HashMap<String, Object>>();
        HashMap<String, Object> order1 = new HashMap<String, Object>();
        order1.put("order", R.drawable.order_list1);
        data.add(order1);
        HashMap<String, Object> order2 = new HashMap<String, Object>();
        order2.put("order", R.drawable.order_list2);
        data.add(order2);
        HashMap<String, Object> order3 = new HashMap<String, Object>();
        order3.put("order", R.drawable.order_list3);
        data.add(order3);
        HashMap<String, Object> order4 = new HashMap<String, Object>();
        order4.put("order", R.drawable.order_list4);
        data.add(order4);
        HashMap<String, Object> order5 = new HashMap<String, Object>();
        order5.put("order", R.drawable.order_list5);
        data.add(order5);
        SimpleAdapter adapter = new SimpleAdapter(getContext(), data, R.layout.simple_adapter,
                new String[]{"order"}, new int[]{R.id.center_user_order_lis});
        mCenterUserOrderLis.setAdapter(adapter);
    }


    @Override
    public void onResume() {
        super.onResume();
        initData();
    }

    private void initData() {
        user = FuLiCenterApplication.getCurrentUser();
        if (user != null) {
            showInfo();
            loadCollectCount();
        }
    }

    private void loadCollectCount() {
        mModel = new UserModel();
        mModel.loadCollectCount(getActivity(), user.getMuserName(),
                new OnCompleteListener<MessageBean>() {
                    @Override
                    public void onSuccess(MessageBean msg) {
                        if (msg != null && msg.isSuccess()) {
                            mTvCollectCount.setText(msg.getMsg());
                        } else {
                            mTvCollectCount.setText("0");
                        }
                    }

                    @Override
                    public void onError(String error) {
                        mTvCollectCount.setText("0");
                    }
                });
    }

    private void showInfo() {
        mTvUserName.setText(user.getMuserNick());
        ImageLoader.setAvatar(ImageLoader.getAvatarUrl(user), getActivity(), mIvUserAvatar);
    }

    @OnClick({R.id.tv_center_settings, R.id.center_user_info})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_center_settings:
            case R.id.center_user_info:
                MFGT.gotoSettingActivity(getActivity());
                break;
        }
    }

    @OnClick(R.id.layout_center_collect)
    public void CollectList() {
        MFGT.gotoCollectList(getActivity());
    }
}
