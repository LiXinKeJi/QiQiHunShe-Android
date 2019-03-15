package com.lxkj.qiqihunshe.app.ui.mine.viewmodel

import android.text.TextUtils
import android.view.View
import com.google.gson.Gson
import com.lxkj.qiqihunshe.R
import com.lxkj.qiqihunshe.app.base.BaseViewModel
import com.lxkj.qiqihunshe.app.retrofitnet.SingleCompose
import com.lxkj.qiqihunshe.app.retrofitnet.SingleObserverInterface
import com.lxkj.qiqihunshe.app.retrofitnet.async
import com.lxkj.qiqihunshe.app.ui.mine.model.PersonalInfoModel
import com.lxkj.qiqihunshe.app.util.*
import com.lxkj.qiqihunshe.databinding.ActivityPersonalInfoBinding
import com.youth.banner.BannerConfig
import io.reactivex.Single
import kotlinx.android.synthetic.main.activity_matching.view.*
import kotlinx.android.synthetic.main.activity_personal_info.view.*
import kotlinx.android.synthetic.main.spinner_tv.view.*
import java.util.*

/**
 * Created by Slingge on 2019/2/21
 */
class PersonalInfoViewModel : BaseViewModel() {

    var bind: ActivityPersonalInfoBinding? = null

    var userId = ""//查看人id

    fun initViewModel() {

        bind!!.banner.updateBannerStyle(BannerConfig.NUM_INDICATOR)

        bind?.let {
            if (userId == StaticUtil.uid) {
                it.tvPerfect.visibility = View.GONE
                it.ivEdit.visibility = View.GONE
                it.ll3.visibility = View.GONE
            } else {
                it. tvPerfect.visibility = View.GONE
                it. ivEdit.visibility = View.GONE
            }
        }
    }


    fun getUserData(): Single<String> {
        val json = "{\"cmd\":\"userDetail\",\"uid\":\"" + StaticUtil.uid + "\",\"userId\":\"" + userId +
                "\",\"lat\":\"" + StaticUtil.lat + "\",\"lon\":\"" + StaticUtil.lng + "\"}"
        abLog.e("获取个人信息", json)
        return retrofit.getData(json).async().compose(SingleCompose.compose(object : SingleObserverInterface {
            override fun onSuccess(response: String) {
                val model = Gson().fromJson(response, PersonalInfoModel::class.java)
                bind!!.model = model
                bind!!.tvFeel.text = "言礼值：" + model.polite

                if (model.icon.isNotEmpty()) {
                    bind!!.banner.setImages(model.icon)
                        .setImageLoader(GlideImageLoader())
                        .start()
                }
                if (userId != StaticUtil.uid) {
                    if (model.love == "0") {//是否喜欢 0否 1是
                        AbStrUtil.setDrawableTop(activity!!, R.drawable.ic_xin, bind!!.tvFllow, 5)
                    } else {
                        AbStrUtil.setDrawableTop(activity!!, R.drawable.ic_xin2, bind!!.tvFllow, 5)
                    }
                } else {
                    bind!!.ll3.visibility = View.GONE
                    bind!!.tvFllow.text = "0"
                }


                if (model.identity == "1") {
                    bind!!.iv.setImageResource(R.drawable.danshen)
                } else if (model.identity == "2") {
                    bind!!.iv.setImageResource(R.drawable.yuehui)
                } else if (model.identity == "3") {
                    bind!!.iv.setImageResource(R.drawable.qianshou)
                }

            }
        }, activity))
    }

}