package com.lxkj.qiqihunshe.app.ui.mine.viewmodel

import android.databinding.ObservableField
import com.lxkj.qiqihunshe.app.base.BaseViewModel
import com.lxkj.qiqihunshe.app.retrofitnet.SingleCompose
import com.lxkj.qiqihunshe.app.retrofitnet.SingleObserverInterface
import com.lxkj.qiqihunshe.app.retrofitnet.async
import com.lxkj.qiqihunshe.app.util.StaticUtil
import io.reactivex.Single
import org.json.JSONObject

/**
 * Created by Slingge on 2019/2/20
 */
class AnswerProblemViewModel : BaseViewModel() {


    val problem = ObservableField<String>()
    val answer = ObservableField<String>()

    fun getproblem(): Single<String> {
        val json = "{\"cmd\":\"getUserQuestion\",\"uid\":\"" + StaticUtil.uid + "\"}"

        return retrofit.getData(json).async().compose(SingleCompose.compose(object : SingleObserverInterface {
            override fun onSuccess(response: String) {
                val obj = JSONObject(response)
                problem.set(obj.getString("title"))
            }
        }, activity))
    }


}