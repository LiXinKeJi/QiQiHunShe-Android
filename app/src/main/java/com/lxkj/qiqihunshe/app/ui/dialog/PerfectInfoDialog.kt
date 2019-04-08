package com.lxkj.qiqihunshe.app.ui.dialog

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Activity
import android.text.TextUtils
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.*
import com.lxkj.qiqihunshe.R
import com.lxkj.qiqihunshe.app.MyApplication
import com.lxkj.qiqihunshe.app.ui.mine.activity.PersonalInfoActivity
import com.lxkj.qiqihunshe.app.ui.mine.activity.RealNameAuthenActivity
import com.lxkj.qiqihunshe.app.util.AbStrUtil
import com.lxkj.qiqihunshe.app.util.ToastUtil
import com.tencent.tauth.AuthActivity


/**
 *  欢迎页去实名认证
 * Created by Slingge on 2019/2/22
 */
@SuppressLint("StaticFieldLeak")
object PerfectInfoDialog {


    private var dialog: AlertDialog? = null
    private var tv_go: TextView? = null

    fun show(context: Activity) {
        if (dialog == null) {
            dialog = AlertDialog.Builder(context, R.style.Dialog).create()
            dialog?.show()
            val view = LayoutInflater.from(context).inflate(R.layout.dialog_perfect_info, null)
            tv_go = view.findViewById(R.id.tv_go)

            dialog!!.window.setContentView(view)

        } else {
            dialog?.show()
        }


        tv_go?.setOnClickListener {
            MyApplication.openActivity(context, RealNameAuthenActivity::class.java)
            diss()
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
 