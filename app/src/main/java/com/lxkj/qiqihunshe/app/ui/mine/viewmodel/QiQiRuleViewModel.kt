package com.lxkj.qiqihunshe.app.ui.mine.viewmodel

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.lxkj.qiqihunshe.app.MyApplication
import com.lxkj.qiqihunshe.app.base.BaseViewModel
import com.lxkj.qiqihunshe.app.retrofitnet.SingleCompose
import com.lxkj.qiqihunshe.app.retrofitnet.SingleObserverInterface
import com.lxkj.qiqihunshe.app.retrofitnet.async
import com.lxkj.qiqihunshe.app.ui.mine.activity.WebViewActivity
import com.lxkj.qiqihunshe.app.ui.mine.adapter.QiQiRuleBaoAdapter
import com.lxkj.qiqihunshe.app.ui.mine.model.QiQiRuleModel
import com.lxkj.qiqihunshe.databinding.ActivityQiqiRuleBinding
import io.reactivex.Single
import org.json.JSONObject

/**
 * Created by Slingge on 2019/2/21
 */
class QiQiRuleViewModel : BaseViewModel() {


    private val adapter1 by lazy { QiQiRuleBaoAdapter() }
    private val adapter2 by lazy { QiQiRuleBaoAdapter() }
    private val adapter3 by lazy { QiQiRuleBaoAdapter() }


    var bind: ActivityQiqiRuleBinding? = null

    fun initViewModel() {

        initData(bind!!.rvGuard, adapter3)
        initData(bind!!.rvRule, adapter2)
        initData(bind!!.rvGuide, adapter1)
    }


    private fun initData(rv: RecyclerView, adapter: QiQiRuleBaoAdapter) {
        rv.isFocusable = false
        rv.layoutManager = LinearLayoutManager(fragment?.context)

        rv.adapter = adapter

        adapter.setMyListener { bean, position ->
            var bundle = Bundle()
            bundle.putString("url", bean.url)
            MyApplication.openActivity(activity, WebViewActivity::class.java, bundle)
        }
    }


    fun getRule(json: String): Single<String> = retrofit.getData(json)
        .async().compose(SingleCompose.compose(object : SingleObserverInterface {
            override fun onSuccess(response: String) {
                var list1 = ArrayList<QiQiRuleModel>()
                var list2 = ArrayList<QiQiRuleModel>()
                var list3 = ArrayList<QiQiRuleModel>()

                if (!TextUtils.isEmpty(response)) {
                    var json = JSONObject(response)
                    var datalist = json.getString("dataList")
                    var list = Gson().fromJson<ArrayList<QiQiRuleModel>>(datalist,
                        object : TypeToken<ArrayList<QiQiRuleModel>>() {}.type)

                    for (q in list) {
                        if (q.type.equals("1")) {
                            list1.add(q)
                        }
                        if (q.type.equals("2")) {
                            list2.add(q)
                        }
                        if (q.type.equals("3")) {
                            list3.add(q)
                        }
                    }
                    adapter1.loadMore(list1, 1)
                    adapter2.loadMore(list2, 1)
                    adapter3.loadMore(list3, 1)
                }
            }

        }, activity))


}