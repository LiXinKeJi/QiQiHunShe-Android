package com.lxkj.qiqihunshe.app.ui.xiaoxi.viewmodel

import android.view.Gravity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.lxkj.qiqihunshe.R
import com.lxkj.qiqihunshe.app.base.BaseViewModel
import com.lxkj.qiqihunshe.app.retrofitnet.GetTagUtil
import com.lxkj.qiqihunshe.app.ui.dialog.AddressPop
import com.lxkj.qiqihunshe.app.ui.dialog.StringSelectPop
import com.lxkj.qiqihunshe.app.ui.model.CityModel
import com.lxkj.qiqihunshe.app.ui.xiaoxi.model.ParamsModel
import com.lxkj.qiqihunshe.app.util.AppJsonFileReader
import com.lxkj.qiqihunshe.app.util.ToastUtil
import com.lxkj.qiqihunshe.databinding.FragmentLookupConditionBinding

/**
 * Created by Slingge on 2019/3/1
 */
class LookupConditionViewModel : BaseViewModel(), AddressPop.AddressCallBack, StringSelectPop.StringCallBack {

    var bind: FragmentLookupConditionBinding? = null

    val model by lazy { ParamsModel() }

    private var addressPop: AddressPop? = null
    private var cityList: List<CityModel> = java.util.ArrayList()//全国城市


    private var flag = -1// 0我的家乡，1我的现居

    private var SelectString = -1//,2我的情感状态,3我的情感计划,4性别

    private var stringPop: StringSelectPop? = null

    private val emotionalList by lazy { ArrayList<String>() }//情感状态集合

    private val sexList by lazy { ArrayList<String>() }//性别

    private val planningList by lazy { ArrayList<String>() }//我的情感计划集合

    private var sex = ""


    //flag 0我的家乡，1我的现居
    fun showAddress(flag: Int) {
        this.flag = flag
        ToastUtil.showToast(flag.toString())
        if (cityList.isEmpty()) {
            cityList = Gson().fromJson(
                AppJsonFileReader.getJsons(fragment?.activity, 0),
                object : TypeToken<List<CityModel>>() {
                }.type
            )
        }
        if (addressPop == null) {
            addressPop = AddressPop(fragment?.context, cityList, this)
        }
        if (!addressPop!!.isShowing) {
            addressPop!!.showAtLocation(bind?.llMain, Gravity.CENTER or Gravity.BOTTOM, 0, 0)
        }
    }


    //家乡
    override fun position(position1: Int, position2: Int) {
        when (flag) {
            0 -> {//我的家乡
                model.birthplace = cityList[position1].areaName +
                        cityList[position1].cities!![position2].areaName
                bind?.tvHometown?.text = model.birthplace
            }
            1 -> {//我的现居
                model.residence = cityList[position1].areaName +
                        cityList[position1].cities!![position2].areaName
                bind?.tvAddress?.text = model.residence
            }
        }

    }


    //情感状态、情感计划
    override fun position(position1: Int) {
        when (SelectString) {
            2 -> {//我的情感状态
                model.marriage = position1.toString()
                bind?.tvState?.text = emotionalList[position1]
            }
            3 -> {//我的情感计划
                model.plan = planningList[position1]
                bind?.tvEmotion?.text = model.plan
            }
            4 -> {//性别
                model.sex = sexList[position1]
                bind?.tvSex?.text = model.sex

                if (model.sex == "男") {
                    sex = "1"
                } else {
                    sex = "0"
                }
                model.sex = sex
            }
        }
    }


    //get我的情感状态
    fun getEmotional() {
        if (emotionalList.isEmpty()) {
            emotionalList.addAll(fragment!!.resources.getStringArray(R.array.emotional))
        }
        SelectString = 2
        showStringWheel(emotionalList)
    }

    //get性别
    fun getSex() {
        if (sexList.isEmpty()) {
            sexList.addAll(fragment!!.resources.getStringArray(R.array.sex))
        }
        SelectString = 4
        showStringWheel(sexList)
    }


    fun showStringWheel(list: ArrayList<String>) {
        stringPop = StringSelectPop(fragment?.context, list, this)
        if (!stringPop!!.isShowing) {
            stringPop!!.showAtLocation(bind?.llMain, Gravity.CENTER or Gravity.BOTTOM, 0, 0)
        }
    }


    //get情感计划
    fun getEmotionalPlanning() {
        if (planningList.isEmpty()) {
            GetTagUtil(fragment?.activity!!, object : GetTagUtil.TagListCallback {
                override fun TagList(tagList: ArrayList<String>) {
                    planningList.addAll(tagList)
                    SelectString = 3
                    showStringWheel(planningList)
                }
            }).getTag(sex, "1")
        } else {
            SelectString = 3
            showStringWheel(planningList)
        }
    }


}
