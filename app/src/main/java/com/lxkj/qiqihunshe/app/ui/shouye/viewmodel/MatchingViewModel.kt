package com.lxkj.qiqihunshe.app.ui.shouye.viewmodel

import android.databinding.ObservableField
import android.view.View
import com.google.gson.Gson
import com.lxkj.qiqihunshe.app.base.BaseViewModel
import com.lxkj.qiqihunshe.app.retrofitnet.async
import com.lxkj.qiqihunshe.app.ui.shouye.model.ShouYeModel
import com.lxkj.qiqihunshe.app.util.ToastUtil
import com.lxkj.qiqihunshe.app.util.abLog
import com.lxkj.qiqihunshe.databinding.ActivityMatchingBinding
import io.reactivex.Single

/**
 * 匹配
 * Created by Slingge on 2019/2/26
 */
class MatchingViewModel : BaseViewModel() {

    lateinit var bind: ActivityMatchingBinding
    var headerUrl = ObservableField<String>()

    var id = ""
    var username = ""

    /**
     * 1聊 2语 人物匹配
     */
    fun randomUser(): Single<String> {
        bind.ivDefaul.visibility = View.GONE
        abLog.e("匹配", Gson().toJson(activity!!.intent.getSerializableExtra("model")))
        return retrofit.getData(Gson().toJson(activity!!.intent.getSerializableExtra("model")))
            .async().doOnSuccess {
                abLog.e("匹配结果", it)
                val model = Gson().fromJson(it, ShouYeModel::class.java)
                if (model.result == "0") {
                    ToastUtil.showToast("匹配成功！")
                    id = model.userId
                    username = model.nickname
                    bind.ivDefaul.visibility = View.VISIBLE
                    bind.ivIn.visibility = View.VISIBLE
                    bind.ivNext.visibility = View.VISIBLE
                } else {
                    ToastUtil.showTopSnackBar(activity, model.resultNote)
                }
            }
    }

}