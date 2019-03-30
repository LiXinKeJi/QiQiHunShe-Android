package com.lxkj.qiqihunshe.app.ui.mine.viewmodel

import android.annotation.SuppressLint
import android.databinding.ObservableField
import android.os.Bundle
import android.view.Gravity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.lxkj.qiqihunshe.app.MyApplication
import com.lxkj.qiqihunshe.app.base.BaseViewModel
import com.lxkj.qiqihunshe.app.retrofitnet.GetTagUtil
import com.lxkj.qiqihunshe.app.retrofitnet.SingleCompose
import com.lxkj.qiqihunshe.app.retrofitnet.SingleObserverInterface
import com.lxkj.qiqihunshe.app.retrofitnet.async
import com.lxkj.qiqihunshe.app.ui.dialog.AddressPop
import com.lxkj.qiqihunshe.app.ui.dialog.StringSelectPop
import com.lxkj.qiqihunshe.app.ui.mine.activity.PayActivity
import com.lxkj.qiqihunshe.app.ui.mine.model.PermissionBuyXuQiuModel
import com.lxkj.qiqihunshe.app.ui.model.CityModel
import com.lxkj.qiqihunshe.app.util.AppJsonFileReader
import com.lxkj.qiqihunshe.app.util.StaticUtil
import com.lxkj.qiqihunshe.app.util.abLog
import com.lxkj.qiqihunshe.databinding.ActivityDemandList4Binding
import io.reactivex.Single
import org.json.JSONObject

/**
 * Created by Slingge on 2019/3/28
 */
class DemandListViewModel4 : BaseViewModel(), AddressPop.AddressCallBack, StringSelectPop.StringCallBack {


    var money = ObservableField<String>()
    var info = ObservableField<String>()

    lateinit var model: PermissionBuyXuQiuModel

    lateinit var bind: ActivityDemandList4Binding
    private var cityList: List<CityModel> = java.util.ArrayList()//全国城市
    var addressPop: AddressPop? = null

    private var eduList: ArrayList<String>? = null//学历集合
    private var stringPop: StringSelectPop? = null


   private var flag = -1

    fun init() {
        activity?.let {
            money.set(it.intent.getStringExtra("money"))
            info.set(it.intent.getStringExtra("info"))
        }
    }


    //flag 0家乡，1现居
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
            addressPop!!.showAtLocation(bind.flMain, Gravity.CENTER or Gravity.BOTTOM, 0, 0)
        }
    }

    override fun position(position1: Int, position2: Int) {
        when (flag) {
            0 -> {//我的家乡
                model.birthplace = cityList[position1].areaName +
                        cityList[position1].cities!![position2].areaName
                bind.tvHeHometown.text = model.birthplace
            }
            1 -> {//我的现居
                model.residence = cityList[position1].areaName +
                        cityList[position1].cities!![position2].areaName
                bind.tvHeResidence.text = model.residence
            }
        }
    }

    //get学历
    fun getEdu() {
        if (eduList == null) {
            GetTagUtil(activity!!, object : GetTagUtil.TagListCallback {
                override fun TagList(tagList: ArrayList<String>) {
                    eduList = tagList
                    showStringWheel(eduList!!)
                }
            }).getTag("0", "8")
        } else {
            showStringWheel(eduList!!)
        }
    }

    //学历
    override fun position(position1: Int) {
        model.education = eduList!![position1]
        bind.tvHeEducation.text = model.education
    }

    fun showStringWheel(list: ArrayList<String>) {
        stringPop = StringSelectPop(activity, list, this)

        if (!stringPop!!.isShowing) {
            stringPop!!.showAtLocation(bind?.flMain, Gravity.CENTER or Gravity.BOTTOM, 0, 0)
        }
    }


    //定制推荐、牵引安排，获取条件
    @SuppressLint("CheckResult")
    fun getXuqiu(): Single<String> {
        val json = "{\"cmd\":\"getXuqiuList\",\"uid\":\"" + StaticUtil.uid + "\",\"type\":\"" + "4" + "\"}"
        abLog.e("获取条件", json)
        return retrofit.getData(json).async().compose(SingleCompose.compose(object : SingleObserverInterface {
            override fun onSuccess(response: String) {
                abLog.e("获取条件", response)
                model = Gson().fromJson(response, PermissionBuyXuQiuModel::class.java)
                model.type = "4"
                if (model.car == "1") {//车 0无 1有
                    bind.tvHeCar.text = "有"
                } else if (model.car == "0") {
                    bind.tvHeCar.text = "无"
                } else {
                    bind.tvHeCar.text = "请选择"
                }

                if (model.house == "1") {//房 0无 1有
                    bind.tvHeRoom.text = "有"
                } else if (model.car == "0") {
                    bind.tvHeRoom.text = "无"
                } else {
                    bind.tvHeRoom.text = "请选择"
                }
                bind.tvHeResidence

                bind.model = model
            }
        }, activity))
    }


    //购买权限
    @SuppressLint("CheckResult")
    fun BuyPer(): Single<String> {
        abLog.e("购买权限", Gson().toJson(model))
        return retrofit.getData(Gson().toJson(model)).async().compose(SingleCompose.compose(object :
            SingleObserverInterface {
            override fun onSuccess(response: String) {
                val obj = JSONObject(response)
                val bundle = Bundle()
                bundle.putString("num", obj.getString("orderId"))
                bundle.putDouble("money", money.get()!!.toDouble())
                bundle.putInt("flag", 0)
                MyApplication.openActivityForResult(activity, PayActivity::class.java, bundle, 0)
            }
        }, activity))
    }

}