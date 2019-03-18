package com.lxkj.qiqihunshe.app.ui.dialog

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.text.TextUtils
import android.view.Gravity
import android.view.LayoutInflater
import android.view.WindowManager
import android.widget.EditText
import android.widget.RadioButton
import android.widget.TextView
import com.lxkj.qiqihunshe.R
import com.lxkj.qiqihunshe.app.rongrun.model.QiQiAssistModel
import com.lxkj.qiqihunshe.app.util.AbStrUtil
import com.lxkj.qiqihunshe.app.util.ToastUtil

/**
 * Created by Slingge on 2019/3/16
 */
@SuppressLint("StaticFieldLeak")
object QiQiAssistDialog {

    interface QiQiAssistCallBack{
        fun Assist(model:QiQiAssistModel)
    }

    private var tv_aumnt: TextView? = null
    private var tv_enter: TextView? = null

    private var et_phone: EditText? = null
    private var et_address: EditText? = null
    private var et_note: EditText? = null

    private var cb_qiqi: RadioButton? = null
    private var cb_service: RadioButton? = null


    private var dialog: AlertDialog? = null
    fun show(context: Activity,qiQiAssistCallBack: QiQiAssistCallBack) {
        if (dialog == null) {
            dialog = AlertDialog.Builder(context, R.style.Dialog).create()
            dialog?.show()
            val view = LayoutInflater.from(context).inflate(R.layout.dialog_qiqi_assist, null)

            tv_aumnt = view.findViewById(R.id.tv_aumnt)
            et_phone = view.findViewById(R.id.et_phone)
            et_address = view.findViewById(R.id.et_address)

            et_note = view.findViewById(R.id.et_note)
            cb_qiqi = view.findViewById(R.id.cb_qiqi)
            cb_service = view.findViewById(R.id.cb_service)

            tv_enter = view.findViewById(R.id.tv_enter)


            dialog!!.window.setContentView(view)

        } else {
            dialog?.show()
        }


        tv_enter!!.setOnClickListener {
            if (cb_qiqi!!.isChecked) {
                val mode = QiQiAssistModel()
                val phone=AbStrUtil.etTostr(et_phone!!)
                if(TextUtils.isEmpty(phone)){
                 ToastUtil.showTopSnackBar(context,"请输入联系人手机号")
                    return@setOnClickListener
                }
                mode.phone=phone

                val address=AbStrUtil.etTostr(et_address!!)
                if(TextUtils.isEmpty(address)){
                    ToastUtil.showTopSnackBar(context,"请输入约见地址")
                    return@setOnClickListener
                }
                mode.address=address

                val note=AbStrUtil.etTostr(et_note!!)
                if(TextUtils.isEmpty(note)){
                    ToastUtil.showTopSnackBar(context,"请输入约见地址")
                    return@setOnClickListener
                }
                mode.remark=note
                qiQiAssistCallBack.Assist(mode)
            } else if (cb_qiqi!!.isChecked) {
                ToastUtil.showTopSnackBar(context,"跳转客服")
            }

        }

        dialog!!.window.clearFlags(
            WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM)

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