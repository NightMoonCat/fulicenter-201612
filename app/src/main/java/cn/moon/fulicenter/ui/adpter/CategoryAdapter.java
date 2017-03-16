package cn.moon.fulicenter.ui.adpter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;

import java.util.ArrayList;
import java.util.List;

import cn.moon.fulicenter.model.bean.CategoryChildBean;
import cn.moon.fulicenter.model.bean.CategoryGroupBean;

/**
 * Created by Moon on 2017/3/16.
 */

public class CategoryAdapter extends BaseExpandableListAdapter {


    Context mContext;
    List<CategoryGroupBean> mCategoryGroupList;
    List<List<CategoryChildBean>> mCategoryChildList;


    public CategoryAdapter(Context context) {
        mCategoryGroupList = new ArrayList<>();
        mCategoryChildList = new ArrayList<>();
        mContext = context;
    }

    @Override
    public int getGroupCount() {
        return mCategoryGroupList != null ? mCategoryGroupList.size() : 0;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return mCategoryChildList.get(groupPosition) != null && mCategoryChildList != null ?
                mCategoryChildList.get(groupPosition).size() : 0;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return mCategoryGroupList.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return mCategoryChildList.get(groupPosition).get(childPosition);
    }

    @Override
    public long getGroupId(int i) {
        return 0;
    }

    @Override
    public long getChildId(int i, int i1) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition,
                             boolean isExpand, View convertView, ViewGroup parent) {


        return null;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        return null;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return false;
    }
}
