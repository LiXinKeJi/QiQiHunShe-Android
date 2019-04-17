package com.lxkj.qiqihunshe.app.ui.mine.activity

import android.os.Bundle
import android.view.View
import com.lxkj.qiqihunshe.R
import com.lxkj.qiqihunshe.app.MyApplication
import com.lxkj.qiqihunshe.app.base.BaseActivity
import com.lxkj.qiqihunshe.app.ui.mine.viewmodel.ReleaseInvitationTypeViewModel
import com.lxkj.qiqihunshe.app.util.StaticUtil
import com.lxkj.qiqihunshe.databinding.ActivityReleaseInvitationTypeBinding
import kotlinx.android.synthetic.main.activity_release_invitation_type.*

/**
 * Created by Slingge on 2019/2/25
 */
class ReleaseInvitationTypeActivity :
    BaseActivity<ActivityReleaseInvitationTypeBinding, ReleaseInvitationTypeViewModel>(), View.OnClickListener {


    override fun getBaseViewModel() = ReleaseInvitationTypeViewModel()

    override fun getLayoutId() = R.layout.activity_release_invitation_type

    private var type = -1//0普通邀约，1情感邀约


    override fun init() {
        initTitle("选择发布类型")

        iv_chifan.setOnClickListener(this)
        iv_lvxing.setOnClickListener(this)
        iv_yundong.setOnClickListener(this)
        iv_dianying.setOnClickListener(this)
        iv_qita.setOnClickListener(this)

    }


    override fun onClick(v: View?) {
        if(StaticUtil.isBail(this)){
            return
        }
        val bundle = Bundle()
        when (v?.id) {
            R.id.iv_chifan -> {
                bundle.putInt("flag", 1)
            }
            R.id.iv_lvxing -> {
                bundle.putInt("flag", 2)
            }
            R.id.iv_yundong -> {
                bundle.putInt("flag", 3)
            }
            R.id.iv_dianying -> {
                bundle.putInt("flag", 4)
            }
            R.id.iv_qita -> {
                bundle.putInt("flag", 5)
            }
        }
        bundle.putInt("type", intent.getIntExtra("type", -1))
        MyApplication.openActivity(this, ReleaseInvitationActivity::class.java, bundle)
    }

}