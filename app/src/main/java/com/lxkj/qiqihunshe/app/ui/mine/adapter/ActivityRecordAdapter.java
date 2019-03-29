package com.lxkj.qiqihunshe.app.ui.mine.adapter;

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
import com.lxkj.qiqihunshe.app.util.GlideUtil;
import com.lxkj.qiqihunshe.app.util.SeePhotoViewUtil;
import com.lxkj.runproject.app.view.SquareImage;

import java.util.List;

/**
 * Created by kxn on 2019/3/7 0007.
 */
public class ActivityRecordAdapter extends RecyclerView.Adapter<ActivityRecordAdapter.ViewHolder> {


    private Context context;
    private List<DataListModel> list;
    private OnItemClickListener onItemClickListener;

    public ActivityRecordAdapter(Context context, List list) {
        this.context = context;
        this.list = list;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_activity_record, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {


        if (null != list.get(position).getTitle())
            holder.tvZhui.setText("主题：" + list.get(position).getTitle());
        else
            holder.tvZhui.setText("");

        if (null != list.get(position).getStarttime())
            holder.tvTime.setText("活动时间：" + list.get(position).getStarttime());
        else
            holder.tvTime.setText("");

        if (null != list.get(position).getContent())
            holder.tvContent.setText("活动内容：" + list.get(position).getContent());

        if (null != list.get(position).getCondition())
            holder.tvRange.setText("限制范围：" + list.get(position).getCondition());

        if (null != list.get(position).getSex()) {
            switch (list.get(position).getSex()) {
                case "0":
                    holder.tvLimit.setText("权限：仅限女");
                    break;
                case "1":
                    holder.tvLimit.setText("权限：仅限男");
                    break;
            }
        }


        if (null != list.get(position).getFee()) {
            switch (list.get(position).getFee()) {
                case "0":
                    holder.tvConsumption.setText("消费：AA");
                    break;
                case "1":
                    holder.tvConsumption.setText("消费：对方买单");
                    break;
                case "2":
                    holder.tvConsumption.setText("消费：自己买单");
                    break;
            }
        }

        if (null != list.get(position).getAddress())
            holder.tvAddress.setText("活动地点：" + list.get(position).getAddress());
        switch (list.get(position).getImage().size()) {
            case 0:
                holder.iv1.setVisibility(View.GONE);
                holder.iv2.setVisibility(View.GONE);
                holder.iv3.setVisibility(View.GONE);
                holder.tvTotalnum.setVisibility(View.GONE);
                break;
            case 1:
                GlideUtil.INSTANCE.glideHeaderLoad(context, list.get(position).getImage().get(0), holder.iv1);
                holder.iv1.setVisibility(View.VISIBLE);
                holder.iv2.setVisibility(View.INVISIBLE);
                holder.iv3.setVisibility(View.INVISIBLE);
                holder.tvTotalnum.setVisibility(View.INVISIBLE);
                break;
            case 2:
                GlideUtil.INSTANCE.glideHeaderLoad(context, list.get(position).getImage().get(0), holder.iv1);
                GlideUtil.INSTANCE.glideHeaderLoad(context, list.get(position).getImage().get(1), holder.iv2);
                holder.iv1.setVisibility(View.VISIBLE);
                holder.iv2.setVisibility(View.VISIBLE);
                holder.iv3.setVisibility(View.INVISIBLE);
                holder.tvTotalnum.setVisibility(View.INVISIBLE);
                break;
            case 3:
                GlideUtil.INSTANCE.glideHeaderLoad(context, list.get(position).getImage().get(0), holder.iv1);
                GlideUtil.INSTANCE.glideHeaderLoad(context, list.get(position).getImage().get(1), holder.iv2);
                GlideUtil.INSTANCE.glideHeaderLoad(context, list.get(position).getImage().get(2), holder.iv3);
                holder.iv1.setVisibility(View.VISIBLE);
                holder.iv2.setVisibility(View.VISIBLE);
                holder.iv3.setVisibility(View.VISIBLE);
                holder.tvTotalnum.setVisibility(View.INVISIBLE);
                break;
            default:
                GlideUtil.INSTANCE.glideHeaderLoad(context, list.get(position).getImage().get(0), holder.iv1);
                GlideUtil.INSTANCE.glideHeaderLoad(context, list.get(position).getImage().get(1), holder.iv2);
                GlideUtil.INSTANCE.glideHeaderLoad(context, list.get(position).getImage().get(2), holder.iv3);
                holder.iv1.setVisibility(View.VISIBLE);
                holder.iv2.setVisibility(View.VISIBLE);
                holder.iv3.setVisibility(View.VISIBLE);
                holder.tvTotalnum.setVisibility(View.VISIBLE);
                holder.tvTotalnum.setText("+" + (list.get(position).getImage().size() - 3));
                break;
        }

        if (null != list.get(position).getAdtime())
            holder.tvDate.setText(list.get(position).getAdtime());


        holder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (null != onItemClickListener)
                    onItemClickListener.OnItemClick(position);
            }
        });

        holder.iv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SeePhotoViewUtil.INSTANCE.toPhotoView(context, list.get(position).getImage(), 0);
            }
        });

        holder.iv3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SeePhotoViewUtil.INSTANCE.toPhotoView(context, list.get(position).getImage(), 1);
            }
        });

        holder.iv3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SeePhotoViewUtil.INSTANCE.toPhotoView(context, list.get(position).getImage(), 2);
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
        @BindView(R.id.header)
        CircleImageView header;
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
        @BindView(R.id.tv_zhui)
        TextView tvZhui;
        @BindView(R.id.tv_time)
        TextView tvTime;
        @BindView(R.id.tv_content)
        TextView tvContent;
        @BindView(R.id.tv_range)
        TextView tvRange;
        @BindView(R.id.tv_limit)
        TextView tvLimit;
        @BindView(R.id.tv_consumption)
        TextView tvConsumption;
        @BindView(R.id.tv_address)
        TextView tvAddress;
        @BindView(R.id.iv_1)
        SquareImage iv1;
        @BindView(R.id.iv_2)
        SquareImage iv2;
        @BindView(R.id.iv_3)
        SquareImage iv3;
        @BindView(R.id.tv_totalnum)
        TextView tvTotalnum;
        @BindView(R.id.tv_date)
        TextView tvDate;
        @BindView(R.id.item)
        ConstraintLayout item;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}

