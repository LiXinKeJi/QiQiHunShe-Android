package com.lxkj.qiqihunshe.app.ui.dialog

import android.content.Context
import android.graphics.drawable.BitmapDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.PopupWindow
import android.widget.TextView
import com.lxkj.qiqihunshe.R
import com.weigan.loopview.LoopView

/**
 * 单个String类型选择
 * Created by Slingge on 2019/3/4
 */
class StringSelectPop(val context: Context?, val list:ArrayList<String>, var wheelViewCallBack: StringCallBack) :
    PopupWindow(context) {

    internal var position = 0


    interface StringCallBack {
        fun position(position1: Int)
    }


    init {

        val v = LayoutInflater.from(context).inflate(R.layout.popup_date, null)
        val loopview = v.findViewById<View>(R.id.loopView) as LoopView
        val loopview2 = v.findViewById<View>(R.id.loopView2) as LoopView
        loopview2.visibility = View.GONE
        val loopview3 = v.findViewById<View>(R.id.loopView3) as LoopView
        loopview3.visibility = View.GONE

        v.findViewById<TextView>(R.id.year).visibility = View.GONE
        v.findViewById<TextView>(R.id.month).visibility = View.GONE
        v.findViewById<TextView>(R.id.day).visibility = View.GONE

        //设置是否循环播放
        //        loopView.setNotLoop();
        //滚动监听
        //设置原始数据
        loopview.setNotLoop()
        loopview.setItems(list)
        loopview.setInitPosition(0)



        loopview.setListener { index ->
            position = index
            wheelViewCallBack.position(position)
        }


        val tv_enter = v.findViewById<View>(R.id.tv_enter) as TextView
        tv_enter.setOnClickListener { v1 -> this.dismiss() }

        //设置初始位置


        this.contentView = v
        // 设置SelectPicPopupWindow弹出窗体的宽
        this.width = ViewGroup.LayoutParams.MATCH_PARENT
        // 设置SelectPicPopupWindow弹出窗体的高
        this.height = ViewGroup.LayoutParams.WRAP_CONTENT
        // 设置SelectPicPopupWindow弹出窗体可点击
        this.animationStyle = android.R.style.Animation_InputMethod
        this.isFocusable = true
        //		this.setOutsideTouchable(true);
        this.setBackgroundDrawable(BitmapDrawable())
        this.softInputMode = WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE
    }

}