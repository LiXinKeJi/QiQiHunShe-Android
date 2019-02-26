package com.lxkj.qiqihunshe.app.ui.dialog

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Activity
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.TextView
import com.lxkj.qiqihunshe.R
import com.lxkj.qiqihunshe.app.MyApplication
import com.lxkj.qiqihunshe.app.ui.shouye.activity.SetupProblemActivity
import com.lxkj.qiqihunshe.app.util.ToastUtil


/**
 * 七七活动报名
 * Created by Slingge on 2019/2/22
 */
@SuppressLint("StaticFieldLeak")
object QuestionsAnswersDialog {


    private var dialog: AlertDialog? = null
    private var tv_setup: TextView? = null
    private var iv_cancel: ImageView? = null

    fun sginUpShow(context: Activity) {
        if (dialog == null) {
            dialog = AlertDialog.Builder(context, R.style.Dialog).create()
            dialog?.show()
            val view = LayoutInflater.from(context).inflate(R.layout.dialog_questions_answers, null)
            tv_setup = view.findViewById(R.id.tv_setup)
            iv_cancel = view.findViewById(R.id.iv_cancel)
            dialog!!.window.setContentView(view)
        } else {
            dialog?.show()
        }

        tv_setup?.setOnClickListener {
            dialog?.dismiss()
            MyApplication.openActivity(context, SetupProblemActivity::class.java)
        }
        iv_cancel?.setOnClickListener {
            dialog?.dismiss()
        }

        val dialogWindow = dialog!!.window
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
 