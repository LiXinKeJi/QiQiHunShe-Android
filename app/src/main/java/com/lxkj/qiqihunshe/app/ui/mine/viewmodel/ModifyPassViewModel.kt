package com.lxkj.qiqihunshe.app.ui.mine.viewmodel

import android.util.Log
import com.lxkj.qiqihunshe.app.base.BaseViewModel
import com.lxkj.qiqihunshe.app.retrofitnet.SingleCompose
import com.lxkj.qiqihunshe.app.retrofitnet.SingleObserverInterface
import com.lxkj.qiqihunshe.app.retrofitnet.async
import com.lxkj.qiqihunshe.app.util.ToastUtil
import io.reactivex.Single

/**
 * Created by Slingge on 2019/2/20
 */
class ModifyPassViewModel:BaseViewModel() {


    private var flag = 0//1支付密码，2登录密码


    fun modifypwd(json: String): Single<String> = retrofit.getData(json)
        .async().compose(SingleCompose.compose(object : SingleObserverInterface {
            override fun onSuccess(response: String) {
                Log.i("sss","response------------------>"+response)
                ToastUtil.showToast(response)
               // activity?.finish()
            }

        }, activity))


}