package com.lxkj.qiqihunshe.app.ui.dialog

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.text.TextUtils
import android.view.Gravity
import android.view.LayoutInflater
import android.view.WindowManager
import android.widget.ImageView
import android.widget.TextView
import com.lxkj.qiqihunshe.R
import com.lxkj.qiqihunshe.app.customview.TwoWayRattingBar
import com.lxkj.qiqihunshe.app.rongrun.model.ImpressionScoreModel

/**
 * Created by Slingge on 2019/3/16
 */
@SuppressLint("StaticFieldLeak")
object ImpressionScoreDialog {


    private var tv_coincide: TextView? = null
    private var tv_first: TextView? = null

    private var tv_cancel: TextView? = null
    private var tv_evter: TextView? = null

    private var tr_coincide: TwoWayRattingBar? = null
    private var tr_first: TwoWayRattingBar? = null

    private var ic_coincide1: ImageView? = null
    private var ic_coincide2: ImageView? = null
    private var ic_coincide3: ImageView? = null

    private var match = ""//相符度
    private var impression = ""//印象值


    interface ImpressionScoreCallBack {
        fun Score(model: ImpressionScoreModel)
    }


    private var dialog: AlertDialog? = null
    fun show(context: Activity, qiQiAssistCallBack: ImpressionScoreCallBack) {
        if (dialog == null) {
            dialog = AlertDialog.Builder(context, R.style.Dialog).create()
            dialog?.show()
            val view = LayoutInflater.from(context).inflate(R.layout.dialog_impression_score, null)

            tv_coincide = view.findViewById(R.id.tv_coincide)
            tv_first = view.findViewById(R.id.tv_first)

            tv_cancel = view.findViewById(R.id.tv_cancel)
            tv_evter = view.findViewById(R.id.tv_evter)

            tr_coincide = view.findViewById(R.id.tr_coincide)
            tr_first = view.findViewById(R.id.tr_first)

            ic_coincide1 = view.findViewById(R.id.ic_coincide1)
            ic_coincide2 = view.findViewById(R.id.ic_coincide2)
            ic_coincide3 = view.findViewById(R.id.ic_coincide3)

            dialog!!.window.setContentView(view)

        } else {
            dialog?.show()
        }


        tv_cancel!!.setOnClickListener {
            val model = ImpressionScoreModel()
            model.match = match
            model.impression = impression
            model.type = "1"
            qiQiAssistCallBack.Score(model)
            dismiss()
        }
        tv_evter!!.setOnClickListener {
            val model = ImpressionScoreModel()
            model.match = match
            model.impression = impression
            model.type = "2"
            qiQiAssistCallBack.Score(model)
            dismiss()
        }

        tr_coincide?.setOnProgressChangeListener(object : TwoWayRattingBar.OnProgressChangeListener {
            override fun onRightProgressChange(progress: Float) {
                tv_coincide!!.text = "相符度 ${(progress * 10).toInt()}分"
                match = progress.toString()
            }

            override fun onLeftProgressChange(progress: Float) {
                tr_coincide!!.setLeftProgress(0f)
            }
        })

        tr_first?.setOnProgressChangeListener(object : TwoWayRattingBar.OnProgressChangeListener {
            override fun onRightProgressChange(progress: Float) {
                tv_first!!.text = "第一印象 ${(progress * 10).toInt()}分"
                impression = progress.toString()
                if ((progress * 10).toInt() >= 6) {
                    ic_coincide1!!.setImageResource(R.drawable.ic_coincide1_nor)
                    ic_coincide2!!.setImageResource(R.drawable.ic_coincide2_nor)
                    ic_coincide3!!.setImageResource(R.drawable.ic_coincide3_hl)
                } else if ((progress * 10).toInt() > 3) {
                    ic_coincide1!!.setImageResource(R.drawable.ic_coincide1_nor)
                    ic_coincide2!!.setImageResource(R.drawable.ic_coincide2_hl)
                    ic_coincide3!!.setImageResource(R.drawable.ic_coincide3_nor)
                } else {
                    ic_coincide1!!.setImageResource(R.drawable.ic_coincide1_hl)
                    ic_coincide2!!.setImageResource(R.drawable.ic_coincide2_nor)
                    ic_coincide3!!.setImageResource(R.drawable.ic_coincide3_nor)
                }
            }

            override fun onLeftProgressChange(progress: Float) {
                tr_first!!.setLeftProgress(0f)
            }
        })



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