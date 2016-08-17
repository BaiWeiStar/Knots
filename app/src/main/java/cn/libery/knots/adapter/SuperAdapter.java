package cn.libery.knots.adapter;

import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.List;

/**
 * Created by Libery on 2016/7/25.
 * Email:libery.szq@qq.com
 */
public abstract class SuperAdapter<E> extends RecyclerView.Adapter<BaseViewHolder> {

    public List<E> mList;
    @LayoutRes
    public int mLayout;
    protected OnItemClickListener mOnItemClickListener;
    protected OnItemLongClickListener mOnItemLongClickListener;

    public void setOnItemClickListener(final OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    public void setOnItemLongClickListener(final OnItemLongClickListener onItemLongClickListener) {
        mOnItemLongClickListener = onItemLongClickListener;
    }

    public SuperAdapter(final List<E> list, @LayoutRes final int layout) {
        mList = list;
        mLayout = layout;
    }

    public List<E> getList() {
        return mList;
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    public void update(List<E> list) {
        mList = list;
        notifyDataSetChanged();
    }

    public void add(E e) {
        mList.add(e);
        notifyDataSetChanged();
    }

    public void addAll(List<E> list) {
        mList.addAll(list);
        notifyDataSetChanged();
    }

    public void remove(E e) {
        mList.remove(e);
        notifyDataSetChanged();
    }

    public void remove(int position) {
        mList.remove(position);
        notifyItemRemoved(position);
    }

    public void removeAll(List<E> e) {
        mList.removeAll(e);
        notifyDataSetChanged();
    }

    public E getItem(int position) {
        return mList.get(position);
    }

    public interface OnItemClickListener {
        void onClick(View view, int position);
    }

    public interface OnItemLongClickListener {
        void onLongClick(View view, int position);
    }

}
