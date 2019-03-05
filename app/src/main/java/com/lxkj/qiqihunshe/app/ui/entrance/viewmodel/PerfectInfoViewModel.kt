package com.lxkj.qiqihunshe.app.ui.entrance.viewmodel

import android.os.Bundle
import android.text.TextUtils
import android.view.Gravity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.lxkj.qiqihunshe.R
import com.lxkj.qiqihunshe.app.MyApplication
import com.lxkj.qiqihunshe.app.base.BaseViewModel
import com.lxkj.qiqihunshe.app.retrofitnet.GetTagUtil
import com.lxkj.qiqihunshe.app.ui.dialog.AddressPop
import com.lxkj.qiqihunshe.app.ui.dialog.DatePop
import com.lxkj.qiqihunshe.app.ui.dialog.StringSelectPop
import com.lxkj.qiqihunshe.app.ui.entrance.MyTypeActivity
import com.lxkj.qiqihunshe.app.ui.entrance.model.PerfectInfoModel
import com.lxkj.qiqihunshe.app.ui.model.CityModel
import com.lxkj.qiqihunshe.app.util.AppJsonFileReader
import com.lxkj.qiqihunshe.app.util.ToastUtil
import com.lxkj.qiqihunshe.app.util.abLog
import com.lxkj.qiqihunshe.databinding.ActivityPerfectInfoBinding

/**
 * Created by Slingge on 2019/2/19
 */
