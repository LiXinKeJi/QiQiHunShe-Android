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

import com.lxkj.qiqihunshe.app.ui.model.CityModel
import com.weigan.loopview.LoopView

import java.util.ArrayList

/**
 * 选择地址
 * Created by Slingge on 2018/1/17 0017.
 */

class AddressPop(context: Context?, private val cityList: List<CityModel>, var wheelViewCallBack: AddressCallBack) :
    PopupWindow(context) {

    internal var position = 0
    internal var position2 = 0
    internal var position3 = 0

    private val list1 = ArrayList<String>()
    private val list2 = ArrayList<String>()
    private val list3 = ArrayList<String>()

    interface AddressCallBack {
        fun position(position1: Int, position2: Int, position3: Int)
    }


    init {


        val v = LayoutInflater.from(context).inflate(R.layout.popup_date, null)
        val loopview = v.findViewById<View>(R.id.loopView) as LoopView
        val loopview2 = v.findViewById<View>(R.id.loopView2) as LoopView
        loopview2.visibility = View.VISIBLE
        val loopview3 = v.findViewById<View>(R.id.loopView3) as LoopView
        loopview3.visibility = View.VISIBLE

        v.findViewById<TextView>(R.id.year).visibility = View.GONE
        v.findViewById<TextView>(R.id.month).visibility = View.GONE
        v.findViewById<TextView>(R.id.day).visibility = View.GONE

        //设置是否循环播放
        //        loopView.setNotLoop();
        //滚动监听
        //设置原始数据
        loopview.setNotLoop()
        setloop1()
        loopview.setItems(list1)
        loopview.setInitPosition(0)

        loopview2.setNotLoop()
        setloop2()
        loopview2.setItems(list2)
        loopview2.setInitPosition(0)

        loopview3.setNotLoop()
        setloop3()
        loopview3.setItems(list3)
        loopview3.setInitPosition(0)
        wheelViewCallBack.position(position, position2, position3)

        loopview.setListener { index ->
            position = index

            position2 = 0
            setloop2()
            loopview2.setItems(list2)
            loopview2.setInitPosition(position2)

            position3 = 0
            setloop3()
            loopview3.setItems(list3)
            loopview3.setInitPosition(position3)

            wheelViewCallBack.position(position, 0, 0)
        }
        loopview2.setListener { index ->
            position2 = index

            position3 = 0
            setloop3()
            loopview3.setItems(list3)
            loopview3.setInitPosition(0)

            wheelViewCallBack.position(position, position2, 0)
        }
        loopview3.setListener { index ->
            position3 = index
            wheelViewCallBack.position(position, position2, position3)
        }

        val tv_enter = v.findViewById<View>(R.id.tv_enter) as TextView
        tv_enter.setOnClickListener { v1 ->
            wheelViewCallBack.position(position, position2, position3)
            this@AddressPop.dismiss()
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

    private fun setloop1() {
        for (i in cityList.indices) {
            list1.add(cityList[i].areaName!!)
        }
    }


    private fun setloop2() {
        list2.clear()
        for (i in 0 until cityList[position].cities!!.size) {
            if (!list2.contains(cityList[position].cities!![i].areaName)) {
                list2.add(cityList[position].cities!![i].areaName!!)
            }
        }
    }

    private fun setloop3() {
        list3.clear()
        for (i in 0 until cityList[position].cities!![position2].counties!!.size) {
            list3.add(cityList[position].cities!![position2].counties!![i].areaName!!)
        }
    }
}
