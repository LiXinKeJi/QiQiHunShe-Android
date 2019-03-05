package com.lxkj.qiqihunshe.app.retrofitnet

import io.reactivex.Single
import retrofit2.http.*
import retrofit2.http.POST


/**
 * Created by Slingge on 2019/2/18
 */
interface RetrofitService {

    @POST("service")
    fun getData(@Query("json") json: String): Single<String>




}