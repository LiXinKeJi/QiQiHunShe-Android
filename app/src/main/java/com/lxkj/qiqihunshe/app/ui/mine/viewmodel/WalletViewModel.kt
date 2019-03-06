package com.lxkj.qiqihunshe.app.ui.mine.viewmodel

import android.view.View
import com.google.gson.Gson
import com.lxkj.qiqihunshe.app.base.BaseViewModel
import com.lxkj.qiqihunshe.app.retrofitnet.SingleCompose
import com.lxkj.qiqihunshe.app.retrofitnet.SingleObserverInterface
import com.lxkj.qiqihunshe.app.retrofitnet.async
import com.lxkj.qiqihunshe.app.ui.mine.model.MyPermissionModel
import com.lxkj.qiqihunshe.app.ui.mine.model.WalletModel
import com.lxkj.qiqihunshe.app.util.StaticUtil
import com.lxkj.qiqihunshe.databinding.ActivityWalletBinding
import io.reactivex.Single

/**
 * Created by Slingge on 2019/2/22
 */
class WalletViewModel : BaseViewModel() {

    var bind: ActivityWalletBinding? = null

    fun getBannel(): Single<String> {
        val json = "{\"cmd\":\"userBalance\",\"uid\":\"" + StaticUtil.uid + "\"}"
        return retrofit.getData(json).async().compose(SingleCompose.compose(object : SingleObserverInterface {
            override fun onSuccess(response: String) {
                val model = Gson().fromJson(response, WalletModel::class.java)
                bind?.model = model
                StaticUtil.amount=model.amount

            }
        }, activity))
    }


    fun getPermission(): Single<String> {
        val json = "{\"cmd\":\"myPermission\",\"uid\":\"" + StaticUtil.uid + "\"}"
        return retrofit.getData(json).async().compose(SingleCompose.compose(object : SingleObserverInterface {
            override fun onSuccess(response: String) {
                val list = ArrayList<MyPermissionModel.dataModel>()
                list.addAll(Gson().fromJson(response, MyPermissionModel::class.java).dataList)
                if (list.isEmpty()) {
                    bind!!.clQunxian.visibility = View.GONE
                } else {
                    for (i in 0 until list.size) {
                        whenPermission(list[i].type)
                    }
                }
            }
        }, activity))
    }


    private fun whenPermission(type: String) {
        when (type) {
            "1" -> bind!!.tv1.visibility = View.VISIBLE
            "2" -> bind!!.tv2.visibility = View.VISIBLE
            "3" -> bind!!.tv3.visibility = View.VISIBLE
            "4" -> bind!!.tv4.visibility = View.VISIBLE
            "5" -> bind!!.tv5.visibility = View.VISIBLE
        }
    }

}