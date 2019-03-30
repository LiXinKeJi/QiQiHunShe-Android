package com.lxkj.qiqihunshe.app.ui.mine.widget

import android.app.Activity
import android.content.Context
import android.support.v4.app.Fragment
import android.view.View
import android.widget.RelativeLayout
import cc.shinichi.library.bean.ImageInfo
import com.lxkj.qiqihunshe.R
import com.lxkj.qiqihunshe.app.ui.dialog.DaShangDialog
import com.lxkj.qiqihunshe.app.ui.mine.model.DynamicModel
import com.lxkj.qiqihunshe.app.ui.mine.model.SpaceDynamicModel
import com.lxkj.qiqihunshe.app.ui.model.DelDynamicModel
import com.lxkj.qiqihunshe.app.ui.model.EventCmdModel
import com.lxkj.qiqihunshe.app.util.*
import kotlinx.android.synthetic.main.include_v.view.*
import kotlinx.android.synthetic.main.item_person_dynamic.view.*
import kotlinx.android.synthetic.main.layout_tell.view.*
import org.greenrobot.eventbus.EventBus

/**
 * Created by Slingge on 2019/2/21
 */
class PersonDynamicItemView(activity: Activity, context: Context?) : RelativeLayout(context) {

    private var activity: Activity? = null

    private var bean: SpaceDynamicModel.dataModel? = null

    init {
        this.activity = activity
    }

    /**
     * 初始化方法
     * */
    init {
        View.inflate(context, R.layout.item_person_dynamic, this)
    }

    fun setData(bean: SpaceDynamicModel.dataModel, position: Int) {
        this.bean = bean
        tv_reward.setOnClickListener {
            DaShangDialog.show(activity!!, object : DaShangDialog.DaShangCallBack {
                override fun dashang(money: String) {

                }
            })
        }

        if (StaticUtil.uid == bean.userId) {
            tv_reward.visibility = View.GONE
            tv_report.visibility = View.GONE
        }

        tv_occupation.text = "职业：" + bean.job
        tv_address.text="地址：${bean.location}"

        if (bean.zan == "0") {
            AbStrUtil.setDrawableLeft(context, R.drawable.ic_zan_nor, tv_zan, 5)
        } else {
            AbStrUtil.setDrawableLeft(context, R.drawable.ic_zan_hl, tv_zan, 5)
        }
        tv_age.text = bean.age
        if (bean.sex == "0") {//0女 1男
            tv_age.setBackgroundResource(R.drawable.bg_girl)
            tv_age.setTextColor(context.resources.getColor(R.color.girl))
            AbStrUtil.setDrawableLeft(context, R.drawable.ic_girl, tv_age, 3)
        } else {
            tv_age.setBackgroundResource(R.drawable.thems_bg35)
            tv_age.setTextColor(context.resources.getColor(R.color.colorThemes))
            AbStrUtil.setDrawableLeft(context, R.drawable.ic_boy, tv_age, 3)
        }

        if (bean.identity == "1") {//1单身 2约会 3牵手
            iv_state.setImageResource(R.drawable.danshen)
        } else if (bean.identity == "2") {
            iv_state.setImageResource(R.drawable.yuehui)
        } else {
            iv_state.setImageResource(R.drawable.qianshou)
        }

        for (i in 0 until bean.permission.size) {
            when (bean.permission[i]) {
                "1" -> iv_v1.visibility = View.VISIBLE
                "2" -> iv_v2.visibility = View.VISIBLE
                "3" -> iv_v3.visibility = View.VISIBLE
                "4" -> iv_v4.visibility = View.VISIBLE
                "5" -> iv_v5.visibility = View.VISIBLE
            }
        }


        tv_time.text = bean.adtime

        tv_content.text = bean.content

        when (bean.images.size) {
            0 -> {
                iv_1.visibility = View.GONE
                iv_2.visibility = View.GONE
                iv_3.visibility = View.GONE
            }
            1 -> {
                iv_1.visibility = View.VISIBLE
                iv_2.visibility = View.INVISIBLE
                iv_3.visibility = View.INVISIBLE
                GlideUtil.glideLoad(context, bean.images[0], iv_1)
            }
            2 -> {
                iv_1.visibility = View.VISIBLE
                iv_2.visibility = View.VISIBLE
                iv_3.visibility = View.INVISIBLE
                GlideUtil.glideLoad(context, bean.images[0], iv_1)
                GlideUtil.glideLoad(context, bean.images[1], iv_2)
            }
            3 -> {
                iv_1.visibility = View.VISIBLE
                iv_2.visibility = View.VISIBLE
                iv_3.visibility = View.VISIBLE
                GlideUtil.glideLoad(context, bean.images[0], iv_1)
                GlideUtil.glideLoad(context, bean.images[1], iv_2)
                GlideUtil.glideLoad(context, bean.images[2], iv_3)
            }
            else -> {
                iv_1.visibility = View.VISIBLE
                iv_2.visibility = View.VISIBLE
                iv_3.visibility = View.VISIBLE
                GlideUtil.glideLoad(context, bean.images[1], iv_1)
                GlideUtil.glideLoad(context, bean.images[2], iv_2)
                GlideUtil.glideLoad(context, bean.images[3], iv_3)
            }
        }


        tv_zan.setOnClickListener {
            EventBus.getDefault().post(EventCmdModel(EventBusCmd.DianZan, (position).toString()))
        }
        tv_report.setOnClickListener {
            EventBus.getDefault().post(EventCmdModel(EventBusCmd.JuBao, (position).toString()))
        }
        tv_reward.setOnClickListener {
            EventBus.getDefault().post(EventCmdModel(EventBusCmd.dashang, (position).toString()))
        }
        tv_share.setOnClickListener {
            EventBus.getDefault().post(EventCmdModel(EventBusCmd.fenxaing, (position).toString()))
        }


        iv_1.setOnClickListener {
            SeePhotoViewUtil.toPhotoView(activity, bean.images, 0)
        }
        iv_2.setOnClickListener {
            SeePhotoViewUtil.toPhotoView(activity, bean.images, 1)
        }
        iv_3.setOnClickListener {
            SeePhotoViewUtil.toPhotoView(activity, bean.images, 2)
        }

    }

    fun upZan(num: String, dongtaiId: String) {
        if (bean?.dongtaiId == dongtaiId) {
            if (bean?.zanNum!!.toInt() < num.toInt()) {
                AbStrUtil.setDrawableLeft(context, R.drawable.ic_zan_hl, tv_zan, 5)
            } else {
                AbStrUtil.setDrawableLeft(context, R.drawable.ic_zan_nor, tv_zan, 5)
            }
            tv_zan.text = num
        }
    }


}