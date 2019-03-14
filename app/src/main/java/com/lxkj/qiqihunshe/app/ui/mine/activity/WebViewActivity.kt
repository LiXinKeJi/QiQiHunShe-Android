package com.lxkj.qiqihunshe.app.ui.mine.activity

import com.lxkj.qiqihunshe.R
import com.lxkj.qiqihunshe.app.base.BaseActivity
import com.lxkj.qiqihunshe.app.retrofitnet.RetrofitUtil
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
            binding.viewmodel = it
            it.bind = binding
            val flag = intent.getIntExtra("flag", -1)//0注册协议
            if (flag == 0) {
                initTitle("注册协议")
                it.setUrl(RetrofitUtil.url + "display/agreement?id=1")
            } else {
                it.setUrl(intent.getStringExtra("url"))
            }
        }

    }


}