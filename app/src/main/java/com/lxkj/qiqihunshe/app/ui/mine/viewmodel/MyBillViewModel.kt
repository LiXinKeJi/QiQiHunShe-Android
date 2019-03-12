package com.lxkj.qiqihunshe.app.ui.mine.viewmodel

import android.databinding.ObservableField
import android.support.v7.widget.LinearLayoutManager
import android.view.Gravity
import com.google.gson.Gson
import com.lxkj.qiqihunshe.app.base.BaseViewModel
import com.lxkj.qiqihunshe.app.retrofitnet.SingleCompose
import com.lxkj.qiqihunshe.app.retrofitnet.SingleObserverInterface
import com.lxkj.qiqihunshe.app.retrofitnet.async
import com.lxkj.qiqihunshe.app.ui.dialog.DateBirthdayPop
import com.lxkj.qiqihunshe.app.ui.mine.adapter.MyBillAdapter
import com.lxkj.qiqihunshe.app.ui.mine.model.MyBillModel
import com.lxkj.qiqihunshe.app.util.StaticUtil
import com.lxkj.qiqihunshe.app.util.abLog
import com.lxkj.qiqihunshe.databinding.ActivityMybillBinding
import io.reactivex.Single

/**
 * Created by Slingge on 2019/2/22
 */
class MyBillViewModel : BaseViewModel(), DateBirthdayPop.DateCallBack {


    val startTime = ObservableField<String>()
    val endTime = ObservableField<String>()

    var page = 1
    var totalPage = 1
     val adapter by lazy { MyBillAdapter() }

    var bind: ActivityMybillBinding? = null

    fun initViewModel() {
        startTime.set("开始时间")
        endTime.set("结束时间")

        bind!!.rvBill.isFocusable = false
        bind!!.rvBill.layoutManager = LinearLayoutManager(fragment?.context)

        bind!!.rvBill.adapter = adapter

    }


    fun getBill(): Single<String> {
        val json = "{\"cmd\":\"userBill\",\"uid\":\"" + StaticUtil.uid + "\",\"begin\":\"" + startTime.get() +
                "\",\"end\":\"" + endTime.get() + "\",\"page\":\"" + page + "\"}"
        abLog.e("获取账单", json)
        return retrofit.getData(json).async().compose(SingleCompose.compose(object : SingleObserverInterface {
            override fun onSuccess(response: String) {
                val model = Gson().fromJson(response, MyBillModel::class.java)

                if (page == 1) {
                    totalPage=model.totalPage
                    if (model.totalPage == 1) {
                        adapter.flag = 0
                        adapter.upData(model.dataList)
                    }
                } else {
                    if (page == model.totalPage) {
                        adapter.loadMore(model.dataList, 0)
                    } else {
                        adapter.loadMore(model.dataList, -1)
                    }
                }
            }
        }, activity))
    }


    private var dateBirthdayPop: DateBirthdayPop? = null
    private var flag = -1//0开始时间，1结束时间
    fun showDateWheel(flag: Int) {
        this.flag = flag
        if (dateBirthdayPop == null) {
            dateBirthdayPop = DateBirthdayPop(activity, this)
        }
        if (!dateBirthdayPop!!.isShowing) {
            dateBirthdayPop!!.showAtLocation(bind?.llMain, Gravity.CENTER or Gravity.BOTTOM, 0, 0)
        }
    }

    override fun position(position1: String, position2: String, position3: String) {
        if (flag == 0) {
            startTime.set("$position1-$position2-$position3")
        } else {
            endTime.set("$position1-$position2-$position3")
        }
    }


}