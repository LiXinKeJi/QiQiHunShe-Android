package com.lxkj.qiqihunshe.app.ui.dialog

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.support.v4.app.ActivityCompat
import android.view.Gravity
import android.view.LayoutInflater
import com.lxkj.qiqihunshe.R
import kotlinx.android.synthetic.main.layout_tell.view.*

/**
 * Created by kxn on 2019/3/5 0005.
 */
@SuppressLint("StaticFieldLeak")
object TellDialog {
    private var dialog: AlertDialog? = null
    fun show(context: Activity, phoneNum: String) {
        if (TellDialog.dialog == null) {
            TellDialog.dialog = AlertDialog.Builder(context, R.style.Dialog).create()
            TellDialog.dialog?.show()
            val view = LayoutInflater.from(context).inflate(R.layout.layout_tell, null)

            view.phoneNumTv.text = phoneNum

            view.iv_cancel.setOnClickListener {
                dialog?.dismiss()
            }
            view.tv_cancel.setOnClickListener {
                dialog?.dismiss()
            }
            view.tv_enter.setOnClickListener {
                val intent = Intent(Intent.ACTION_CALL, Uri.parse("tel:$phoneNum"))
                if (ActivityCompat.checkSelfPermission(
                        context,
                        Manifest.permission.CALL_PHONE
                    ) == PackageManager.PERMISSION_GRANTED
                ) {
                    context.startActivity(intent)
                }

            }

            TellDialog.dialog!!.window.setContentView(view)

        } else {
            TellDialog.dialog?.show()
        }

        val dialogWindow = TellDialog.dialog!!.window
        dialogWindow.setWindowAnimations(R.style.dialogAnim)//淡入、淡出动画
        dialogWindow.setGravity(Gravity.CENTER)//显示在底部
        val m = context.windowManager
        val d = m.defaultDisplay; // 获取屏幕宽、高用
        val p = dialogWindow.attributes; // 获取对话框当前的参数值
//        p.height = (d.height * 0.5).toInt() // 高度设置为屏幕的0.5
        p.width = d.width // 宽度设置为屏幕的0.65
        dialogWindow.attributes = p
    }
}