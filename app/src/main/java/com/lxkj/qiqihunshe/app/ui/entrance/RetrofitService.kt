package com.lxkj.qiqihunshe.app.ui.entrance

import com.lxkj.qiqihunshe.app.ui.entrance.model.SignInModel
import io.reactivex.Single
import retrofit2.http.POST
import retrofit2.http.Query

/**
 * Created by Slingge on 2019/3/4
 */
interface RetrofitService {


    @POST("77hunshe")
    fun getData(@Query("json") json: String): Single<SignInModel>


}