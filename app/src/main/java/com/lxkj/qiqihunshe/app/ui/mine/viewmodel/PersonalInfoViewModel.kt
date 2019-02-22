package com.lxkj.qiqihunshe.app.ui.mine.viewmodel

import com.lxkj.qiqihunshe.app.base.BaseViewModel
import com.lxkj.qiqihunshe.app.util.GlideImageLoader
import com.lxkj.qiqihunshe.databinding.ActivityPersonalInfoBinding
import com.youth.banner.BannerConfig
import java.util.*

/**
 * Created by Slingge on 2019/2/21
 */
class PersonalInfoViewModel : BaseViewModel() {

    var bind: ActivityPersonalInfoBinding? = null


    private val array = arrayOf(
        "http://ww4.sinaimg.cn/large/006uZZy8jw1faic1xjab4j30ci08cjrv.jpg",
        "http://ww4.sinaimg.cn/large/006uZZy8jw1faic21363tj30ci08ct96.jpg",
        "http://ww4.sinaimg.cn/large/006uZZy8jw1faic259ohaj30ci08c74r.jpg",
        "http://ww4.sinaimg.cn/large/006uZZy8jw1faic2b16zuj30ci08cwf4.jpg",
        "http://ww4.sinaimg.cn/large/006uZZy8jw1faic2e7vsaj30ci08cglz.jpg"
    )

    fun initViewModel() {

        bind!!.banner.setImages(Arrays.asList(array))
            .setImageLoader(GlideImageLoader())
            .start()
        bind!!.banner.updateBannerStyle(BannerConfig.NUM_INDICATOR)


    }


}