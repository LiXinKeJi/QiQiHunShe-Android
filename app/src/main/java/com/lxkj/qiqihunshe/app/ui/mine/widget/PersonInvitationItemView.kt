package com.lxkj.qiqihunshe.app.ui.mine.widget

import android.app.Activity
import android.content.Context
import android.view.View
import android.widget.RelativeLayout
import com.lxkj.qiqihunshe.R
import com.lxkj.qiqihunshe.app.ui.mine.model.SpaceInvitationModel
import com.lxkj.qiqihunshe.app.ui.model.EventCmdModel
import com.lxkj.qiqihunshe.app.util.*
import kotlinx.android.synthetic.main.item_person_invitation.view.*
import org.greenrobot.eventbus.EventBus

/**
 * Created by Slingge on 2019/2/21
 */
class PersonInvitationItemView(activity: Activity, context: Context?) : RelativeLayout(context) {

    private var activity: Activity? = null

    init {
        this.activity = activity
    }

    /**
     * 初始化方法
     * */
    init {
        View.inflate(context, R.layout.item_person_invitation, this)
    }

    fun setData(bean: SpaceInvitationModel.dataModel, positon: Int) {

        if (bean.sex == "0") {//0女 1男
            tv_age.setBackgroundResource(R.drawable.bg_girl)
            tv_age.setTextColor(context.resources.getColor(R.color.girl))
            AbStrUtil.setDrawableLeft(context, R.drawable.ic_girl, tv_age, 3)
        } else {
            tv_age.setBackgroundResource(R.drawable.thems_bg35)
            tv_age.setTextColor(context.resources.getColor(R.color.colorThemes))
            AbStrUtil.setDrawableLeft(context, R.drawable.ic_boy, tv_age, 3)
        }

        GlideUtil.glideHeaderLoad(context, bean.icon, header)

        tv_zhui.text = "主题：${bean.title}"
        tv_time.text = "活动时间：${bean.starttime}"
        tv_content.text = "活动内容：${bean.content}"
        tv_address.text = "活动地点：${bean.address}"

        tv_age.text = bean.age

        tv_name.text = bean.nickname
        tv_occupation.text = "职业：" + bean.job

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
            tv_totalnum.visibility = View.VISIBLE
            tv_totalnum.text = (bean.image.size - 3).toString()
        }

        iv_1.setOnClickListener {
            SeePhotoViewUtil.toPhotoView(activity, bean.image, 0)
        }
        iv_2.setOnClickListener {
            SeePhotoViewUtil.toPhotoView(activity, bean.image, 1)
        }
        iv_3.setOnClickListener {
            SeePhotoViewUtil.toPhotoView(activity, bean.image, 2)
        }


        tv_report.setOnClickListener {
            ToastUtil.showTopSnackBar(activity,positon.toString())
            EventBus.getDefault().post(EventCmdModel(EventBusCmd.JuBao, positon.toString()))
        }

    }


}