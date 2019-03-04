package com.lxkj.qiqihunshe.app.retrofitnet

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query


/**
 * Created by Slingge on 2019/2/18
 */
interface RetrofitService {

    @POST("77hunshe")
    fun getData(@Query("json") json: String): Single<String>

    @GET("福利/10/1")
    fun  getArticleDetail(): Single<String>
}