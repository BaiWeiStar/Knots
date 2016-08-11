package cn.libery.knots.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by Libery on 2016/7/25.
 * Email:libery.szq@qq.com
 */
public class BaseViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private SuperAdapter.OnItemClickListener mOnItemClickListener;

    public BaseViewHolder(final View itemView, SuperAdapter.OnItemClickListener listener) {
        super(itemView);
        mOnItemClickListener = listener;
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(final View v) {
        if (mOnItemClickListener != null) {
            mOnItemClickListener.onClick(v, getAdapterPosition());
        }
    }

}
