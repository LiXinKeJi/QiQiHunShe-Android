package com.lxkj.qiqihunshe.app.ui.mine.viewmodel

import android.annotation.SuppressLint
import com.lxkj.qiqihunshe.app.base.BaseViewModel
import com.lxkj.qiqihunshe.databinding.ActivityWebviewBinding
import com.tencent.smtt.sdk.WebView

/**
 * Created by Slingge on 2019/2/21
 */
class WebViewViewModel : BaseViewModel() {


    var bind: ActivityWebviewBinding? = null

    private var webView: WebView? = null


    fun setUrl(url:String) {
        webView = bind!!.mywebview.webView
        initWebViewSettings()

        webView?.loadUrl(url)
    }


    private fun initWebViewSettings() {
        val settings = webView!!.settings
        // 设置支持js
        settings.javaScriptEnabled = true
        settings.javaScriptCanOpenWindowsAutomatically = true
        settings.loadWithOverviewMode = true
        settings.setAppCacheEnabled(true)
        settings.domStorageEnabled = true
        // 关闭缓存
        //        settings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        // 支持自动加载图片
        settings.loadsImagesAutomatically = true
        // 设置出现缩放工具
        settings.setSupportZoom(true)
        settings.builtInZoomControls = true
        // 扩大比例的缩放
        settings.useWideViewPort = true
        // 自适应屏幕
        settings.loadWithOverviewMode = true
    }

}