package com.lxkj.qiqihunshe.app.ui.dialog

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.TextView
import com.lxkj.qiqihunshe.R
import org.greenrobot.eventbus.EventBus

/**
 * Created by Slingge on 2019/2/28
 */
@SuppressLint("StaticFieldLeak")
object SignupExamineDialog {


    private var dialog: AlertDialog? = null

    private lateinit var tv_tip: TextView

    private lateinit var tv_no: TextView
    private lateinit var tv_agree: TextView


    interface SignupExamineCallBack {
        fun result(boolean: Boolean)
    }


    fun show(context: Activity, name: String, signupExamineCallBack: SignupExamineCallBack) {
        if (dialog == null) {
            dialog = AlertDialog.Builder(context, R.style.Dialog).create()
            dialog?.show()
            val view = LayoutInflater.from(context).inflate(R.layout.dialog_signup_examine, null)
            tv_no = view.findViewById(R.id.tv_no)
            tv_tip = view.findViewById(R.id.tv_tip)
            tv_agree = view.findViewById(R.id.tv_agree)

            dialog!!.window.setContentView(view)

        } else {
            dialog?.show()
        }


        tv_tip.text = "是否同意${name}参加活动？"

        tv_no.setOnClickListener {
            signupExamineCallBack.result(false)
            dialog?.dismiss()
        }
        tv_agree.setOnClickListener {
            signupExamineCallBack.result(true)
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