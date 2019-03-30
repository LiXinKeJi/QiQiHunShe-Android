package com.lxkj.qiqihunshe.app.ui.mine.viewmodel

import android.content.Intent
import android.net.Uri
import android.view.View
import com.google.gson.Gson
import com.lxkj.qiqihunshe.R
import com.lxkj.qiqihunshe.app.base.BaseViewModel
import com.lxkj.qiqihunshe.app.retrofitnet.SingleCompose
import com.lxkj.qiqihunshe.app.retrofitnet.SingleObserverInterface
import com.lxkj.qiqihunshe.app.retrofitnet.async
import com.lxkj.qiqihunshe.app.ui.mine.model.QiQiDynamicDetailsModel
import com.lxkj.qiqihunshe.app.util.StaticUtil
import com.lxkj.qiqihunshe.app.util.ToastUtil
import com.lxkj.qiqihunshe.app.util.abLog
import com.lxkj.qiqihunshe.databinding.ActivityQiqiDynamicDetailsBinding
import io.reactivex.Single

/**
 * Created by Slingge on 2019/2/21
 */
class QiQiDynamicDetailsViewModel : BaseViewModel() {

    var id = ""
    var bind: ActivityQiqiDynamicDetailsBinding? = null

    var model = QiQiDynamicDetailsModel()

    fun getDate(): Single<String> {
        val json = "{\"cmd\":\"activityDetail\",\"uid\":\"" + StaticUtil.uid + "\",\"activityId\":\"" + id + "\"}"
        abLog.e("动态",json)
        return retrofit.getData(json).async()
            .compose(SingleCompose.compose(object : SingleObserverInterface {
                override fun onSuccess(response: String) {
                    model = Gson().fromJson(response, QiQiDynamicDetailsModel::class.java)
                    bind?.let {
                        it.model = model
                        it.myWebView.webView.loadUrl(model.url)

                        if (model.status == "0") {// 状态 0未报名 1待审核 2已通过 3已拒绝
                            it.rlCall.visibility = View.GONE
                        } else if (model.status == "1") {
                            it.ivState.setImageResource(R.drawable.daishenhe)
                            it.rlCall.visibility = View.VISIBLE
                        } else if (model.status == "2") {
                            it.ivState.setImageResource(R.drawable.yishenhe)
                            it.rlCall.visibility = View.VISIBLE
                        } else {
                            it.ivState.setImageResource(R.drawable.weitongguo)
                            it.rlCall.visibility = View.VISIBLE
                        }
                    }
                }
            }, activity))
    }


    //报名、取消报名,type 0报名 1取消
    fun signUp(type: String): Single<String> {

        val json = "{\"cmd\":\"editActivity\",\"uid\":\"" + StaticUtil.uid + "\",\"activityId\":\"" + id +
                "\",\"type\":\"" + type + "\"}"

        return retrofit.getData(json).async().compose(SingleCompose.compose(object : SingleObserverInterface {
            override fun onSuccess(response: String) {
                if (type == "0") {
                    ToastUtil.showToast("报名成功，请等待审核")
                } else {
                    ToastUtil.showToast("取消报名成功")
                }
                activity!!.finish()
            }
        }, activity))
    }


    fun toCallPhone() {
        val intent = Intent(Intent.ACTION_DIAL)
        val data = Uri.parse("tel:" + model.phone)
        intent.data = data
        activity!!.startActivity(intent)
    }


}