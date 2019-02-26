package com.lxkj.qiqihunshe.app.ui.dialog

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Activity
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import com.google.gson.Gson
import com.lxkj.qiqihunshe.R
import com.lxkj.qiqihunshe.app.customview.FlowLayout
import com.lxkj.qiqihunshe.app.util.ToastUtil


/**
 * 聊天举报
 * Created by Slingge on 2019/2/22
 */
@SuppressLint("StaticFieldLeak")
object ReportDialog2 {

    private var dialog: AlertDialog? = null
    private var tv_enter: TextView? = null
    private var tv_cancel: TextView? = null
    private var iv_cancel: ImageView? = null

    private var tv_startTime: TextView? = null
    private var tv_endTime: TextView? = null
    private var tv_upfile: TextView? = null


    private val list by lazy { ArrayList<String>() }

    fun show(context: Activity) {
        if (dialog == null) {
            dialog = AlertDialog.Builder(context, R.style.Dialog).create()
            dialog?.show()
            val view = LayoutInflater.from(context).inflate(R.layout.dialog_report2, null)
            tv_enter = view.findViewById(R.id.tv_enter)
            tv_cancel = view.findViewById(R.id.tv_cancel)
            iv_cancel = view.findViewById(R.id.iv_cancel)

            tv_startTime = view.findViewById(R.id.tv_startTime)
            tv_endTime = view.findViewById(R.id.tv_endTime)
            tv_upfile = view.findViewById(R.id.tv_upfile)

            dialog!!.window.setContentView(view)

            initTLable(context, view.findViewById(R.id.fl_jubao), view.findViewById(R.id.fl_main))
        } else {
            dialog?.show()
        }


        tv_cancel?.setOnClickListener {
            dialog?.dismiss()

        }
        tv_enter?.setOnClickListener {
            ToastUtil.showToast(Gson().toJson(list))
            dialog?.dismiss()
        }
        iv_cancel?.setOnClickListener {
            dialog?.dismiss()
        }

        val dialogWindow = dialog!!.window
        dialogWindow.setWindowAnimations(R.style.dialogAnim)//淡入、淡出动画
        dialogWindow.setGravity(Gravity.BOTTOM)//显示在底部
        val m = context.windowManager
        val d = m.defaultDisplay; // 获取屏幕宽、高用
        val p = dialogWindow.attributes; // 获取对话框当前的参数值
//        p.height = (d.height * 0.5).toInt() // 高度设置为屏幕的0.5
        p.width = d.width   // 宽度设置为屏幕的0.65
        dialogWindow.attributes = p
    }


    fun initTLable(context: Activity, flType: FlowLayout, fl: FrameLayout) {

        val array = context.resources.getStringArray(R.array.report)

        for (i in 0 until array.size) {
            val tv = LayoutInflater.from(context).inflate(
                R.layout.layout_flow_talent_type, fl, false
            ) as TextView
            tv.text = array[i]

            tv.setBackgroundResource(R.drawable.bg_gray_60)
            tv.setTextColor(context.resources.getColor(R.color.colorTabText))

            tv.setOnClickListener {
                if (list.contains(array[i])) {
                    list.remove(array[i])
                    tv.setBackgroundResource(R.drawable.bg_gray_60)
                    tv.setTextColor(context.resources.getColor(R.color.colorTabText))
                } else {
                    list.add(array[i])
                    tv.setBackgroundResource(R.drawable.them_bg35)
                    tv.setTextColor(context.resources.getColor(R.color.white))
                }
            }

            flType.addView(tv)
        }
    }


    fun diss() {
        dialog?.let {
            if (it.isShowing) {
                it.dismiss()
            }
            dialog = null
        }
    }


}
 