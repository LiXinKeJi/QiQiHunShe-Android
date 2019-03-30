package com.lxkj.qiqihunshe.app.ui.xiaoxi.model

import java.io.Serializable

/**
 * Created by Slingge on 2019/3/20
 */
class FindUserRelationshipModel : Serializable {

    var dataList = ArrayList<dataModel>()

    class dataModel : Serializable {
        var userId = ""
        var icon = ""

        var nickname = ""
        var relationship = ""// 0:临时，1:相识，2:约会,3:牵手,4:拉黑

        var yuejian = ""// 是否为约见中人 0否 1是
    }


}