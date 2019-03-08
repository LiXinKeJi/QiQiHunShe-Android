package com.lxkj.qiqihunshe.app.ui.mine.viewmodel

import android.support.v7.widget.GridLayoutManager
import android.view.View
import com.google.gson.Gson
import com.lxkj.qiqihunshe.app.base.BaseViewModel
import com.lxkj.qiqihunshe.app.retrofitnet.SingleCompose
import com.lxkj.qiqihunshe.app.retrofitnet.SingleObserverInterface
import com.lxkj.qiqihunshe.app.retrofitnet.async
import com.lxkj.qiqihunshe.app.ui.mine.adapter.ImageAdapter
import com.lxkj.qiqihunshe.app.ui.mine.model.MyInvitationDetailsModel
import com.lxkj.qiqihunshe.app.util.StaticUtil
import com.lxkj.qiqihunshe.databinding.ActivityPersonInvitationDetailsBinding
import io.reactivex.Single

/**
 * 个人信息邀约详情
 * Created by Slingge on 2019/2/21
 */
class PersonInvitationDetailsViewModel:BaseViewModel(){

    val imageAdapter by lazy { ImageAdapter() }

    var model = MyInvitationDetailsModel()

    var yaoyueId = ""//邀约id

    var bind: ActivityPersonInvitationDetailsBinding?=null


    fun initViewmodel(){
        bind!!.rvImage.isFocusable = false
        bind!!.rvImage.layoutManager = GridLayoutManager(fragment?.context, 3)
        bind!!.rvImage.adapter = imageAdapter
    }

    fun getYaoyueDetails(): Single<String> {

        val json = "{\"cmd\":\"yaoyueDetail\",\"uid\":\"" + StaticUtil.uid + "\",\"yaoyueId\":\"" + yaoyueId + "\"}"

        return retrofit.getData(json).async().compose(SingleCompose.compose(object : SingleObserverInterface {
            override fun onSuccess(response: String) {
                model = Gson().fromJson(response, MyInvitationDetailsModel::class.java)
                bind!!.model = model

                imageAdapter.loadMore(model.image, 1)

            }
        }, activity))
    }




}