package cn.moon.fulicenter.ui.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.moon.fulicenter.R;
import cn.moon.fulicenter.model.bean.CategoryChildBean;
import cn.moon.fulicenter.model.bean.CategoryGroupBean;
import cn.moon.fulicenter.model.net.CategoryModel;
import cn.moon.fulicenter.model.net.ICategoryModel;
import cn.moon.fulicenter.model.net.OnCompleteListener;

/**
 * A simple {@link Fragment} subclass.
 */
public class CategoryFragment extends Fragment {


    @BindView(R.id.elvCategory)
    ExpandableListView mElvCategory;

    ICategoryModel mModel;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_category, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mModel = new CategoryModel();
        loadGroupData();

    }

    private void loadGroupData() {
        mModel.loadGroupData(getContext(), new OnCompleteListener<CategoryGroupBean[]>() {
            @Override
            public void onSuccess(CategoryGroupBean[] result) {

            }

            @Override
            public void onError(String error) {

            }
        });
    }

    private void loadChildData(int parentId) {
        mModel.loadChildData(getContext(), parentId, new OnCompleteListener<CategoryChildBean[]>() {
            @Override
            public void onSuccess(CategoryChildBean[] result) {

            }

            @Override
            public void onError(String error) {

            }
        });
    }
}
