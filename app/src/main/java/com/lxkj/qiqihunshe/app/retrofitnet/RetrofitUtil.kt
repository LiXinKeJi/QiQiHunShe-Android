package com.lxkj.qiqihunshe.app.retrofitnet

import com.lxkj.qiqihunshe.app.MyApplication
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Created by Slingge on 2018/11/13
 */
object RetrofitUtil {


    private val url = "http://192.168.3.2:8090/"

    private val RecyclerUrl = "http://gank.io/api/data/"

    private fun getClient(): OkHttpClient {
        val cache = Cache(MyApplication.getInstance().cacheDir, (1024 * 1024).toLong())
        val loggingInterceptor = HttpLoggingInterceptor(HttpLoggingInterceptor.Logger { message -> /*Logger.e(message)*/ })
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return OkHttpClient.Builder()
                .cache(cache)
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .addInterceptor(loggingInterceptor)
                .build()
    }

    fun getRetrofitApi(): Retrofit {
        val retrofit = Retrofit.Builder()
                .baseUrl(RecyclerUrl)
                .client(getClient())
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
        return retrofit
    }


    fun getRetrofitRecyclerApi(url:String): Retrofit {
        val retrofit = Retrofit.Builder()
                .baseUrl(url)
                .client(getClient())
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
        return retrofit
    }

}