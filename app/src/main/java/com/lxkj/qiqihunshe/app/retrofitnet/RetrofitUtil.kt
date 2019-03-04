package com.lxkj.qiqihunshe.app.retrofitnet

import com.lxkj.qiqihunshe.app.MyApplication
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.io.File
import java.util.HashMap
import java.util.concurrent.TimeUnit

/**
 * Created by Slingge on 2018/11/13
 */
object RetrofitUtil {


    private val url = "http://192.168.3.2:8090/77hunshe/api/"

    val upLoad = "http://192.168.3.2:8090/77hunshe/api/uploadFile"

    private val providerMap = HashMap<String, NetProvider>()

    private var provider: NetProvider? = null

    fun getClient(): OkHttpClient {

        val cache = Cache(MyApplication.getInstance().cacheDir, (1024 * 1024).toLong())
        val loggingInterceptor =
            HttpLoggingInterceptor(HttpLoggingInterceptor.Logger { message ->
                /*Logger.e(message)*/
            })
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return OkHttpClient.Builder()
            .cache(cache)
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
           /* .addInterceptor(loggingInterceptor)
            .addInterceptor(object : Interceptor {
                override fun intercept(chain: Interceptor.Chain): Response {
                    val request = chain.request()
                    val proceed = chain.proceed(request)
                    return proceed
                }
            })*/
//            .addInterceptor(NetInterceptor(RequestHandler()))
            .build()
    }

    fun getRetrofitApi(): Retrofit {
        val retrofit = Retrofit.Builder()
            .baseUrl(url)
            .client(getClient())
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
        return retrofit
    }


    fun getRetrofitRecyclerApi(url: String): Retrofit {
        val retrofit = Retrofit.Builder()
            .baseUrl(url)
            .client(getClient())
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
        return retrofit
    }


    fun prepareFilePart(path: String): MultipartBody.Part {
        val file = File(path)

        // 为file建立RequestBody实例
        val requestFile = RequestBody.create(
            MediaType.parse("image/png"), file
        )


        // MultipartBody.Part借助文件名完成最终的上传
        return MultipartBody.Part.createFormData("file", file.name, requestFile)
    }
}