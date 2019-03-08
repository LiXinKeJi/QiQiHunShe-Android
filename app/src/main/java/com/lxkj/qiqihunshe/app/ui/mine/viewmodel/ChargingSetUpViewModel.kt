package com.lxkj.qiqihunshe.app.ui.mine.viewmodel

import com.google.gson.Gson
import com.lxkj.qiqihunshe.app.MyApplication
import com.lxkj.qiqihunshe.app.base.BaseViewModel
import com.lxkj.qiqihunshe.app.retrofitnet.SingleCompose
import com.lxkj.qiqihunshe.app.retrofitnet.SingleObserverInterface
import com.lxkj.qiqihunshe.app.retrofitnet.async
import com.lxkj.qiqihunshe.app.ui.mine.model.ChargingSetUpModel
import com.lxkj.qiqihunshe.app.util.StaticUtil
import com.lxkj.qiqihunshe.app.util.ToastUtil
import com.lxkj.qiqihunshe.app.util.abLog
import com.lxkj.qiqihunshe.databinding.ActivityChargingSetupBinding
import io.reactivex.Single
import org.json.JSONObject

/**
 * Created by Slingge on 2019/2/25
 */
class ChargingSetUpViewModel : BaseViewModel() {


 val model by lazy { ChargingSetUpModel() }

 var bind: ActivityChargingSetupBinding? = null

 fun caiyiFee(): Single<String> {
  abLog.e("才艺费用",Gson().toJson(model))
  return retrofit.getData(Gson().toJson(model)).async()
   .compose(SingleCompose.compose(object : SingleObserverInterface {
    override fun onSuccess(response: String) {
     ToastUtil.showToast("修改成功")
     activity?.finish()
    }
   }, activity))
 }


 fun getcaiyiFee(): Single<String> {
  val json = "{\"cmd\":\"caiyiFee\",\"uid\":\"" + StaticUtil.uid + "\"}"
  return retrofit.getData(json).async()
   .compose(SingleCompose.compose(object : SingleObserverInterface {
    override fun onSuccess(response: String) {
     val obj = JSONObject(response)
     model.voice = obj.getString("voice")
     model.video = obj.getString("video")
     bind!!.model = model
    }
   }, activity))
 }

}