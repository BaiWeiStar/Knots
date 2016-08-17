package cn.libery.knots.adapter;

import android.support.annotation.LayoutRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.libery.knots.R;
import cn.libery.knots.model.code.GitTree;

/**
 * Created by Libery on 2016/8/17.
 * Email:libery.szq@qq.com
 */
public class CodeTreeAdapter extends SuperAdapter<GitTree> {

    public CodeTreeAdapter(final List<GitTree> list, @LayoutRes final int layout) {
        super(list, layout);
    }

    @Override
    public BaseViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        return new TreeViewHolder(LayoutInflater.from(parent.getContext()).inflate(mLayout, parent, false),
                mOnItemClickListener);
    }

    @Override
    public void onBindViewHolder(final BaseViewHolder holder, final int position) {
        TreeViewHolder treeHolder = (TreeViewHolder) holder;
        treeHolder.mTree.setText(getItem(position).getPath() + "/");
    }

    public static class TreeViewHolder extends BaseViewHolder {

        @BindView(R.id.tree)
        TextView mTree;

        public TreeViewHolder(final View itemView, final OnItemClickListener listener) {
            super(itemView, listener);
            ButterKnife.bind(this, itemView);
        }
    }

}
