package com.lxkj.qiqihunshe.app.ui.xiaoxi.widget

import android.content.Context
import android.view.View
import android.widget.RelativeLayout
import com.lxkj.qiqihunshe.R
import com.lxkj.qiqihunshe.app.MyApplication
import com.lxkj.qiqihunshe.app.ui.xiaoxi.model.MessageModel
import com.lxkj.qiqihunshe.app.util.abLog
import kotlinx.android.synthetic.main.item_message.view.*

/**
 * Created by Slingge on 2019/2/21
 */
class CommunicationItemView : RelativeLayout {


    constructor(context: Context) : super(context)

    /**
     * 初始化方法
     * */
    init {
        View.inflate(context, R.layout.item_message, this)
    }

    fun setData(bean: MessageModel) {
        abLog.e("system", bean.system)
        if (bean.system == "0") {//系统新朋友
            tv_msgNum.visibility = View.GONE
            tv_type.visibility = View.GONE
            tv_time.visibility = View.GONE
            tv_content.visibility = View.INVISIBLE
            tv_age.visibility = View.GONE

            tv_title.text="新的朋友"
           tv_sysNum.visibility = View.VISIBLE
            MyApplication.setRedNum(tv_sysNum,120)
        } else {
            tv_age.visibility = View.VISIBLE
            tv_content.visibility = View.VISIBLE

            tv_msgNum.visibility = View.GONE
            tv_type.visibility = View.GONE
            tv_time.visibility = View.GONE

        }

    }


}