package com.lxkj.qiqihunshe.app.retrofitnet

import android.databinding.BindingAdapter
import android.widget.ImageView
import com.lxkj.qiqihunshe.app.util.GlideUtil


/**
 * 加载网络图片
 * Created by Slingge on 2018/8/1 0001.
 */
object LoadImage {


    @BindingAdapter("app:imageUrl")
    @JvmStatic
    fun loadImage(imageView: ImageView, url: String?) {
        url?.let {
            //url不为空或者null，执行
            GlideUtil.glideLoad(imageView.context, url, imageView)
        }
    }

}
