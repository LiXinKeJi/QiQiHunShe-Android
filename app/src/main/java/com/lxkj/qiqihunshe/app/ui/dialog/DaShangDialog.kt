package com.lxkj.qiqihunshe.app.ui.dialog

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Activity
import android.text.InputFilter
import android.text.TextUtils
import android.view.Gravity
import android.view.LayoutInflater
import android.view.WindowManager
import android.widget.*
import com.google.gson.Gson
import com.lxkj.qiqihunshe.R
import com.lxkj.qiqihunshe.app.customview.FlowLayout
import com.lxkj.qiqihunshe.app.util.AbStrUtil
import com.lxkj.qiqihunshe.app.util.CashierInputFilter
import com.lxkj.qiqihunshe.app.util.ToastUtil


/**
 * 七七活动报名举报
 * Created by Slingge on 2019/2/22
 */
@SuppressLint("StaticFieldLeak")
object DaShangDialog {


    interface DaShangCallBack {
        fun dashang(money: String)
    }

    private var dialog: AlertDialog? = null
    private var tv_play: TextView? = null
    private var iv_cancel: ImageView? = null

    private var et_money: EditText? = null
    private var radio: RadioGroup? = null

    private var money = "1"

    fun show(context: Activity, daShangCallBack: DaShangCallBack) {
        if (dialog == null) {
            dialog = AlertDialog.Builder(context, R.style.Dialog).create()
            dialog?.show()
            val view = LayoutInflater.from(context).inflate(R.layout.dialog_reward, null)
            tv_play = view.findViewById(R.id.tv_play)
            iv_cancel = view.findViewById(R.id.iv_cancel)

            et_money = view.findViewById(R.id.et_money)
            radio = view.findViewById(R.id.radio)

            dialog!!.window.setContentView(view)

        } else {
            dialog?.show()
        }

        radio?.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.tv_1 -> {
                    money = "1"
                }
                R.id.tv_2 -> {
                    money = "2"
                }
                R.id.tv_5 -> {
                    money = "5"
                }
                R.id.tv_10 -> {
                    money = "10"
                }
            }
        }

        et_money?.filters = arrayOf<InputFilter>(CashierInputFilter())

        tv_play?.setOnClickListener {
            if (!TextUtils.isEmpty(AbStrUtil.etTostr(et_money!!))) {
                money = AbStrUtil.etTostr(et_money!!)
                if (TextUtils.isEmpty(money)) {
                    ToastUtil.showToast("请选择或输入打赏金额")
                    return@setOnClickListener
                }

                if (money.toDouble() == 0.0) {
                    ToastUtil.showToast("打赏金额错误")
                    return@setOnClickListener
                }
                daShangCallBack.dashang(money)
            } else {
                daShangCallBack.dashang(money)
            }
            dialog?.dismiss()
        }
        iv_cancel?.setOnClickListener {
            dialog?.dismiss()
        }


        dialog!!.window.clearFlags(
            WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM
        )


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
 