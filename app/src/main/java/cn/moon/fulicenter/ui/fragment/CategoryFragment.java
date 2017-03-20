package cn.moon.fulicenter.ui.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.moon.fulicenter.R;
import cn.moon.fulicenter.model.bean.CategoryChildBean;
import cn.moon.fulicenter.model.bean.CategoryGroupBean;
import cn.moon.fulicenter.model.net.CategoryModel;
import cn.moon.fulicenter.model.net.ICategoryModel;
import cn.moon.fulicenter.model.net.OnCompleteListener;
import cn.moon.fulicenter.model.utils.ResultUtils;
import cn.moon.fulicenter.ui.adpter.CategoryAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class CategoryFragment extends Fragment {

    int loadIndex = 0;
    @BindView(R.id.elvCategory)
    ExpandableListView mElvCategory;
    CategoryAdapter mAdapter;

    ICategoryModel mModel;
    List<CategoryGroupBean> mGroupList = new ArrayList<>();
    ArrayList<ArrayList<CategoryChildBean>> mChildList = new ArrayList<>();
    @BindView(R.id.layout_tips)
    LinearLayout mLayoutTips;

    View loadView;
    View loadFail;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_category, container, false);
        ButterKnife.bind(this, view);
        loadView = LayoutInflater.from(getContext()).inflate(R.layout.loading, mLayoutTips, false);
        loadFail = LayoutInflater.from(getContext()).inflate(R.layout.load_fail, mLayoutTips, false);

        mLayoutTips.addView(loadView);
        mLayoutTips.addView(loadFail);
        loadView.setVisibility(View.VISIBLE);
        loadFail.setVisibility(View.GONE);
        showDialog(true, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mModel = new CategoryModel();
        mAdapter = new CategoryAdapter(getContext());
        mElvCategory.setAdapter(mAdapter);
        loadGroupData();

    }

    private void loadGroupData() {
        mModel.loadGroupData(getContext(), new OnCompleteListener<CategoryGroupBean[]>() {
            @Override
            public void onSuccess(CategoryGroupBean[] result) {
                if (result != null && result.length > 0) {
                    ArrayList<CategoryGroupBean> list = ResultUtils.array2List(result);
                    mGroupList.clear();
                    mGroupList.addAll(list);
                    for (int i = 0; i < list.size(); i++) {
                        mChildList.add(new ArrayList<CategoryChildBean>());
                        loadChildData(list.get(i).getId(), i);
                    }

                }
            }

            @Override
            public void onError(String error) {
                Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();
                showDialog(false, false);
            }
        });
    }

    private void loadChildData(int parentId, final int index) {
        mModel.loadChildData(getContext(), parentId, new OnCompleteListener<CategoryChildBean[]>() {
            @Override
            public void onSuccess(CategoryChildBean[] result) {
                loadIndex++;
                if (result != null && result.length > 0) {
                    ArrayList<CategoryChildBean> list = ResultUtils.array2List(result);
                    mChildList.set(index, list);
                }
                if (loadIndex == mGroupList.size()) {
                    mAdapter.initData(mGroupList, mChildList);
                    showDialog(false,true);
                }
            }

            @Override
            public void onError(String error) {
                loadIndex++;
                showDialog(false, false);
            }
        });
    }

    @OnClick(R.id.layout_tips)
    public void loadAgain() {
        if (loadFail.getVisibility() == View.VISIBLE) {
            loadGroupData();
            showDialog(true, false);
        }
    }

    private void showDialog(boolean dialog, boolean success) {
        loadView.setVisibility(dialog ? View.VISIBLE : View.GONE);
        if (dialog) {
            loadFail.setVisibility(View.GONE);
            mLayoutTips.setVisibility(View.VISIBLE);
        } else {
            mLayoutTips.setVisibility(success ? View.GONE : View.VISIBLE);
            loadFail.setVisibility(success ? View.GONE : View.VISIBLE);

        }
    }


}
