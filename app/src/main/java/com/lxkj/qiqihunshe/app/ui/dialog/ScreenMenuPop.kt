package com.lxkj.qiqihunshe.app.ui.dialog

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.widget.FrameLayout
import android.widget.PopupWindow
import android.widget.TextView
import com.lxkj.qiqihunshe.R
import com.lxkj.qiqihunshe.app.MyApplication
import com.lxkj.qiqihunshe.app.ui.shouye.activity.MatchingActivity
import com.lxkj.qiqihunshe.app.ui.shouye.activity.StrokeActivity
import com.lxkj.qiqihunshe.app.ui.xiaoxi.activity.LookupActivity

/**
 * 预约时间
 * Created by Slingge on 2017/5/3 0003.
 */

class ScreenMenuPop(private val context: Activity) : View.OnClickListener {


    private var pop: PopupWindow? = null


    @SuppressLint("ClickableViewAccessibility")
    fun screenPopwindow(rlTopBar: View) {
        val view = LayoutInflater.from(context).inflate(
            R.layout.pop_screen_menu, null
        )

        view.findViewById<TextView>(R.id.tv_liao).setOnClickListener(this)
        view.findViewById<TextView>(R.id.tv_yu).setOnClickListener(this)
        view.findViewById<TextView>(R.id.tv_pei).setOnClickListener(this)
        view.findViewById<TextView>(R.id.tv_hua).setOnClickListener(this)
        view.findViewById<TextView>(R.id.tv_jingzhun).setOnClickListener(this)
        view.findViewById<TextView>(R.id.tv_tiaojian).setOnClickListener(this)
        view.findViewById<TextView>(R.id.tv_jingji).setOnClickListener(this)


        // 创建弹出窗口
        // 窗口内容为layoutLeft，里面包含一个ListView
        // 窗口宽度跟tvLeft一样
        pop = PopupWindow(
            view, FrameLayout.LayoutParams.WRAP_CONTENT,
            FrameLayout.LayoutParams.WRAP_CONTENT
        )

        val cd = ColorDrawable(0)
        pop!!.setBackgroundDrawable(cd)
        pop!!.animationStyle = R.style.PopupAnimation
        pop!!.update()
        pop!!.inputMethodMode = PopupWindow.INPUT_METHOD_NEEDED
        pop!!.isTouchable = true // 设置popupwindow可点击
        pop!!.isOutsideTouchable = true // 设置popupwindow外部可点击
        pop!!.isFocusable = true // 获取焦点

        // 设置popupwindow的位置（相对tvLeft的位置）
        pop!!.showAsDropDown(rlTopBar, 0, 0)

        pop!!.setTouchInterceptor(View.OnTouchListener { v, event ->
            // 如果点击了popupwindow的外部，popupwindow也会消失
            if (event.action == MotionEvent.ACTION_OUTSIDE) {
                pop!!.dismiss()
                return@OnTouchListener true
            }
            false
        })
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.tv_liao -> {
                val bundle = Bundle()
                bundle.putInt("flag", 0)
                MyApplication.openActivity(context, MatchingActivity::class.java, bundle)
            }
            R.id.tv_yu -> {
                val bundle = Bundle()
                bundle.putInt("flag", 1)
                MyApplication.openActivity(context, MatchingActivity::class.java, bundle)
            }
            R.id.tv_pei -> {//先判断有没有答卷完成
                QuestionsAnswersDialog.sginUpShow(context)
            }
            R.id.tv_hua -> {
                MyApplication.openActivity(context, StrokeActivity::class.java)
            }
            R.id.tv_jingzhun -> {
                val bundle = Bundle()
                bundle.putInt("flag", 0)
                MyApplication.openActivity(context, LookupActivity::class.java, bundle)
            }
            R.id.tv_tiaojian -> {
                val bundle = Bundle()
                bundle.putInt("flag", 1)
                MyApplication.openActivity(context, LookupActivity::class.java, bundle)
            }
            R.id.tv_jingji -> {
                val bundle = Bundle()
                bundle.putInt("flag", 2)
                MyApplication.openActivity(context, LookupActivity::class.java, bundle)
            }
        }
        pop!!.dismiss()
    }


}