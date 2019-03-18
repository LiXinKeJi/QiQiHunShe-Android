package com.lxkj.qiqihunshe.app.util

import android.content.Context
import cc.shinichi.library.ImagePreview
import cc.shinichi.library.bean.ImageInfo
import com.lxkj.qiqihunshe.R


/**
 * 查看图片
 * Created by Slingge on 2017/7/4 0004.
 */

object SeePhotoViewUtil {


    //wifi自适应清晰度
    fun toPhotoView(context: Context?, imageInfoList: ArrayList<String>, int: Int) {

        val images = ArrayList<ImageInfo>()
        for (j in 0 until imageInfoList.size) {
            val info = ImageInfo()
            info.originUrl = imageInfoList[j]
            info.thumbnailUrl = imageInfoList[j]
            images.add(info)
        }


        ImagePreview
            .getInstance()
            .setContext(context!!)
            .setIndex(0)
            .setImageInfoList(images)
            .setShowDownButton(true)
            .setLoadStrategy(ImagePreview.LoadStrategy.NetworkAuto)
            .setFolderName("BigImageViewDownload")
            .setScaleLevel(1, 3, 5)
            .setZoomTransitionDuration(300)

            .setEnableClickClose(true)// 是否启用点击图片关闭。默认启用
            .setEnableDragClose(true)// 是否启用上拉/下拉关闭。默认不启用

            .setShowCloseButton(false)// 是否显示关闭页面按钮，在页面左下角。默认不显示
//                .setCloseIconResId(R.drawable.ic_action_close)// 设置关闭按钮图片资源，可不填，默认为：R.drawable.ic_action_close

            .setShowDownButton(true)// 是否显示下载按钮，在页面右下角。默认显示
            .setDownIconResId(R.drawable.icon_download_new)// 设置下载按钮图片资源，可不填，默认为：R.drawable.icon_download_new

            .setShowIndicator(true)// 设置是否显示顶部的指示器（1/9）。默认显示
            .setIndex(int)
            .start()
    }


}
