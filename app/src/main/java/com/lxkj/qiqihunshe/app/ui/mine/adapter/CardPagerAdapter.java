package com.lxkj.qiqihunshe.app.ui.mine.adapter;

import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.lxkj.qiqihunshe.R;
import com.lxkj.qiqihunshe.app.interf.CardAdapter;
import com.lxkj.qiqihunshe.app.ui.mine.model.PermissionBuyModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Slingge on 2017/7/13 0013.
 */

public class CardPagerAdapter extends PagerAdapter implements CardAdapter {

    private List<CardView> mView;
    private List<PermissionBuyModel.dataModel> mData;
    private float mBaseElevation;

    public void setCardPagerCallBack(CardPagerCallBack cardPagerCallBack) {
        this.cardPagerCallBack = cardPagerCallBack;
    }

    private CardPagerCallBack cardPagerCallBack;

    public interface CardPagerCallBack {
        void CardPager(String type, String money);
    }

    public CardPagerAdapter() {
        mView = new ArrayList<>();
        mData = new ArrayList<>();
    }

    public void addCarditem(PermissionBuyModel.dataModel item) {
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
        CardView cardView = view.findViewById(R.id.cardView);
        if (mBaseElevation == 0) {
            mBaseElevation = cardView.getCardElevation();
        }

        cardView.setMaxCardElevation(mBaseElevation * 5);
        mView.set(position, cardView);

        return view;
    }


    private void bind(final PermissionBuyModel.dataModel item, View view) {
        TextView tv_title = view.findViewById(R.id.tv_title);
        TextView tv_money = view.findViewById(R.id.tv_money);
        TextView tv_info = view.findViewById(R.id.tv_info);
        TextView tv_time = view.findViewById(R.id.tv_time);
        TextView tv_buy = view.findViewById(R.id.tv_buy);

        switch (item.getType()) {
            case "1":
                tv_title.setText("小七推荐");
                break;
            case "2":
                tv_title.setText("约见点评");
                break;
            case "3":
                tv_title.setText("经济查找");
                break;
            case "4":
                tv_title.setText("定制推荐");
                break;
            case "5":
                tv_title.setText("牵引安排");
                break;
        }

        if (!TextUtils.isEmpty(item.getPrice())) {
            tv_money.setText("￥" + item.getPrice() + "/月");
        }
        if (!TextUtils.isEmpty(item.getContent())) {
            tv_info.setText(item.getContent());
        }
        if (!TextUtils.isEmpty(item.getEndTime())) {
            tv_time.setText("到期时间：" + item.getEndTime());
        }

        tv_buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cardPagerCallBack.CardPager(item.getType(), item.getPrice());
            }
        });

    }


}
