package com.lxkj.qiqihunshe.app.ui.xiaoxi.viewmodel

import android.view.Gravity
import com.lxkj.qiqihunshe.R
import com.lxkj.qiqihunshe.app.base.BaseViewModel
import com.lxkj.qiqihunshe.app.retrofitnet.GetTagUtil
import com.lxkj.qiqihunshe.app.ui.dialog.StringSelectPop
import com.lxkj.qiqihunshe.app.ui.xiaoxi.model.ParamsModel
import com.lxkj.qiqihunshe.app.util.StaticUtil
import com.lxkj.qiqihunshe.databinding.FragmentLookupEconomicsBinding

/**
 * Created by Slingge on 2019/3/1
 */
class LookupEconomicsViewModel : BaseViewModel(),
    StringSelectPop.StringCallBack {


    var bind: FragmentLookupEconomicsBinding? = null

    val model by lazy { ParamsModel() }


    private var SelectString = -1//1学历,2我的情感状态,3我的情感计划，4性别,5月收入，6车辆 7房屋

    private var stringPop: StringSelectPop? = null

    private val eduList by lazy { ArrayList<String>() }//学历集合
    private val incomeList by lazy { ArrayList<String>() }//收入集合
    private val carList by lazy { ArrayList<String>() }//车集合
    private val houseList by lazy { ArrayList<String>() }//房集合

    private val emotionalList by lazy { ArrayList<String>() }//情感状态集合

    private val planningList by lazy { ArrayList<String>() }//我的情感计划集合


    private val sexList by lazy { ArrayList<String>() }//性别


    //学历,情感状态,情感计划
    override fun position(position1: Int) {
        when (SelectString) {
            1 -> {
                model.education = eduList!![position1]
                bind?.tvEducation?.text = model.education
            }
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
            }
            5 -> {//月收入
                model.salary = incomeList[position1]
                bind?.tvIncome?.text = model.salary
            }
            6 -> {//车辆
                model.car = carList[position1]
                bind?.tvCar?.text = model.car
            }
            7 -> {//房屋
                model.house = houseList[position1]
                bind?.tvRoom?.text = model.house
            }
        }
    }


    //get学历
    fun getEdu() {
        if (eduList.isEmpty()) {
            GetTagUtil(fragment?.activity!!, object : GetTagUtil.TagListCallback {
                override fun TagList(tagList: ArrayList<String>) {
                    eduList.addAll(tagList)
                    SelectString = 1
                    showEdu()
                }
            }).getTag(StaticUtil.sex, "8")
        } else {
            SelectString = 1
            showEdu()
        }
    }

    //get月收入
    fun getIncome() {
        if (incomeList.isEmpty()) {
            GetTagUtil(fragment?.activity!!, object : GetTagUtil.TagListCallback {
                override fun TagList(tagList: ArrayList<String>) {
                    incomeList.addAll(tagList)
                    SelectString = 5
                    showStringWheel(incomeList!!)
                }
            }).getTag(model.sex, "5")
        } else {
            SelectString = 5
            showStringWheel(incomeList!!)
        }
    }

    //get车
    fun getCar() {
        if (carList.isEmpty()) {
            GetTagUtil(fragment?.activity!!, object : GetTagUtil.TagListCallback {
                override fun TagList(tagList: ArrayList<String>) {
                    carList.addAll(tagList)
                    SelectString = 6
                    showStringWheel(carList!!)
                }
            }).getTag(model.sex, "6")
        } else {
            SelectString = 6
            showStringWheel(carList!!)
        }
    }

    //get房
    fun getHouse() {
        if (houseList.isEmpty()) {
            GetTagUtil(fragment?.activity!!, object : GetTagUtil.TagListCallback {
                override fun TagList(tagList: ArrayList<String>) {
                    houseList.addAll(tagList)
                    SelectString = 7
                    showStringWheel(houseList!!)
                }
            }).getTag(model.sex, "7")
        } else {
            SelectString = 7
            showStringWheel(houseList!!)
        }
    }


    fun showEdu() {
        showStringWheel(eduList!!)
    }

    //get性别
    fun getSex() {
        if (sexList.isEmpty()) {
            sexList.addAll(fragment!!.resources.getStringArray(R.array.sex))
        }
        SelectString = 4
        showStringWheel(sexList)
    }


    //get我的情感状态
    fun getEmotional() {
        if (emotionalList.isEmpty()) {
            emotionalList.addAll(fragment?.activity!!.resources.getStringArray(R.array.emotional))
        }
        SelectString = 2
        showStringWheel(emotionalList)
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
            }).getTag(StaticUtil.sex, "1")
        } else {
            SelectString = 3
            showStringWheel(planningList)
        }
    }


}
