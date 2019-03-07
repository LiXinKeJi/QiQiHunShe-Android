package com.lxkj.qiqihunshe.app.ui.entrance.viewmodel

import android.view.LayoutInflater
import android.widget.TextView
import com.lxkj.qiqihunshe.R
import com.lxkj.qiqihunshe.app.base.BaseViewModel
import com.lxkj.qiqihunshe.app.ui.entrance.model.MyTypeModel
import com.lxkj.qiqihunshe.databinding.ActivityMytypeBinding

/**
 * Created by Slingge on 2019/2/19
 */
class MyTypeViewModel : BaseViewModel() {

    var bind: ActivityMytypeBinding? = null

    val array by lazy { ArrayList<MyTypeModel>() }

    fun initTLable() {

        for (i in 0..10) {
            var model = MyTypeModel()
            model.name = i.toString()
            array.add(model)
        }

        for (i in 0 until array.size) {
            val tv = LayoutInflater.from(activity).inflate(
                R.layout.layout_flow_talent_type, bind?.clMain, false
            ) as TextView
            tv.text = array[i].name

            tv.setOnClickListener {
                CleatStat(tv, i)
            }

            bind!!.flType.addView(tv)
        }
    }


    private fun CleatStat(tv: TextView, i: Int) {
        if (array[i].isSelect) {//取消选中
            tv.setBackgroundResource(R.drawable.them_line35)
            array[i].isSelect = false
        } else {
            tv.setBackgroundResource(R.drawable.thems_bg35)
            array[i].isSelect = true
        }
    }


}