package com.lxkj.qiqihunshe.app.ui.dialog

import android.app.Activity
import android.app.AlertDialog
import android.view.Gravity
import android.view.LayoutInflater
import com.lxkj.qiqihunshe.R
import kotlinx.android.synthetic.main.dialog_select_time_meet.view.*

/**
 * Created by kxn on 2019/3/1 0001.
 */
object SelectMeetTimeDialog{
    private var dialog: AlertDialog? = null
    fun show(context: Activity) {
        if (SelectMeetTimeDialog.dialog == null) {
            SelectMeetTimeDialog.dialog = AlertDialog.Builder(context, R.style.Dialog).create()
            SelectMeetTimeDialog.dialog?.show()
            val view = LayoutInflater.from(context).inflate(R.layout.dialog_select_time_meet, null)




            view.ivClose.setOnClickListener {
                dialog?.dismiss()
            }
            view.tvConfirm.setOnClickListener {
                dialog?.dismiss()
            }

            SelectMeetTimeDialog.dialog!!.window.setContentView(view)

        } else {
            SelectMeetTimeDialog.dialog?.show()
        }

        val dialogWindow = SelectMeetTimeDialog.dialog!!.window
        dialogWindow.setWindowAnimations(R.style.dialogAnim)//淡入、淡出动画
        dialogWindow.setGravity(Gravity.CENTER)//显示在屏幕中间
        val m = context.windowManager
        val d = m.defaultDisplay; // 获取屏幕宽、高用
        val p = dialogWindow.attributes; // 获取对话框当前的参数值
//        p.height = (d.height * 0.5).toInt() // 高度设置为屏幕的0.5
        p.width = (d.width * 0.8).toInt() // 宽度设置为屏幕的0.65
        dialogWindow.attributes = p
    }
}