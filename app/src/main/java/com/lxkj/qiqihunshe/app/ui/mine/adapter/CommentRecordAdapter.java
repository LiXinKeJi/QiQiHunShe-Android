package com.lxkj.qiqihunshe.app.ui.mine.adapter;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.lxkj.qiqihunshe.R;
import com.lxkj.qiqihunshe.app.customview.CircleImageView;
import com.lxkj.qiqihunshe.app.ui.xiaoxi.model.DataListModel;
import com.lxkj.qiqihunshe.app.util.GlideUtil;

import java.util.List;

/**
 * Created by kxn on 2019/3/7 0007.
 * 点评记录
 */
public class CommentRecordAdapter extends RecyclerView.Adapter<CommentRecordAdapter.ViewHolder> {

    private Context context;
    private List<DataListModel> list;
    private OnItemClickListener onItemClickListener;

    public CommentRecordAdapter(Context context, List list) {
        this.context = context;
        this.list = list;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_comment_record, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        switch (list.get(position).getType()) {
            case "1": //我点评的
                GlideUtil.INSTANCE.glideHeaderLoad(context, list.get(position).getMyIcon(), holder.ivHeader0);
                GlideUtil.INSTANCE.glideHeaderLoad(context, list.get(position).getUserIcon(), holder.ivHeader1);
                break;
            case "2"://对方点评我的
                GlideUtil.INSTANCE.glideHeaderLoad(context, list.get(position).getUserIcon(), holder.ivHeader0);
                GlideUtil.INSTANCE.glideHeaderLoad(context, list.get(position).getMyIcon(), holder.ivHeader1);
                break;
        }


        if (null != list.get(position).getUserAge())
            holder.tvAge.setText(list.get(position).getUserAge());
        else
            holder.tvAge.setText("");

        if (null != list.get(position).getUserNickname())
            holder.tvName.setText(list.get(position).getUserNickname());
        else
            holder.tvName.setText("");

        if (null != list.get(position).getUserJob())
            holder.tvOccupation.setText("职业：" + list.get(position).getUserJob());

        if (null != list.get(position).getMatch())
            holder.tvAccord.setText("相符值：" + list.get(position).getMatch());
        if (null != list.get(position).getImpression())
            holder.tvImpression.setText("印象值：" + list.get(position).getImpression());

        if (null != list.get(position).getTime())
            holder.tvTime.setText(list.get(position).getTime());


        if (null != list.get(position).getUserSex()) {
            switch (list.get(position).getUserSex()) {
                case "0"://女
                    holder.tvAge.setBackgroundResource(R.mipmap.bg_sex_nv);
                    holder.tvAge.setTextColor(context.getResources().getColor(R.color.girl));
                    break;
                case "1"://男
                    holder.tvAge.setBackgroundResource(R.mipmap.bg_sex_nan);
                    holder.tvAge.setTextColor(context.getResources().getColor(R.color.colorAccent));
                    break;
            }
        }

        holder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (null != onItemClickListener)
                    onItemClickListener.OnItemClick(position);
            }
        });


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
        @BindView(R.id.iv_header0)
        CircleImageView ivHeader0;
        @BindView(R.id.iv_header1)
        CircleImageView ivHeader1;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_age)
        TextView tvAge;
        @BindView(R.id.tv_time)
        TextView tvTime;
        @BindView(R.id.tv_occupation)
        TextView tvOccupation;
        @BindView(R.id.tv_accord)
        TextView tvAccord;
        @BindView(R.id.tv_impression)
        TextView tvImpression;
        @BindView(R.id.item)
        ConstraintLayout item;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}

