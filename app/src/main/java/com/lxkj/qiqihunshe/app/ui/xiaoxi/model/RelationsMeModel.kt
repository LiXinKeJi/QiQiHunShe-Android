package com.lxkj.qiqihunshe.app.ui.xiaoxi.model

import com.lxkj.qiqihunshe.app.base.BaseModel

/**
 * Created by Slingge on 2019/4/3
 */
class RelationsMeModel : BaseModel() {


    var dataList = ArrayList<DataModel>()

    class DataModel {
        var userId = ""
        var icon = ""
        var nickname = ""

        var relationship = ""
        var yuejian = ""
    }


}