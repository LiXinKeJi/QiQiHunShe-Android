package com.lxkj.qiqihunshe.app.ui.dialog

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.RadioGroup
import android.widget.TextView
import com.lxkj.qiqihunshe.R
import com.lxkj.qiqihunshe.app.customview.TwoWayRattingBar
import com.lxkj.qiqihunshe.app.ui.fujin.model.ValuesModel
import com.lxkj.qiqihunshe.app.util.ToastUtil
import org.greenrobot.eventbus.EventBus

/**
 * Created by Slingge on 2019/2/28
 */
@SuppressLint("StaticFieldLeak")
object ScreenPersonDialog {


    private var dialog: AlertDialog? = null
    private var iv_cancel: ImageView? = null

    private var tv_play: TextView? = null
    private var tv_range: TextView? = null

    private var radio: RadioGroup? = null

    private var sb_pressure: TwoWayRattingBar? = null

    private var min = "18"
    private var max = "100"
    private var sex = ""

    fun show(context: Activity) {
        if (dialog == null) {
            dialog = AlertDialog.Builder(context, R.style.Dialog).create()
            dialog?.show()
            val view = LayoutInflater.from(context).inflate(R.layout.dialog_screen_person, null)
            iv_cancel = view.findViewById(R.id.iv_cancel)
            tv_play = view.findViewById(R.id.tv_play)
            tv_range = view.findViewById(R.id.tv_range)
            sb_pressure = view.findViewById(R.id.sb_pressure)
            dialog!!.window.setContentView(view)

        } else {
            dialog?.show()
        }

        iv_cancel?.setOnClickListener {
            dialog?.dismiss()
        }
        tv_play?.setOnClickListener {
            EventBus.getDefault().post(ValuesModel(min, max, sex))
            dialog?.dismiss()
        }

        radio?.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.tv_all -> {
                    sex = "全部"
                }
                R.id.tv_boy -> {
                    sex = "男"
                }
                R.id.tv_girl -> {
                    sex = "女"
                }
            }
        }


        sb_pressure?.setOnProgressChangeListener(object : TwoWayRattingBar.OnProgressChangeListener {
            override fun onRightProgressChange(progress: Float) {
                max = (progress * 100).toInt().toString()
                if (max.toInt() < min.toInt()) {
                    max = min
                }
                tv_range?.text = "${min}-${max}"
            }

            override fun onLeftProgressChange(progress: Float) {
                min = (progress * 100 + 18).toInt().toString()
                if (min.toInt() > max.toInt()) {
                    min = max
                }
                tv_range?.text = "${min}-${max}"
            }
        })

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


    fun diss() {
        dialog?.let {
            if (it.isShowing) {
                it.dismiss()
            }
            dialog = null
        }
    }


}