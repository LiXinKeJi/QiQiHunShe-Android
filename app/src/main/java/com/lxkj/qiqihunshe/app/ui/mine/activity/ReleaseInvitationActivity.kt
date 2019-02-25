package com.lxkj.qiqihunshe.app.ui.mine.activity

import com.lxkj.qiqihunshe.R
import com.lxkj.qiqihunshe.app.base.BaseActivity
import com.lxkj.qiqihunshe.app.ui.mine.viewmodel.ReleaseInvitationViewModel
import com.lxkj.qiqihunshe.app.util.ToastUtil
import com.lxkj.qiqihunshe.databinding.ActivityReleaseInvitationBinding

/**
 * Created by Slingge on 2019/2/25
 */
class ReleaseInvitationActivity :
    BaseActivity<ActivityReleaseInvitationBinding, ReleaseInvitationViewModel>() {

    private var flag = -1//0吃饭，1旅行，2运动，3电影，4其他

    override fun getBaseViewModel() = ReleaseInvitationViewModel()

    override fun getLayoutId() = R.layout.activity_release_invitation

    override fun init() {
        initTitle("发布邀约活动")
        flag = intent.getIntExtra("flag", -1)
        ToastUtil.showToast(flag.toString())

        viewModel?.let {
            binding.viewmodel = it
            it.bind = binding
            it.initViewModel()
        }

    }


}