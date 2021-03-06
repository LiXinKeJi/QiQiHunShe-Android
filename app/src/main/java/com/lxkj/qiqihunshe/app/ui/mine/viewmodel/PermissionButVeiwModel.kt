package com.lxkj.qiqihunshe.app.ui.mine.viewmodel

import android.annotation.SuppressLint
import android.os.Bundle
import com.google.gson.Gson
import com.lxkj.qiqihunshe.app.MyApplication
import com.lxkj.qiqihunshe.app.base.BaseViewModel
import com.lxkj.qiqihunshe.app.interf.ShadowTransformer
import com.lxkj.qiqihunshe.app.retrofitnet.SingleCompose
import com.lxkj.qiqihunshe.app.retrofitnet.SingleObserverInterface
import com.lxkj.qiqihunshe.app.retrofitnet.async
import com.lxkj.qiqihunshe.app.retrofitnet.bindLifeCycle
import com.lxkj.qiqihunshe.app.ui.mine.activity.DemandListActivity4
import com.lxkj.qiqihunshe.app.ui.mine.activity.DemandListActivity5
import com.lxkj.qiqihunshe.app.ui.mine.activity.PayActivity
import com.lxkj.qiqihunshe.app.ui.mine.adapter.CardPagerAdapter
import com.lxkj.qiqihunshe.app.ui.mine.model.PermissionBuyModel
import com.lxkj.qiqihunshe.app.ui.mine.model.PermissionBuyXuQiuModel
import com.lxkj.qiqihunshe.app.util.StaticUtil
import com.lxkj.qiqihunshe.app.util.ToastUtil
import com.lxkj.qiqihunshe.app.util.abLog
import com.lxkj.qiqihunshe.databinding.ActivityPermissionBuyBinding
import io.reactivex.Single
import org.json.JSONObject

/**
 * Created by Slingge on 2019/3/2
 */
class PermissionButVeiwModel : BaseViewModel(), CardPagerAdapter.CardPagerCallBack {

    val mCardAdapter by lazy { CardPagerAdapter() }

    var bind: ActivityPermissionBuyBinding? = null


    fun getPermissionList(): Single<String> {
        val json = "{\"cmd\":\"permissionList\",\"uid\":\"" + StaticUtil.uid + "\"}"
        return retrofit.getData(json).async()
            .compose(SingleCompose.compose(object : SingleObserverInterface {
                override fun onSuccess(response: String) {
                    val model = Gson().fromJson(response, PermissionBuyModel::class.java)
                    for (i in 0 until model.dataList.size) {
                        mCardAdapter.addCarditem(model.dataList[i])
                    }
                    bind!!.viewPager.adapter = mCardAdapter
                    val mCardShadowTransformer = ShadowTransformer(bind!!.viewPager, mCardAdapter)
                    mCardShadowTransformer.enableScaling(true)

                    mCardAdapter.setCardPagerCallBack(this@PermissionButVeiwModel)
                }
            }, activity))
    }


    //点击购买回调
    @SuppressLint("CheckResult")
    override fun CardPager(type: String, money: String, info: String) {
        if (type == "4") {
            val bundle = Bundle()
            bundle.putString("money", money)
            bundle.putString("info", info)
            MyApplication.openActivityForResult(activity, DemandListActivity4::class.java, bundle, 4)
        } else if (type == "5") {
            val bundle = Bundle()
            bundle.putString("money", money)
            bundle.putString("info", info)
            MyApplication.openActivityForResult(activity, DemandListActivity5::class.java, bundle, 5)
        } else {
            val model = PermissionBuyXuQiuModel()
            model.type = type
            BuyPer(model, money)
        }
    }


    //购买权限
    @SuppressLint("CheckResult")
    fun BuyPer(model: PermissionBuyXuQiuModel?, money: String) {
        abLog.e("购买权限", Gson().toJson(model))
        retrofit.getData(Gson().toJson(model)).async().compose(SingleCompose.compose(object : SingleObserverInterface {
            override fun onSuccess(response: String) {
                val obj = JSONObject(response)
                val bundle = Bundle()
                bundle.putString("num", obj.getString("orderId"))
                bundle.putDouble("money", money.toDouble())
                bundle.putInt("flag", 0)
                MyApplication.openActivityForResult(activity, PayActivity::class.java, bundle, 0)
            }
        }, activity)).subscribe({}, { toastFailure(it) })
    }


}