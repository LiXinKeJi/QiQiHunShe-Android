package com.lxkj.qiqihunshe.app.ui.mine.viewmodel

import android.view.View
import com.lxkj.qiqihunshe.app.base.BaseViewModel
import com.lxkj.qiqihunshe.app.retrofitnet.async
import com.lxkj.qiqihunshe.app.util.StaticUtil
import com.lxkj.qiqihunshe.app.util.ToastUtil
import com.lxkj.qiqihunshe.databinding.ActivityCheckinBinding
import io.reactivex.Single
import org.json.JSONObject

/**
 * Created by Slingge on 2019/2/21
 */
class CheckInViewModel : BaseViewModel() {

     var bind: ActivityCheckinBinding? = null

    fun checkIn(): Single<String> {
        val json = "{\"cmd\":\"sign\",\"uid\":\"" + StaticUtil.uid + "\"}"
        return retrofit.getData(json).async()
            .doOnSuccess {
                val obj = JSONObject(it)
                if(obj.getString("result")!="0"){
                    ToastUtil.showTopSnackBar(activity,obj.getString("resultNote"))
                }
                when (obj.getString("qty").toInt()) {
                    1 -> {
                        bind!!.iv1.visibility = View.VISIBLE
                    }
                    2 -> {
                        bind!!.iv1.visibility = View.VISIBLE
                        bind!!.iv2.visibility = View.VISIBLE
                    }
                    3 -> {
                        bind!!.iv1.visibility = View.VISIBLE
                        bind!!.iv2.visibility = View.VISIBLE
                        bind!!.iv3.visibility = View.VISIBLE
                    }
                    4 -> {
                        bind!!.iv1.visibility = View.VISIBLE
                        bind!!.iv2.visibility = View.VISIBLE
                        bind!!.iv3.visibility = View.VISIBLE
                        bind!!.iv4.visibility = View.VISIBLE
                    }
                    5 -> {
                        bind!!.iv1.visibility = View.VISIBLE
                        bind!!.iv2.visibility = View.VISIBLE
                        bind!!.iv3.visibility = View.VISIBLE
                        bind!!.iv4.visibility = View.VISIBLE
                        bind!!.iv5.visibility = View.VISIBLE
                    }
                    6 -> {
                        bind!!.iv1.visibility = View.VISIBLE
                        bind!!.iv2.visibility = View.VISIBLE
                        bind!!.iv3.visibility = View.VISIBLE
                        bind!!.iv4.visibility = View.VISIBLE
                        bind!!.iv5.visibility = View.VISIBLE
                        bind!!.iv6.visibility = View.VISIBLE
                    }
                    7 -> {
                        bind!!.iv1.visibility = View.VISIBLE
                        bind!!.iv2.visibility = View.VISIBLE
                        bind!!.iv3.visibility = View.VISIBLE
                        bind!!.iv4.visibility = View.VISIBLE
                        bind!!.iv5.visibility = View.VISIBLE
                        bind!!.iv6.visibility = View.VISIBLE
                        bind!!.iv7.visibility = View.VISIBLE
                    }
                    else -> {
                        bind!!.iv1.visibility = View.VISIBLE
                        bind!!.iv2.visibility = View.VISIBLE
                        bind!!.iv3.visibility = View.VISIBLE
                        bind!!.iv4.visibility = View.VISIBLE
                        bind!!.iv5.visibility = View.VISIBLE
                        bind!!.iv7.visibility = View.VISIBLE
                    }
                }
            }

    }


}