package com.lxkj.qiqihunshe.app.ui.mine.viewmodel

import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import com.lxkj.qiqihunshe.app.base.BaseViewModel
import com.lxkj.qiqihunshe.app.retrofitnet.SingleCompose
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
        for (i in 0 until 5) {
            val model = QiQiBlackListModel()
            list.add(model)
        }
        adapter.upData(list)

    }


    fun getBlackList(json: String): Single<String> = retrofit.getData(json)
        .async().compose(SingleCompose.compose(object : SingleObserverInterface {
            override fun onSuccess(response: String) {
                Log.i(TAG,"it------------------>"+response)
                var json= JSONObject(response)
                if(json.getString("result").equals("0")) {
                    ToastUtil.showToast(json.getString("resultNote"))

                }else{
                    ToastUtil.showToast(json.getString("resultNote"))

                }
            }

        }, activity))

}