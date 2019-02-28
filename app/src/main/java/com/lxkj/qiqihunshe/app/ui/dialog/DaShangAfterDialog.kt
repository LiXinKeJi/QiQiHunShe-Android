package com.lxkj.qiqihunshe.app.ui.dialog

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Activity
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.*
import com.lxkj.qiqihunshe.R


/**
 * 七七活动报名举报
 * Created by Slingge on 2019/2/22
 */
@SuppressLint("StaticFieldLeak")
object DaShangAfterDialog {


    private var dialog: AlertDialog? = null
    private var iv_cancel: ImageView? = null


    private val list by lazy { ArrayList<String>() }

    fun show(context: Activity) {
        if (dialog == null) {
            dialog = AlertDialog.Builder(context, R.style.Dialog).create()
            dialog?.show()
            val view = LayoutInflater.from(context).inflate(R.layout.dialog_reward_after, null)
            iv_cancel = view.findViewById(R.id.iv_cancel)

            dialog!!.window.setContentView(view)

        } else {
            dialog?.show()
        }


        iv_cancel?.setOnClickListener {
            dialog?.dismiss()
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
 