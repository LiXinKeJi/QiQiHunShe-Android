package com.lxkj.qiqihunshe.app.interf;

import android.support.v4.view.ViewPager;
import android.support.v7.widget.CardView;
import android.view.View;

/**
 * PageTransformer ViewPager切换动画
 * Created by Slingge on 2017/7/15 0015.
 */

public class ShadowTransformer implements ViewPager.OnPageChangeListener, ViewPager.PageTransformer {

    private ViewPager mViewPager;
    private CardAdapter mAdapter;
    private float mLastOffset;
    private boolean mScalingEnabled;

    public ShadowTransformer(ViewPager mViewPager, CardAdapter mAdapter) {
        this.mViewPager = mViewPager;
        this.mAdapter = mAdapter;
        mViewPager.addOnPageChangeListener(this);
    }


    public void enableScaling(boolean enable) {
        if (mScalingEnabled && !enable) {
            CardView currentCard = mAdapter.getCardViewAt(mViewPager.getCurrentItem());
            if (currentCard != null) {
                currentCard.animate().scaleY(1);//不放大
                currentCard.animate().scaleX(1);
            }
        } else if (!mScalingEnabled && enable) {
            CardView currentCard = mAdapter.getCardViewAt(mViewPager.getCurrentItem());
            if (currentCard != null) {
                currentCard.animate().scaleY(1.1f);//放大1.1倍
                currentCard.animate().scaleX(1.1f);//放大1.1倍
            }
        }
        mScalingEnabled = enable;
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        int realCurrentPosition;
        int nextPosition;
        float baseElevation = mAdapter.getBaseElevation();
        float realOffset;
        boolean goingLeft = mLastOffset > positionOffset;

        if (goingLeft) {
            realCurrentPosition = position + 1;
            nextPosition = position;
            realOffset = 1 - positionOffset;
        } else {
            nextPosition = position + 1;
            realCurrentPosition = position;
            realOffset = positionOffset;
        }

        if (nextPosition > mAdapter.getCount() - 1 || realCurrentPosition > mAdapter.getCount() - 1) {
            return;
        }

        CardView currentCard = mAdapter.getCardViewAt(realCurrentPosition);
        if (currentCard != null) {
            if (mScalingEnabled) {
                currentCard.setScaleX((float) (1 + 0.1 * (1 - realOffset)));
                currentCard.setScaleY((float) (1 + 0.1 * (1 - realOffset)));
            }
            currentCard.setCardElevation((baseElevation + baseElevation * (5 - 1) * (1 - realOffset)));
        }

        CardView nextcard = mAdapter.getCardViewAt(nextPosition);
        if (nextcard != null) {
            if (mScalingEnabled) {
                nextcard.setScaleX((float) (1 + 0.1 * (realOffset)));
                nextcard.setScaleY((float) (1 + 0.1 * (realOffset)));
            }
            nextcard.setCardElevation((baseElevation + baseElevation * (5 - 1) * (realOffset)));
        }
        mLastOffset = positionOffset;
    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }


    @Override
    public void transformPage(View page, float position) {

    }

}
