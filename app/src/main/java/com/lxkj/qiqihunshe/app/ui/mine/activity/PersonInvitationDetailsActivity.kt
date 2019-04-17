package com.lxkj.qiqihunshe.app.ui.mine.activity

import android.view.View
import com.lxkj.qiqihunshe.R
import com.lxkj.qiqihunshe.app.base.BaseActivity
import com.lxkj.qiqihunshe.app.retrofitnet.bindLifeCycle
import com.lxkj.qiqihunshe.app.ui.dialog.DynamicSignUpAfterDialog
import com.lxkj.qiqihunshe.app.ui.dialog.ReportDialog1
import com.lxkj.qiqihunshe.app.ui.mine.viewmodel.PersonInvitationDetailsViewModel
import com.lxkj.qiqihunshe.app.util.SeePhotoViewUtil
import com.lxkj.qiqihunshe.app.util.StaticUtil
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
        tv_signup.setOnClickListener(this)

        viewModel?.let {
            binding.viewmodel = it
            it.bind = binding
            it.yaoyueId = intent.getStringExtra("id")
            it.initViewmodel()
            it.getYaoyueDetails().bindLifeCycle(this).subscribe({ }, { toastFailure(it) })

            it.imageAdapter.setMyListener { itemBean, position ->
                SeePhotoViewUtil.toPhotoView(this, it.model.image, position)
            }
        }
    }


    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.tv_report -> {
                ReportDialog1.getReportList(this, "3", object : ReportDialog1.ReportCallBack {
                    override fun report(report: String) {
                        viewModel!!.yaoyueReport(report).bindLifeCycle(this@PersonInvitationDetailsActivity)
                            .subscribe({}, { toastFailure(it) })
                    }
                })
            }
            R.id.tv_signup -> {
                if(StaticUtil.isBail(this)){
                    return
                }
                viewModel!!.singUp().bindLifeCycle(this).subscribe({}, { toastFailure(it) })
            }
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        ReportDialog1.diss()
        DynamicSignUpAfterDialog.diss()
    }

}