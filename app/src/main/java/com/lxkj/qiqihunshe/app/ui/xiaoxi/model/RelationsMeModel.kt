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

        var relationship = ""// 0:临时，1:相识，2:约会,3:牵手,4:拉黑
        var yuejian = ""//是否为约见中人 0否 1是

        var isPipei=""//// 是否为匹配用户 0否 1是
    }


}