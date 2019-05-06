package com.lxkj.qiqihunshe.app.ui.dialog

import android.content.Context
import android.graphics.drawable.BitmapDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.PopupWindow
import android.widget.TextView
import com.lxkj.qiqihunshe.R
import com.lxkj.qiqihunshe.app.util.GetDateTimeUtil
import com.weigan.loopview.LoopView

import java.util.*

/**
 * 选择日期
 * Created by Slingge on 2018/1/17 0017.
 */

//maxDay true 使用未来时间
class DatePop(context: Context?, var wheelViewCallBack: DateCallBack) : PopupWindow(context) {

    internal var position = 0
    internal var position2 = 0
    internal var position3 = 0

    internal var position4 = 0
    internal var position5 = 0
    internal var position6 = 0

    private val yearList by lazy { ArrayList<String>() }
    private val monthList by lazy { ArrayList<String>() }
    private val dayList by lazy { ArrayList<String>() }

    private val hourList by lazy { ArrayList<String>() }
    private val minList by lazy { ArrayList<String>() }

    private var month = 0//当前月份
    private var day = 0//当天
    private var year = 0
    private var hour=0
    private var min=0
    private var s=0


    private lateinit var loopview4:LoopView
    private lateinit var loopview5:LoopView
    private lateinit var loopview6:LoopView

    private lateinit var tv_hour:TextView
    private lateinit var tv_min:TextView
    private lateinit var tv_second:TextView


    interface DateCallBack {
        fun position(position1: String, position2: String, position3: String, position4: String, position5: String, position6: String)
        fun position()
    }


