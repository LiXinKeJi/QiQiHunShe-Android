package com.lxkj.qiqihunshe.app.util

import android.app.Activity
import android.content.Context

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


    private val shareUrl="https://www.dytt8.net/"
    private val description="分享内容"

    fun share(activity: Activity ) {
        if(!isWeixinAvilible(activity)){
            ToastUtil.showTopSnackBar(activity,"请先安装微信")
            return
        }

        val web = UMWeb(shareUrl)
        web.title = activity.resources.getString(R.string.app_name)//标题
        web.description = description//描述
        val image = UMImage(activity, R.mipmap.ic_launcher)
        web.setThumb(image)

        val shareAction = ShareAction(activity).withMedia(web).setDisplayList(
            SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE/*,
            SHARE_MEDIA.QQ*/
        ).setCallback(object : UMShareListener {
            override fun onStart(share_media: SHARE_MEDIA) {
            }

            override fun onResult(share_media: SHARE_MEDIA) {
                ToastUtil.showTopSnackBar(activity, "分享成功")
            }

            override fun onError(share_media: SHARE_MEDIA, throwable: Throwable) {
                ToastUtil.showTopSnackBar(activity, "分享失败")
                abLog.e("分享失败",throwable.message!!)
            }

            override fun onCancel(share_media: SHARE_MEDIA) {
                ToastUtil.showTopSnackBar(activity, "分享取消")
            }

        })
        val config = ShareBoardConfig()
        config.setMenuItemBackgroundShape(ShareBoardConfig.BG_SHAPE_NONE)
        shareAction.open(config)
    }

    fun share(activity: Activity,  share_media: SHARE_MEDIA) {
        val web = UMWeb("")
        web.title = activity.resources.getString(R.string.app_name)//标题
        web.description = "描述"//描述
        val image = UMImage(activity, R.mipmap.ic_launcher)
        web.setThumb(image)
        ShareAction(activity)
            .setPlatform(share_media)//传入平台
            .withMedia(web)//分享内容
            .setCallback(object : UMShareListener {
                override fun onStart(share_media: SHARE_MEDIA) {
                }

                override fun onResult(share_media: SHARE_MEDIA) {

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


    fun isWeixinAvilible(context: Context): Boolean {
        val packageManager = context.packageManager// 获取packagemanager
        val pinfo = packageManager.getInstalledPackages(0)// 获取所有已安装程序的包信息
        if (pinfo != null) {
            for (i in pinfo.indices) {
                val pn = pinfo[i].packageName
                if (pn == "com.tencent.mm") {
                    return true
                }
            }
        }
        return false
    }


}
