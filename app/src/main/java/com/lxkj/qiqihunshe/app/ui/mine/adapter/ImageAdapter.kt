package com.lxkj.qiqihunshe.app.ui.mine.adapter

import android.content.Context
import com.lxkj.qiqihunshe.app.base.BaseListAdapter
import com.lxkj.qiqihunshe.app.ui.mine.widget.ImageItemView

/**
 * 评论
 * Created by Slingge on 2019/2/22
 */
class ImageAdapter : BaseListAdapter<String, ImageItemView>() {


    override fun getitemView(context: Context): ImageItemView {
        return ImageItemView(context)
    }

    override fun refreshItemView(view: ImageItemView, itembean: String) {
        view.setData(itembean)
    }


}