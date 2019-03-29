package com.lxkj.qiqihunshe.app.ui.shouye.adapter;

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
import com.lxkj.qiqihunshe.app.util.DisplayUtil;
import com.lxkj.qiqihunshe.app.util.GlideUtil;
import com.lxkj.qiqihunshe.app.util.ToastUtil;
import com.lxkj.runproject.app.view.SquareImage;

import java.util.List;

/**
 * Created by kxn on 2019/3/8 0008.
 */
public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {

    private Context context;
    private List<DataListModel> list;
    private OnItemClickListener onItemClickListener;

    public HistoryAdapter(Context context, List list) {
        this.context = context;
        this.list = list;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_matching_history, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        GlideUtil.INSTANCE.glideHeaderLoad(context, list.get(position).getIcon(), holder.ivHeader);

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
            holder.tvZhiye.setText("职业："+list.get(position).getJob());
        else
            holder.tvZhiye.setText("");





        if (null != list.get(position).getDistance())
            holder.tvDistance.setText(DisplayUtil.distanceFormat(Double.parseDouble(list.get(position).getDistance())));
        else
            holder.tvDistance.setText("");

        if (null != list.get(position).getPlan())
            holder.tvEmotional.setText("情感计划："+list.get(position).getPlan());
        else
            holder.tvEmotional.setText("");

        if (null != list.get(position).getIntroduction())
            holder.tvAutograph.setText("个人签名："+list.get(position).getIntroduction());
        else
            holder.tvAutograph.setText("个人签名：暂无");

        if (null != list.get(position).getCredit())
            holder.tvReputation.setText("信誉值：" + list.get(position).getCredit());
        if (null != list.get(position).getPolite())
            holder.tvFeel.setText("言礼值：" + list.get(position).getPolite());
        if (null != list.get(position).getSafe())
            holder.tvSecurity.setText("综合安全值：" + list.get(position).getSafe());




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
        @BindView(R.id.iv_header)
        CircleImageView ivHeader;
        @BindView(R.id.iv_state)
        ImageView ivState;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_age)
        TextView tvAge;
        @BindView(R.id.tv_distance)
        TextView tvDistance;
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
        @BindView(R.id.item)
        ConstraintLayout item;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
