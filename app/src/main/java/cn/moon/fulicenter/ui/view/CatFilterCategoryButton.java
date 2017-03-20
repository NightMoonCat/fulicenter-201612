package cn.moon.fulicenter.ui.view;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.moon.fulicenter.R;
import cn.moon.fulicenter.model.bean.CategoryChildBean;
import cn.moon.fulicenter.model.utils.CommonUtils;
import cn.moon.fulicenter.model.utils.ImageLoader;


public class CatFilterCategoryButton extends android.support.v7.widget.AppCompatButton {
    private static final String TAG = "CatFilterCategoryButton";

    Context mContext;
    boolean isExpand = false;
    PopupWindow mPopupWindow;

    GridView mGridView;
    CatFilterAdapter mAdapter;
    ArrayList<CategoryChildBean> mList = new ArrayList<>();

    public CatFilterCategoryButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        setCatFilterOnClickListener();
    }

    public void initView(String groupName, ArrayList<CategoryChildBean> list) {
        if (groupName == null && list == null) {
            CommonUtils.showShortToast("小类数据获取异常");
            return;
        }
        this.setText(groupName);
        mList = list;
        mGridView = new GridView(mContext);
        mAdapter = new CatFilterAdapter(mContext,mList);
        mGridView.setAdapter(mAdapter);

    }

    private void setCatFilterOnClickListener() {

        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isExpand) {
                    initPop();
                } else {
                    if (mPopupWindow != null && mPopupWindow.isShowing()) {
                        mPopupWindow.dismiss();
                    }
                }
                showArrow();
            }
        });
    }

    private void initPop() {
        if (mPopupWindow == null) {
            mPopupWindow = new PopupWindow(mContext);
            mPopupWindow.setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
            mPopupWindow.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
            mPopupWindow.setBackgroundDrawable(new ColorDrawable(0xbb000000));
            mPopupWindow.setContentView(mGridView);
        }
        mPopupWindow.showAsDropDown(this);
    }

    private void showArrow() {
        Drawable end = getResources().getDrawable(isExpand ?
                R.drawable.arrow2_up : R.drawable.arrow2_down);
        setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, end, null);
        isExpand = !isExpand;
    }




    class CatFilterAdapter extends BaseAdapter {
        Context mContext;
        List<CategoryChildBean> mList;

        public CatFilterAdapter(Context context, List<CategoryChildBean> list) {
            mContext = context;
            mList = list;
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

            CatFilterViewHolder(View view) {
                ButterKnife.bind(this, view);
            }

            public void bind(int position) {
                CategoryChildBean bean = mList.get(position);
                mTvCatFilterChildName.setText(bean.getName());
                ImageLoader.downloadImg(mContext,mIvCatFilterChildIcon,bean.getImageUrl());
            }
        }
    }

}