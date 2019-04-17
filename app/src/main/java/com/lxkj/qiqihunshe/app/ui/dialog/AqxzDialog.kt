package com.lxkj.qiqihunshe.app.ui.dialog

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.view.Gravity
import android.view.LayoutInflater
import com.lxkj.qiqihunshe.R
import com.lxkj.qiqihunshe.app.ui.quyu.model.DataListModel
import kotlinx.android.synthetic.main.dialog_aqxz_map.view.*

/**
 * Created by kxn on 2019/3/1 0001.'
 * 区域 安全协助 弹框
 */
@SuppressLint("StaticFieldLeak")
object AqxzDialog {
    private var dialog: AlertDialog? = null

    var onTellListener: OnTellListener? = null


    fun setListener(onTellListener: OnTellListener) {
        this.onTellListener = onTellListener
    }

    fun show(context: Activity, data: DataListModel) {
        if (AqxzDialog.dialog == null) {
            AqxzDialog.dialog = AlertDialog.Builder(context, R.style.Dialog).create()
            AqxzDialog.dialog?.show()
            val view = LayoutInflater.from(context).inflate(R.layout.dialog_aqxz_map, null)

            view.nameTv.text = (data?.username)
            view.phoneTv.text = (data?.phone)
            view.fwfwTv.text = (data?.extent)

            view.ivClose.setOnClickListener {
                dialog?.dismiss()
            }
            view.confirmTv.setOnClickListener {
                dialog?.dismiss()
            }

            view.phoneIv.setOnClickListener {
                onTellListener!!.onTell(data.phone)
            }

            AqxzDialog.dialog!!.window.setContentView(view)

        } else {
            AqxzDialog.dialog?.show()
        }

        val dialogWindow = AqxzDialog.dialog!!.window
        dialogWindow.setWindowAnimations(R.style.dialogAnim)//淡入、淡出动画
        dialogWindow.setGravity(Gravity.BOTTOM)//显示在底部
        val m = context.windowManager
        val d = m.defaultDisplay; // 获取屏幕宽、高用
        val p = dialogWindow.attributes; // 获取对话框当前的参数值
//        p.height = (d.height * 0.5).toInt() // 高度设置为屏幕的0.5
        p.width = d.width // 宽度设置为屏幕的0.65
        dialogWindow.attributes = p
    }
    interface OnTellListener {
        fun onTell(phoneNum: String)
    }

}