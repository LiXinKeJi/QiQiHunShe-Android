package com.lxkj.qiqihunshe.app.ui.xiaoxi.widget

import android.content.Context
import android.view.View
import android.widget.RelativeLayout
import com.lxkj.qiqihunshe.R
import com.lxkj.qiqihunshe.app.MyApplication
import com.lxkj.qiqihunshe.app.ui.xiaoxi.activity.AddFriendActivity
import com.lxkj.qiqihunshe.app.ui.xiaoxi.model.MessageModel
import com.lxkj.qiqihunshe.app.util.abLog
import kotlinx.android.synthetic.main.item_message.view.*

/**
 * Created by Slingge on 2019/2/21
 */
class NewFriendItemView : RelativeLayout {


    constructor(context: Context) : super(context)

    /**
     * 初始化方法
     * */
    init {
        View.inflate(context, R.layout.item_newfirend, this)
    }

    fun setData(bean: MessageModel) {

        tv_type.setOnClickListener {
            MyApplication.openActivity(context,AddFriendActivity::class.java)
        }
    }


}