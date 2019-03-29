package com.lxkj.qiqihunshe.app.ui.dialog

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.lxkj.qiqihunshe.R
import com.lxkj.qiqihunshe.app.customview.TwoWayRattingBar
import com.lxkj.qiqihunshe.app.ui.model.CityModel
import com.lxkj.qiqihunshe.app.ui.model.ShouYeScreenModel
import com.lxkj.qiqihunshe.app.util.AppJsonFileReader
import java.util.ArrayList
import android.widget.ArrayAdapter
import com.lxkj.qiqihunshe.app.util.ToastUtil


/**
 * Created by Slingge on 2019/3/1
 */
@SuppressLint("StaticFieldLeak")
object ScreenShouYeDialog : AdapterView.OnItemSelectedListener {

    private var dialog: AlertDialog? = null
    private var iv_cancel: ImageView? = null

    private var tv_play: TextView? = null
    private var tv_range: TextView? = null
    private var tv_distance: TextView? = null

    private var radio: RadioGroup? = null

    private var sb_pressure: TwoWayRattingBar? = null
    private var sb_distance: TwoWayRattingBar? = null

    private var sp_province: Spinner? = null
    private var sp_city: Spinner? = null
    private var addList: List<CityModel> = ArrayList()//全国城市
    private val list1 = ArrayList<String>()//省
    private val list2 = ArrayList<String>()//市
    private var spinnerAdapter1: ArrayAdapter<String>? = null
    private var spinnerAdapter2: ArrayAdapter<String>? = null
    private var position = 0
    private var position2 = 0

    private var min = "0"
    private var max = "100"

    private val model by lazy { ShouYeScreenModel() }

    fun show(context: Activity) {
        if (dialog == null) {
            dialog = AlertDialog.Builder(context, R.style.Dialog).create()
            dialog?.show()
            val view = LayoutInflater.from(context).inflate(R.layout.dialog_shouye_screen, null)
            iv_cancel = view.findViewById(R.id.iv_cancel)
            tv_play = view.findViewById(R.id.tv_play)
            tv_range = view.findViewById(R.id.tv_range)
            tv_distance = view.findViewById(R.id.tv_distance)
            sb_pressure = view.findViewById(R.id.sb_pressure)
            sb_distance = view.findViewById(R.id.sb_distance)

            sp_province = view.findViewById(R.id.sp_province)
            sp_city = view.findViewById(R.id.sp_city)

            dialog!!.window.setContentView(view)

            addList =
                Gson().fromJson(AppJsonFileReader.getJsons(context, 0), object : TypeToken<List<CityModel>>() {}.type)
            setloop1()
            spinnerAdapter1 = ArrayAdapter(context, R.layout.item_spinner_text, list1)
            sp_province?.adapter = spinnerAdapter1

            setloop2()
            spinnerAdapter2 = ArrayAdapter(context, R.layout.item_spinner_text, list2)
            sp_city?.adapter = spinnerAdapter2

            sp_province?.onItemSelectedListener = this
            sp_city?.onItemSelectedListener = this
        } else {
            dialog?.show()
        }

        iv_cancel?.setOnClickListener {
            dialog?.dismiss()
        }
        tv_play?.setOnClickListener {
            ToastUtil.showTopSnackBar(context, Gson().toJson(model))
            dialog?.dismiss()
        }

        radio?.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.tv_all -> {
                    model.emotion = "全部"
                }
                R.id.tv_wei -> {
                    model.emotion = "未婚"
                }
                R.id.tv_yihun -> {
                    model.emotion = "已婚"
                }
            }
        }

        sb_distance?.setOnProgressChangeListener(object : TwoWayRattingBar.OnProgressChangeListener {
            override fun onRightProgressChange(progress: Float) {
                max = (progress * 100).toInt().toString()
                if (max.toInt() < min.toInt()) {
                    max = min
                }
                model.minDistance = min
                tv_distance?.text = "${min}-${max}"
            }

            override fun onLeftProgressChange(progress: Float) {
                min = (progress * 100).toInt().toString()
                if (min.toInt() > max.toInt()) {
                    min = max
                }
                model.minDistance = max
                tv_distance?.text = "${min}-${max}"
            }
        })


        sb_pressure?.setOnProgressChangeListener(object : TwoWayRattingBar.OnProgressChangeListener {
            override fun onRightProgressChange(progress: Float) {
                max = (progress * 100).toInt().toString()
                if (max.toInt() < min.toInt()) {
                    max = min
                }
                model.minAge = min
                tv_range?.text = "${min}-${max}"
            }

            override fun onLeftProgressChange(progress: Float) {
                min = (progress * 100 + 18).toInt().toString()
                if (min.toInt() > max.toInt()) {
                    min = max
                }
                model.maxAge = max
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


    override fun onNothingSelected(parent: AdapterView<*>?) {

    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        when (parent!!.id) {
            R.id.sp_province -> {
                this.position = position
                model.province = list1[position]

                position2 = 0
                setloop2()
                spinnerAdapter2!!.notifyDataSetChanged()
                sp_city?.setSelection(0, true)
                model.city = list2[position2]

            }
            R.id.sp_city -> {
                this.position2 = position
                model.city = list2[position2]
            }
        }
    }


    private fun setloop1() {
        for (i in addList.indices) {
            list1.add(addList[i].areaName!!)
        }
    }


    private fun setloop2() {
        list2.clear()
        for (i in 0 until addList[position].cities!!.size) {
            if (!list2.contains(addList[position].cities!![i].areaName)) {
                list2.add(addList[position].cities!![i].areaName!!)
            }
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