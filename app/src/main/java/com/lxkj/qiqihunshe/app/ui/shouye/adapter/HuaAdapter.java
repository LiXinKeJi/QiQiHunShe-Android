package com.lxkj.qiqihunshe.app.ui.shouye.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.linyuzai.likeornot.BaseAdapter;
import com.lxkj.qiqihunshe.R;
import com.lxkj.qiqihunshe.app.ui.shouye.model.DataListModel;
import com.lxkj.qiqihunshe.app.util.GlideUtil;

import java.util.List;

/**
 * Created by kxn on 2019/3/8 0008.
 */
public class HuaAdapter extends BaseAdapter {
    Context context;
    List<DataListModel> list;

    public HuaAdapter(Context context, List<DataListModel> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public View getView(View convertView, ViewGroup parent, int position) {
        if (convertView == null)
            convertView = LayoutInflater.from(context).inflate(R.layout.item_like_or_not, null);

        ImageView image = convertView.findViewById(R.id.image);
        GlideUtil.INSTANCE.glideLoad(context, list.get(position).getIcon(), image);
        return convertView;
    }
}
