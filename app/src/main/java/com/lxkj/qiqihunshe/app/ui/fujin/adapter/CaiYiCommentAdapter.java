package com.lxkj.qiqihunshe.app.ui.fujin.adapter;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.lxkj.qiqihunshe.R;
import com.lxkj.qiqihunshe.app.customview.CircleImageView;
import com.lxkj.qiqihunshe.app.ui.fujin.model.DataListModel;
import com.lxkj.qiqihunshe.app.util.AbStrUtil;
import com.lxkj.qiqihunshe.app.util.DisplayUtil;
import com.lxkj.qiqihunshe.app.util.GlideUtil;

import java.util.List;

/**
 * Created by kxn on 2019/3/8 0008.
 */
public class CaiYiCommentAdapter extends RecyclerView.Adapter<CaiYiCommentAdapter.ViewHolder> {

    private Context context;
    private List<DataListModel> list;
    private OnItemClickListener onItemClickListener;

    public CaiYiCommentAdapter(Context context, List list) {
        this.context = context;
        this.list = list;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_comment_cy, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        GlideUtil.INSTANCE.glideHeaderLoad(context, list.get(position).getIcon(), holder.ivHeader);


        if (null != list.get(position).getAge())
            holder.tvAge.setText(list.get(position).getAge());
        else
            holder.tvAge.setText("");

        if (null != list.get(position).getSex()) {
            switch (list.get(position).getSex()) {
                case "0"://女
                    holder.tvAge.setBackgroundResource(R.drawable.bg_girl);
                    AbStrUtil.INSTANCE.setDrawableLeft(context, R.drawable.ic_girl, holder.tvAge, 3);
                    holder.tvAge.setTextColor(context.getResources().getColor(R.color.girl));
                    break;
                case "1"://男
                    holder.tvAge.setBackgroundResource(R.drawable.thems_bg35);
                    holder.tvAge.setTextColor(context.getResources().getColor(R.color.colorAccent));
                    AbStrUtil.INSTANCE.setDrawableLeft(context, R.drawable.ic_boy, holder.tvAge, 3);
                    break;
            }
        }

        if (null != list.get(position).getNickname())
            holder.tvName.setText(list.get(position).getNickname());
        else
            holder.tvName.setText("");

        if (null != list.get(position).getDistance())
            holder.tvJuli.setText(DisplayUtil.distanceFormat(Double.parseDouble(list.get(position).getDistance())));
        else
            holder.tvJuli.setText("");


        if (null != list.get(position).getComment())
            holder.tvComment.setText(list.get(position).getComment());
        else
            holder.tvComment.setText("");


        if (null != list.get(position).getAdtime())
            holder.tvTime.setText(list.get(position).getAdtime());
        else
            holder.tvTime.setText("");


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public interface OnItemClickListener {
        void OnItemClick(int position);
    }

    public interface OnAgreeClickListener {
        void OnAgreeClick(int position);
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_header)
        CircleImageView ivHeader;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_age)
        TextView tvAge;
        @BindView(R.id.tv_comment)
        TextView tvComment;
        @BindView(R.id.tv_juli)
        TextView tvJuli;
        @BindView(R.id.tv_time)
        TextView tvTime;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
