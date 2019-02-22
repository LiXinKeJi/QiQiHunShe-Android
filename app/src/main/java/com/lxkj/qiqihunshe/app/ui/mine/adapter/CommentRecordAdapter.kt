package com.lxkj.qiqihunshe.app.ui.mine.adapter

import android.content.Context
import com.lxkj.qiqihunshe.app.base.BaseListAdapter
import com.lxkj.qiqihunshe.app.ui.mine.model.CommentRecordModel
import com.lxkj.qiqihunshe.app.ui.mine.widget.CommentRecordItemView

/**
 * Created by Slingge on 2019/2/21
 */
class CommentRecordAdapter : BaseListAdapter<CommentRecordModel, CommentRecordItemView>() {


    override fun getitemView(context: Context): CommentRecordItemView {
        return CommentRecordItemView(context)
    }

    override fun refreshItemView(view: CommentRecordItemView, itembean: CommentRecordModel) {
    }


}