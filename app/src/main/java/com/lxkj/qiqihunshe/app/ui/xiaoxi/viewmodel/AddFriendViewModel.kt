package com.lxkj.qiqihunshe.app.ui.xiaoxi.viewmodel

import android.view.View
import com.google.gson.Gson
import com.lxkj.qiqihunshe.R
import com.lxkj.qiqihunshe.app.base.BaseViewModel
import com.lxkj.qiqihunshe.app.retrofitnet.SingleCompose
import com.lxkj.qiqihunshe.app.retrofitnet.SingleObserverInterface
import com.lxkj.qiqihunshe.app.retrofitnet.async
import com.lxkj.qiqihunshe.app.ui.xiaoxi.model.DataListModel
import com.lxkj.qiqihunshe.app.ui.xiaoxi.model.XxModel
import com.lxkj.qiqihunshe.app.util.GlideUtil
import com.lxkj.qiqihunshe.app.util.StaticUtil
import com.lxkj.qiqihunshe.app.util.ToastUtil
import com.lxkj.qiqihunshe.databinding.ActivityAddfriendBinding

/**
 * Created by Slingge on 2019/3/1
 */
class AddFriendViewModel : BaseViewModel() {
    var bind: ActivityAddfriendBinding? = null

    var params = HashMap<String, String>()

    //精确查找
    fun addFriend(userId: String, content: String) {
        params["cmd"] = "addFriend"
        params["uid"] = StaticUtil.uid
        params["userId"] = userId
        params["content"] = content
        retrofit.getData(Gson().toJson(params))
            .async()
            .compose(SingleCompose.compose(object : SingleObserverInterface {
                override fun onSuccess(response: String) {
                    val model = Gson().fromJson(response, XxModel::class.java)
                    ToastUtil.showTopSnackBar(activity, "添加成功！")
                    activity?.finish()
                }
            }, activity)).subscribe({
            }, {
            })
    }
}