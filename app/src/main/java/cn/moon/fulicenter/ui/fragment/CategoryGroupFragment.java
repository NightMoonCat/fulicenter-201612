package cn.moon.fulicenter.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import cn.moon.fulicenter.model.bean.CategoryGroupBean;
import cn.moon.fulicenter.model.net.CategoryModel;
import cn.moon.fulicenter.model.net.ICategoryModel;
import cn.moon.fulicenter.model.net.OnCompleteListener;
import cn.moon.fulicenter.model.utils.ResultUtils;

public class CategoryGroupFragment extends ListFragment {
    ICategoryModel model;
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        model = new CategoryModel();
        getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        new WorkThread().start();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void setAdapter(ArrayList<CategoryGroupBean> list){
        setListAdapter(new ArrayAdapter<CategoryGroupBean>(getContext(),
                android.R.layout.simple_list_item_activated_1,
                android.R.id.text1,
                list));
        getListView().setItemChecked(0,true);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        CategoryGroupBean groupBean = (CategoryGroupBean) getListView().getItemAtPosition(position);
//        CommonUtils.showLongToast(groupBean.getId()+","+groupBean.getName());
        EventBus.getDefault().post(groupBean);
    }

    class WorkThread extends Thread{
        @Override
        public void run() {
            model.loadGroupData(getContext(), new OnCompleteListener<CategoryGroupBean[]>() {
                @Override
                public void onSuccess(CategoryGroupBean[] result) {
                    if (result != null) {
                        ArrayList<CategoryGroupBean> list = ResultUtils.array2List(result);
                        EventBus.getDefault().post(list);
//                        setAdapter(list);
                    }
                }

                @Override
                public void onError(String error) {

                }
            });
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}