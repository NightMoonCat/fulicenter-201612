package cn.moon.fulicenter.ui.view;
//
//import android.content.Context;
//import android.graphics.Color;
//import android.graphics.drawable.ColorDrawable;
//import android.graphics.drawable.Drawable;
//import android.util.AttributeSet;
//import android.view.View;
//import android.widget.LinearLayout;
//import android.widget.PopupWindow;
//import android.widget.TextView;
//
//import cn.moon.fulicenter.R;
//
///**
// * Created by Moon on 2017/3/17.
// */
//
//public class CatFilterCategoryButton extends android.support.v7.widget.AppCompatButton {
//    boolean isExpand = false;
//    PopupWindow mPopupWindow;
//
//    Context mContext;
//    public CatFilterCategoryButton(Context context, AttributeSet attrs) {
//        super(context, attrs);
//        mContext = context;
//        showArrow();
//        initPop();
//    }
//
//    private void initPop() {
//        if (mPopupWindow==null) {
//            mPopupWindow = new PopupWindow(mContext);
//            mPopupWindow.setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
//            mPopupWindow.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
//            mPopupWindow.setBackgroundDrawable(new ColorDrawable(0xbb000000));
//            TextView tv = new TextView(mContext);
//            tv.setTextColor(getResources().getColor(R.color.red));
//            tv.setTextSize(30);
//            tv.setText("CatFilterCategoryButton");
//            mPopupWindow.setContentView(tv);
//        }
//        mPopupWindow.showAsDropDown(this);
//
////
////        mPopupWindow = new PopupWindow(mContext);
////        mPopupWindow.setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
////        mPopupWindow.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
////        mPopupWindow.setBackgroundDrawable(new ColorDrawable(0xbb000000));
////        TextView tv = new TextView(mContext);
////        tv.setTextColor(Color.RED);
////        tv.setTextSize(40);
////        tv.setText("这里显示示例扩展界面");
////        mPopupWindow.setContentView(tv);
//////        mPopupWindow.showAsDropDown(this);
//    }
//
//    private void showArrow() {
//        setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (!isExpand) {
//                    initPop();
//                } else {
//                    if (mPopupWindow != null && mPopupWindow.isShowing()) {
//                        mPopupWindow.dismiss();
//                    }
//                }
//
//                Drawable end = getResources().getDrawable(isExpand ?
//                        R.drawable.arrow2_up : R.drawable.arrow2_down);
//                setCompoundDrawablesRelativeWithIntrinsicBounds(null, null,
//                        end, null);
//                isExpand = !isExpand;
//            }
//        });
//    }
//}

        import android.content.Context;
        import android.graphics.drawable.ColorDrawable;
        import android.graphics.drawable.Drawable;
        import android.util.AttributeSet;
        import android.view.View;
        import android.widget.Button;
        import android.widget.LinearLayout;
        import android.widget.PopupWindow;
        import android.widget.TextView;

        import cn.moon.fulicenter.R;



public class CatFilterCategoryButton extends android.support.v7.widget.AppCompatButton {
    private static final String TAG = "CatFilterCategoryButton";

    Context mContext;
    boolean isExpan = false;
    PopupWindow mPopupWindow;

    public CatFilterCategoryButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        setCatFilterOnClickListener();
    }

    private void setCatFilterOnClickListener() {

        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isExpan){
                    initPop();
                }else{
                    if (mPopupWindow!=null && mPopupWindow.isShowing()) {
                        mPopupWindow.dismiss();
                    }
                }
                showArrow();
            }
        });
    }

    private void initPop() {
        if (mPopupWindow==null) {
            mPopupWindow = new PopupWindow(mContext);
            mPopupWindow.setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
            mPopupWindow.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
            mPopupWindow.setBackgroundDrawable(new ColorDrawable(0xbb000000));
            TextView tv = new TextView(mContext);
            tv.setTextColor(getResources().getColor(R.color.red));
            tv.setTextSize(30);
            tv.setText("CatFilterCategoryButton");
            mPopupWindow.setContentView(tv);
        }
        mPopupWindow.showAsDropDown(this);
    }

    private void showArrow(){
        Drawable end = getResources().getDrawable(isExpan ?
                R.drawable.arrow2_up : R.drawable.arrow2_down);
        setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, end, null);
        isExpan = !isExpan;
    }
}