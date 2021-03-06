package com.lxkj.qiqihunshe.app.ui.mine.viewmodel

import android.support.v7.widget.GridLayoutManager
import com.google.gson.Gson
import com.lxkj.qiqihunshe.R
import com.lxkj.qiqihunshe.app.base.BaseViewModel
import com.lxkj.qiqihunshe.app.retrofitnet.SingleCompose
import com.lxkj.qiqihunshe.app.retrofitnet.SingleObserverInterface
import com.lxkj.qiqihunshe.app.retrofitnet.async
import com.lxkj.qiqihunshe.app.ui.dialog.DynamicSignUpAfterDialog
import com.lxkj.qiqihunshe.app.ui.mine.adapter.ImageAdapter
import com.lxkj.qiqihunshe.app.ui.mine.model.MyInvitationDetailsModel
import com.lxkj.qiqihunshe.app.util.AbStrUtil
import com.lxkj.qiqihunshe.app.util.SeePhotoViewUtil
import com.lxkj.qiqihunshe.app.util.StaticUtil
import com.lxkj.qiqihunshe.app.util.ToastUtil
import com.lxkj.qiqihunshe.databinding.ActivityPersonInvitationDetailsBinding
import io.reactivex.Single
import kotlinx.android.synthetic.main.activity_mydynamic.*

/**
 * 个人信息邀约详情
 * Created by Slingge on 2019/2/21
 */
class PersonInvitationDetailsViewModel : BaseViewModel() {

    val imageAdapter by lazy { ImageAdapter() }

    var model = MyInvitationDetailsModel()

    var yaoyueId = ""//邀约id

    var bind: ActivityPersonInvitationDetailsBinding? = null


    fun initViewmodel() {
        bind!!.rvImage.isFocusable = false
        bind!!.rvImage.layoutManager = GridLayoutManager(fragment?.context, 3)
        bind!!.rvImage.adapter = imageAdapter

        imageAdapter.setMyListener { itemBean, position ->
            SeePhotoViewUtil.toPhotoView(activity,model.image,position)
        }
    }

    fun getYaoyueDetails(): Single<String> {

        val json = "{\"cmd\":\"yaoyueDetail\",\"uid\":\"" + StaticUtil.uid + "\",\"yaoyueId\":\"" + yaoyueId + "\"}"

        return retrofit.getData(json).async().compose(SingleCompose.compose(object : SingleObserverInterface {
            override fun onSuccess(response: String) {
                model = Gson().fromJson(response, MyInvitationDetailsModel::class.java)
                bind?.let {
                    bind!!.model = model
                    if (model.sexOnly == "0") {
                        it.tvLimit.text = "仅限：女"
                    } else if (model.sexOnly == "1") {
                        it.tvLimit.text = "仅限：男"
                    } else {
                        it.tvLimit.text = "仅限：不限性别"
                    }

                    if (model.fee == "0") {//0AA 1对方买单 2我买单
                        it.tvConsu.text = "消费：AA"
                    } else if (model.fee == "1") {
                        it.tvConsu.text = "消费：对方买单"
                    } else {
                        it.tvConsu.text = "消费：我买单"
                    }

                    if (model.identity == "1") {//1单身 2约会 3牵手
                        it.ivState.setImageResource(R.drawable.danshen)
                    } else if (model.identity == "2") {
                        it.ivState.setImageResource(R.drawable.yuehui)
                    } else {
                        it.ivState.setImageResource(R.drawable.qianshou)
                    }

                    if (model.sex == "0") {//女
                        it.tvAge.setBackgroundResource(R.drawable.bg_girl)
                        AbStrUtil.setDrawableLeft(activity!!,R.drawable.ic_girl,it.tvAge,3)
                        it.tvAge.setTextColor(activity!!.resources.getColor(R.color.girl))
                    }else{
                        it.tvAge.setBackgroundResource(R.drawable.thems_bg35)
                        AbStrUtil.setDrawableLeft(activity!!,R.drawable.ic_boy,it.tvAge,3)
                        it.tvAge.setTextColor(activity!!.resources.getColor(R.color.colorThemes))
                    }

                }

                imageAdapter.loadMore(model.image, 1)

            }
        }, activity))
    }


    //举报邀约
    fun yaoyueReport(Report: String): Single<String> {
        val json = "{\"cmd\":\"yaoyueReport\",\"uid\":\"" + StaticUtil.uid + "\",\"yaoyueId\":\"" + yaoyueId +
                "\",\"content\":\"" + Report + "\"}"
        return retrofit.getData(json).async()
            .compose(SingleCompose.compose(object : SingleObserverInterface {
                override fun onSuccess(response: String) {
                    ToastUtil.showTopSnackBar(activity, "举报成功")
                }
            }, activity))
    }


    //报名
    fun singUp(): Single<String> {
        val json = "{\"cmd\":\"joinYaoyue\",\"uid\":\"" + StaticUtil.uid + "\",\"yaoyueId\":\"" + yaoyueId +
                "\"}"
        return retrofit.getData(json).async().compose(SingleCompose.compose(object : SingleObserverInterface {
            override fun onSuccess(response: String) {
                DynamicSignUpAfterDialog.sginUpShow(activity!!, object : DynamicSignUpAfterDialog.SignUpCallBack {
                    override fun signFinish() {
                        activity!!.finish()
                    }
                })
            }
        }, activity))
    }

}