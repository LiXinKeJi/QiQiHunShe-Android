package com.lxkj.qiqihunshe.app.ui.mine.viewmodel

import com.lxkj.qiqihunshe.app.base.BaseViewModel
import com.lxkj.qiqihunshe.app.retrofitnet.SingleCompose
import com.lxkj.qiqihunshe.app.retrofitnet.SingleObserverInterface
import com.lxkj.qiqihunshe.app.retrofitnet.async
import io.reactivex.Single

/**
 * Created by Slingge on 2019/2/19
 */
class ContactUsViewModel : BaseViewModel() {


    fun getcontactUs(): Single<String> {
        val json = "{\"cmd\":\"contactUs\"" + "}"
        return retrofit.getData(json).async().compose(SingleCompose.compose(object :SingleObserverInterface{
            override fun onSuccess(response: String) {

            }
        },activity))
    }


}