package com.lxkj.qiqihunshe.app.ui.mine.activity

import com.lxkj.qiqihunshe.R
import com.lxkj.qiqihunshe.app.base.BaseActivity
import com.lxkj.qiqihunshe.app.ui.mine.viewmodel.WebViewViewModel
import com.lxkj.qiqihunshe.databinding.ActivityWebviewBinding

/**
 * Created by Slingge on 2019/2/21
 */
class WebViewActivity : BaseActivity<ActivityWebviewBinding, WebViewViewModel>() {


    override fun getBaseViewModel() = WebViewViewModel()

    override fun getLayoutId() = R.layout.activity_webview

    override fun init() {
        initTitle("详情")

        viewModel?.let {
            binding.viewmodel=it
            it.bind=binding
            it.setUrl(intent.getStringExtra("url"))
        }

    }
}