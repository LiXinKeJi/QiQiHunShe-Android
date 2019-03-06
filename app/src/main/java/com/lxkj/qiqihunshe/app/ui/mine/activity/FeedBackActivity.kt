package com.lxkj.qiqihunshe.app.ui.mine.activity

import android.text.TextUtils
import android.util.Log
import com.lxkj.qiqihunshe.R
import com.lxkj.qiqihunshe.app.base.BaseActivity
import com.lxkj.qiqihunshe.app.retrofitnet.bindLifeCycle
import com.lxkj.qiqihunshe.app.ui.mine.model.FeedBackModel
import com.lxkj.qiqihunshe.app.ui.mine.viewmodel.FeedBackViewModel
import com.lxkj.qiqihunshe.app.util.Md5Util
import com.lxkj.qiqihunshe.app.util.StaticUtil
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
        initTitle("意见反馈0")

        tv_enter.setOnClickListener {
            model.noify()
            if(TextUtils.isEmpty(model.content)){
                ToastUtil.showToast(getString(R.string.content_isnot_null))
                return@setOnClickListener
            }
            val json = "{\"cmd\":\"feedback"  +
                    "\",\"uid\":\"" + StaticUtil.uid+
                    "\",\"content\":\"" + model.content+
                    "\"}"

            Log.i("sss","FeedBackActivity------------------->"+json)

            viewModel!!.feedback(json).bindLifeCycle(this)
                .subscribe({}, { toastFailure(it) })
            //ToastUtil.showTopSnackBar(this,model.content)
        }

        viewModel?.let {
            binding.viewmodel = it
            binding.model = model
            it.bind = binding
            it.initViewModel()
        }
    }


}