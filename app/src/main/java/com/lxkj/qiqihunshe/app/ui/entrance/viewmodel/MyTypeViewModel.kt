package com.lxkj.qiqihunshe.app.ui.entrance.viewmodel

import android.content.Intent
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

    private val array by lazy { ArrayList<MyTypeModel>() }
    val lable by lazy { ArrayList<String>() }
    private val tvList by lazy { ArrayList<TextView>() }

    var flag = 0//1我的类型(单选)，2兴趣爱好，3地点标签,4他的类型(单选)

    fun initTLable() {

        for (i in 0 until lable.size) {
            var model = MyTypeModel()
            model.name = lable[i]
            array.add(model)
        }

        for (i in 0 until array.size) {
            val tv = LayoutInflater.from(activity).inflate(
                R.layout.layout_flow_talent_type, bind?.clMain, false
            ) as TextView
            tv.text = array[i].name

            tv.setOnClickListener {
                if (flag == 4 || flag == 1) {
                    for (j in 0 until array.size) {
                        if (array[j].isSelect) {
                            array[j].isSelect = false
                        }
                    }
                    for (j in 0 until tvList.size) {
                        tvList[j].setBackgroundResource(R.drawable.them_line35)
                    }
                    array[i].isSelect = true
                    tv.setBackgroundResource(R.drawable.thems_bg35)
                } else {
                    CleatStat(tv, i)
                }
            }
            tvList.add(tv)
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

    fun back() {

        val sb = StringBuffer()

        for (i in 0 until array.size) {
            if (array[i].isSelect) {
                sb.append(array[i].name + ",")
            }
        }

        val intetent = Intent()
        intetent.putExtra("lable", sb.toString().substring(0, sb.toString().length - 1))
        activity?.let {
            it.setResult(0, intetent)
            it.finish()
        }

    }


}