package cn.moon.fulicenter.ui.adpter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.moon.fulicenter.R;
import cn.moon.fulicenter.model.bean.CategoryChildBean;
import cn.moon.fulicenter.model.bean.CategoryGroupBean;
import cn.moon.fulicenter.model.utils.ImageLoader;
import cn.moon.fulicenter.ui.view.MFGT;

/**
 * Created by Moon on 2017/3/16.
 */

public class CategoryAdapter extends BaseExpandableListAdapter {
    Context mContext;
    List<CategoryGroupBean> mCategoryGroupList;
    ArrayList<ArrayList<CategoryChildBean>> mCategoryChildList;

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
    public CategoryGroupBean getGroup(int groupPosition) {
        return mCategoryGroupList.get(groupPosition);
    }

    @Override
    public CategoryChildBean getChild(int groupPosition, int childPosition) {
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
    public View getGroupView(int groupPosition, boolean isExpand,
                             View convertView, ViewGroup parent) {
        GroupViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.item_category_group, null);
            holder = new GroupViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (GroupViewHolder) convertView.getTag();
        }

        ImageLoader.downloadImg(mContext, holder.mIvGroupIcon, getGroup(groupPosition).getImageUrl());
        holder.mTvGroupName.setText(getGroup(groupPosition).getName());
        if (isExpand) {
            holder.mIvExpand.setImageResource(R.mipmap.expand_off);
        } else {
            holder.mIvExpand.setImageResource(R.mipmap.expand_on);
        }
        return convertView;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ChildViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.item_category_child, null);
            holder = new ChildViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ChildViewHolder) convertView.getTag();
        }


        final CategoryChildBean bean = getChild(groupPosition, childPosition);

        if (bean != null) {
            ImageLoader.downloadImg(mContext, holder.mIvChildIcon,
                    bean.getImageUrl());
            holder.mTvChildName.setText(bean.getName());

            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    MFGT.gotoCategoryChildActivity(mContext,bean.getId(),
                            getGroup(groupPosition).getName(),mCategoryChildList.get(groupPosition));
                }
            });
        }
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    public void initData(List<CategoryGroupBean> groupList, ArrayList<ArrayList<CategoryChildBean>> childList) {
        mCategoryGroupList.addAll(groupList);
        mCategoryChildList.addAll(childList);
        notifyDataSetChanged();
    }

    static class GroupViewHolder {
        @BindView(R.id.ivGroupIcon)
        ImageView mIvGroupIcon;
        @BindView(R.id.tvGroupName)
        TextView mTvGroupName;
        @BindView(R.id.ivExpand)
        ImageView mIvExpand;

        GroupViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    static class ChildViewHolder {
        @BindView(R.id.ivChildIcon)
        ImageView mIvChildIcon;
        @BindView(R.id.tvChildName)
        TextView mTvChildName;

        ChildViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
