package cn.libery.knots.adapter;

import android.support.annotation.LayoutRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.libery.knots.R;
import cn.libery.knots.model.code.GitTree;
import cn.libery.knots.utils.ConvertUtil;

/**
 * Created by Libery on 2016/8/11.
 * Email:libery.szq@qq.com
 */
public class CodeAdapter extends SuperAdapter<GitTree> {

    public CodeAdapter(final List<GitTree> list, @LayoutRes final int layout) {
        super(list, layout);
    }

    @Override
    public BaseViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        return new CodeViewHolder(LayoutInflater.from(parent.getContext()).inflate(mLayout, parent, false),
                mOnItemClickListener);
    }

    @Override
    public void onBindViewHolder(final BaseViewHolder holder, final int position) {
        GitTree tree = mList.get(position);
        CodeViewHolder codeHolder = (CodeViewHolder) holder;
        codeHolder.codeName.setText(tree.getPath());
        if (!"tree".equals(tree.getType())) {
            codeHolder.codeSize.setText(ConvertUtil.getCodeSize(tree.getSize()));
        } else {
            codeHolder.codeSize.setText("");
        }
    }

    public static class CodeViewHolder extends BaseViewHolder {

        @BindView(R.id.code_img)
        ImageView codeImg;
        @BindView(R.id.code_name)
        TextView codeName;
        @BindView(R.id.code_size)
        TextView codeSize;

        public CodeViewHolder(final View itemView, final OnItemClickListener listener) {
            super(itemView, listener);
            ButterKnife.bind(this, itemView);
        }

    }
}
