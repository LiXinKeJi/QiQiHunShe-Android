package com.lxkj.qiqihunshe.app.ui.mine.widget

import android.content.Context
import android.view.View
import android.widget.RelativeLayout
import com.lxkj.qiqihunshe.R
import com.lxkj.qiqihunshe.app.ui.dialog.EditDialog
import com.lxkj.qiqihunshe.app.ui.mine.model.MyInvitationDetailsModel
import com.lxkj.qiqihunshe.app.ui.model.EventCmdModel
import com.lxkj.qiqihunshe.app.util.AbStrUtil
import com.lxkj.qiqihunshe.app.util.EventBusCmd
import com.lxkj.qiqihunshe.app.util.GlideUtil
import kotlinx.android.synthetic.main.activity_mydynamic.*
import kotlinx.android.synthetic.main.include_v.*
import kotlinx.android.synthetic.main.include_v.view.*
import kotlinx.android.synthetic.main.item_myinvitation_details.view.*
import net.lucode.hackware.magicindicator.buildins.commonnavigator.model.PositionData
import org.greenrobot.eventbus.EventBus
import java.text.FieldPosition

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

    fun setData(bean: MyInvitationDetailsModel.dataModel,position: Int) {


        if (bean.userSex == "0") {//0女 1男
            tv_age.setBackgroundResource(R.drawable.bg_girl)
            AbStrUtil.setDrawableLeft(context, R.drawable.ic_girl, tv_age, 3)
        } else {
            tv_age.setBackgroundResource(R.drawable.thems_bg35)
            AbStrUtil.setDrawableLeft(context, R.drawable.ic_boy, tv_age, 3)
        }

        if (bean.userIdentity == "1") {//1单身 2约会 3牵手
            iv_state.setImageResource(R.drawable.danshen)
        } else if (bean.userIdentity == "2") {
            iv_state.setImageResource(R.drawable.yuehui)
        } else {
            iv_state.setImageResource(R.drawable.qianshou)
        }

        for (i in 0 until bean.userPermission.size) {
            when (bean.userPermission[i]) {
                "1" -> iv_v1.visibility = View.VISIBLE
                "2" -> iv_v2.visibility = View.VISIBLE
                "3" -> iv_v3.visibility = View.VISIBLE
                "4" -> iv_v4.visibility = View.VISIBLE
                "5" -> iv_v5.visibility = View.VISIBLE
            }
        }


        tv_name.text=bean.userNickname

        GlideUtil.glideHeaderLoad(context,bean.userIcon,iv_header)
        tv_zhiye.text="职业：${bean.userJob}"
        tv_plan.text="情感计划：${bean.plan}"
        tv_current.text="个人签名：${bean.introduction}"

        tv_reputation.text="信誉值：${bean.credit}"
        tv_feel.text="言礼值：${bean.polite}"
        tv_security.text="综合安全值：${bean.safe}"

        tv_time.text=bean.time

        when (type) {
            0 -> {
                tv_agree.setBackgroundResource(R.drawable.button_click5)
                tv_agree.visibility = View.VISIBLE
                tv_reason.visibility = View.GONE

                tv_agree.setOnClickListener{//同意申请
                    EventBus.getDefault().post(EventCmdModel("agree",position.toString()))
                }
            }
            1 -> {
                tv_agree.setBackgroundResource(R.drawable.button_click_red5)
                tv_agree.visibility = View.VISIBLE
                tv_reason.visibility = View.GONE
                tv_agree.text="删除"

                tv_agree.setOnClickListener{//拒绝申请
                    EventBus.getDefault().post(EventCmdModel("del",position.toString()))
                }
            }
            2 -> {
                tv_agree.visibility = View.GONE
                tv_reason.visibility = View.VISIBLE
                tv_reason.text=""
            }
        }


    }


}