package com.lxkj.qiqihunshe.app.ui.dialog

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.TextView
import com.lxkj.qiqihunshe.R

/**
 * 申请退还信誉金
 * Created by Slingge on 2019/2/28
 */
@SuppressLint("StaticFieldLeak")
object BailRefundAfterDialog {

    private var dialog: AlertDialog? = null
    private var tv_enter: TextView? = null

    private var tv_content: TextView? = null


    fun show(context: Activity) {
        if (dialog == null) {
            dialog = AlertDialog.Builder(context, R.style.Dialog).create()
            dialog?.show()
            val view = LayoutInflater.from(context).inflate(R.layout.dialog_bail_refund_after, null)
            tv_content = view.findViewById(R.id.tv_content)
            tv_enter = view.findViewById(R.id.tv_enter)
            dialog!!.window.setContentView(view)
        } else {
            dialog?.show()
        }

        tv_content?.setOnClickListener {
            dialog?.dismiss()
        }
        tv_enter?.setOnClickListener {
            dialog?.dismiss()
            context.finish()
        }


        val dialogWindow = dialog!!.window
        dialogWindow.setWindowAnimations(R.style.dialogAnim)//淡入、淡出动画
        dialogWindow.setGravity(Gravity.CENTER)//显示在底部
        val m = context.windowManager
        val d = m.defaultDisplay; // 获取屏幕宽、高用
        val p = dialogWindow.attributes; // 获取对话框当前的参数值
//        p.height = (d.height * 0.5).toInt() // 高度设置为屏幕的0.5
        p.width = (d.width * 0.85).toInt() // 宽度设置为屏幕的0.65
        dialogWindow.attributes = p
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