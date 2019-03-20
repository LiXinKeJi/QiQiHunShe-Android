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
import com.lxkj.qiqihunshe.app.ui.mine.model.SpaceDynamicModel;
import com.lxkj.qiqihunshe.app.ui.xiaoxi.model.DataListModel;
import com.lxkj.qiqihunshe.app.util.AbStrUtil;
import com.lxkj.qiqihunshe.app.util.GlideUtil;
import com.lxkj.qiqihunshe.app.util.ToastUtil;
import com.lxkj.runproject.app.view.SquareImage;

import java.util.List;

/**
 * Created by kxn on 2019/3/7 0007.
 */
public class NearDynamicAdapter extends RecyclerView.Adapter<NearDynamicAdapter.ViewHolder> {


    private Context context;
    private List<SpaceDynamicModel.dataModel> list;
    private OnItemClickListener onItemClickListener;

    public NearDynamicAdapter(Context context, List list) {
        this.context = context;
        this.list = list;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_dynamic, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        GlideUtil.INSTANCE.glideHeaderLoad(context, list.get(position).getIcon(), holder.header);

        if (null != list.get(position).getIdentity()) {
            switch (list.get(position).getIdentity()) {
                case "1"://单身
                    holder.ivState.setBackgroundResource(R.mipmap.ic_ds);
                    break;
                case "2"://约会
                    holder.ivState.setBackgroundResource(R.mipmap.ic_yh);
                    break;
                case "3"://牵手
                    holder.ivState.setBackgroundResource(R.mipmap.ic_qs);
                    break;
            }
        }

        if (null != list.get(position).getAge())
            holder.tvAge.setText(list.get(position).getAge());
        else
            holder.tvAge.setText("");

        if (null != list.get(position).getSex()) {
            switch (list.get(position).getSex()) {
                case "0"://女
                    holder.tvAge.setBackgroundResource(R.drawable.bg_girl);
                    holder.tvAge.setTextColor(context.getResources().getColor(R.color.girl));
                    AbStrUtil.INSTANCE.setDrawableLeft(context, R.drawable.ic_girl, holder.tvAge, 3);
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

        if (null != list.get(position).getJob())
            holder.tvOccupation.setText(list.get(position).getJob());
        else
            holder.tvOccupation.setText("");


        switch (list.get(position).getPermission().size()) {
            case 0:
                holder.ivV1.setVisibility(View.GONE);
                holder.ivV2.setVisibility(View.GONE);
                holder.ivV3.setVisibility(View.GONE);
                holder.ivV4.setVisibility(View.GONE);
                holder.ivV5.setVisibility(View.GONE);
                break;
            case 1:
                holder.ivV1.setVisibility(View.VISIBLE);
                holder.ivV2.setVisibility(View.GONE);
                holder.ivV3.setVisibility(View.GONE);
                holder.ivV4.setVisibility(View.GONE);
                holder.ivV5.setVisibility(View.GONE);
                break;
            case 2:
                holder.ivV1.setVisibility(View.VISIBLE);
                holder.ivV2.setVisibility(View.VISIBLE);
                holder.ivV3.setVisibility(View.GONE);
                holder.ivV4.setVisibility(View.GONE);
                holder.ivV5.setVisibility(View.GONE);
                break;
            case 3:
                holder.ivV1.setVisibility(View.VISIBLE);
                holder.ivV2.setVisibility(View.VISIBLE);
                holder.ivV3.setVisibility(View.VISIBLE);
                holder.ivV4.setVisibility(View.GONE);
                holder.ivV5.setVisibility(View.GONE);
                break;
            case 4:
                holder.ivV1.setVisibility(View.VISIBLE);
                holder.ivV2.setVisibility(View.VISIBLE);
                holder.ivV3.setVisibility(View.VISIBLE);
                holder.ivV4.setVisibility(View.VISIBLE);
                holder.ivV5.setVisibility(View.GONE);
                break;
            case 5:
                holder.ivV1.setVisibility(View.VISIBLE);
                holder.ivV2.setVisibility(View.VISIBLE);
                holder.ivV3.setVisibility(View.VISIBLE);
                holder.ivV4.setVisibility(View.VISIBLE);
                holder.ivV5.setVisibility(View.VISIBLE);
                break;
        }


        if (null != list.get(position).getAdtime())
            holder.tvTime.setText(list.get(position).getAdtime());
        else
            holder.tvTime.setText("");

        if (null != list.get(position).getContent())
            holder.tvContent.setText(list.get(position).getContent());

        if (null != list.get(position).getLocation())
            holder.tvAddress.setText(list.get(position).getLocation());

        switch (list.get(position).getImages().size()) {
            case 0:
                holder.iv1.setVisibility(View.GONE);
                holder.iv2.setVisibility(View.GONE);
                holder.iv3.setVisibility(View.GONE);
                holder.tvTotalnum.setVisibility(View.GONE);
                break;
            case 1:
                GlideUtil.INSTANCE.glideHeaderLoad(context, list.get(position).getImages().get(0), holder.iv1);
                holder.iv1.setVisibility(View.VISIBLE);
                holder.iv2.setVisibility(View.INVISIBLE);
                holder.iv3.setVisibility(View.INVISIBLE);
                holder.tvTotalnum.setVisibility(View.INVISIBLE);
                break;
            case 2:
                GlideUtil.INSTANCE.glideHeaderLoad(context, list.get(position).getImages().get(0), holder.iv1);
                GlideUtil.INSTANCE.glideHeaderLoad(context, list.get(position).getImages().get(1), holder.iv2);
                holder.iv1.setVisibility(View.VISIBLE);
                holder.iv2.setVisibility(View.VISIBLE);
                holder.iv3.setVisibility(View.INVISIBLE);
                holder.tvTotalnum.setVisibility(View.INVISIBLE);
                break;
            case 3:
                GlideUtil.INSTANCE.glideHeaderLoad(context, list.get(position).getImages().get(0), holder.iv1);
                GlideUtil.INSTANCE.glideHeaderLoad(context, list.get(position).getImages().get(1), holder.iv2);
                GlideUtil.INSTANCE.glideHeaderLoad(context, list.get(position).getImages().get(2), holder.iv3);
                holder.iv1.setVisibility(View.VISIBLE);
                holder.iv2.setVisibility(View.VISIBLE);
                holder.iv3.setVisibility(View.VISIBLE);
                holder.tvTotalnum.setVisibility(View.GONE);
                break;
            default:
                GlideUtil.INSTANCE.glideHeaderLoad(context, list.get(position).getImages().get(0), holder.iv1);
                GlideUtil.INSTANCE.glideHeaderLoad(context, list.get(position).getImages().get(1), holder.iv2);
                GlideUtil.INSTANCE.glideHeaderLoad(context, list.get(position).getImages().get(2), holder.iv3);
                holder.iv1.setVisibility(View.VISIBLE);
                holder.iv2.setVisibility(View.VISIBLE);
                holder.iv3.setVisibility(View.VISIBLE);
                holder.tvTotalnum.setVisibility(View.VISIBLE);
                holder.tvTotalnum.setText("+" + (list.get(position).getImages().size() - 3));
                break;
        }

        if (null != list.get(position).getZanNum())
            holder.tvZan.setText(list.get(position).getZanNum());
        else
            holder.tvZan.setText("0");

        if (null != list.get(position).getCommentNum())
            holder.tvNum.setText(list.get(position).getCommentNum());
        else
            holder.tvNum.setText("0");

        if (list.get(position).getZan().equals("0")) {// 0未点赞 1已点
            AbStrUtil.INSTANCE.setDrawableLeft(context, R.drawable.ic_zan_nor, holder.tvZan, 5);
        } else {
            AbStrUtil.INSTANCE.setDrawableLeft(context, R.drawable.ic_zan_hl, holder.tvZan, 5);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (null != onItemClickListener)
                    onItemClickListener.OnItemClick(position);
            }
        });


        holder.tvShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickListener.shareClick(position);
            }
        });

        holder.tvZan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickListener.zanClick(position);
            }
        });


        holder.tvReward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickListener.daShnangClick(position);
            }
        });
        holder.tvReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickListener.juBaoClick(position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public interface OnItemClickListener {
        void OnItemClick(int position);

        void zanClick(int position);

        void shareClick(int position);

        void daShnangClick(int position);

        void juBaoClick(int position);
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.header)
        CircleImageView header;
        @BindView(R.id.iv_state)
        ImageView ivState;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_occupation)
        TextView tvOccupation;
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
        @BindView(R.id.tv_age)
        TextView tvAge;
        @BindView(R.id.tv_report)
        ImageView tvReport;
        @BindView(R.id.tv_content)
        TextView tvContent;
        @BindView(R.id.iv_1)
        SquareImage iv1;
        @BindView(R.id.iv_2)
        SquareImage iv2;
        @BindView(R.id.iv_3)
        SquareImage iv3;
        @BindView(R.id.tv_totalnum)
        TextView tvTotalnum;
        @BindView(R.id.tv_address)
        TextView tvAddress;
        @BindView(R.id.tv_time)
        TextView tvTime;
        @BindView(R.id.tv_zan)
        TextView tvZan;
        @BindView(R.id.tv_num)
        TextView tvNum;
        @BindView(R.id.tv_share)
        TextView tvShare;
        @BindView(R.id.tv_reward)
        TextView tvReward;
        @BindView(R.id.item)
        ConstraintLayout item;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
