package com.lxkj.qiqihunshe.app.util

import android.app.Activity
import com.leon.lfilepickerlibrary.LFilePicker
import com.leon.lfilepickerlibrary.utils.Constant

/**
 * Created by Slingge on 2019/3/14
 */
object SelectFileUtil {


    fun select(context: Activity) {
        LFilePicker()
            .withActivity(context)
            .withRequestCode(0)
            .withTitle("文件选择")
            .withIconStyle(Constant.ICON_STYLE_BLUE)
            .withBackIcon(Constant.BACKICON_STYLEONE)
            .withMutilyMode(true)//true 多选，false单选
            .withMaxNum(9)
            .withNotFoundBooks("至少选择一个文件")
            .withChooseMode(true)//文件夹选择模式
            .withFileFilter(arrayOf("jpg", "png", "avi", "mp4", "mp3", "wmv"))
            .start()
    }


}