class PerfectInfoViewModel : BaseViewModel(), DatePop.DateCallBack, AddressPop.AddressCallBack,
    StringSelectPop.StringCallBack {


    var bind: ActivityPerfectInfoBinding? = null

    val model by lazy { PerfectInfoModel() }

    private var datePop: DatePop? = null
    private var addressPop: AddressPop? = null
    private var cityList: List<CityModel> = java.util.ArrayList()//全国城市

    private var type = -1//0我的出生日期

    private var flag = -1// 0我的家乡，1我的现居

    private var SelectString = -1//0民族，1学历,2我的情感状态,3我的情感计划，4他的情感状态,5他的情感计划，6他的薪资范围

    private var stringPop: StringSelectPop? = null
    private var nationList: ArrayList<String>? = null

    private var eduList: ArrayList<String>? = null//学历集合

    private val emotionalList by lazy { ArrayList<String>() }//情感状态集合

    private val planningList by lazy { ArrayList<String>() }//我的情感计划集合

    private val heplanningList by lazy { ArrayList<String>() }//我的情感计划集合

    private val MyTypeList by lazy { ArrayList<String>() }//我的类型
    private val HeTypeList by lazy { ArrayList<String>() }//他的类型
    private val hobbyList by lazy { ArrayList<String>() }//兴趣爱好集合

    private val labelList by lazy { ArrayList<String>() }//地点标签集合

    private val HeSalaryList by lazy { ArrayList<String>() }//他的薪资范围

    private val HeCarList by lazy { ArrayList<String>() }//他的车
    private val HeRoomList by lazy { ArrayList<String>() }//他的房


    //flag 0我的出生日期
    fun showDate(flag: Int) {
        this.type = flag
        if (datePop == null) {
            datePop = DatePop(activity, this)
        }
        if (!datePop!!.isShowing) {
            datePop!!.showAtLocation(bind?.llMain, Gravity.CENTER or Gravity.BOTTOM, 0, 0)
        }
    }


    //flag 0我的家乡，1我的现居，2他的家乡，3他的现居
    fun showAddress(flag: Int) {
        this.flag = flag
        ToastUtil.showToast(flag.toString())
        if (cityList.isEmpty()) {
            cityList = Gson().fromJson(AppJsonFileReader.getJsons(activity, 0), object : TypeToken<List<CityModel>>() {
            }.type)
        }
        if (addressPop == null) {
            addressPop = AddressPop(activity, cityList, this)
        }
        if (!addressPop!!.isShowing) {
            addressPop!!.showAtLocation(bind?.llMain, Gravity.CENTER or Gravity.BOTTOM, 0, 0)
        }
    }


    //出生日期
    override fun position(position1: String, position2: String, position3: String) {
        when (type) {
            0 -> {
                model.birthday = "$position1-$position2-$position3"
                bind?.tvBirthday?.text = model.birthday
            }
        }
    }


    //家乡
    override fun position(position1: Int, position2: Int, position3: Int) {
        when (flag) {
            0 -> {//我的家乡
                model.birthplace = cityList[position1].areaName +
                        cityList[position1].cities!![position2].areaName +
                        cityList[position1].cities!![position2].counties!![position3].areaName
                bind?.tvHometown?.text = model.birthplace
            }
            1 -> {//我的现居
                model.residence = cityList[position1].areaName +
                        cityList[position1].cities!![position2].areaName +
                        cityList[position1].cities!![position2].counties!![position3].areaName
                bind?.tvResidence?.text = model.residence
            }
            2 -> {//他的家乡
                model.birthplace2 = cityList[position1].areaName +
                        cityList[position1].cities!![position2].areaName +
                        cityList[position1].cities!![position2].counties!![position3].areaName
                bind!!.tvHeHometown.text = model.birthplace
                abLog.e("家乡", model.birthplace2)
            }
            3 -> {//他的现居
                model.residence2 = cityList[position1].areaName +
                        cityList[position1].cities!![position2].areaName +
                        cityList[position1].cities!![position2].counties!![position3].areaName
                bind!!.tvHeResidence.text = model.birthplace
                abLog.e("现居", model.residence2)
            }
        }

    }

    //民族
    fun nation() {
        SelectString = 0
        if (nationList == null) {
            nationList =
                Gson().fromJson(AppJsonFileReader.getJsons(activity, 1), object : TypeToken<List<String>>() {}.type)
        }
        showStringWheel(nationList!!)
    }


    //民族,学历,情感状态
    override fun position(position1: Int) {
        when (SelectString) {
            0 -> {//民族
                model.nation = nationList!![position1]
                bind?.tvNation?.text = model.nation
            }
            1 -> {
                model.education = eduList!![position1]
                bind?.tvEducation?.text = model.education
            }
            2 -> {//我的情感状态
                model.marriage = position1.toString()
                bind?.tvEmotionalState?.text = emotionalList[position1]
            }
            3 -> {//我的情感计划
                model.plan = planningList[position1]
                bind?.tvEmotionalPlanning?.text = model.plan
            }
            4 -> {//他的情感状态
                model.marriage2 = position1.toString()
                bind?.tvHeEmotionalState?.text = emotionalList[position1]
            }
            5 -> {//他的情感计划
                model.plan2 = position1.toString()
                bind?.tvHeEmotionalPlanning?.text = heplanningList[position1]
            }
            6 -> {//他的薪资
                model.salary2 = HeSalaryList[position1]
                bind?.tvHeEmotionalPlanning?.text = model.salary2
            }
            7 -> {//他的车
                model.car2 = HeCarList[position1]
                if (model.house2 == "0") {
                    bind?.tvHeCar?.text = "无"
                } else {
                    bind?.tvHeCar?.text = model.car2
                }
            }
            8 -> {//他的房
                model.house2 = HeRoomList[position1]
                if (model.house2 == "0") {
                    bind?.tvHeRoom?.text = "无"
                } else {
                    bind?.tvHeRoom?.text = model.house2
                }
            }
            9 -> {//他的学历
                model.education2 = eduList!![position1]
                bind?.tvHeEducation?.text = model.education2
            }
        }
    }


    //get学历
    fun getEdu() {
        if (eduList == null) {
            GetTagUtil(activity!!, object : GetTagUtil.TagListCallback {
                override fun TagList(tagList: ArrayList<String>) {
                    eduList = tagList
                    SelectString = 1
                    showEdu()
                }
            }).getTag(model.sex, "8")
        } else {
            SelectString = 1
            showEdu()
        }
    }

    fun showEdu() {
        showStringWheel(eduList!!)
    }

    fun showHeEdu() {
        if (eduList == null) {
            GetTagUtil(activity!!, object : GetTagUtil.TagListCallback {
                override fun TagList(tagList: ArrayList<String>) {
                    eduList = tagList
                    SelectString = 9
                    showEdu()
                }
            }).getTag(model.sex, "8")
        } else {
            SelectString = 9
            showEdu()
        }
    }


    //get我的情感状态
    fun getEmotional() {
        if (emotionalList.isEmpty()) {
            emotionalList.addAll(activity!!.resources.getStringArray(R.array.emotional))
        }
        SelectString = 2
        showStringWheel(emotionalList)
    }

    //get他的情感状态
    fun getHeEmotional() {
        if (emotionalList.isEmpty()) {
            emotionalList.addAll(activity!!.resources.getStringArray(R.array.emotional))
        }
        SelectString = 4
        showStringWheel(emotionalList)
    }

    fun showStringWheel(list: ArrayList<String>) {

        stringPop = StringSelectPop(activity, list, this)

        if (!stringPop!!.isShowing) {
            stringPop!!.showAtLocation(bind?.llMain, Gravity.CENTER or Gravity.BOTTOM, 0, 0)
        }
    }


    //get情感计划
    fun getEmotionalPlanning() {
        if (planningList.isEmpty()) {
            GetTagUtil(activity!!, object : GetTagUtil.TagListCallback {
                override fun TagList(tagList: ArrayList<String>) {
                    planningList.addAll(tagList)
                    SelectString = 3
                    showStringWheel(planningList)
                }
            }).getTag(model.sex, "1")
        } else {
            SelectString = 3
            showStringWheel(planningList)
        }
    }

    //get他的情感计划
    fun getHeEmotionalPlanning() {
        if (heplanningList.isEmpty()) {
            GetTagUtil(activity!!, object : GetTagUtil.TagListCallback {
                override fun TagList(tagList: ArrayList<String>) {
                    heplanningList.addAll(tagList)
                    SelectString = 5
                    showStringWheel(heplanningList)
                }
            }).getTag(getHeSex(), "1")
        } else {
            SelectString = 5
            showStringWheel(heplanningList)
        }
    }


    //获取他的性别，与我相反
    private fun getHeSex(): String {
        if (TextUtils.isEmpty(model.sex)) {
            return ""
        } else if (model.sex == "0") {
            return "1"
        } else {
            return "0"
        }
    }


    //我的类型
    fun getMyType() {
        if (MyTypeList.isEmpty()) {
            GetTagUtil(activity!!, object : GetTagUtil.TagListCallback {
                override fun TagList(tagList: ArrayList<String>) {
                    MyTypeList.addAll(tagList)
                    val bundle = Bundle()
                    bundle.putStringArrayList("list", MyTypeList)
                    MyApplication.openActivityForResult(activity, MyTypeActivity::class.java, bundle, 1)
                }
            }).getTag(model.sex, "2")
        } else {
            val bundle = Bundle()
            bundle.putStringArrayList("list", MyTypeList)
            MyApplication.openActivityForResult(activity, MyTypeActivity::class.java, bundle, 1)
        }
    }

    //兴趣爱好
    fun getHobby() {
        if (hobbyList.isEmpty()) {
            GetTagUtil(activity!!, object : GetTagUtil.TagListCallback {
                override fun TagList(tagList: ArrayList<String>) {
                    hobbyList.addAll(tagList)
                    val bundle = Bundle()
                    bundle.putStringArrayList("list", hobbyList)
                    MyApplication.openActivityForResult(activity, MyTypeActivity::class.java, bundle, 2)
                }
            }).getTag(getHeSex(), "3")
        } else {
            val bundle = Bundle()
            bundle.putStringArrayList("list", hobbyList)
            MyApplication.openActivityForResult(activity, MyTypeActivity::class.java, bundle, 2)
        }
    }


    //地点标签
    fun getLabel() {
        if (labelList.isEmpty()) {
            GetTagUtil(activity!!, object : GetTagUtil.TagListCallback {
                override fun TagList(tagList: ArrayList<String>) {
                    hobbyList.addAll(tagList)
                    val bundle = Bundle()
                    bundle.putStringArrayList("list", labelList)
                    MyApplication.openActivityForResult(activity, MyTypeActivity::class.java, bundle, 3)
                }
            }).getTag(model.sex, "4")
        } else {
            val bundle = Bundle()
            bundle.putStringArrayList("list", labelList)
            MyApplication.openActivityForResult(activity, MyTypeActivity::class.java, bundle, 3)
        }
    }


    //他的类型
    fun getHeType() {
        if (HeTypeList.isEmpty()) {
            GetTagUtil(activity!!, object : GetTagUtil.TagListCallback {
                override fun TagList(tagList: ArrayList<String>) {
                    HeTypeList.addAll(tagList)
                    val bundle = Bundle()
                    bundle.putStringArrayList("list", HeTypeList)
                    MyApplication.openActivityForResult(activity, MyTypeActivity::class.java, bundle, 4)
                }
            }).getTag(getHeSex(), "2")
        } else {
            val bundle = Bundle()
            bundle.putStringArrayList("list", HeTypeList)
            MyApplication.openActivityForResult(activity, MyTypeActivity::class.java, bundle, 4)
        }
    }


    //他的薪资范围
    fun getHeSalary() {
        if (HeSalaryList.isEmpty()) {
            GetTagUtil(activity!!, object : GetTagUtil.TagListCallback {
                override fun TagList(tagList: ArrayList<String>) {
                    HeSalaryList.addAll(tagList)
                    SelectString = 6
                    showStringWheel(planningList)
                }
            }).getTag(getHeSex(), "5")
        } else {
            SelectString = 6
            showStringWheel(planningList)
        }
    }


    //他是否有车
    fun getHeCar() {
        if (HeCarList.isEmpty()) {
            GetTagUtil(activity!!, object : GetTagUtil.TagListCallback {
                override fun TagList(tagList: ArrayList<String>) {
                    HeCarList.addAll(tagList)
                    SelectString = 7
                    showStringWheel(HeCarList)
                }
            }).getTag(getHeSex(), "6")
        } else {
            SelectString = 7
            showStringWheel(HeCarList)
        }
    }

    //他的房
    fun getHeRoom() {
        if (HeRoomList.isEmpty()) {
            GetTagUtil(activity!!, object : GetTagUtil.TagListCallback {
                override fun TagList(tagList: ArrayList<String>) {
                    HeRoomList.addAll(tagList)
                    SelectString = 8
                    showStringWheel(HeRoomList)
                }
            }).getTag(getHeSex(), "7")
        } else {
            SelectString = 8
            showStringWheel(HeRoomList)
        }
    }

}
