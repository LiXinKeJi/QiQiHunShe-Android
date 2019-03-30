package com.lxkj.qiqihunshe.app.ui.mine.widget

import android.content.Context
import android.view.View
import android.widget.RelativeLayout
import com.lxkj.qiqihunshe.R
import com.lxkj.qiqihunshe.app.ui.mine.model.SpaceInvitationModel
import com.lxkj.qiqihunshe.app.ui.model.EventCmdModel
import com.lxkj.qiqihunshe.app.util.EventBusCmd
import com.lxkj.qiqihunshe.app.util.GlideUtil
import com.lxkj.qiqihunshe.app.util.SeePhotoViewUtil
import kotlinx.android.synthetic.main.item_space_invitation.view.*
import org.greenrobot.eventbus.EventBus

/**
 * Created by Slingge on 2019/2/21
 */
class SpaceInvitationitenView : RelativeLayout {


    constructor(context: Context) : super(context)

    /**
     * 初始化方法
     * */
    init {
        View.inflate(context, R.layout.item_space_invitation, this)
    }

    fun setData(bean: SpaceInvitationModel.dataModel, position: Int) {

        if (bean.image.isEmpty()) {
            iv_1.visibility = View.GONE
            iv_2.visibility = View.GONE
            iv_3.visibility = View.GONE
        } else if (bean.image.size == 1) {
            iv_2.visibility = View.INVISIBLE
            iv_3.visibility = View.INVISIBLE
            GlideUtil.glideLoad(context, bean.image[0], iv_1)
        } else if (bean.image.size == 2) {
            iv_3.visibility = View.INVISIBLE
            GlideUtil.glideLoad(context, bean.image[0], iv_1)
            GlideUtil.glideLoad(context, bean.image[1], iv_2)
        } else if (bean.image.size == 3) {
            GlideUtil.glideLoad(context, bean.image[0], iv_1)
            GlideUtil.glideLoad(context, bean.image[1], iv_2)
            GlideUtil.glideLoad(context, bean.image[2], iv_3)
        } else if (bean.image.size > 3) {
            tv_num.visibility = View.VISIBLE
            tv_num.text = (bean.image.size - 3).toString()
        }

        iv_1.setOnClickListener {
            SeePhotoViewUtil.toPhotoView(context, bean.image, 0)
        }
        iv_2.setOnClickListener {
            SeePhotoViewUtil.toPhotoView(context, bean.image, 1)
        }
        iv_3.setOnClickListener {
            SeePhotoViewUtil.toPhotoView(context, bean.image, 2)
        }


        tv_psersonnum.text = "当前报名人数：${bean.yes}人  ${bean.wait}人待审核  ${bean.no}人拒绝"

        tv_zhui.text = "主题：${bean.title}"
        tv_time.text = "活动时间：${bean.starttime}"
        tv_content.text = "活动内容：${bean.content}"
        tv_address.text = "活动地点：${bean.address}"
        tv_range.text = "限制范围：${bean.condition}"
        if (bean.sex == "0") {
            tv_limit.text = "仅限：女"
        } else if (bean.sex == "1") {
            tv_limit.text = "仅限：男"
        } else {
            tv_limit.text = "仅限：无限制"
        }
        if (bean.fee == "0") {
            tv_consu.text = "消费：AA"
        } else if (bean.fee == "1") {
            tv_consu.text = "消费：对方买单"
        } else {
            tv_consu.text = "消费：我买单"
        }


        tv_date.text = bean.adtime

        iv_del.setOnClickListener {
            if (cv_del.visibility == View.VISIBLE) {
                cv_del.visibility = View.GONE
            } else {
                cv_del.visibility = View.VISIBLE
            }
        }

        cv_del.setOnClickListener {
            EventBus.getDefault().post(EventCmdModel(EventBusCmd.DelInvitation, position.toString()))
        }

    }


}