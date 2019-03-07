package com.lxkj.qiqihunshe.app.ui.mine.viewmodel

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
import com.lxkj.qiqihunshe.app.ui.mine.adapter.QiQiDynamicSignUpRecordAdapter
import com.lxkj.qiqihunshe.app.ui.mine.model.QiQiDynamicModel
import com.lxkj.qiqihunshe.databinding.ActivitySignupRecordBinding
import io.reactivex.Single
import org.json.JSONObject

/**
 * Created by Slingge on 2019/2/22
 */
class SignUpRecordViewModel:BaseViewModel(){



    private val adapter by lazy { QiQiDynamicSignUpRecordAdapter() }

    var bind: ActivitySignupRecordBinding? = null

    fun initViewModel() {
        bind!!.rvRecord.isFocusable = false
        bind!!.rvRecord.layoutManager = LinearLayoutManager(fragment?.context)

        bind!!.rvRecord.adapter = adapter

        val list = ArrayList<QiQiDynamicModel>()
//        for (i in 0 until 5) {
//            val model = QiQiDynamicModel()
//            list.add(model)
//        }
        adapter.upData(list)

        adapter.setMyListener { itemBean, position ->
           MyApplication.openActivity(activity, QiQiDynamicDetailsActivity::class.java)
        }
    }


    fun getData(json: String): Single<String> = retrofit.getData(json)
        .async().compose(SingleCompose.compose(object : SingleObserverInterface {
            override fun onSuccess(response: String) {
                Log.i("sss","response-------------->"+response)
//                var json= JSONObject(response)
//                var list= Gson().fromJson<ArrayList<QiQiDynamicModel>>(json.getString("dataList"),object:
//                    TypeToken<ArrayList<QiQiDynamicModel>>(){}.type)
                val list = ArrayList<QiQiDynamicModel>()

                adapter.loadMore(list,1)
                adapter.notifyDataSetChanged()

            }

        }, activity))


}