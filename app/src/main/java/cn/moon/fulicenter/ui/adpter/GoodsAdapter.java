package cn.moon.fulicenter.ui.adpter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.moon.fulicenter.R;
import cn.moon.fulicenter.application.I;
import cn.moon.fulicenter.model.bean.NewGoodsBean;
import cn.moon.fulicenter.model.utils.ImageLoader;
import cn.moon.fulicenter.ui.activity.GoodsDetailsActivity;
import cn.moon.fulicenter.ui.view.MFGT;

/**
 * Created by Moon on 2017/3/15.
 */

public class GoodsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    boolean more;

    Context mContext;
    List<NewGoodsBean> mList;
    @BindView(R.id.tvFooter)
    TextView mtvFooter;

    public void setMtvFooter(TextView mtvFooter) {
        this.mtvFooter = mtvFooter;
        notifyDataSetChanged();
    }

    public GoodsAdapter(Context mContext, List<NewGoodsBean> mList) {
        this.mContext = mContext;
        this.mList = mList;
        more = true;
    }

    public boolean isMore() {
        return more;
    }

    public void setMore(boolean more) {
        this.more = more;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layout;
        switch (viewType) {
            case I.TYPE_FOOTER:
                layout = View.inflate(mContext, R.layout.item_footer, null);
                return new FooterViewHolder(layout);
            case I.TYPE_ITEM:
                layout = View.inflate(mContext, R.layout.item_goods, null);
                return new GoodsViewHolder(layout);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder parentHolder, int position) {
        if (getItemViewType(position) == I.TYPE_FOOTER) {
            FooterViewHolder holder = (FooterViewHolder) parentHolder;
            holder.tvFooter.setText(getFooterString());
            return;
        }
        GoodsViewHolder holder = (GoodsViewHolder) parentHolder;
        final NewGoodsBean bean = mList.get(position);
        holder.tvGoodsName.setText(bean.getGoodsName());
        holder.tvPrice.setText(bean.getCurrencyPrice());
        ImageLoader.downloadImg(mContext, holder.ivGoodsThumb, bean.getGoodsThumb());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MFGT.gotoGoodsDetails(mContext,bean.getGoodsId());
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList != null ? mList.size() + 1 : 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == getItemCount() - 1) {
            return I.TYPE_FOOTER;
        }
        return I.TYPE_ITEM;
    }

    public int getFooterString() {
        return more ? R.string.load_more : R.string.no_more;
    }

    static class GoodsViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.ivGoodsThumb)
        ImageView ivGoodsThumb;
        @BindView(R.id.tvGoodsName)
        TextView tvGoodsName;
        @BindView(R.id.tvPrice)
        TextView tvPrice;

        GoodsViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    static class FooterViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tvFooter)
        TextView tvFooter;

        FooterViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
