package com.lxkj.qiqihunshe.app.util

import android.content.Context
import android.text.TextUtils
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.lxkj.qiqihunshe.R


/**
 * Created by Slingge on 2018/10/23
 */
object GlideUtil {

    val options = RequestOptions()
        .placeholder(R.mipmap.ic_launcher)
//            .error(R.drawable.logo)
        .skipMemoryCache(true)
        .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)

    //通用
    fun glideLoad(context: Context?, url: String?, imageView: ImageView?) {
        if (imageView == null) {
            return
        }
        if (TextUtils.isEmpty(url)) {
            imageView.setImageResource(R.mipmap.ic_launcher)
            return
        }
        Glide.with(context!!)
            .load(url)
            .apply(options)
            .into(imageView)
    }

    val optionsHeader = RequestOptions()
            .placeholder(R.mipmap.ic_launcher)
            .error(R.mipmap.ic_launcher)
        .skipMemoryCache(false)
        .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)

    //头像
    fun glideHeaderLoad(context: Context?, url: String?, imageView: ImageView?) {
        if (imageView == null) {
            return
        }
        if (TextUtils.isEmpty(url)) {
            imageView.setImageResource(R.mipmap.ic_launcher)
            return
        }
        Glide.with(context!!)
            .load(url)
            .apply(optionsHeader)
            .into(imageView)
    }


    val Rounde = 6
    val roundedCorners = RoundedCorners(Rounde)
    //圆角
    fun glideRoundLoad(context: Context, url: String?, imageView: ImageView?) {
        if (imageView == null) {
            return
        }
        Glide.with(context)
            .load(url)
            .apply(
                RequestOptions.bitmapTransform(roundedCorners).placeholder(R.mipmap.ic_launcher)
//                        .error(R.drawable.logo)
                    .skipMemoryCache(false)
                    .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
            )
            .into(imageView)
    }


}