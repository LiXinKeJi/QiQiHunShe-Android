package com.lxkj.qiqihunshe.app.ui.xiaoxi.activity

import android.text.TextUtils
import android.view.View
import com.lxkj.qiqihunshe.R
import com.lxkj.qiqihunshe.app.base.BaseActivity
import com.lxkj.qiqihunshe.app.ui.xiaoxi.model.DataListModel
import com.lxkj.qiqihunshe.app.ui.xiaoxi.viewmodel.AddFriendViewModel
import com.lxkj.qiqihunshe.app.util.AbStrUtil
import com.lxkj.qiqihunshe.app.util.GlideUtil
import com.lxkj.qiqihunshe.app.util.ToastUtil
import com.lxkj.qiqihunshe.databinding.ActivityAddfriendBinding
import kotlinx.android.synthetic.main.activity_addfriend.*
import kotlinx.android.synthetic.main.include_v.*

/**
 * Created by Slingge on 2019/3/1
 */
class AddFriendActivity : BaseActivity<ActivityAddfriendBinding, AddFriendViewModel>() {

    override fun getBaseViewModel() = AddFriendViewModel()

    override fun getLayoutId() = R.layout.activity_addfriend

    override fun init() {
        initTitle("添加")
        var model = intent.getSerializableExtra("model") as DataListModel
        viewModel?.let {
            it.bind = binding
        }

        GlideUtil.glideHeaderLoad(this, model.icon, iv_header)
        if (null != model.age)
            tv_age?.text = (model.age)
        else
            tv_age?.text = ("")

        if (null != model.nickname)
            tv_name?.text = (model.nickname)
        else
            tv_name?.text = ("")
        if (null != model.job)
            tv_zhiye?.text = ("职业：" + model.job)
        if (null != model.plan)
            tv_emotional?.text = ("情感计划：" + model.plan)
        if (null != model.introduction)
            tv_autograph?.text = ("个人签名：" + model.introduction)
        if (null != model.credit)
            tv_reputation?.text = ("信誉值：" + model.credit)
        if (null != model.polite)
            tv_feel?.text = ("言礼值：" + model.polite)
        if (null != model.safe)
            tv_security?.text = ("综合安全值：" + model.safe)

        when (model.permission.size) {
            0 -> {
                iv_v1.visibility = (View.GONE)
                iv_v2.visibility = (View.GONE)
                iv_v3.visibility = (View.GONE)
                iv_v4.visibility = (View.GONE)
                iv_v5.visibility = (View.GONE)
            }
            1 -> {
                iv_v1.visibility = (View.VISIBLE)
                iv_v2.visibility = (View.GONE)
                iv_v3.visibility = (View.GONE)
                iv_v4.visibility = (View.GONE)
                iv_v5.visibility = (View.GONE)
            }
            2 -> {
                iv_v1.visibility = (View.VISIBLE)
                iv_v2.visibility = (View.VISIBLE)
                iv_v3.visibility = (View.GONE)
                iv_v4.visibility = (View.GONE)
                iv_v5.visibility = (View.GONE)
            }
            3 -> {
                iv_v1.visibility = (View.VISIBLE)
                iv_v2.visibility = (View.VISIBLE)
                iv_v3.visibility = (View.VISIBLE)
                iv_v4.visibility = (View.GONE)
                iv_v5.visibility = (View.GONE)
            }
            4 -> {
                iv_v1.visibility = (View.VISIBLE)
                iv_v2.visibility = (View.VISIBLE)
                iv_v3.visibility = (View.VISIBLE)
                iv_v4.visibility = (View.VISIBLE)
                iv_v5.visibility = (View.GONE)
            }
            5 -> {
                iv_v1.visibility = (View.VISIBLE)
                iv_v2.visibility = (View.VISIBLE)
                iv_v3.visibility = (View.VISIBLE)
                iv_v4.visibility = (View.VISIBLE)
                iv_v5.visibility = (View.VISIBLE)
            }
        }


        if (null != model.sex) {
            when (model.sex) {
                "0"//女
                -> {
                    tv_age?.setBackgroundResource(R.drawable.bg_girl)
                    tv_age?.setTextColor(resources?.getColor(R.color.girl)!!)
                    AbStrUtil.setDrawableLeft(this, R.drawable.ic_girl, tv_age, 3)
                }
                "1"//男
                -> {
                    tv_age?.setBackgroundResource(R.drawable.thems_bg35)
                    tv_age?.setTextColor(resources?.getColor(R.color.colorThemes)!!)
                    AbStrUtil.setDrawableLeft(this, R.drawable.ic_boy, tv_age, 3)
                }
            }
        }

        tv_add.setOnClickListener {
            if (TextUtils.isEmpty(et_msg.text.toString())) {
                ToastUtil.showTopSnackBar(this, "请输入招呼内容！")
            } else {
                viewModel?.addFriend(model.userId, et_msg.text.toString())
            }
        }

    }

}