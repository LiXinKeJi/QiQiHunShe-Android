package com.lxkj.qiqihunshe.app.ui.dialog

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.text.InputFilter
import android.text.TextUtils
import android.view.Gravity
import android.view.LayoutInflater
import android.view.WindowManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.RadioGroup
import android.widget.TextView
import com.lxkj.qiqihunshe.R
import com.lxkj.qiqihunshe.app.ui.fujin.model.DivideModel
import com.lxkj.qiqihunshe.app.util.AbStrUtil
import com.lxkj.qiqihunshe.app.util.CashierInputFilter
import com.lxkj.qiqihunshe.app.util.ToastUtil
import org.greenrobot.eventbus.EventBus

/**
 * Created by Slingge on 2019/3/16
 */
@SuppressLint("StaticFieldLeak")
object DivideDialog {

    private var tv_cancel: TextView? = null
    private var tv_enter: TextView? = null

    private var et_me: EditText? = null
    private var et_you: EditText? = null

    private var iv_cancel: ImageView? = null

    private var radioGroup: RadioGroup? = null


    private var dialog: AlertDialog? = null
    fun show(context: Activity,yuejianId:String) {
        if (dialog == null) {
            dialog = AlertDialog.Builder(context, R.style.Dialog).create()
            dialog?.show()
            val view = LayoutInflater.from(context).inflate(R.layout.dialog_divide, null)

            tv_cancel = view.findViewById(R.id.tv_cancel)
            tv_enter = view.findViewById(R.id.tv_enter)

            et_me = view.findViewById(R.id.et_me)
            et_you = view.findViewById(R.id.et_you)
            et_me?.filters = arrayOf<InputFilter>(CashierInputFilter())
            et_you?.filters = arrayOf<InputFilter>(CashierInputFilter())

            radioGroup = view.findViewById(R.id.radioGroup)

            iv_cancel=view.findViewById(R.id.iv_cancel)

            dialog!!.window.setContentView(view)

        } else {
            dialog?.show()
        }

        var flag = -1//0我买单，1aa

        radioGroup!!.setOnCheckedChangeListener { group, checkedId ->
            if (checkedId == R.id.rb_me) {
                flag = 0
            } else {
                flag = 1
            }
        }



        tv_enter!!.setOnClickListener {
            if (flag == -1) {
                ToastUtil.showToast("请选择消费划分")
                return@setOnClickListener
            }

            if (flag == 0) {
                val model = DivideModel()
                model.type = "1"
                model.yuejianId=yuejianId
                EventBus.getDefault().post(model)
            } else {
                val money = AbStrUtil.etTostr(et_me!!)
                if (TextUtils.isEmpty(money)) {
                    ToastUtil.showToast("请输入消费总金额")
                    return@setOnClickListener
                }
                val youMoney = AbStrUtil.etTostr(et_you!!)
                if (TextUtils.isEmpty(youMoney)) {
                    ToastUtil.showToast("请输入对方支付金额")
                    return@setOnClickListener
                }
                val model = DivideModel()
                model.type = "2"
                model.amount=money
                model.money=youMoney
                model.yuejianId=yuejianId
                EventBus.getDefault().post(model)
            }
            dialog!!.dismiss()
        }
        tv_cancel!!.setOnClickListener {
            dialog!!.dismiss()
        }

        iv_cancel!!.setOnClickListener {
            dialog!!.dismiss()
        }


        dialog!!.window.clearFlags(
            WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM
        )

        val dialogWindow = dialog!!.window
        dialogWindow.setWindowAnimations(R.style.dialogAnim)//淡入、淡出动画
        dialogWindow.setGravity(Gravity.BOTTOM)//显示在底部
        val m = context.windowManager
        val d = m.defaultDisplay; // 获取屏幕宽、高用
        val p = dialogWindow.attributes; // 获取对话框当前的参数值
//        p.height = (d.height * 0.5).toInt() // 高度设置为屏幕的0.5
        p.width = d.width // 宽度设置为屏幕的0.65
        dialogWindow.attributes = p
    }


    fun dismiss() {
        dialog?.let {
            if (it.isShowing) {
                it.dismiss()
            }
            dialog = null
        }
    }


}