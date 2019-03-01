package com.lxkj.qiqihunshe.app.ui.xiaoxi.widget

import android.content.Context
import android.view.View
import android.widget.RelativeLayout
import com.lxkj.qiqihunshe.R
import com.lxkj.qiqihunshe.app.MyApplication
import com.lxkj.qiqihunshe.app.ui.xiaoxi.activity.AddFriendActivity
import com.lxkj.qiqihunshe.app.ui.xiaoxi.activity.LookupResultActivity
import com.lxkj.qiqihunshe.app.ui.xiaoxi.model.LookResultModel
import kotlinx.android.synthetic.main.item_lookup_result.view.*

/**
 * Created by Slingge on 2019/2/21
 */
class LookupResultItemView : RelativeLayout {


    constructor(context: Context) : super(context)

    /**
     * 初始化方法
     * */
    init {
        View.inflate(context, R.layout.item_lookup_result, this)
    }

    fun setData(bean: LookResultModel) {

        tv_add.setOnClickListener {
            MyApplication.openActivity(context, AddFriendActivity::class.java)
        }
    }


}