package com.lxkj.qiqihunshe.app.ui.mine.model

import com.lxkj.qiqihunshe.BR
import com.lxkj.qiqihunshe.app.base.BaseModel
import com.lxkj.qiqihunshe.app.util.StaticUtil

/**
 * Created by Slingge on 2019/2/25
 */
class ReleaseDynamicModel : BaseModel() {

    val cmd = "addDongtai"
    val uid = StaticUtil.uid

    var content = ""

    var images = ""
    var location = ""
    var lon = ""


    var lat = ""
    var status = "0"//0公开 1隐私
    var type = ""//0普通动态 1情感动态


    fun notiCinten() {
        notifyPropertyChanged(BR.content)

    }

}