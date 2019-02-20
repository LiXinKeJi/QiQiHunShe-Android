package com.lxkj.qiqihunshe.app.ui.mine.activity

import com.lxkj.qiqihunshe.R
import com.lxkj.qiqihunshe.app.base.BaseActivity
import com.lxkj.qiqihunshe.app.ui.mine.model.FeedBackModel
import com.lxkj.qiqihunshe.app.ui.mine.viewmodel.FeedBackViewModel
import com.lxkj.qiqihunshe.app.util.ToastUtil
import com.lxkj.qiqihunshe.databinding.ActivityFeedbackBinding
import kotlinx.android.synthetic.main.activity_feedback.*

/**
 * Created by Slingge on 2019/2/20
 */
class FeedBackActivity : BaseActivity<ActivityFeedbackBinding, FeedBackViewModel>() {


    override fun getBaseViewModel() = FeedBackViewModel()

    override fun getLayoutId() = R.layout.activity_feedback

    private val model by lazy { FeedBackModel() }

    override fun init() {
        WhiteStatusBar()
        initTitle("意见反馈")

        tv_enter.setOnClickListener {
            model.noify()
            ToastUtil.showTopSnackBar(this,model.content)
        }

        viewModel?.let {
            binding.viewmodel = it
            binding.model = model
            it.bind = binding
            it.initViewModel()
        }
    }


}