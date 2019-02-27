package com.lxkj.qiqihunshe.app.ui.mine.activity

import android.view.View
import com.lxkj.qiqihunshe.R
import com.lxkj.qiqihunshe.app.base.BaseActivity
import com.lxkj.qiqihunshe.app.ui.dialog.ReportDialog1
import com.lxkj.qiqihunshe.app.ui.mine.viewmodel.PersonInvitationDetailsViewModel
import com.lxkj.qiqihunshe.databinding.ActivityPersonInvitationDetailsBinding
import kotlinx.android.synthetic.main.activity_person_invitation_details.*

/**
 * 邀约详情
 * Created by Slingge on 2019/2/22
 */
class PersonInvitationDetailsActivity :
    BaseActivity<ActivityPersonInvitationDetailsBinding, PersonInvitationDetailsViewModel>(),
    View.OnClickListener {


    override fun getBaseViewModel() = PersonInvitationDetailsViewModel()

    override fun getLayoutId() = R.layout.activity_person_invitation_details

    override fun init() {
        initTitle("邀约详情")
        WhiteStatusBar()

        tv_report.setOnClickListener(this)


        viewModel?.let {
            binding.viewmodel = it

        }

    }


    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.tv_report -> {
                ReportDialog1.show(this)
            }
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        ReportDialog1.diss()
    }

}