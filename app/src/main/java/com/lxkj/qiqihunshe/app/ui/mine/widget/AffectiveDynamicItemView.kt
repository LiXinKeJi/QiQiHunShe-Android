package com.lxkj.qiqihunshe.app.ui.mine.widget

import android.content.Context
import android.view.View
import android.widget.RelativeLayout
import com.lxkj.qiqihunshe.R
import com.lxkj.qiqihunshe.app.util.AbStrUtil
import com.lxkj.qiqihunshe.app.util.GlideUtil
import kotlinx.android.synthetic.main.include_v.view.*
import com.lxkj.qiqihunshe.app.ui.mine.model.SpaceDynamicModel
import com.lxkj.qiqihunshe.app.ui.model.EventCmdModel
import com.lxkj.qiqihunshe.app.util.EventBusCmd
import kotlinx.android.synthetic.main.item_person_dynamic.view.*
import org.greenrobot.eventbus.EventBus

/**
 * 情感动态
 * Created by Slingge on 2019/2/21
 */
class AffectiveDynamicItemView : RelativeLayout {


    constructor(context: Context) : super(context)

    /**
     * 初始化方法
     * */
    init {
        View.inflate(context, R.layout.item_person_dynamic, this)
    }

    fun setData(bean: SpaceDynamicModel.dataModel, position: Int) {

        GlideUtil.glideHeaderLoad(context, bean.icon, header)
        tv_name.text = bean.nickname

        tv_occupation.text = bean.job
        tv_content.text = bean.content

        tv_address.text = bean.location
        tv_time.text = bean.adtime

        tv_zan.text = bean.zanNum
        tv_num.text = bean.commentNum


        if (bean.zan == "0") {
            AbStrUtil.setDrawableLeft(context, R.drawable.ic_zan_nor, tv_zan, 5)
        } else {
            AbStrUtil.setDrawableLeft(context, R.drawable.ic_zan_hl, tv_zan, 5)
        }
        tv_age.text = bean.age
        if (bean.sex == "0") {//0女 1男
            tv_age.setBackgroundResource(R.drawable.bg_girl)
            AbStrUtil.setDrawableLeft(context, R.drawable.ic_girl, tv_age, 3)
        } else {
            tv_age.setBackgroundResource(R.drawable.thems_bg35)
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


        if (bean.images.isEmpty()) {
            ll_image.visibility = View.GONE
        } else if (bean.images.size == 1) {
            iv_2.visibility = View.INVISIBLE
            iv_3.visibility = View.INVISIBLE
            GlideUtil.glideLoad(context,bean.images[0],iv_1)
        }else if (bean.images.size == 2) {
            iv_2.visibility = View.INVISIBLE
            GlideUtil.glideLoad(context,bean.images[0],iv_1)
            GlideUtil.glideLoad(context,bean.images[1],iv_2)
        }else if (bean.images.size == 3) {
            GlideUtil.glideLoad(context,bean.images[0],iv_3)
            GlideUtil.glideLoad(context,bean.images[0],iv_1)
            GlideUtil.glideLoad(context,bean.images[1],iv_2)
        }else if (bean.images.size > 3) {
            GlideUtil.glideLoad(context,bean.images[0],iv_3)
            GlideUtil.glideLoad(context,bean.images[0],iv_1)
            GlideUtil.glideLoad(context,bean.images[1],iv_2)

            tv_totalnum.visibility = View.INVISIBLE
            tv_totalnum.text="+"+(bean.images.size-3).toString()
        }




        tv_zan.setOnClickListener {
            EventBus.getDefault().post(EventCmdModel(EventBusCmd.DianZan,(position).toString()))
        }
        tv_report.setOnClickListener {
            EventBus.getDefault().post(EventCmdModel(EventBusCmd.JuBao,(position).toString()))
        }
        tv_reward.setOnClickListener {
            EventBus.getDefault().post(EventCmdModel(EventBusCmd.dashang,(position).toString()))
        }
        tv_share.setOnClickListener {
            EventBus.getDefault().post(EventCmdModel(EventBusCmd.fenxaing,(position).toString()))
        }

    }


}