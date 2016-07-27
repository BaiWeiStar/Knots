package cn.libery.knots.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.libery.knots.R;
import cn.libery.knots.model.Repository;

/**
 * Created by Libery on 2016/7/25.
 * Email:libery.szq@qq.com
 */
public class RecStarredAdapter extends SuperAdapter<Repository> {

    private List<Repository> mList;

    public RecStarredAdapter(final List<Repository> list, int layout) {
        super(list, layout);
        mList = list;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        return new StarViewHolder(LayoutInflater.from(parent.getContext()).inflate(mLayout, parent, false));
    }

    @Override
    public void onBindViewHolder(final BaseViewHolder holder, final int position) {
        StarViewHolder viewHolder = (StarViewHolder) holder;
        Repository rep = mList.get(position);
        viewHolder.fullName.setText(rep.getFull_name());
        viewHolder.desc.setText(rep.getDescription());
        viewHolder.starredCount.setText(rep.getStargazers_count() + "");
        viewHolder.starredTime.setText(rep.getPushed_at());
        viewHolder.forkCount.setText(rep.getForks() + "");
        viewHolder.type.setText(rep.getLanguage());
    }

    public static class StarViewHolder extends BaseViewHolder {
        @BindView(R.id.item_full_name)
        TextView fullName;

        @BindView(R.id.item_desc)
        TextView desc;

        @BindView(R.id.item_start_count)
        TextView starredCount;

        @BindView(R.id.item_time)
        TextView starredTime;

        @BindView(R.id.item_fork_count)
        TextView forkCount;

        @BindView(R.id.item_type)
        TextView type;

        public StarViewHolder(final View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }

}
