package cn.moon.fulicenter.ui.adpter;

import android.content.Context;
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

/**
 * Created by Moon on 2017/3/15.
 */

public class BoutiqueAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    boolean more;

    Context mContext;
    List<BoutiqueBean> mList;
    @BindView(R.id.tvFooter)
    TextView mtvFooter;

    public void setMtvFooter(TextView mtvFooter) {
        this.mtvFooter = mtvFooter;
        notifyDataSetChanged();
    }

    public BoutiqueAdapter(Context mContext, List<BoutiqueBean> mList) {
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
                layout = View.inflate(mContext, R.layout.item_boutique, null);
                return new BoutiqueViewHolder(layout);
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
        BoutiqueViewHolder holder = (BoutiqueViewHolder) parentHolder;
        BoutiqueBean bean = mList.get(position);
        holder.mTvBoutiqueTitle.setText(bean.getTitle());
        holder.mTvBoutiqueName.setText(bean.getName());
        holder.mTvBoutiqueDescription.setText(bean.getDescription());
        ImageLoader.downloadImg(mContext,holder.mIvBoutiqueImg,bean.getImageurl());
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


    static class FooterViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tvFooter)
        TextView tvFooter;

        FooterViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    class BoutiqueViewHolder extends RecyclerView.ViewHolder{
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
