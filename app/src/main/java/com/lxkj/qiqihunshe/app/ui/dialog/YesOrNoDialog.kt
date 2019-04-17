package com.lxkj.qiqihunshe.app.ui.dialog

import android.app.AlertDialog
import android.content.Context
import android.view.*
import android.widget.TextView
import com.lxkj.qiqihunshe.R

/**
 * Created by Slingge on 2017/7/14 0014.
 */

object YesOrNoDialog {


    interface YesOrNoCallback {
        fun YesOrNo(b: Boolean)
    }


    fun showDialog(context: Context, title: String, cancle: String, enter: String, yesOrNoCallback: YesOrNoCallback) {
        val builder = AlertDialog.Builder(context, R.style.Dialog).create() //
        builder.show()
        val factory = LayoutInflater.from(context)
        val view = factory.inflate(R.layout.dialog_yes_no, null)
        builder.window!!.setContentView(view)
        builder.setCancelable(true)//点击返回消失
        builder.setCanceledOnTouchOutside(false)//点击屏幕不消失

        val tv_titles = view.findViewById<TextView>(R.id.tv_title)
        tv_titles.text = title
        val tv_yes = view.findViewById<TextView>(R.id.tv_yes)
        tv_yes.text = enter
        val tv_no = view.findViewById<TextView>(R.id.tv_no)
        tv_no.text = cancle

        val dialogWindow = builder.window
        val lp = dialogWindow!!.getAttributes()
        val d = context.resources.displayMetrics // 获取屏幕宽、高用
        lp.width = (d.widthPixels * 0.8).toInt() // 宽带度设置为屏幕的0.8

        dialogWindow!!.setGravity(Gravity.CENTER)// 显示在居中

        tv_no.setOnClickListener {
            builder.dismiss()
            yesOrNoCallback.YesOrNo(false)
        }
        tv_yes.setOnClickListener {
            yesOrNoCallback.YesOrNo(true)
            builder.dismiss()
        }
    }

}
