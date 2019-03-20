package com.lxkj.qiqihunshe.app.ui.dialog

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Activity
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import com.google.gson.Gson
import com.lxkj.qiqihunshe.R
import com.lxkj.qiqihunshe.app.customview.FlowLayout
import com.lxkj.qiqihunshe.app.retrofitnet.*
import com.lxkj.qiqihunshe.app.retrofitnet.exception.dispatchFailure
import com.lxkj.qiqihunshe.app.ui.shouye.model.ReportModel
import com.lxkj.qiqihunshe.app.util.ToastUtil
import com.lxkj.qiqihunshe.app.util.abLog


/**
 * 七七活动 邀约等举报
 * 1聊天 2动态 3邀约 4才艺
 * Created by Slingge on 2019/2/22
 */
@SuppressLint("StaticFieldLeak")
object ReportDialog1 {


    interface ReportCallBack {
        fun report(report: String)
    }


    private var dialog: AlertDialog? = null
    private var tv_enter: TextView? = null
    private var tv_cancel: TextView? = null
    private var iv_cancel: ImageView? = null

    private var flLayout: FlowLayout? = null
    private var fl_main: FrameLayout? = null

    private val list by lazy { ArrayList<String>() }
    private val array by lazy { ArrayList<String>() }


    val retrofit by lazy { RetrofitUtil.getRetrofit().create(RetrofitService::class.java) }


    private var type = ""//上一个类型

    @SuppressLint("CheckResult")
    fun getReportList(context: Activity, type: String, reportCallBack: ReportCallBack) {
        if (this.type != type) {
            array.clear()
            this.type = type
        }
        if (array.isEmpty()) {
            val json = "{\"cmd\":\"getReportList\",\"type\":\"$type\"}"
            retrofit.getData(json).async().compose(SingleCompose.compose(object : SingleObserverInterface {
                override fun onSuccess(response: String) {
                    val mode = Gson().fromJson(response, ReportModel::class.java)
                    array.addAll(mode.dataList)
                    show(context, reportCallBack)
                }
            }, context)).subscribe({}, { dispatchFailure(context, it) })
        } else {
            show(context, reportCallBack)
        }

    }

    //type1聊天 2动态 3邀约 4才艺
    fun show(context: Activity, reportCallBack: ReportCallBack) {
        list.clear()
        if (dialog == null) {
            dialog = AlertDialog.Builder(context, R.style.Dialog).create()
            dialog?.show()
            val view = LayoutInflater.from(context).inflate(R.layout.dialog_report1, null)
            tv_enter = view.findViewById(R.id.tv_enter)
            tv_cancel = view.findViewById(R.id.tv_cancel)
            iv_cancel = view.findViewById(R.id.iv_cancel)

            flLayout = view.findViewById(R.id.fl_jubao)
            fl_main = view.findViewById(R.id.fl_main)

            dialog!!.window.setContentView(view)

        } else {
            dialog?.show()
        }

        initTLable(context, flLayout!!, fl_main!!)

        tv_cancel?.setOnClickListener {
            dialog?.dismiss()

        }
        tv_enter?.setOnClickListener {
            if (list.isEmpty()) {
                ToastUtil.showToast("请选择举报内容")
                return@setOnClickListener
            }
            val sb = StringBuffer()
            for (i in 0 until list.size) {
                sb.append(list[i] + ",")
            }
            reportCallBack.report(sb.toString().substring(0, sb.toString().length - 1))
            dialog?.dismiss()
        }
        iv_cancel?.setOnClickListener {
            dialog?.dismiss()
        }

        val dialogWindow = dialog!!.window
        dialogWindow.setWindowAnimations(R.style.dialogAnim)//淡入、淡出动画
        dialogWindow.setGravity(Gravity.BOTTOM)//显示在底部
        val m = context.windowManager
        val d = m.defaultDisplay; // 获取屏幕宽、高用
        val p = dialogWindow.attributes; // 获取对话框当前的参数值
//        p.height = (d.height * 0.5).toInt() // 高度设置为屏幕的0.5
        p.width = d.width   // 宽度设置为屏幕的0.65
        dialogWindow.attributes = p
    }


    fun initTLable(context: Activity, flType: FlowLayout, fl: FrameLayout) {
        flType.removeAllViews()
        for (i in 0 until array.size) {
            val tv = LayoutInflater.from(context).inflate(
                R.layout.layout_flow_talent_type, fl, false
            ) as TextView
            tv.text = array[i]

            tv.setBackgroundResource(R.drawable.bg_gray_60)
            tv.setTextColor(context.resources.getColor(R.color.colorTabText))

            tv.setOnClickListener {
                if (list.contains(array[i])) {
                    list.remove(array[i])
                    tv.setBackgroundResource(R.drawable.bg_gray_60)
                    tv.setTextColor(context.resources.getColor(R.color.colorTabText))
                } else {
                    list.add(array[i])
                    tv.setBackgroundResource(R.drawable.them_bg35)
                    tv.setTextColor(context.resources.getColor(R.color.white))
                }
            }

            flType.addView(tv)
        }
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
 