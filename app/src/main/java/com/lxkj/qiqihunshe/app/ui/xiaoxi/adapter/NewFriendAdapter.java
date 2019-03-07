package com.lxkj.qiqihunshe.app.ui.xiaoxi.adapter;

import android.content.Context;
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
import com.lxkj.qiqihunshe.app.customview.CircleImageView;
import com.lxkj.qiqihunshe.app.ui.xiaoxi.model.DataListModel;
import com.lxkj.qiqihunshe.app.util.GlideUtil;

import java.util.List;

/**
 * Created by kxn on 2019/3/6 0006.
 * 新朋友适配器
 */
public class NewFriendAdapter extends RecyclerView.Adapter<NewFriendAdapter.ViewHolder> {


    private Context context;
    private List<DataListModel> list;
    private OnItemClickListener onItemClickListener;
    OnAgreeClickListener onAgreeClickListener;

    public NewFriendAdapter(Context context, List list) {
        this.context = context;
        this.list = list;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setOnAgreeClickListener(OnAgreeClickListener onAgreeClickListener) {
        this.onAgreeClickListener = onAgreeClickListener;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_firend_new, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        GlideUtil.INSTANCE.glideHeaderLoad(context, list.get(position).getIcon(), holder.ivHead);

        if (null != list.get(position).getNickname())
            holder.tvName.setText(list.get(position).getNickname());
        else
            holder.tvName.setText("");

        if (null != list.get(position).getContent())
            holder.tvMessage.setText(list.get(position).getContent());
        else
            holder.tvMessage.setText("");



        holder.llItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (null != onItemClickListener)
                    onItemClickListener.OnItemClick(position);
            }
        });

        if (null != list.get(position).getStatus()){
            switch (list.get(position).getStatus()){
                case "0"://0待审核
                    holder.tvTag.setText("同意");
                    holder.tvTag.setBackgroundResource(R.drawable.bg_rect_17acf6_5dp);
                    break;
                case "1"://1同意
                    holder.tvTag.setText("已添加");
                    holder.tvTag.setBackgroundResource(R.drawable.bg_rect_c8c8c8_5dp);
                    break;
                case "2"://2拒绝
                    holder.tvTag.setText("已拒绝");
                    holder.tvTag.setBackgroundResource(R.drawable.bg_rect_c8c8c8_5dp);
                    break;
            }
        }

        holder.tvTag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (null != onAgreeClickListener){
                    onAgreeClickListener.OnAgreeClick(position);
                }
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
        @BindView(R.id.ivHead)
        CircleImageView ivHead;
        @BindView(R.id.tvName)
        TextView tvName;
        @BindView(R.id.tvMessage)
        TextView tvMessage;
        @BindView(R.id.tvTag)
        TextView tvTag;
        @BindView(R.id.ll_item)
        LinearLayout llItem;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
