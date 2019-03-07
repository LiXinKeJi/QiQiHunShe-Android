package com.lxkj.qiqihunshe.app.ui.mine.viewmodel

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.lxkj.qiqihunshe.app.MyApplication
import com.lxkj.qiqihunshe.app.base.BaseViewModel
import com.lxkj.qiqihunshe.app.retrofitnet.SingleCompose
import com.lxkj.qiqihunshe.app.retrofitnet.SingleObserverInterface
import com.lxkj.qiqihunshe.app.retrofitnet.async
import com.lxkj.qiqihunshe.app.ui.mine.activity.QiQiDynamicDetailsActivity
import com.lxkj.qiqihunshe.app.ui.mine.adapter.QiQiDynamicAdapter
import com.lxkj.qiqihunshe.app.ui.mine.model.QiQiBlackListModel
import com.lxkj.qiqihunshe.app.ui.mine.model.QiQiDynamicModel
import com.lxkj.qiqihunshe.app.ui.mine.model.QiQiRuleModel
import com.lxkj.qiqihunshe.app.util.ToastUtil
import com.lxkj.qiqihunshe.databinding.ActivityQiqiDynamicBinding
import io.reactivex.Single
import org.json.JSONObject

/**
 * 活动报名记录
 * Created by Slingge on 2019/2/21
 */
class QiQiDynamicViewModel : BaseViewModel() {


    private val adapter by lazy { QiQiDynamicAdapter() }

    var bind: ActivityQiqiDynamicBinding? = null

    fun initViewModel() {
        bind!!.rvDyna.isFocusable = false
        bind!!.rvDyna.layoutManager = LinearLayoutManager(fragment?.context)

        bind!!.rvDyna.adapter = adapter

        val list = ArrayList<QiQiDynamicModel>()
//        for (i in 0 until 5) {
//            val model = QiQiDynamicModel()
//            list.add(model)
//        }
        adapter.upData(list)

        adapter.setMyListener { itemBean, position ->
            var bundle=Bundle()
            bundle.putSerializable("data",itemBean)
            MyApplication.openActivity(activity, QiQiDynamicDetailsActivity::class.java,bundle)
        }
    }

    fun getData(json: String): Single<String> = retrofit.getData(json)
        .async().compose(SingleCompose.compose(object : SingleObserverInterface {
            override fun onSuccess(response: String) {
                Log.i("sss","response-------------->"+response)
                var json=JSONObject(response)
                var list= Gson().fromJson<ArrayList<QiQiDynamicModel>>(json.getString("dataList"),object:
                    TypeToken<ArrayList<QiQiDynamicModel>>(){}.type)
                adapter.loadMore(list,1)

            }

        }, activity))




}