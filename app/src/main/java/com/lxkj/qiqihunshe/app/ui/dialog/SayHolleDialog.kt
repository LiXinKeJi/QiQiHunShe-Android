package com.lxkj.qiqihunshe.app.ui.dialog

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import com.lxkj.qiqihunshe.R
import kotlinx.android.synthetic.main.dialog_say_holle_map.view.*
import java.util.ArrayList

/**
 * Created by kxn on 2019/3/1 0001.
 */
@SuppressLint("StaticFieldLeak")
object SayHolleDialog {
    private var dialog: AlertDialog? = null
    private var messagSpinner: Spinner? = null
    fun show(context: Activity) {
        if (SayHolleDialog.dialog == null) {
            SayHolleDialog.dialog = AlertDialog.Builder(context, R.style.Dialog).create()
            SayHolleDialog.dialog?.show()
            val view = LayoutInflater.from(context).inflate(R.layout.dialog_say_holle_map, null)
            messagSpinner = view.findViewById(R.id.messageSpinner)

            val messages = ArrayList<String>()
            messages.add("您好！")
            messages.add("吃了没！")
            messages.add("拜拜！")


            val ageAdapter = ArrayAdapter(context, R.layout.spinner_tv, messages)
            ageAdapter.setDropDownViewResource(R.layout.spinner_item)
            messagSpinner?.setAdapter(ageAdapter)
            messagSpinner?.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(adapterView: AdapterView<*>, view: View, i: Int, l: Long) {

                }

                override fun onNothingSelected(adapterView: AdapterView<*>) {}
            })

            view.ivClose.setOnClickListener {
                dialog?.dismiss()
            }
            view.cancelTv.setOnClickListener {
                dialog?.dismiss()
            }
            view.confirmTv.setOnClickListener {
                dialog?.dismiss()
            }

            SayHolleDialog.dialog!!.window.setContentView(view)

        } else {
            SayHolleDialog.dialog?.show()
        }

        val dialogWindow = SayHolleDialog.dialog!!.window
        dialogWindow.setWindowAnimations(R.style.dialogAnim)//淡入、淡出动画
        dialogWindow.setGravity(Gravity.BOTTOM)//显示在底部
        val m = context.windowManager
        val d = m.defaultDisplay; // 获取屏幕宽、高用
        val p = dialogWindow.attributes; // 获取对话框当前的参数值
//        p.height = (d.height * 0.5).toInt() // 高度设置为屏幕的0.5
        p.width = d.width // 宽度设置为屏幕的0.65
        dialogWindow.attributes = p
    }
}