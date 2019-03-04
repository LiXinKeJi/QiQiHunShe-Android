package com.lxkj.qiqihunshe.app.util

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Handler
import android.os.Message
import com.lxkj.qiqihunshe.app.interf.UpLoadFileCallBack
import com.lxkj.qiqihunshe.app.retrofitnet.*
import okhttp3.*
import java.io.File
import java.util.concurrent.TimeUnit
import okhttp3.RequestBody
import okhttp3.MultipartBody
import org.json.JSONObject
import java.io.IOException


/**
 * Created by Slingge on 2019/3/4
 */
class UpFileUtil(val activity: Activity, val loadFileCallBack: UpLoadFileCallBack) {


    private var list: ArrayList<String>? = null

    private val urlList by lazy { ArrayList<String>() }

    private var count = 0

    fun setListPath(list: ArrayList<String>) {
        this.list = list
        this.list?.let {
            for (i in 0 until it.size) {
                count = i
                upLoad(it[i])
            }

        }
    }


    private var mOkHttpClient: OkHttpClient? = null
    private var body: RequestBody? = null
    fun upLoad(path: String) {

        if (count == 0 && urlList.isNotEmpty()) {
            urlList.clear()
        }

        val file = File(path)
        if (mOkHttpClient == null) {
            mOkHttpClient = RetrofitUtil.getClient()
        }

        val requestBody = MultipartBody.Builder().setType(MultipartBody.FORM)


        if (body == null) {
            body = RequestBody.create(MediaType.parse("image/*"), file)
        }

        requestBody.addFormDataPart("file", file.name, body)

        val request = Request.Builder().url(RetrofitUtil.upLoad).post(requestBody.build()).build()

        mOkHttpClient!!.newBuilder().readTimeout(60000, TimeUnit.MILLISECONDS).build().newCall(request).enqueue(
            object : okhttp3.Callback {
                override fun onFailure(call: okhttp3.Call, e: IOException) {
                    ThreadUtil.runOnMainThread(Runnable {
                        ToastUtil.showTopSnackBar(activity, e.message!!)
                    })
                }

                override fun onResponse(call: okhttp3.Call, response: okhttp3.Response) {
                    response.body()?.let {
                        abLog.e("上传图片", it.toString())
                        val obj = JSONObject(it.string())
                        if (obj.getString("result") == "0") {
                            if (list == null || list!!.isEmpty()) {
                                loadFileCallBack.uoLoad(obj.getString("url"))
                            } else {
                                val message = Message()
                                message.obj = obj.getString("url")
                                handler.sendMessage(message)
                            }
                        } else {
                            ToastUtil.showToast(obj.getString("resultNote"))
                        }
                    }
                }
            }
        )
    }


    private val handler = @SuppressLint("HandlerLeak")
    object : Handler() {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)

            urlList.add(msg.obj.toString())

            if(urlList.size==list!!.size){
                loadFileCallBack.uoLoad(urlList)
                urlList.clear()
            }
        }
    }


}