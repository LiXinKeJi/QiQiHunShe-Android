package com.lxkj.qiqihunshe.app.ui.mine.viewmodel

import android.content.Intent
import android.databinding.ObservableField
import com.google.gson.Gson
import com.lxkj.qiqihunshe.app.base.BaseViewModel
import com.lxkj.qiqihunshe.app.interf.UpLoadFileCallBack
import com.lxkj.qiqihunshe.app.retrofitnet.SingleCompose
import com.lxkj.qiqihunshe.app.retrofitnet.SingleObserverInterface
import com.lxkj.qiqihunshe.app.retrofitnet.UpFileUtil
import com.lxkj.qiqihunshe.app.retrofitnet.async
import com.lxkj.qiqihunshe.app.ui.mine.model.ReleaseSkillModel
import com.lxkj.qiqihunshe.app.util.ProgressDialogUtil
import com.lxkj.qiqihunshe.app.util.abLog
import com.lxkj.qiqihunshe.databinding.ActivityReleaseSkillBinding
import io.reactivex.Single

/**
 * Created by Slingge on 2019/2/25
 */
class ReleaseSkillViewModel : BaseViewModel(), UpLoadFileCallBack {

    var bind: ActivityReleaseSkillBinding? = null

    val content = ObservableField<String>()
    val title = ObservableField<String>()

    var model = ReleaseSkillModel()


    private val upload by lazy { UpFileUtil(activity!!, this) }

    fun init() {


    }


    fun releaseSkill(): Single<String> {

        return retrofit.getData(Gson().toJson(model)).async()
            .compose(SingleCompose.compose(object : SingleObserverInterface {
                override fun onSuccess(response: String) {
                    val intent = Intent()
                    intent.putExtra("cmd", "add")
                    activity?.let {
                        it.setResult(1, intent)
                        it.finish()
                    }
                }
            }, activity))
    }


    fun upVideo(path: String) {
        ProgressDialogUtil.showProgressDialog(activity!!)
        upload.upLoad(path)
    }

    override fun uoLoad(url: String) {
        ProgressDialogUtil.dismissDialog()
        abLog.e("视频路径",url)
        if(url.contains(".jpg")){
            model.image = url
        }else{
            model.video = url
        }
    }

    override fun uoLoad(url: List<String>) {

    }


}