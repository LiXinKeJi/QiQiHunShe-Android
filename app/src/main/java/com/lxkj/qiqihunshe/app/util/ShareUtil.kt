package com.lxkj.qiqihunshe.app.util

import android.app.Activity

import com.lxkj.qiqihunshe.R
import com.umeng.socialize.ShareAction
import com.umeng.socialize.UMShareListener
import com.umeng.socialize.bean.SHARE_MEDIA
import com.umeng.socialize.media.UMImage
import com.umeng.socialize.media.UMWeb
import com.umeng.socialize.shareboard.ShareBoardConfig


/**
 * 分享工具类
 */
object ShareUtil {


    fun share(activity: Activity, url: String, description: String) {
        val web = UMWeb(url)
        web.title = activity.resources.getString(R.string.app_name)//标题
        web.description = description//描述
        val image = UMImage(activity, R.mipmap.ic_launcher)
        web.setThumb(image)

        val shareAction = ShareAction(activity).withMedia(web).setDisplayList(
            SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE,
            SHARE_MEDIA.QQ
        ).setCallback(object : UMShareListener {
            override fun onStart(share_media: SHARE_MEDIA) {
            }

            override fun onResult(share_media: SHARE_MEDIA) {
                ToastUtil.showTopSnackBar(activity, "分享成功")
            }

            override fun onError(share_media: SHARE_MEDIA, throwable: Throwable) {
                ToastUtil.showTopSnackBar(activity, "分享失败")
            }

            override fun onCancel(share_media: SHARE_MEDIA) {
                ToastUtil.showTopSnackBar(activity, "分享取消")
            }

        })
        val config = ShareBoardConfig()
        config.setMenuItemBackgroundShape(ShareBoardConfig.BG_SHAPE_NONE)
        shareAction.open(config)
    }

    fun share(activity: Activity, url: String, description: String, share_media: SHARE_MEDIA) {
        val web = UMWeb(url)
        web.title = activity.resources.getString(R.string.app_name)//标题
        web.description = description//描述
        val image = UMImage(activity, R.mipmap.ic_launcher)
        web.setThumb(image)
        ShareAction(activity)
            .setPlatform(share_media)//传入平台
            .withMedia(web)//分享内容
            .setCallback(object : UMShareListener {
                override fun onStart(share_media: SHARE_MEDIA) {
                }

                override fun onResult(share_media: SHARE_MEDIA) {
                    ToastUtil.showTopSnackBar(activity, "分享成功")
                }

                override fun onError(share_media: SHARE_MEDIA, throwable: Throwable) {
                    ToastUtil.showTopSnackBar(activity, "分享失败")
                }

                override fun onCancel(share_media: SHARE_MEDIA) {
                    ToastUtil.showTopSnackBar(activity, "分享取消")
                }
            })//回调监听器
            .share()
    }


}
