package com.lxkj.qiqihunshe.app.ui.xiaoxi.widget

import android.content.Context
import android.view.View
import android.widget.RelativeLayout
import com.lxkj.qiqihunshe.R
import com.lxkj.qiqihunshe.app.MyApplication
import com.lxkj.qiqihunshe.app.ui.xiaoxi.model.MessageModel
import kotlinx.android.synthetic.main.item_message.view.*

/**
 * Created by Slingge on 2019/2/21
 */
class MessageItemView : RelativeLayout {


    constructor(context: Context) : super(context)

    /**
     * 初始化方法
     * */
    init {
        View.inflate(context, R.layout.item_message, this)
    }

    fun setData(bean: MessageModel) {
        if (bean.system == "0") {
            tv_sysNum.visibility = View.VISIBLE

            tv_msgNum.visibility = View.GONE
            tv_type.visibility = View.GONE
            tv_time.visibility = View.GONE
            MyApplication.setRedNum(tv_sysNum, 10)

            tv_title.text = bean.title
            if (bean.title == "互动通知") {
                header.setImageResource(R.drawable.ic_tip1)
            } else if (bean.title == "小七提醒") {
                header.setImageResource(R.drawable.ic_tip2)
            }

        } else {
            MyApplication.setRedNum(tv_msgNum, 20)
        }

        if (bean.flag == "1") {//可以解除关系
            tv_msgNum.visibility = View.GONE
            tv_type.visibility = View.GONE
            tv_time.visibility = View.GONE
            tv_sysNum.visibility = View.GONE

            tv_relieve.visibility = View.VISIBLE
        }

    }


}