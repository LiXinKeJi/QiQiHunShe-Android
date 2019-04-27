package com.lxkj.qiqihunshe.app.ui.xiaoxi.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.lxkj.qiqihunshe.R;
import com.lxkj.qiqihunshe.app.MyApplication;
import com.lxkj.qiqihunshe.app.customview.CircleImageView;
import com.lxkj.qiqihunshe.app.customview.RoundImageView;
import com.lxkj.qiqihunshe.app.ui.mine.activity.PersonalInfoActivity;
import com.lxkj.qiqihunshe.app.ui.xiaoxi.model.DataListModel;
import com.lxkj.qiqihunshe.app.util.GlideUtil;

import java.util.List;

/**
 * Created by kxn on 2019/3/6 0006.
 * 小七提醒适配器
 */
public class XqHintAdapter extends RecyclerView.Adapter<XqHintAdapter.ViewHolder> {


    private Context context;
    private List<DataListModel> list;
    private OnItemClickListener onItemClickListener;

    public XqHintAdapter(Context context, List list) {
        this.context = context;
        this.list = list;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_remind, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        GlideUtil.INSTANCE.glideHeaderLoad(context, list.get(position).getIcon(), holder.image);

        if (null != list.get(position).getAdtime())
            holder.tvTime.setText(list.get(position).getAdtime());
        else
            holder.tvTime.setText("");

        if (null != list.get(position).getTitle())
            holder.tvTitle.setText(list.get(position).getTitle());
        else
            holder.tvTitle.setText("");

        if (null != list.get(position).getContent())
            holder.tvContent.setText(list.get(position).getContent());
        else
            holder.tvContent.setText("");


        holder.see.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (null != onItemClickListener)
                    onItemClickListener.OnItemClick(position);
            }
        });

        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("userId", list.get(position).getUserId());
                MyApplication.openActivity(context, PersonalInfoActivity.class, bundle);
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


    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_time)
        TextView tvTime;
        @BindView(R.id.iv_header)
        CircleImageView ivHeader;
        @BindView(R.id.image)
        RoundImageView image;
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.tv_content)
        TextView tvContent;
        @BindView(R.id.see)
        TextView see;
        @BindView(R.id.cl_message)
        ConstraintLayout clMessage;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}