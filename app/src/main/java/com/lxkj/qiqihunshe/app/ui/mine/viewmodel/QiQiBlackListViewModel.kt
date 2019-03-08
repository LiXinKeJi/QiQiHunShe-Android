package com.lxkj.qiqihunshe.app.ui.mine.viewmodel

import android.support.v7.widget.LinearLayoutManager
import android.util.Log

import com.lxkj.qiqihunshe.app.base.BaseViewModel
import com.lxkj.qiqihunshe.app.retrofitnet.SingleCompose
import com.lxkj.qiqihunshe.app.retrofitnet.async

import com.lxkj.qiqihunshe.app.retrofitnet.SingleObserverInterface
import com.lxkj.qiqihunshe.app.retrofitnet.async
import com.lxkj.qiqihunshe.app.ui.mine.adapter.QiQiBlackListAdapter
import com.lxkj.qiqihunshe.app.ui.mine.model.QiQiBlackListModel

import com.lxkj.qiqihunshe.app.util.ToastUtil
import com.lxkj.qiqihunshe.databinding.ActivityRecyvlerviewBinding

import io.reactivex.Single
import org.json.JSONObject

/**
 *
 * Created by Slingge on 2019/2/26
 */
class QiQiBlackListViewModel : BaseViewModel() {

    private val adapter by lazy { QiQiBlackListAdapter() }

    var bind: ActivityRecyvlerviewBinding? = null
    val TAG="QiQiBlackListActivity"

    fun initViewModel() {
        bind!!.recycler.layoutManager = LinearLayoutManager(fragment?.context)

        bind!!.recycler.adapter = adapter

        val list = ArrayList<QiQiBlackListModel>()
//        for (i in 0 until 5) {
//            val model = QiQiBlackListModel()
//            list.add(model)
//        }
        adapter.upData(list)

    }


    fun getBlackData(json: String): Single<String> = retrofit.getData(json)
        .async().compose(SingleCompose.compose(object : SingleObserverInterface {
            override fun onSuccess(response: String) {
                 Log.i("sss","response------------------>"+response)
                ToastUtil.showTopSnackBar(activity,"获取数据成功")
                var list=ArrayList<QiQiBlackListModel>()
                adapter.loadMore(list,1)
                adapter.notifyDataSetChanged()
            }

        }, activity))





}