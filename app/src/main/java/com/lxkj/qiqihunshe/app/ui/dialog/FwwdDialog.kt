package com.lxkj.qiqihunshe.app.ui.dialog

import android.app.Activity
import android.app.AlertDialog
import android.view.Gravity
import android.view.LayoutInflater
import com.lxkj.qiqihunshe.R
import com.lxkj.qiqihunshe.app.ui.quyu.model.DataListModel
import kotlinx.android.synthetic.main.dialog_fwwd_map.view.*

/**
 * Created by kxn on 2019/3/1 0001.
 * 区域 服务网点 弹框
 */
object FwwdDialog {
    private var dialog: AlertDialog? = null
    var onTellListener: OnTellListener? = null


    fun setListener(onTellListener: OnTellListener) {
        this.onTellListener = onTellListener
    }

    fun show(context: Activity, data: DataListModel) {
        if (FwwdDialog.dialog == null) {
            FwwdDialog.dialog = AlertDialog.Builder(context, R.style.Dialog).create()
            FwwdDialog.dialog?.show()
            val view = LayoutInflater.from(context).inflate(R.layout.dialog_fwwd_map, null)

            view.addressTv.text = (data?.address)
            view.phoneTv.text = (data?.phone)
            view.fwfwTv.text = (data?.extent)

            view.phoneIv.setOnClickListener {
                onTellListener!!.onTell(data.phone)
            }


            view.ivClose.setOnClickListener {
                dialog?.dismiss()
            }
            view.confirmTv.setOnClickListener {
                dialog?.dismiss()
            }

            FwwdDialog.dialog!!.window.setContentView(view)

        } else {
            FwwdDialog.dialog?.show()
        }

        val dialogWindow = FwwdDialog.dialog!!.window
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