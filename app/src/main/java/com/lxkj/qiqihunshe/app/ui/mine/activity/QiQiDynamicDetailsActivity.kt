package com.lxkj.qiqihunshe.app.ui.mine.activity

import com.lxkj.qiqihunshe.R
import com.lxkj.qiqihunshe.app.base.BaseActivity
import com.lxkj.qiqihunshe.app.ui.mine.model.QiQiDynamicDetailsModel
import com.lxkj.qiqihunshe.app.ui.mine.model.QiQiDynamicModel
import com.lxkj.qiqihunshe.app.ui.mine.viewmodel.QiQiDynamicDetailsViewModel
import com.lxkj.qiqihunshe.databinding.ActivityQiqiDynamicDetailsBinding
import kotlinx.android.synthetic.main.activity_qiqi_dynamic_details.*

/**
 * Created by Slingge on 2019/2/21
 */
class QiQiDynamicDetailsActivity : BaseActivity<ActivityQiqiDynamicDetailsBinding, QiQiDynamicDetailsViewModel>() {


    override fun getBaseViewModel() = QiQiDynamicDetailsViewModel()

    override fun getLayoutId() = R.layout.activity_qiqi_dynamic_details

    override fun init() {
        initTitle("活动详情")

        var qiQiDynamicModel=intent.getSerializableExtra("data") as QiQiDynamicModel



        viewModel?.let {
            binding.viewmodel=it


        }

        tv_title.setText(qiQiDynamicModel.title)
        tv_brief.setText("简介:"+qiQiDynamicModel.introduction)
       // tv_time.setText(qiQiDynamicModel.)
    }

}