package com.lxkj.qiqihunshe.app.interf

import android.support.v7.widget.CardView

/**
 * Created by Slingge on 2017/7/13 0013.
 */

 public interface CardAdapter {

    val baseElevation: Float

    val count: Int

    fun getCardViewAt(position: Int): CardView



}
