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
import com.lxkj.qiqihunshe.app.ui.xiaoxi.model.DataListModel;
import com.lxkj.qiqihunshe.app.util.GlideUtil;
import com.lxkj.runproject.app.view.SquareImage;

import java.util.List;

/**
 * Created by kxn on 2019/3/7 0007.
 */
public class NearInvitationAdapter extends RecyclerView.Adapter<NearInvitationAdapter.ViewHolder> {


    private Context context;
    private List<DataListModel> list;
    private OnItemClickListener onItemClickListener;

    public NearInvitationAdapter(Context context, List list) {
        this.context = context;
        this.list = list;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_invitation, parent, false);
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
                    holder.tvAge.setBackgroundResource(R.mipmap.bg_sex_nv);
                    holder.tvAge.setTextColor(context.getResources().getColor(R.color.girl));
                    break;
                case "1"://男
                    holder.tvAge.setBackgroundResource(R.mipmap.bg_sex_nan);
                    holder.tvAge.setTextColor(context.getResources().getColor(R.color.colorAccent));
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
                holder.tvTotalnum.setVisibility(View.GONE);
                break;
            case 2:
                GlideUtil.INSTANCE.glideHeaderLoad(context, list.get(position).getImage().get(0), holder.iv1);
                GlideUtil.INSTANCE.glideHeaderLoad(context, list.get(position).getImage().get(1), holder.iv2);
                holder.iv1.setVisibility(View.VISIBLE);
                holder.iv2.setVisibility(View.VISIBLE);
                holder.iv3.setVisibility(View.INVISIBLE);
                holder.tvTotalnum.setVisibility(View.GONE);
                break;
            case 3:
                GlideUtil.INSTANCE.glideHeaderLoad(context, list.get(position).getImage().get(0), holder.iv1);
                GlideUtil.INSTANCE.glideHeaderLoad(context, list.get(position).getImage().get(1), holder.iv2);
                GlideUtil.INSTANCE.glideHeaderLoad(context, list.get(position).getImage().get(2), holder.iv3);
                holder.iv1.setVisibility(View.VISIBLE);
                holder.iv2.setVisibility(View.VISIBLE);
                holder.iv3.setVisibility(View.VISIBLE);
                holder.tvTotalnum.setVisibility(View.GONE);
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


        holder.iv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.seePhotoonClick(position, 0);
            }
        });
        holder.iv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.seePhotoonClick(position, 1);
            }
        });
        holder.iv3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.seePhotoonClick(position, 2);
            }
        });


        if (null != list.get(position).getAdtime())
            holder.tvDate.setText(list.get(position).getAdtime());


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (null != onItemClickListener)
                    onItemClickListener.OnItemClick(position);
            }
        });


        holder.tvReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.ReportonClick(position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public interface OnItemClickListener {
        void OnItemClick(int position);

        void ReportonClick(int position);

        void seePhotoonClick(int position, int count);
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
        @BindView(R.id.tv_zhui)
        TextView tvZhui;
        @BindView(R.id.tv_time)
        TextView tvTime;
        @BindView(R.id.tv_content)
        TextView tvContent;
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
