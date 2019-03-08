package com.lxkj.qiqihunshe.app.ui.shouye.viewmodel

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.google.gson.Gson
import com.linyuzai.likeornot.BaseAdapter
import com.linyuzai.likeornot.LikeOrNotView
import com.lxkj.qiqihunshe.R
import com.lxkj.qiqihunshe.app.base.BaseViewModel
import com.lxkj.qiqihunshe.app.retrofitnet.SingleCompose
import com.lxkj.qiqihunshe.app.retrofitnet.SingleObserverInterface
import com.lxkj.qiqihunshe.app.retrofitnet.async
import com.lxkj.qiqihunshe.app.ui.shouye.adapter.HuaAdapter
import com.lxkj.qiqihunshe.app.ui.shouye.model.DataListModel
import com.lxkj.qiqihunshe.app.ui.shouye.model.ShouYeModel
import com.lxkj.qiqihunshe.app.util.StaticUtil
import com.lxkj.qiqihunshe.app.util.ToastUtil
import com.lxkj.qiqihunshe.databinding.ActivityStrokeBinding
import java.util.HashMap

/**
 * Created by Slingge on 2019/2/26
 */
class StrokeViewModel : BaseViewModel() {
    var bind: ActivityStrokeBinding? = null
    var adapter: HuaAdapter? = null
    var list = ArrayList<DataListModel>()
    var page = 1
    var totalPage = 1


    /**
     * 获取列表数据
     */
    fun getList() {
        var params = HashMap<String, String>()
        params["cmd"] = "hua"
        params["uid"] = StaticUtil.uid
        params["page"] = page.toString()
        retrofit.getData(Gson().toJson(params))
            .async()
            .compose(SingleCompose.compose(object : SingleObserverInterface {
                override fun onSuccess(response: String) {
                    var model = Gson().fromJson(response, ShouYeModel::class.java)
                    list.addAll(model.dataList)
                    adapter = HuaAdapter(activity, list)
                    bind!!.likeOrNot.adapter = adapter
                }
            }, activity)).subscribe({
            }, {
            })
    }

    /**
     * 喜欢/不喜欢
     */
    fun addLove(position: Int,type : String) {
        var params = HashMap<String, String>()
        params["cmd"] = "addLove"
        params["uid"] = StaticUtil.uid
        params["userId"] = list[position].userId
        params["type"] = type
        retrofit.getData(Gson().toJson(params))
            .async().doOnSuccess {  }.doOnError {  }
            .subscribe({
            }, {
            })
    }


}