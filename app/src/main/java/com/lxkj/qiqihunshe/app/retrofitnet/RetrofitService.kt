package com.lxkj.qiqihunshe.app.retrofitnet

import com.lxkj.qiqihunshe.app.base.BaseModel
import io.reactivex.Single
import okhttp3.MultipartBody
import retrofit2.http.*
import java.util.*
import retrofit2.http.PartMap
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.POST
import retrofit2.http.Multipart


/**
 * Created by Slingge on 2019/2/18
 */
interface RetrofitService {

    @POST("service")
    fun getData(@Query("json") json: String): Single<String>

    @POST("uploadFile")
    fun upLoad(@Query("file") file: MultipartBody.Part): Single<String>


    @Multipart
    @POST("uploadFile")
    fun upImg(@Part file: MultipartBody.Part): Call<ResponseBody>

}