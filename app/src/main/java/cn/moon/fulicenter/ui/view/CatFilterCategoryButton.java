package cn.moon.fulicenter.ui.view;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import java.util.ArrayList;

import cn.moon.fulicenter.R;
import cn.moon.fulicenter.model.bean.CategoryChildBean;
import cn.moon.fulicenter.model.utils.CommonUtils;
import cn.moon.fulicenter.ui.adpter.CatFilterAdapter;


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
        mAdapter = new CatFilterAdapter(mContext,mList,groupName);
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


}