package com.lxkj.qiqihunshe.app.ui.quyu.viewmodel

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import android.view.View
import android.webkit.*
import android.widget.ImageView
import cn.bingoogolapple.bgabanner.BGABanner
import com.google.gson.Gson
import com.lxkj.qiqihunshe.app.MyApplication
import com.lxkj.qiqihunshe.app.base.BaseViewModel
import com.lxkj.qiqihunshe.app.retrofitnet.SingleCompose
import com.lxkj.qiqihunshe.app.retrofitnet.SingleObserverInterface
import com.lxkj.qiqihunshe.app.retrofitnet.async
import com.lxkj.qiqihunshe.app.ui.quyu.model.ShopDetailModel
import com.lxkj.qiqihunshe.app.util.GlideImageLoader
import com.lxkj.qiqihunshe.app.util.GlideUtil
import com.lxkj.qiqihunshe.app.util.SeePhotoViewUtil
import com.lxkj.qiqihunshe.app.util.StringUtil
import com.lxkj.qiqihunshe.databinding.ActivityShopDetailBinding
import com.youth.banner.BannerConfig
import io.reactivex.Single

/**
 * Created by kxn on 2019/3/1 0001.
 */
class ShopDetailViewModel : BaseViewModel() {

    var id: String? = null//店铺id


    var bind: ActivityShopDetailBinding? = null

    lateinit var model: ShopDetailModel

    fun init() {
        bind?.let {
            it.banner.setOnBannerListener {
                SeePhotoViewUtil.toPhotoView(activity, model.banner, it)
            }
            it.banner.updateBannerStyle(BannerConfig.NUM_INDICATOR)
        }
    }

    //获取店铺详情
    fun getshopDetail(): Single<String> {
        val json = "{\"cmd\":\"shopDetail\",\"shopId\":\"$id\"}"
        return retrofit.getData(json)
            .async()
            .compose(SingleCompose.compose(object : SingleObserverInterface {
                override fun onSuccess(response: String) {
                    model = Gson().fromJson(response, ShopDetailModel::class.java)
                    bind?.let {
                        it.model = model
                        it.banner.setImages(model.banner)
                            .setImageLoader(GlideImageLoader())
                            .start()

                        it.webView.webView.loadUrl(model.url)
                    }
                }
            }, activity))
    }


}