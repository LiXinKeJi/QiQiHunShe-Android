package com.lxkj.qiqihunshe.app.ui.dialog

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.TextView
import com.lxkj.qiqihunshe.R
import com.lxkj.qiqihunshe.app.util.ToastUtil
import org.greenrobot.eventbus.EventBus

/**
 * Created by Slingge on 2019/2/28
 */
@SuppressLint("StaticFieldLeak")
object VoiceTipDialog {


    private var dialog: AlertDialog? = null
    private var iv_cancel: ImageView? = null

    private var tv_tip: TextView? = null
    private var tv_play: TextView? = null


    fun show(context: Activity, name: String,type: String) {
        if (dialog == null) {
            dialog = AlertDialog.Builder(context, R.style.Dialog).create()
            dialog?.show()
            val view = LayoutInflater.from(context).inflate(R.layout.dialog_voice, null)
            iv_cancel = view.findViewById(R.id.iv_cancel)
            tv_tip = view.findViewById(R.id.tv_tip)
            tv_play = view.findViewById(R.id.tv_play)
            dialog!!.window.setContentView(view)

        } else {
            dialog?.show()
        }

        iv_cancel?.setOnClickListener {
            dialog?.dismiss()
        }
        tv_play?.setOnClickListener {
            EventBus.getDefault().post("next")
            diss()
        }

        tv_tip?.text = "与${name}发起${type}通话需要XX元一分钟"

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