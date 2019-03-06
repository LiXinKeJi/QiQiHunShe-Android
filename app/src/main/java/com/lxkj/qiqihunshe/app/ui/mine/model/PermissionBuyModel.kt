package com.lxkj.qiqihunshe.app.ui.mine.model

import com.lxkj.qiqihunshe.app.base.BaseModel

/**
 * Created by Slingge on 2019/3/6
 */
class PermissionBuyModel : BaseModel() {


    var dataList = ArrayList<dataModel>()

    class dataModel {
        var type = ""
        var price = ""
        var content = ""
        var endTime = ""
    }

}