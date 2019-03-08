package com.lxkj.qiqihunshe.app.ui.mine.widget

import android.content.Context
import android.view.View
import android.widget.RelativeLayout
import com.lxkj.qiqihunshe.R
import com.lxkj.qiqihunshe.app.util.GlideUtil
import kotlinx.android.synthetic.main.item_image.view.*

/**
 * Created by Slingge on 2019/2/21
 */
class ImageItemView : RelativeLayout {


    constructor(context: Context) : super(context)

    /**
     * 初始化方法
     * */
    init {
        View.inflate(context, R.layout.item_image, this)
    }

    fun setData(url: String) {
        GlideUtil.glideLoad(context, url, image)
    }


}