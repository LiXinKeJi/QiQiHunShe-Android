package com.lxkj.qiqihunshe.app.retrofitnet

import com.google.gson.GsonBuilder
import com.lxkj.qiqihunshe.app.MyApplication
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.io.File
import java.io.IOException
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL
import java.util.HashMap
import java.util.concurrent.TimeUnit

/**
 * Created by Slingge on 2018/11/13
 */
object RetrofitUtil {


    private val url = "http://192.168.3.2:8090/77hunshe/api/"

    val upLoad = "http://192.168.3.2:8090/77hunshe/api/uploadFile"
    private val providerMap = HashMap<String, NetProvider>()
    private val retrofitMap = HashMap<String, Retrofit>()
    private val clientMap = HashMap<String, OkHttpClient>()



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

    fun getRetrofit(): Retrofit {
        val retrofit = Retrofit.Builder()
            .baseUrl(url)
            .client(getClient())
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
        return retrofit
    }



   /* fun getRetrofit( ): Retrofit {
       *//* var provider = commonProvider
        if (empty(url)) {
            throw IllegalStateException("url can not be null")
        }
        if (retrofitMap[url] != null) {
            return retrofitMap[url]!!
        }

        if (provider == null) {
            provider = providerMap[url]
            if (provider == null) {
                provider = commonProvider
            }
        }
        checkProvider(provider)
*//*
        val gson = GsonBuilder()
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            .create()

        val builder = Retrofit.Builder()
            .baseUrl(url)
//            .client(getClient())
            .client(OkHttpClient())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))

        val retrofit = builder.build()
        retrofitMap.put(url, retrofit)
        providerMap.put(url, commonProvider!!)

        return retrofit
    }

    private fun empty(url: String?): Boolean {
        return url == null || url.isEmpty()
    }

    private fun getClient(): OkHttpClient {
        if (empty(url)) {
            throw IllegalStateException("url can not be null")
        }
        if (clientMap[url] != null) {
            return clientMap[url]!!
        }

        checkProvider(commonProvider)

        val builder = OkHttpClient.Builder()

        builder.connectTimeout(if (commonProvider!!.configConnectTimeoutSecs() != 0L)
            commonProvider!!.configConnectTimeoutSecs()
        else
            connectTimeoutMills, TimeUnit.SECONDS)
        builder.readTimeout(if (commonProvider!!.configReadTimeoutSecs() != 0L)
            commonProvider!!.configReadTimeoutSecs()
        else
            readTimeoutMills, TimeUnit.SECONDS)

        builder.writeTimeout(if (commonProvider!!.configWriteTimeoutSecs() != 0L)
            commonProvider!!.configReadTimeoutSecs()
        else
            readTimeoutMills, TimeUnit.SECONDS)
        val cookieJar = commonProvider!!.configCookie()
        if (cookieJar != null) {
            builder.cookieJar(cookieJar)
        }
        commonProvider!!.configHttps(builder)

        val handler = commonProvider!!.configHandler()
        if (handler != null) {
            builder.addInterceptor(NetInterceptor(handler))
        }

        val interceptors = commonProvider!!.configInterceptors()
        if (!empty(interceptors)) {
            for (interceptor in interceptors!!) {
                builder.addInterceptor(interceptor)
            }
        }

        if (commonProvider!!.configLogEnable()) {
            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            builder.addInterceptor(loggingInterceptor)
        }

        val client = builder.build()
        clientMap.put(url, client)
        providerMap.put(url, commonProvider!!)

        return client
    }*/

    private fun empty(interceptors: Array<Interceptor>?): Boolean {
        return interceptors == null || interceptors.size == 0
    }

    private fun checkProvider(provider: NetProvider?) {
        if (provider == null) {
            throw IllegalStateException("must register provider first")
        }
    }

    fun getRetrofitMap(): Map<String, Retrofit> {
        return retrofitMap
    }

    fun getClientMap(): Map<String, OkHttpClient> {
        return clientMap
    }

    val connectTimeoutMills = 10 * 1000L
    val readTimeoutMills = 10 * 1000L
    var commonProvider: NetProvider? = null
        private set



    fun registerProvider(provider: NetProvider) {
        RetrofitUtil.commonProvider = provider
    }

    fun registerProvider(url: String, provider: NetProvider) {
        providerMap.put(url, provider)
    }

    fun clearCache() {
        retrofitMap.clear()
        clientMap.clear()
    }



}