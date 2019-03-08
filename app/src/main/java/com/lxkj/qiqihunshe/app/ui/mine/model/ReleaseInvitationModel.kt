package com.lxkj.qiqihunshe.app.ui.mine.model

import android.databinding.Bindable
import com.lxkj.qiqihunshe.BR
import com.lxkj.qiqihunshe.app.base.BaseModel
import com.lxkj.qiqihunshe.app.util.StaticUtil

/**
 * Created by Slingge on 2019/2/25
 */
class ReleaseInvitationModel : BaseModel() {


    val cmd = "addYaoyue"
    val uid = StaticUtil.uid

    var typeId = ""
    var category = ""//0普通邀约 1征婚

    @Bindable
    var title = ""
    @Bindable
    var content = ""

    var image = ""
    var status = ""//0公开 1隐私

    var starttime = ""//活动开始时间
    var address = ""

    var lon = ""
    var lat = ""

    @Bindable
    var condition = ""//限制范围
    var sex = ""//性别 0女 1男 2不限

    var fee = ""//费用 0AA 1对方买单 2我买单


    fun notif() {
        notifyPropertyChanged(BR.title)
        notifyPropertyChanged(BR.content)
        notifyPropertyChanged(BR.condition)
    }

}