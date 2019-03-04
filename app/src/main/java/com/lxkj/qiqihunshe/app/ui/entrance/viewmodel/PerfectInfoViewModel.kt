package com.lxkj.qiqihunshe.app.ui.entrance.viewmodel

import android.annotation.SuppressLint
import android.view.Gravity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.lxkj.qiqihunshe.app.base.BaseViewModel
import com.lxkj.qiqihunshe.app.ui.dialog.AddressPop
import com.lxkj.qiqihunshe.app.ui.dialog.DatePop
import com.lxkj.qiqihunshe.app.ui.entrance.model.PerfectInfoModel
import com.lxkj.qiqihunshe.app.ui.model.CityModel
import com.lxkj.qiqihunshe.app.util.AppJsonFileReader
import com.lxkj.qiqihunshe.databinding.ActivityPerfectInfoBinding

/**
 * Created by Slingge on 2019/2/19
 */
class PerfectInfoViewModel : BaseViewModel(), DatePop.DateCallBack, AddressPop.AddressCallBack {

    var bind: ActivityPerfectInfoBinding? = null

    val model by lazy { PerfectInfoModel() }

    private var datePop: DatePop? = null
    private var addressPop: AddressPop? = null
    private var cityList: List<CityModel> = java.util.ArrayList()//全国城市

    private var type = -1//0我的出生日期

    private var flag = -1// 0我的家乡，1我的现居


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


    //flag 0我的家乡，1我的现居
    fun showAddress(flag: Int) {
        this.flag = flag
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
        when(type){
            0->{
                model.birthday = "$position1-$position2-$position3"
                bind?.tvBirthday?.text = model.birthday
            }
        }

    }


    //家乡
    override fun position(position1: Int, position2: Int, position3: Int) {
        when(type){
            0->{
                model.birthplace = cityList[position1].areaName +
                        cityList[position1].cities!![position2].areaName +
                        cityList[position1].cities!![position2].counties!![position3].areaName
                bind?.tvHometown?.text = model.birthplace
            }
            1->{
                model.residence = cityList[position1].areaName +
                        cityList[position1].cities!![position2].areaName +
                        cityList[position1].cities!![position2].counties!![position3].areaName
                bind?.tvResidence?.text = model.birthplace
            }
        }
    }


}