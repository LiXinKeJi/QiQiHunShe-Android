package com.lxkj.qiqihunshe.app.customview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import com.tencent.smtt.sdk.WebView;

/**
 * @author mao
 * @version v1.0
 * @date 2017/4/7 21:18
 * @des 腾讯TBS浏览服务x5内核WebView
 * @link http://blog.csdn.net/github_2011/article/details/71600734
 */
public class X5WebView extends WebView {

    public X5WebView(Context arg0) {
        super(arg0);
        setBackgroundColor(85621);
    }

    @SuppressLint("SetJavaScriptEnabled")
    public X5WebView(Context arg0, AttributeSet arg1) {
        super(arg0, arg1);

        // this.setWebChromeClient(chromeClient);
        // WebStorage webStorage = WebStorage.getInstance();
//        initWebViewSettings();
        this.getView().setClickable(true);
    }



}
