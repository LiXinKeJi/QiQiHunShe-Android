package com.lxkj.qiqihunshe.app.retrofitnet

import android.databinding.BindingAdapter
import android.widget.ImageView
import com.lxkj.qiqihunshe.app.util.GlideUtil

/**
 * 加载网络图片
 * Created by Slingge on 2018/8/1 0001.
 */
object LoadImage {


    @BindingAdapter("bind:imageUrl")
    @JvmStatic
    fun loadImage(imageView: ImageView, url: String?) {
        GlideUtil.glideLoad(imageView.context, url, imageView)
    }


    @BindingAdapter("bind:imageHeaderUrl")
    @JvmStatic
    fun loadHeaderImage(imageView: ImageView, url: String?) {
        GlideUtil.glideHeaderLoad(imageView.context, url, imageView)
    }


}
