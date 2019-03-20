package com.lxkj.qiqihunshe.app.ui.shouye.viewmodel

import android.databinding.ObservableField
import com.google.gson.Gson
import com.lxkj.qiqihunshe.app.base.BaseViewModel
import com.lxkj.qiqihunshe.app.retrofitnet.SingleCompose
import com.lxkj.qiqihunshe.app.retrofitnet.SingleObserverInterface
import com.lxkj.qiqihunshe.app.retrofitnet.async
import com.lxkj.qiqihunshe.app.ui.shouye.model.ShouYeModel
import com.lxkj.qiqihunshe.app.util.ToastUtil
import com.lxkj.qiqihunshe.app.util.abLog
import io.reactivex.Single

/**
 * 匹配
 * Created by Slingge on 2019/2/26
 */
class MatchingViewModel : BaseViewModel() {

    var type = "1"

    var headerUrl=ObservableField<String>()

    /**
     * 1聊 2语 人物匹配
     */
    fun randomUser(): Single<String> {
        abLog.e("匹配", Gson().toJson(activity!!.intent.getSerializableExtra("model")))
        return retrofit.getData(Gson().toJson(activity!!.intent.getSerializableExtra("model")))
            .async().compose(SingleCompose.compose(object : SingleObserverInterface {
                override fun onSuccess(response: String) {
                    val model = Gson().fromJson(response, ShouYeModel::class.java)
                    if (model.result == "0") {
                        ToastUtil.showTopSnackBar(activity, "匹配成功！" + model.userId)
                    } else {
                        ToastUtil.showTopSnackBar(activity, model.resultNote)
                    }
                }
            }, activity))
    }

}