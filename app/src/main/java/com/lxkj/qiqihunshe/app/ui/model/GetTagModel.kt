package com.lxkj.qiqihunshe.app.ui.model

import com.lxkj.qiqihunshe.app.base.BaseModel

/**
 * Created by Slingge on 2019/3/4
 */
class GetTagModel : BaseModel() {

    val cmd = "getTags"

    var type: String = ""
    var sex: String = ""


    var dataList = ArrayList<String>()


}