    init {
        val v = LayoutInflater.from(context).inflate(R.layout.popup_date, null)
        val loopview = v.findViewById<View>(R.id.loopView) as LoopView
        val loopview2 = v.findViewById<View>(R.id.loopView2) as LoopView
        val loopview3 = v.findViewById<View>(R.id.loopView3) as LoopView

          loopview4 = v.findViewById<View>(R.id.loopView4) as LoopView
          loopview5 = v.findViewById<View>(R.id.loopView5) as LoopView
          loopview6 = v.findViewById<View>(R.id.loopView6) as LoopView
        loopview4.visibility = View.VISIBLE
        loopview5.visibility = View.VISIBLE
        loopview6.visibility = View.VISIBLE
        loopview.setTextSize(16f)
        loopview2.setTextSize(16f)
        loopview3.setTextSize(16f)

        v.findViewById<View>(R.id.year).visibility = View.VISIBLE
        v.findViewById<View>(R.id.month).visibility = View.VISIBLE
        v.findViewById<View>(R.id.day).visibility = View.VISIBLE

        tv_hour= v.findViewById(R.id.hour)
        tv_hour.visibility=View.VISIBLE

        tv_min= v.findViewById(R.id.min)
        tv_min.visibility=View.VISIBLE

        tv_second= v.findViewById(R.id.second)
        tv_second.visibility=View.VISIBLE

        //设置是否循环播放
        //        loopView.setNotLoop();
        //滚动监听
        //设置原始数据

        if (yearList.isEmpty()) {
            val c = Calendar.getInstance()
            year = c.get(Calendar.YEAR)// 获取当前年份
            month = c.get(Calendar.MONTH) + 1// 获取当前月份
            day = c.get(Calendar.DAY_OF_MONTH)

            hour=c.get(Calendar.HOUR_OF_DAY)
            min=c.get(Calendar.MINUTE)
            s=c.get(Calendar.SECOND)

            getYear()
            getMonth()
            getDay(year.toString(), month.toString())

            getHour()
            getMin()
        }

//        loopview.setNotLoop()
        loopview.setItems(yearList)
        position = yearList.size - 1
        loopview.setCurrentPosition(position)

        loopview2.setNotLoop()
        loopview2.setItems(monthList)
        position2 = month - 1
        loopview2.setCurrentPosition(position2)

        loopview3.setNotLoop()
        loopview3.setItems(dayList)
        position3 = day - 1
        loopview3.setCurrentPosition(position3)

        loopview4.setNotLoop()
        loopview4.setItems(hourList)
        loopview4.setCurrentPosition(position4)

        loopview5.setNotLoop()
        loopview5.setItems(minList)
        loopview5.setCurrentPosition(position5)

        loopview6.setNotLoop()
        loopview6.setItems(minList)
        loopview6.setCurrentPosition(position6)

        wheelViewCallBack.position(
            yearList[yearList.size - 1],
            monthList[month - 1],
            dayList[day - 1],
            hourList[hour-1],
            minList[min-1],
            minList[s-1]
        )


        loopview.setListener { index ->
            position = index

            position2 = 0

            loopview2.setItems(monthList)

            loopview2.setInitPosition(0)

            position3 = 0
            getDay(yearList[position], monthList[position2])
            loopview3.setItems(dayList)
            loopview3.setInitPosition(position3)

            wheelViewCallBack.position(
                yearList[position],
                monthList[position2],
                dayList[position3],
                hourList[position4],
                hourList[position5],
                hourList[position6]
            )
        }
        loopview2.setListener { index ->
            position2 = index

            position3 = 0

            getDay(yearList[position], monthList[position2])

            loopview3.setItems(dayList)

            loopview3.setInitPosition(0)

            wheelViewCallBack.position(
                yearList[position],
                monthList[position2],
                dayList[position3],
                hourList[position4],
                minList[position5],
                hourList[position6]
            )
        }
        loopview3.setListener { index ->
            position3 = index
            wheelViewCallBack.position(
                yearList[position],
                monthList[position2],
                dayList[position3],
                hourList[position4],
                minList[position5],
                hourList[position6]
            )
        }

        loopview4.setListener { index ->
            position4 = index
            wheelViewCallBack.position(
                yearList[position],
                monthList[position2],
                dayList[position3],
                hourList[position4],
                minList[position5],
                hourList[position6]
            )
        }

        loopview5.setListener { index ->
            position5 = index
            wheelViewCallBack.position(
                yearList[position],
                monthList[position2],
                dayList[position3],
                hourList[position4],
                minList[position5],
                hourList[position6]
            )
        }


        val tv_enter = v.findViewById<View>(R.id.tv_enter) as TextView
        tv_enter.setOnClickListener { v1 ->
            wheelViewCallBack.position(
                yearList[position],
                monthList[position2],
                dayList[position3],
                hourList[position4],
                minList[position5],
                hourList[position6]
            )
            wheelViewCallBack.position()
            this@DatePop.dismiss()
        }

        //设置初始位置
        this.contentView = v
        // 设置SelectPicPopupWindow弹出窗体的宽
        this.width = ViewGroup.LayoutParams.MATCH_PARENT
        // 设置SelectPicPopupWindow弹出窗体的高
        this.height = ViewGroup.LayoutParams.WRAP_CONTENT
        // 设置SelectPicPopupWindow弹出窗体可点击
        this.animationStyle = android.R.style.Animation_InputMethod
        this.isFocusable = true
        //		this.setOutsideTouchable(true);
        this.setBackgroundDrawable(BitmapDrawable())
        this.softInputMode = WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE
    }


    private fun getYear() {
        yearList.clear()
        for (i in year - 100..year) {
            yearList.add(i.toString())
        }

    }

    private fun getMonth() {
        monthList.clear()
        for (i in 1..12) {
            if (i < 10) {
                monthList.add("0$i")
            } else {
                monthList.add(i.toString())
            }
        }
    }

    private fun getDay(year: String, month: String) {
        val max = GetDateTimeUtil.MaxDayFromDay_OF_MONTH(year.toInt(), month.toInt())
        dayList.clear()
        for (i in 1..max) {
            if (i < 10) {
                dayList.add("0$i")
            } else {
                dayList.add(i.toString())
            }
        }
    }

    private fun getHour() {
        for (i in 0..23) {
            if (i < 10) {
                hourList.add("0$i")
            } else {
                hourList.add(i.toString())
            }
        }
    }

    private fun getMin() {
        for (i in 0..59) {
            if (i < 10) {
                minList.add("0$i")
            } else {
                minList.add(i.toString())
            }
        }
    }

    fun hide() {
        loopview4.visibility=View.GONE
        loopview5.visibility=View.GONE
        loopview6.visibility=View.GONE

        tv_hour.visibility=View.GONE
        tv_min.visibility=View.GONE
        tv_second.visibility=View.GONE
    }

}
