package com.lxkj.qiqihunshe.app.ui.mine.viewmodel

import android.view.View
import cc.shinichi.library.bean.ImageInfo
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
import org.json.JSONObject
import java.util.ArrayList

/**
 * Created by Slingge on 2019/2/21
 */
class PersonalInfoViewModel : BaseViewModel() {

    var bind: ActivityPersonalInfoBinding? = null
    var model = PersonalInfoModel()
    var userId = ""//查看人id

    private var type = ""//1喜欢 2不喜欢

    fun initViewModel() {

        bind!!.banner.updateBannerStyle(BannerConfig.NUM_INDICATOR)

        bind?.let {
            if (userId == StaticUtil.uid) {
                it.tvPerfect.visibility = View.GONE
                it.ivEdit.visibility = View.GONE
                it.ll3.visibility = View.GONE
            } else {
                it.tvPerfect.visibility = View.GONE
                it.ivEdit.visibility = View.GONE
            }
            it.banner.setOnBannerListener {
                SeePhotoViewUtil.toPhotoView(activity, model.icon, it)
            }
        }

    }


    fun getUserData(): Single<String> {
        val json = "{\"cmd\":\"userDetail\",\"uid\":\"" + StaticUtil.uid + "\",\"userId\":\"" + userId +
                "\",\"lat\":\"" + StaticUtil.lat + "\",\"lon\":\"" + StaticUtil.lng + "\"}"
        abLog.e("获取个人信息", json)
        return retrofit.getData(json).async().compose(SingleCompose.compose(object : SingleObserverInterface {
            override fun onSuccess(response: String) {
                model = Gson().fromJson(response, PersonalInfoModel::class.java)
                bind!!.model = model
                bind!!.tvFeel.text = "言礼值：" + model.polite

                if (model.icon.isNotEmpty()) {
                    bind!!.banner.setImages(model.icon)
                        .setImageLoader(GlideImageLoader())
                        .start()
                }

                if (model.sex == "0") {//0女 1男
                    bind!!.tvAge.setBackgroundResource(R.drawable.bg_girl)
                    bind!!.tvAge.setTextColor(activity!!.resources.getColor(R.color.girl))
                    AbStrUtil.setDrawableLeft(activity!!, R.drawable.ic_girl, bind!!.tvAge, 3)
                } else {
                    bind!!.tvAge.setBackgroundResource(R.drawable.thems_bg35)
                    bind!!.tvAge.setTextColor(activity!!.resources.getColor(R.color.colorThemes))
                    AbStrUtil.setDrawableLeft(activity!!, R.drawable.ic_boy, bind!!.tvAge, 3)
                }

                if (userId != StaticUtil.uid) {
                    if (model.love == "0") {//是否喜欢 0否 1是
                        type = "2"
                        AbStrUtil.setDrawableTop(activity!!, R.drawable.ic_xin, bind!!.tvFllow, 5)
                        bind!!.tvFllow.text = "不喜欢"
                    } else {
                        type = "1"
                        AbStrUtil.setDrawableTop(activity!!, R.drawable.ic_xin2, bind!!.tvFllow, 5)
                        bind!!.tvFllow.text = "喜欢"
                    }
                } else {
                    bind!!.ll3.visibility = View.GONE
                    bind!!.tvFllow.text = model.loveNum
                }

                bind!!.tvDistance.text = DoubleCalculationUtil.mTokm(model.distance)

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


    //喜欢
    fun floow(): Single<String> {
        var json = ""
        if (type == "1") {//1喜欢 2不喜欢
            json = "{\"cmd\":\"addLove\",\"uid\":\"" + StaticUtil.uid + "\",\"userId\":\"" + userId +
                    "\",\"type\":\"" + "2" + "\"}"
        } else {
            json = "{\"cmd\":\"addLove\",\"uid\":\"" + StaticUtil.uid + "\",\"userId\":\"" + userId +
                    "\",\"type\":\"" + "1" + "\"}"
        }
        return retrofit.getData(json).async().compose(SingleCompose.compose(object : SingleObserverInterface {
            override fun onSuccess(response: String) {
                if (type == "1") {//1喜欢 2不喜欢
                    type = "2"
                    bind!!.tvFllow.text = "不喜欢"
                    AbStrUtil.setDrawableTop(activity!!, R.drawable.ic_xin, bind!!.tvFllow, 5)
                } else {
                    type = "1"
                    bind!!.tvFllow.text = "喜欢"
                    AbStrUtil.setDrawableTop(activity!!, R.drawable.ic_xin2, bind!!.tvFllow, 5)
                }
            }
        }, activity))

    }

    fun isFirend(): Single<String> {
        val json = "{\"cmd\":\"isFriends\",\"uid\":\"" + StaticUtil.uid + "\",\"taid\":\"" + userId + "\"}"
        return retrofit.getData(json).async().compose(SingleCompose.compose(object : SingleObserverInterface {
            override fun onSuccess(response: String) {
                val obj = JSONObject(response)
                if (obj.getString("status") == "0") {
                    bind?.let {
                        it.tvConversation.setBackgroundResource(R.drawable.gray_bg30)
                        it.tvConversation.setOnClickListener(null)
                    }
                }
            }
        }, activity))
    }

}