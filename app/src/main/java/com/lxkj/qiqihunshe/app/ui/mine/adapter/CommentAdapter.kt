package com.lxkj.qiqihunshe.app.ui.mine.adapter

import android.content.Context
import com.lxkj.qiqihunshe.app.base.BaseListAdapter
import com.lxkj.qiqihunshe.app.ui.mine.model.CommentModel
import com.lxkj.qiqihunshe.app.ui.mine.widget.CommentItemView

/**
 * 评论
 * Created by Slingge on 2019/2/22
 */
class CommentAdapter : BaseListAdapter<CommentModel.dataModel, CommentItemView>() {
    override fun refreshItemView(view: CommentItemView, itembean: CommentModel.dataModel, position: Int) {

    }


    override fun getitemView(context: Context): CommentItemView {
        return CommentItemView(context)
    }

    override fun refreshItemView(view: CommentItemView, itembean: CommentModel.dataModel) {
        view.setData(itembean)
    }


}