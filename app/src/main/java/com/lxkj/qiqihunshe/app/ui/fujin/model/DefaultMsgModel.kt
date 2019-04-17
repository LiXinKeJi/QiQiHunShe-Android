package com.lxkj.qiqihunshe.app.ui.fujin.model

import com.lxkj.qiqihunshe.app.base.BaseModel

/**
 * Created by Slingge on 2019/4/17
 */
class DefaultMsgModel : BaseModel() {

    var dataList = ArrayList<dataModel>()

    class dataModel {
        var content = ""
        var status = ""// 是否默认 0否 1是
    }


}