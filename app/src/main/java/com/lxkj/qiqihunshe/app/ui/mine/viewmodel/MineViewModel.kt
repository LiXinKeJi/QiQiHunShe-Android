package com.lxkj.qiqihunshe.app.ui.mine.viewmodel

import com.lxkj.qiqihunshe.app.base.BaseViewModel
import com.lxkj.qiqihunshe.databinding.FragmentMineBinding
import com.google.gson.Gson
import com.lxkj.qiqihunshe.R
import com.lxkj.qiqihunshe.app.MyApplication
import com.lxkj.qiqihunshe.app.retrofitnet.SingleCompose
import com.lxkj.qiqihunshe.app.retrofitnet.SingleObserverInterface
import com.lxkj.qiqihunshe.app.retrofitnet.async
import com.lxkj.qiqihunshe.app.retrofitnet.bindLifeCycle
import com.lxkj.qiqihunshe.app.ui.dialog.CategoryPop
import com.lxkj.qiqihunshe.app.ui.mine.model.MineModel
import com.lxkj.qiqihunshe.app.util.SharePrefUtil
import com.lxkj.qiqihunshe.app.util.SharedPreferencesUtil
import com.lxkj.qiqihunshe.app.util.StaticUtil
import com.lxkj.qiqihunshe.app.util.abLog
import io.reactivex.Single
import java.util.*


/**
 * Created by Slingge on 2019/2/16
 */
class MineViewModel : BaseViewModel(), CategoryPop.Categoryinterface {


    var bind: FragmentMineBinding? = null

    var auth = ""

    fun getMine(): Single<String> {
        val json = "{\"cmd\":\"userInfo\",\"uid\":\"" + StaticUtil.uid + "\"}"
        return retrofit.getData(json).async()
            .doOnSuccess {
                val model = Gson().fromJson(it, MineModel::class.java)
                bind?.let {
                    it.model = model
                    it.pbReputation.progress = (model.credit.toDouble() * 100).toInt()
                    it.pbFeel.progress = (model.polite.toDouble() * 100).toInt()

                    if (model.identity == "1") {
                        it.tvState.text = "单身"
                    } else if (model.identity == "2") {
                        it.tvState.text = "约会"
                    } else if (model.identity == "3") {
                        it.tvState.text = "牵手"
                    }
                    StaticUtil.headerUrl = model.icon
                    StaticUtil.nickName = model.nickname
                    SharedPreferencesUtil.putSharePre(fragment!!.activity, "userIcon", model.icon)
                    SharedPreferencesUtil.putSharePre(fragment!!.activity, "nickName", model.nickname)
                    MyApplication.setRedNum(it.tvMsgNum1, model.xiaoqi.toInt())
                    MyApplication.setRedNum(it.tvMsgNum2, model.interact.toInt())
                }

                StaticUtil.isReal=model.auth

                StaticUtil.headerUrl = model.icon
                StaticUtil.nickName = model.nickname
                StaticUtil.age = model.age
            }
    }


    fun motifyState(identity: Int) {
        val json = "{\"cmd\":\"editIdentity\",\"uid\":\"" + StaticUtil.uid + "\",\"identity\":\"" + identity + "\"}"
        abLog.e("修改状态", json)
        retrofit.getData(json).async()
            .compose(SingleCompose.compose(object : SingleObserverInterface {
                override fun onSuccess(response: String) {
                    bind!!.tvState.text = list[identity]
                }
            }, fragment?.activity)).bindLifeCycle(fragment!!).subscribe({}, { toastFailure(it) })
    }


    private var categoryPop: CategoryPop? = null
    private val list by lazy { ArrayList<String>() }
    //选择状态
    fun selectState() {
        if (list.isEmpty()) {
            list.addAll(fragment!!.context!!.resources.getStringArray(R.array.state))
        }
        if (categoryPop == null) {
            categoryPop = CategoryPop(fragment!!.context!!)
            categoryPop?.setCatinterface(this)
        }
        categoryPop!!.screenPopwindow(list, bind!!.tvState)
    }

    // 选中的状态
    override fun category(position: Int) {
        motifyState(position)
    }


}