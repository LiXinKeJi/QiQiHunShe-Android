package com.lxkj.qiqihunshe.app.ui.mine.viewmodel

import android.os.Bundle
import android.support.v4.app.FragmentManager
import com.google.gson.Gson
import com.lxkj.qiqihunshe.R
import com.lxkj.qiqihunshe.app.base.BaseViewModel
import com.lxkj.qiqihunshe.app.retrofitnet.SingleCompose
import com.lxkj.qiqihunshe.app.retrofitnet.SingleObserverInterface
import com.lxkj.qiqihunshe.app.retrofitnet.async
import com.lxkj.qiqihunshe.app.ui.fujin.fragment.SkillFragment
import com.lxkj.qiqihunshe.app.ui.fujin.model.DataListModel
import com.lxkj.qiqihunshe.app.util.StaticUtil
import com.lxkj.qiqihunshe.app.util.abLog
import io.reactivex.Single

/**
 * Created by Slingge on 2019/3/7
 */
class MySkillDetailsViewModel : BaseViewModel() {

    var fragmentManager: FragmentManager? = null

    fun getSkill(): Single<String> {
        val json =
            "{\"cmd\":\"caiyiDetail\",\"uid\":\"" + StaticUtil.uid + "\",\"caiyiId\":\"" + activity!!.intent.getStringExtra(
                "id"
            ) + "\"}"
        abLog.e("才艺id", json)
        return retrofit.getData(json).async().compose(SingleCompose.compose(object : SingleObserverInterface {
            override fun onSuccess(response: String) {
                val model = Gson().fromJson(response, DataListModel::class.java)
                val bundle = Bundle()
                bundle.putSerializable("model", model)
                bundle.putInt("flag",0)
                skillFragment.arguments = bundle
                switchFragment()
            }
        }, activity))
    }

    val skillFragment by lazy { SkillFragment() }
    private fun switchFragment() {
        val transaction = fragmentManager!!.beginTransaction()
        transaction.add(R.id.containers, skillFragment).commit() // 隐藏当前的fragment，add下一个到Activity中
    }

}