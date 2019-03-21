package com.lxkj.qiqihunshe.app.ui.mine.viewmodel

import android.text.TextUtils
import android.view.LayoutInflater
import android.widget.TextView
import com.google.gson.Gson
import com.lxkj.qiqihunshe.R
import com.lxkj.qiqihunshe.app.base.BaseViewModel
import com.lxkj.qiqihunshe.app.customview.FlowLayout
import com.lxkj.qiqihunshe.app.retrofitnet.SingleCompose
import com.lxkj.qiqihunshe.app.retrofitnet.SingleObserverInterface
import com.lxkj.qiqihunshe.app.retrofitnet.async
import com.lxkj.qiqihunshe.app.ui.mine.model.PersonDataModel
import com.lxkj.qiqihunshe.app.util.abLog
import com.lxkj.qiqihunshe.databinding.FragmentPersonDataBinding
import io.reactivex.Single
import java.util.ArrayList

/**
 * Created by Slingge on 2019/2/21
 */
class PersonDataViewModel : BaseViewModel() {

    var userId = ""

    var bind: FragmentPersonDataBinding? = null

    private val zeou by lazy { ArrayList<String>() }//择偶条件

    fun getPersonData(): Single<String> {
        val json = "{\"cmd\":\"userData\",\"userId\":\"$userId\"}"
        abLog.e("个人资料", json)
        return retrofit.getData(json).async().compose(SingleCompose.compose(object : SingleObserverInterface {
            override fun onSuccess(response: String) {
                val model = Gson().fromJson(response, PersonDataModel::class.java)
                bind?.let {
                    it.model = model

                    if (model.marriage == "0") {// 情感状态 0未婚 1已婚 2离异
                        it.tvState.text = "未婚"
                    } else if (model.marriage == "1") {
                        it.tvState.text = "已婚"
                    } else if (model.marriage == "2") {
                        it.tvState.text = "离异"
                    }

                    addLable(model.zeou_type)
                    addLable(model.zeou_birthplace)
                    addLable(model.zeou_residence)
                    addLable(model.zeou_height)
                    if (model.zeou_marriage == "0") {// 情感状态 0未婚 1已婚 2离异（择偶条件）
                        addLable("未婚")
                    } else if (model.zeou_marriage == "1") {
                        addLable("未婚")
                    } else {
                        addLable("离异")
                    }
                    addLable(model.zeou_education)
                    addLable(model.zeou_salary)
                    addLable(model.zeou_car)
                    addLable(model.zeou_house)
                    addLable(model.zeou_plan)

                    getLable(it.flType, zeou)

                    getLable(it.flBoby, model.interest)
                    getLable(it.flAddress, model.locale)
                }
            }
        }, fragment!!.activity))
    }


    private fun getLable(flType: FlowLayout, array: ArrayList<String>) {

        for (i in array) {
            val tv = LayoutInflater.from(fragment!!.activity).inflate(
                R.layout.layout_flow_talent_type, bind!!.llMain, false
            ) as TextView
            tv.text = i
            flType.addView(tv)
        }
    }


    fun addLable(string: String) {
        if (TextUtils.isEmpty(string)) {
            return
        }
        zeou.add(string)
    }


}