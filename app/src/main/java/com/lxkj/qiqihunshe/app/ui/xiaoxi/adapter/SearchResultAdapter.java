package com.lxkj.qiqihunshe.app.ui.xiaoxi.adapter;

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
import com.lxkj.qiqihunshe.app.ui.xiaoxi.model.DataListModel;
import com.lxkj.qiqihunshe.app.util.AbStrUtil;
import com.lxkj.qiqihunshe.app.util.GlideUtil;

import java.util.List;

/**
 * Created by kxn on 2019/3/6 0006.
 */
public class SearchResultAdapter extends RecyclerView.Adapter<SearchResultAdapter.ViewHolder> {


    private Context context;
    private List<DataListModel> list;
    private OnItemClickListener onItemClickListener;
    private OnAddClickListener onAddListener;

    public SearchResultAdapter(Context context, List list) {
        this.context = context;
        this.list = list;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setOnAddListener(OnAddClickListener onAddListener) {
        this.onAddListener = onAddListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_lookup_result, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        GlideUtil.INSTANCE.glideHeaderLoad(context, list.get(position).getIcon(), holder.ivHeader);

        holder.tvAge.setText(list.get(position).getAge());

        holder.tvName.setText(list.get(position).getNickname());

        if (null != list.get(position).getJob())
            holder.tvZhiye.setText("职业：" + list.get(position).getJob());
        if (null != list.get(position).getPlan())
            holder.tvEmotional.setText("情感计划：" + list.get(position).getPlan());
        if (null != list.get(position).getIntroduction())
            holder.tvAutograph.setText("个人签名：" + list.get(position).getIntroduction());
        if (null != list.get(position).getCredit())
            holder.tvReputation.setText("信誉值：" + list.get(position).getCredit());
        if (null != list.get(position).getPolite())
            holder.tvFeel.setText("言礼值：" + list.get(position).getPolite());
        if (null != list.get(position).getSafe())
            holder.tvSecurity.setText("综合安全值：" + list.get(position).getSafe());

        for (int i = 0; i < list.get(position).getPermission().size(); i++) {
            switch (list.get(position).getPermission().get(i)) {
                case "1":
                    holder.ivV1.setVisibility(View.VISIBLE);
                    break;
                case "2":
                    holder.ivV2.setVisibility(View.VISIBLE);
                    break;
                case "3":
                    holder.ivV3.setVisibility(View.VISIBLE);
                    break;
                case "4":
                    holder.ivV4.setVisibility(View.VISIBLE);
                    break;
                case "5":
                    holder.ivV5.setVisibility(View.VISIBLE);
                    break;
            }
        }


        switch (list.get(position).getSex()) {
            case "0"://女
                holder.tvAge.setBackgroundResource(R.drawable.bg_girl);
                AbStrUtil.INSTANCE.setDrawableLeft(context, R.drawable.ic_girl, holder.tvAge, 3);
                holder.tvAge.setTextColor(context.getResources().getColor(R.color.girl));
                break;
            case "1"://男
                holder.tvAge.setBackgroundResource(R.drawable.thems_bg35);
                AbStrUtil.INSTANCE.setDrawableLeft(context, R.drawable.ic_boy, holder.tvAge, 3);
                holder.tvAge.setTextColor(context.getResources().getColor(R.color.colorThemes));
                break;
        }

        holder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (null != onItemClickListener)
                    onItemClickListener.OnItemClick(position);
            }
        });

        holder.tvAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (null != onAddListener)
                    onAddListener.OnAddClick(position);
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

    public interface OnAddClickListener {
        void OnAddClick(int position);
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_header)
        CircleImageView ivHeader;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_age)
        TextView tvAge;
        @BindView(R.id.tv_add)
        TextView tvAdd;
        @BindView(R.id.tv_degree)
        TextView tvDegree;
        @BindView(R.id.tv_zhiye)
        TextView tvZhiye;
        @BindView(R.id.tv_emotional)
        TextView tvEmotional;
        @BindView(R.id.tv_autograph)
        TextView tvAutograph;
        @BindView(R.id.tv_reputation)
        TextView tvReputation;
        @BindView(R.id.tv_feel)
        TextView tvFeel;
        @BindView(R.id.tv_security)
        TextView tvSecurity;
        @BindView(R.id.iv_v1)
        ImageView ivV1;
        @BindView(R.id.iv_v2)
        ImageView ivV2;
        @BindView(R.id.iv_v3)
        ImageView ivV3;
        @BindView(R.id.iv_v4)
        ImageView ivV4;
        @BindView(R.id.iv_v5)
        ImageView ivV5;
        @BindView(R.id.item)
        ConstraintLayout item;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}