package com.lxkj.qiqihunshe.app.ui.mine.model

import com.lxkj.qiqihunshe.app.base.BaseModel
import com.lxkj.qiqihunshe.app.util.StaticUtil

/**
 * Created by Slingge on 2019/2/25
 */
class ReleaseSkillModel : BaseModel() {

    val cmd = "addCaiyi"

    val uid=StaticUtil.uid

    var title=""
    var content=""
    var image=""//封面图
    var video=""
    var location=""
    var lon=""
    var lat=""

    var videoPath = ""


}