package com.lxkj.qiqihunshe.app.rongrun.plugin

import android.content.Context
import android.support.v4.app.Fragment
import com.lxkj.qiqihunshe.app.rongrun.RongYunUtil
import io.rong.imkit.RongExtension
import io.rong.imkit.plugin.ImagePlugin

/**
 * Created by Slingge on 2019/4/3
 */
class ImagePlugin : ImagePlugin() {

    override fun onClick(currentFragment: Fragment?, extension: RongExtension?) {
        if (RongYunUtil.isLinShiModel == 0) {
            return
        }
        super.onClick(currentFragment, extension)
    }

    override fun obtainTitle(context: Context?): String {
        return "图片"
    }


}