package com.lxkj.qiqihunshe.app.ui.mine.activity

import android.view.View
import com.lxkj.qiqihunshe.R
import com.lxkj.qiqihunshe.app.base.BaseActivity
import com.lxkj.qiqihunshe.app.retrofitnet.bindLifeCycle
import com.lxkj.qiqihunshe.app.ui.dialog.EditDialog
import com.lxkj.qiqihunshe.app.ui.mine.viewmodel.MyInvitationDetailsViewModel
import com.lxkj.qiqihunshe.app.ui.model.EventCmdModel
import com.lxkj.qiqihunshe.app.util.EventBusCmd
import com.lxkj.qiqihunshe.databinding.ActivityMyinvitationDetailsBinding
import kotlinx.android.synthetic.main.activity_myinvitation_details.*
import kotlinx.android.synthetic.main.include_title.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

/**
 * 我的邀约详情
 * Created by Slingge on 2019/2/25
 */
class MyInvitationDetailsActivity :
    BaseActivity<ActivityMyinvitationDetailsBinding, MyInvitationDetailsViewModel>() {


    override fun getBaseViewModel() = MyInvitationDetailsViewModel()

    override fun getLayoutId() = R.layout.activity_myinvitation_details


    override fun init() {
        EventBus.getDefault().register(this)
        initTitle("邀约详情")
        viewModel?.let {
            it.bind = binding
            it.yaoyueId = intent.getStringExtra("id")
            it.init()

            it.getYaoyueDetails().bindLifeCycle(this).subscribe({ setData() }, { it })
        }

        tv_right.visibility = View.VISIBLE
        tv_right.text = "删除"
        tv_right.setOnClickListener {
            viewModel!!.DelInvitation().bindLifeCycle(this).subscribe({},{toastFailure(it)})
        }
    }


    fun setData() {
        viewModel?.let {
            if (it.model.sexOnly == "1") {
                tv_limit.text = "仅限：男"
            } else if (it.model.sexOnly == "0") {
                tv_limit.text = "仅限：女"
            } else {
                tv_limit.text = "仅限：无性别限制"
            }
            if (it.model.fee == "0") {
                tv_consu.text = "消费：AA"
            } else if (it.model.fee == "1") {
                tv_consu.text = "消费：对方买单"
            } else {
                tv_consu.text = "消费：我买单"
            }
        }

    }


    @Subscribe
    fun onEvent(model: EventCmdModel) {
        if (model.cmd == "agree") {//同意
            viewModel!!.agree(model.res.toInt(), "1", "")
        } else if (model.cmd == "del") {//删除
            EditDialog.show(this, object : EditDialog.EditCallBack {
                override fun edit(str: String) {
                    viewModel!!.agree(model.res.toInt(), "2", str)
                }
            })
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
        EditDialog.diss()
    }


}