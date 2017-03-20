package cn.moon.fulicenter.ui.adpter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.moon.fulicenter.R;
import cn.moon.fulicenter.model.bean.CategoryChildBean;
import cn.moon.fulicenter.model.utils.ImageLoader;
import cn.moon.fulicenter.ui.activity.CategoryChildActivity;
import cn.moon.fulicenter.ui.view.MFGT;

/**
 * Created by Moon on 2017/3/20.
 */

public class CatFilterAdapter extends BaseAdapter {
    Context mContext;
    ArrayList<CategoryChildBean> mList;
    String mGroupName;

    public CatFilterAdapter(Context context, ArrayList<CategoryChildBean> list,String groupName) {
        mContext = context;
        mList = list;
        mGroupName = groupName;
    }

    @Override
    public int getCount() {
        return mList != null ? mList.size() : 0;
    }

    @Override
    public CategoryChildBean getItem(int i) {
        return mList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        CatFilterViewHolder holder;
        if (view == null) {
            view = View.inflate(mContext, R.layout.item_cat_fliter, null);
            holder = new CatFilterViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (CatFilterViewHolder) view.getTag();
        }
        holder.bind(position);
        return view;
    }

    class CatFilterViewHolder {
        @BindView(R.id.ivCatFilterChildIcon)
        ImageView mIvCatFilterChildIcon;
        @BindView(R.id.tvCatFilterChildName)
        TextView mTvCatFilterChildName;
        @BindView(R.id.layout_category_child)
        LinearLayout mLayoutCategoryChild;

        CatFilterViewHolder(View view) {
            ButterKnife.bind(this, view);
        }

        public void bind(int position) {
            final CategoryChildBean bean = mList.get(position);
            mTvCatFilterChildName.setText(bean.getName());
            ImageLoader.downloadImg(mContext, mIvCatFilterChildIcon, bean.getImageUrl());
            mLayoutCategoryChild.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    MFGT.gotoCategoryChildActivity(mContext,bean.getId(),
                            mGroupName,mList);
                    MFGT.finish(((CategoryChildActivity)mContext));
                }
            });
        }
    }

}
