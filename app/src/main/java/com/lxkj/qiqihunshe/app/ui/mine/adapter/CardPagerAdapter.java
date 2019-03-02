package com.lxkj.qiqihunshe.app.ui.mine.adapter;

import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.lxkj.qiqihunshe.R;
import com.lxkj.qiqihunshe.app.interf.CardAdapter;
import com.lxkj.qiqihunshe.app.ui.model.PermissionBuyModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Slingge on 2017/7/13 0013.
 */

public class CardPagerAdapter extends PagerAdapter implements CardAdapter {

    private List<CardView> mView;
    private List<PermissionBuyModel> mData;
    private float mBaseElevation;


    public CardPagerAdapter() {
        mView = new ArrayList<>();
        mData = new ArrayList<>();
    }

    public void addCarditem(PermissionBuyModel item) {
        mView.add(null);
        mData.add(item);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
        mView.set(position, null);
    }

    @Override
    public float getBaseElevation() {
        return mBaseElevation;
    }

    @Override
    public CardView getCardViewAt(int position) {
        return mView.get(position);
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }


    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.item_permission_buy, container, false);
        container.addView(view);
        bind(mData.get(position), view);
        CardView cardView = (CardView) view.findViewById(R.id.cardView);
        if (mBaseElevation == 0) {
            mBaseElevation = cardView.getCardElevation();
        }

        cardView.setMaxCardElevation(mBaseElevation * 5);
        mView.set(position, cardView);

        return view;
    }


    private void bind(PermissionBuyModel item, View view) {
        TextView tv_title = (TextView) view.findViewById(R.id.tv_title);
//        TextView tv_count = (TextView) view.findViewById(R.id.tv_count);
//        tv_title.setText(item.getmTitle());
//        tv_count.setText(item.getmText());
    }


}
