package com.lxkj.qiqihunshe.app.ui.mine.viewmodel

import android.annotation.SuppressLint
import com.google.gson.Gson
import com.lxkj.qiqihunshe.app.MyApplication
import com.lxkj.qiqihunshe.app.base.BaseViewModel
import com.lxkj.qiqihunshe.app.interf.ShadowTransformer
import com.lxkj.qiqihunshe.app.retrofitnet.SingleCompose
import com.lxkj.qiqihunshe.app.retrofitnet.SingleObserverInterface
import com.lxkj.qiqihunshe.app.retrofitnet.async
import com.lxkj.qiqihunshe.app.retrofitnet.bindLifeCycle
import com.lxkj.qiqihunshe.app.ui.mine.adapter.CardPagerAdapter
import com.lxkj.qiqihunshe.app.ui.mine.model.PermissionBuyModel
import com.lxkj.qiqihunshe.app.util.StaticUtil
import com.lxkj.qiqihunshe.app.util.ToastUtil
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
                    for(i in 0 until model.dataList.size){
                        mCardAdapter.addCarditem(model.dataList[i])
                    }
                    bind!!.viewPager.adapter = mCardAdapter
                    val mCardShadowTransformer = ShadowTransformer(bind!!.viewPager, mCardAdapter)
                    mCardShadowTransformer.enableScaling(true)

                    mCardAdapter.setCardPagerCallBack(this@PermissionButVeiwModel)
                }
            }, activity))
    }


    @SuppressLint("CheckResult")
    override fun CardPager(type: String?) {
        val json = "{\"cmd\":\"buyPermission\",\"uid\":\"" + StaticUtil.uid + "\",\"type\":\"" + type + "\"}"
        retrofit.getData(json).async().compose(SingleCompose.compose(object : SingleObserverInterface {
            override fun onSuccess(response: String) {
                val obj = JSONObject(response)
                ToastUtil.showTopSnackBar(activity, obj.getString("orderId"))
            }
        }, activity)) .subscribe({},{toastFailure(it)})
    }


}