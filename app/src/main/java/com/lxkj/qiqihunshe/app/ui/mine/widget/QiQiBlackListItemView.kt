package com.lxkj.qiqihunshe.app.ui.mine.widget

import android.annotation.SuppressLint
import android.content.Context
import android.view.View
import android.widget.RelativeLayout
import com.lxkj.qiqihunshe.R
import com.lxkj.qiqihunshe.app.ui.mine.model.QiQiBlackListModel
import com.lxkj.qiqihunshe.app.util.AbStrUtil
import com.lxkj.qiqihunshe.app.util.GlideUtil
import kotlinx.android.synthetic.main.include_v.view.*
import kotlinx.android.synthetic.main.item_blacklist.view.*

/**
 * Created by Slingge on 2019/2/21
 */
class QiQiBlackListItemView : RelativeLayout {

    private var flag: Int = -1

    constructor(context: Context, flag: Int) : super(context){
        this.flag=flag
    }

    /**
     * 初始化方法
     * */
    init {
        View.inflate(context, R.layout.item_blacklist, this)
    }

    @SuppressLint("SetTextI18n")
    fun setData(bean: QiQiBlackListModel.dataModel, position: Int) {


        if (flag == 0) {//0七七黑名单，1我的黑名单
            item_my.visibility = View.GONE
            item_service.visibility = View.VISIBLE

            tv_name.text = bean.nickname
            GlideUtil.glideHeaderLoad(context, bean.icon, iv_header)
            tv_realname.text = "姓名：" + bean.realname
            tv_hometown.text = "家乡：" + bean.birthplace
            tv_current.text = "现居：" + bean.residence
            tv_id.text = "身份证号：" + bean.idnumber
            tv_phone.text = "手机号：" + bean.phone
        } else {
            item_my.visibility = View.VISIBLE
            item_service.visibility = View.GONE

            tv_name0.text = bean.nickname
            GlideUtil.glideHeaderLoad(context, bean.icon, iv_header0)

            when (bean.identity) {
                "1"//单身
                -> iv_state.setBackgroundResource(R.mipmap.ic_ds)
                "2"//约会
                -> iv_state.setBackgroundResource(R.mipmap.ic_yh)
                "3"//牵手
                -> iv_state.setBackgroundResource(R.mipmap.ic_qs)
            }

            tv_age.text = bean.age
            when (bean.sex) {
                "0"//女
                -> {
                    AbStrUtil.setDrawableLeft(context, R.drawable.ic_girl, tv_age, 3)
                    tv_age.setTextColor(context.resources.getColor(R.color.girl))
                    tv_age.setBackgroundResource(R.drawable.bg_girl)
                }
                "1"//男
                -> {
                    AbStrUtil.setDrawableLeft(context, R.drawable.ic_boy, tv_age, 3)
                    tv_age.setTextColor(context.resources.getColor(R.color.colorAccent))
                    tv_age.setBackgroundResource(R.drawable.thems_bg35)
                }
            }

            tv_distance.text = "添加时间：" + bean.adtime

            tv_zhiye.text = "职业：" + bean.job

            tv_emotional.text = "情感计划：" + bean.plan

            tv_autograph.text = "个人签名：" + bean.introduction

            for (i in 0 until bean.permission.size) {
                when (bean.permission[i]) {
                    "1" -> iv_v1.visibility = View.VISIBLE
                    "2" -> iv_v2.visibility = View.VISIBLE
                    "3" -> iv_v3.visibility = View.VISIBLE
                    "4" -> iv_v4.visibility = View.VISIBLE
                    "5" -> iv_v5.visibility = View.VISIBLE
                }
            }

            tv_reputation.text = "信誉值：" + bean.credit
            tv_feel.text = "言礼值：" + bean.polite
            tv_security.text = "综合安全值：" + bean.safe

        }

    }


}