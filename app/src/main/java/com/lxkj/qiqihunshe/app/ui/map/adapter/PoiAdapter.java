package com.lxkj.qiqihunshe.app.ui.map.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.baidu.mapapi.search.core.PoiInfo;
import com.lxkj.qiqihunshe.R;

import java.util.List;

/**
 * Created by kxn on 2019/3/8 0008.
 */
public class PoiAdapter extends BaseAdapter {
    Context context;
    List<PoiInfo> list;

    public PoiAdapter(Context context, List<PoiInfo> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (null == view) {
            view = LayoutInflater.from(context).inflate(R.layout.item_poi, null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        }else
            holder = ((ViewHolder) view.getTag());

        holder.tvAddress.setText(list.get(i).getName());
        holder.tvAddressDetail.setText(list.get(i).getAddress());

        return view;
    }

    class ViewHolder {
        @BindView(R.id.tvAddress)
        TextView tvAddress;
        @BindView(R.id.tvAddressDetail)
        TextView tvAddressDetail;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
