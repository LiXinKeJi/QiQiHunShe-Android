package com.lxkj.qiqihunshe.app.ui.mine.viewmodel

import android.content.Intent
import android.support.v7.widget.GridLayoutManager
import com.google.gson.Gson
import com.google.gson.JsonArray
import com.luck.picture.lib.entity.LocalMedia
import com.lxkj.qiqihunshe.app.base.BaseViewModel
import com.lxkj.qiqihunshe.app.interf.UpLoadFileCallBack
import com.lxkj.qiqihunshe.app.retrofitnet.SingleCompose
import com.lxkj.qiqihunshe.app.retrofitnet.SingleObserverInterface
import com.lxkj.qiqihunshe.app.retrofitnet.UpFileUtil
import com.lxkj.qiqihunshe.app.retrofitnet.async
import com.lxkj.qiqihunshe.app.ui.mine.adapter.ReleaseAdapter
import com.lxkj.qiqihunshe.app.ui.mine.model.ReleaseDynamicModel
import com.lxkj.qiqihunshe.app.util.StaticUtil
import com.lxkj.qiqihunshe.app.util.ToastUtil
import com.lxkj.qiqihunshe.app.util.abLog
import com.lxkj.qiqihunshe.databinding.ActivityReleaseDynamicBinding
import io.reactivex.Single

/**
 * Created by Slingge on 2019/2/25
 */
class ReleaseDynamicViewModel : BaseViewModel(), ReleaseAdapter.ImageRemoveCallback {


    var bind: ActivityReleaseDynamicBinding? = null

    val ablumList by lazy { ArrayList<LocalMedia>() }
    var imageAdapter: ReleaseAdapter? = null
    val model by lazy { ReleaseDynamicModel() }


    fun initViewModel() {
        model.lat=StaticUtil.lat
        model.lon=StaticUtil.lng//必须要有默认经纬度，不能为空
        ablumList.add(LocalMedia())
        imageAdapter = ReleaseAdapter(activity!!, ablumList, 9, this)
        imageAdapter?.setFlag(1)
        bind!!.rvAlbum.layoutManager = GridLayoutManager(activity, 3)
        bind!!.rvAlbum.adapter = imageAdapter
    }


    fun setImage(images: List<LocalMedia>) {
        for (i in 0 until images.size) {
            ablumList.add(ablumList.size - 1, images[i])
        }
        imageAdapter!!.notifyDataSetChanged()
    }

    override fun imageRemove(i: Int) {
        ablumList.removeAt(i)
        imageAdapter!!.notifyDataSetChanged()

    }

    fun fendDynamic(): Single<String> {
        abLog.e("发布动态", Gson().toJson(model))
        return retrofit.getData(Gson().toJson(model)).async()
            .compose(SingleCompose.compose(object : SingleObserverInterface {
                override fun onSuccess(response: String) {
                    ToastUtil.showToast("发布成功")
                    val intent=Intent()
                    intent.putExtra("cmd","add")
                    activity!!.setResult(0,intent)
                    activity?.finish()
                }
            }, activity))
    }


}