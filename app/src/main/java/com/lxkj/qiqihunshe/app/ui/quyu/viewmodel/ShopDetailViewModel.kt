package com.lxkj.qiqihunshe.app.ui.quyu.viewmodel

import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import android.view.View
import android.webkit.*
import android.widget.ImageView
import cn.bingoogolapple.bgabanner.BGABanner
import com.google.gson.Gson
import com.lxkj.qiqihunshe.app.base.BaseViewModel
import com.lxkj.qiqihunshe.app.retrofitnet.SingleCompose
import com.lxkj.qiqihunshe.app.retrofitnet.SingleObserverInterface
import com.lxkj.qiqihunshe.app.retrofitnet.async
import com.lxkj.qiqihunshe.app.ui.quyu.model.ShopDetailModel
import com.lxkj.qiqihunshe.app.util.GlideUtil
import com.lxkj.qiqihunshe.app.util.StringUtil
import com.lxkj.qiqihunshe.databinding.ActivityShopDetailBinding
import io.reactivex.Single

/**
 * Created by kxn on 2019/3/1 0001.
 */
class ShopDetailViewModel : BaseViewModel() {
    var model : ShopDetailModel? = null
    var bind: ActivityShopDetailBinding? = null
    //获取店铺详情
    fun getshopDetail(json: String): Single<String> =
        retrofit.getData(json)
            .async()
            .compose(SingleCompose.compose(object : SingleObserverInterface {
                override fun onSuccess(response: String) {
                    model = Gson().fromJson(response, ShopDetailModel::class.java)
                    setData(model!!)
                }
            },activity))

    fun setData(model: ShopDetailModel) {
        bind?.addressTv?.text = model?.address
        bind?.nameTv?.text = model?.shopName
        bind?.priceTv?.text = "人均价格：¥" + model?.price + "起"
        val bannerAdapter = object : BGABanner.Adapter<ImageView, String> {
            override fun fillBannerItem(banner: BGABanner, view: ImageView, model: String?, position: Int) {
                GlideUtil.glideLoad(activity, model, view)
            }
        }
        bind?.banner?.setAdapter(bannerAdapter)
        bind?.banner?.setDelegate(object : BGABanner.Delegate<ImageView, String> {
            override fun onBannerItemClick(banner: BGABanner, itemView: ImageView, model: String?, position: Int) {

            }
        })
        bind?.banner?.setData(model.banner,null)

        if (!StringUtil.isEmpty(model.url)) {
            bind?.webView?.setWebChromeClient(WebChromeClient())
            bind?.webView?.setWebViewClient(object : WebViewClient() {
                override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                    view.loadUrl(url)
                    return true
                }

                override fun onPageStarted(view: WebView, url: String, favicon: Bitmap) {
                    super.onPageStarted(view, url, favicon)
                }

                override fun onPageFinished(view: WebView, url: String) {
                    view.loadUrl("javascript:window.local_obj.showSource('<head>'+" + "document.getElementsByTagName('html')[0].innerHTML+'</head>');")
                    super.onPageFinished(view, url)
                }
            })
            val webSettings = bind?.webView?.getSettings()
            webSettings?.setJavaScriptEnabled(true)
            webSettings?.setPluginState(WebSettings.PluginState.ON)
            webSettings?.setUseWideViewPort(true) // 关键点
            webSettings?.setAllowFileAccess(true) // 允许访问文件
            webSettings?.setSupportZoom(true) // 支持缩放
            webSettings?.setLoadWithOverviewMode(true)
            webSettings?.setCacheMode(WebSettings.LOAD_NO_CACHE) // 不加载缓存内容
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                webSettings?.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW)
            }
            bind?.webView?.getSettings()?.setUseWideViewPort(true)
            bind?.webView?.addJavascriptInterface(InJavaScriptLocalObj(), "local_obj")
            bind?.webView?.loadUrl(model.url)
        }
    }

    inner class InJavaScriptLocalObj {
        @JavascriptInterface
        fun showSource(html: String) {
            refreshHtmlContent(html)
        }
    }

    private fun refreshHtmlContent(html: String) {
        bind?.webView?.loadData(getHtmlData(html), "text/html", "UTF-8")
    }

    fun getHtmlData(bodyHTML: String): String {

        val head = ("<head>"
                + "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0, user-scalable=no\"> "
                + "<style>img{max-width: 100%; width:auto; height:auto;}</style>"
                + "</head>")
        return "<html>$head<body>$bodyHTML</body></html>"
    }

}