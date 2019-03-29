package com.lxkj.qiqihunshe.app.ui.shouye.viewmodel

import com.google.gson.Gson
import com.lxkj.qiqihunshe.app.base.BaseViewModel
import com.lxkj.qiqihunshe.app.retrofitnet.SingleCompose
import com.lxkj.qiqihunshe.app.retrofitnet.SingleObserverInterface
import com.lxkj.qiqihunshe.app.retrofitnet.async
import com.lxkj.qiqihunshe.app.retrofitnet.bindLifeCycle
import com.lxkj.qiqihunshe.app.ui.shouye.model.ShouYeModel
import com.lxkj.qiqihunshe.app.util.StaticUtil
import com.lxkj.qiqihunshe.app.util.ToastUtil

/**
 * Created by Slingge on 2019/2/26
 */
class ShouYe4ViewModel : BaseViewModel() {
    var answer = "0"//0未回答 1已回答
    //获取列表
    fun getPeiList(){
        var params = HashMap<String,String>()
        params["cmd"] = "peiList"
        params["uid"] = StaticUtil.uid
        retrofit.getData(Gson().toJson(params))
            .async()
            .compose(SingleCompose.compose(object : SingleObserverInterface {
                override fun onSuccess(response: String) {
                    val model = Gson().fromJson(response, ShouYeModel::class.java)
                    answer = model.answer;
                }
            }, fragment?.activity)).bindLifeCycle(fragment!!).subscribe({
            }, {
            })
    }


}