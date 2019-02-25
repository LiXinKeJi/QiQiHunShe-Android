package com.lxkj.qiqihunshe.app.ui.mine.widget

import android.content.Context
import android.view.View
import android.widget.RelativeLayout
import com.lxkj.qiqihunshe.R
import com.lxkj.qiqihunshe.app.ui.mine.model.MyInvitationDetailsModel
import kotlinx.android.synthetic.main.item_myinvitation_details.view.*

/**
 * Created by Slingge on 2019/2/21
 */
class MyInvitationDetailsItemView : RelativeLayout {

    private var type = -1//0待审核，1当前报名人数，2拒绝人数

    constructor(context: Context, type: Int) : super(context) {
        this.type = type
    }

    /**
     * 初始化方法
     * */
    init {
        View.inflate(context, R.layout.item_myinvitation_details, this)
    }

    fun setData(bean: MyInvitationDetailsModel) {

        when (type) {
            0 -> {
                tv_agree.setBackgroundResource(R.drawable.button_click5)
                tv_agree.visibility = View.VISIBLE
                tv_reason.visibility = View.GONE
            }
            1 -> {
                tv_agree.setBackgroundResource(R.drawable.button_click_red5)
                tv_agree.visibility = View.VISIBLE
                tv_reason.visibility = View.GONE
            }
            2 -> {
                tv_agree.visibility = View.GONE
                tv_reason.visibility = View.VISIBLE
            }
        }

    }


}