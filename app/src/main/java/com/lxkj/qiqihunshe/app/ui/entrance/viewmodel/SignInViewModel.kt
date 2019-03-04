package com.lxkj.qiqihunshe.app.ui.entrance.viewmodel

import android.databinding.ObservableField
import com.lxkj.qiqihunshe.app.base.BaseModel
import com.lxkj.qiqihunshe.app.base.BaseViewModel
import com.lxkj.qiqihunshe.app.retrofitnet.RetrofitService
import com.lxkj.qiqihunshe.app.retrofitnet.SingleCompose
import com.lxkj.qiqihunshe.app.retrofitnet.SingleObserverInterface
import com.lxkj.qiqihunshe.app.retrofitnet.async
import com.lxkj.qiqihunshe.app.ui.entrance.model.SignInModel
import com.lxkj.qiqihunshe.databinding.ActivitySigninBinding
import io.reactivex.Single

/**
 * Created by Slingge on 2019/2/16
 */
class SignInViewModel(val retrofit: RetrofitService) : BaseViewModel() {

    val headerUrl = ObservableField<String>()

    var bind: ActivitySigninBinding? = null


    fun sginIn(json: String): Single<String> =
        retrofit.getArticleDetail()
            .async()
            .compose(SingleCompose.compose(object : SingleObserverInterface<SignInModel> {
                override fun onSuccess(response: SignInModel) {

                }
            }, activity!!))


}