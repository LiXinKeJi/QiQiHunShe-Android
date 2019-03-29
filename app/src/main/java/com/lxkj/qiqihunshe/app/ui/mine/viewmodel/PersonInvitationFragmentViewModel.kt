package com.lxkj.qiqihunshe.app.ui.mine.viewmodel

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.google.gson.Gson
import com.lxkj.qiqihunshe.app.MyApplication
import com.lxkj.qiqihunshe.app.base.BaseViewModel
import com.lxkj.qiqihunshe.app.retrofitnet.SingleCompose
import com.lxkj.qiqihunshe.app.retrofitnet.SingleObserverInterface
import com.lxkj.qiqihunshe.app.retrofitnet.async
import com.lxkj.qiqihunshe.app.ui.mine.activity.MyInvitationDetailsActivity
import com.lxkj.qiqihunshe.app.ui.mine.adapter.PersonInvitationAdapter
import com.lxkj.qiqihunshe.app.ui.mine.model.SpaceInvitationModel
import com.lxkj.qiqihunshe.app.util.StaticUtil
import com.lxkj.qiqihunshe.app.util.ToastUtil
import com.lxkj.qiqihunshe.databinding.FragmentPersonInvitationBinding
import io.reactivex.Single

/**
 * Created by Slingge on 2019/2/21
 */
class PersonInvitationFragmentViewModel : BaseViewModel() {

    var userId = ""

    val adapter by lazy { PersonInvitationAdapter() }

    var bind: FragmentPersonInvitationBinding? = null

    var page = 1
    var totalPage = 1

    fun initViewModel() {
        bind!!.rvDynamic.layoutManager = LinearLayoutManager(fragment?.context)

        bind!!.rvDynamic.adapter = adapter
        adapter.activity = fragment!!.activity
        adapter.setMyListener { itemBean, position ->
            val bundle = Bundle()
            bundle.putString("id", itemBean.yaoyueId)
            bundle.putInt("position", position)
            MyApplication.openActivity(fragment?.context, MyInvitationDetailsActivity::class.java, bundle)
        }
    }


    fun getYaoyue(): Single<String> {
        val json = "{\"cmd\":\"userYaoyue\",\"uid\":\"" + StaticUtil.uid +
                "\",\"category\":\"" + "1" + "\",\"page\":\"" + page + "\"}"
        return retrofit.getData(json).async().compose(SingleCompose.compose(object : SingleObserverInterface {
            override fun onSuccess(response: String) {
                val model = Gson().fromJson(response, SpaceInvitationModel::class.java)

                if (page == 1) {
                    if (model.totalPage == 1) {
                        adapter.flag = 0
                    }
                    adapter.upData(model.dataList)
                } else {
                    if (page == model.totalPage) {
                        adapter.loadMore(model.dataList, 0)
                    } else {
                        adapter.loadMore(model.dataList, -1)
                    }
                }
            }
        }, fragment?.activity))
    }


    fun removeItem(position: Int) {
        adapter.removeItem(position)
    }

    fun jubao(report: String, toInt: Int): Single<String> {
        val json =
            "{\"cmd\":\"yaoyueReport\",\"uid\":\"" + StaticUtil.uid + "\",\"yaoyueId\":\"" + adapter.getList()[toInt].yaoyueId +
                    "\",\"content\":\"" + report + "\"}"

        return retrofit.getData(json).async()
            .compose(SingleCompose.compose(object : SingleObserverInterface {
                override fun onSuccess(response: String) {
                    ToastUtil.showTopSnackBar(fragment!!.activity, "举报成功")
                }
            }, fragment!!.activity))
    }


}