package cn.moon.fulicenter.ui.adpter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.moon.fulicenter.R;
import cn.moon.fulicenter.application.I;
import cn.moon.fulicenter.model.bean.BoutiqueBean;
import cn.moon.fulicenter.model.utils.ImageLoader;
import cn.moon.fulicenter.ui.activity.BoutiqueChildActivity;

/**
 * Created by Moon on 2017/3/15.
 */

public class BoutiqueAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context mContext;
    List<BoutiqueBean> mList;


    public BoutiqueAdapter(Context mContext, List<BoutiqueBean> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layout;
        layout = View.inflate(mContext, R.layout.item_boutique, null);
        return new BoutiqueViewHolder(layout);

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder parentHolder, int position) {
        BoutiqueViewHolder holder = (BoutiqueViewHolder) parentHolder;
        final BoutiqueBean bean = mList.get(position);
        holder.mTvBoutiqueTitle.setText(bean.getTitle());
        holder.mTvBoutiqueName.setText(bean.getName());
        holder.mTvBoutiqueDescription.setText(bean.getDescription());
        ImageLoader.downloadImg(mContext, holder.mIvBoutiqueImg, bean.getImageurl());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mContext.startActivity(new Intent(mContext,BoutiqueChildActivity.class)
                        .putExtra(I.NewAndBoutiqueGoods.CAT_ID,bean.getId()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList != null ? mList.size() : 0;
    }


    class BoutiqueViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.ivBoutiqueImg)
        ImageView mIvBoutiqueImg;
        @BindView(R.id.tvBoutiqueTitle)
        TextView mTvBoutiqueTitle;
        @BindView(R.id.tvBoutiqueName)
        TextView mTvBoutiqueName;
        @BindView(R.id.tvBoutiqueDescription)
        TextView mTvBoutiqueDescription;

        BoutiqueViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
