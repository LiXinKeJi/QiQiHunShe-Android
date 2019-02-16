package com.lxkj.qiqihunshe.app.util

import android.app.ProgressDialog
import android.content.Context
import com.lxkj.qiqihunshe.R


/**
 * 类说明: 自定义ProgressDialog
 */
object ProgressDialogUtil {

    var proDialog: ProgressDialog? = null


    fun dismissDialog() {
        if (proDialog != null) {
            proDialog!!.dismiss()
            proDialog = null
        }
    }


    fun showProgressDialog(context: Context, msg: String?) {
        if (proDialog == null) {
            proDialog = ProgressDialog(context, R.style.Dialog)
            if (msg == null) {
                proDialog = ProgressDialog.show(context, null, "加载中...")
            } else {
                proDialog = ProgressDialog.show(context, null, msg)
            }
            proDialog!!.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL)
            proDialog!!.setCancelable(true)//点击返回消失
            proDialog!!.setCanceledOnTouchOutside(false)//点击屏幕不消失
            proDialog!!.show()
        } else {
            if (proDialog!!.isShowing) {
                proDialog!!.dismiss()
            }
            proDialog!!.show()
        }
    }

    fun showProgressDialog(context: Context) {
        if (proDialog == null) {
            proDialog = ProgressDialog(context, R.style.Dialog)
            proDialog = ProgressDialog.show(context, null, "加载中...")
            proDialog!!.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL)
            proDialog!!.setCancelable(true)//点击返回消失
            proDialog!!.setCanceledOnTouchOutside(false)//点击屏幕不消失
            proDialog!!.show()
        } else {
            if (proDialog!!.isShowing) {
                proDialog!!.dismiss()
            }
            proDialog!!.show()
        }
    }


}
