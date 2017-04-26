package cn.moon.fulicenter.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.moon.fulicenter.R;
import cn.moon.fulicenter.application.I;
import cn.moon.fulicenter.model.bean.CategoryChildBean;
import cn.moon.fulicenter.model.bean.CategoryGroupBean;
import cn.moon.fulicenter.model.net.CategoryModel;
import cn.moon.fulicenter.model.net.ICategoryModel;
import cn.moon.fulicenter.model.net.OnCompleteListener;
import cn.moon.fulicenter.model.utils.ImageLoader;
import cn.moon.fulicenter.model.utils.L;
import cn.moon.fulicenter.model.utils.ResultUtils;
import cn.moon.fulicenter.ui.view.MFGT;

public class CategoryChildFragment extends Fragment {
    ICategoryModel model;
    int parentId = 344;
    String groupName;
    @BindView(R.id.rv_category_child)
    RecyclerView mRvCategoryChild;
    childAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_category_child, container, false);
        ButterKnife.bind(this, view);
        initView();
        EventBus.getDefault().register(this);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }

    private void initView() {
        mRvCategoryChild.setHasFixedSize(true);
        StaggeredGridLayoutManager sgm = new StaggeredGridLayoutManager(I.COLUM_NUM,StaggeredGridLayoutManager.VERTICAL);
        mRvCategoryChild.setLayoutManager(sgm);
        adapter = new childAdapter(new ArrayList<CategoryChildBean>(),"");
        mRvCategoryChild.setAdapter(adapter);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        model = new CategoryModel();
        syncData();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void update(CategoryGroupBean group){
        L.e("child","group="+group);
        if (group!=null) {
            parentId = group.getId();
            groupName = group.getName();
            syncData();
        }
    }

    public void syncData() {
        model.loadChildData(getContext(), parentId, new OnCompleteListener<CategoryChildBean[]>() {
            @Override
            public void onSuccess(CategoryChildBean[] result) {
                if (result != null) {
                    ArrayList<CategoryChildBean> list = ResultUtils.array2List(result);
                    adapter.setChildList(list,groupName);
                }
            }

            @Override
            public void onError(String error) {
            }
        });
    }

    class childAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        ArrayList<CategoryChildBean> childList = new ArrayList<>();
        String groupName;

        public childAdapter(ArrayList<CategoryChildBean> childList,String name) {
            this.childList = childList;
            groupName = name;
        }

        public void setChildList(ArrayList<CategoryChildBean> childList,String name) {
            this.childList = childList;
            groupName = name;
            notifyDataSetChanged();
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getContext()).inflate(R.layout.adapter_child, parent, false);
            ChildViewHolder vh = new ChildViewHolder(view);
            return vh;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
            ChildViewHolder vh = (ChildViewHolder) holder;
            final CategoryChildBean child = childList.get(position);
            if (child != null) {
                vh.mTvChild.setText(child.getName());
                ImageLoader.downloadImg(getContext(), vh.mIvChild, child.getImageUrl());
                vh.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        MFGT.gotoCategoryChildActivity(getContext(),child.getId(),
                                groupName,
                                childList);
                    }
                });
            }
        }

        @Override
        public int getItemCount() {
            return childList.size();
        }

        class ChildViewHolder extends RecyclerView.ViewHolder {
            @BindView(R.id.iv_child)
            ImageView mIvChild;
            @BindView(R.id.tv_child)
            TextView mTvChild;

            ChildViewHolder(View view) {
                super(view);
                ButterKnife.bind(this, view);
            }
        }
    }
}