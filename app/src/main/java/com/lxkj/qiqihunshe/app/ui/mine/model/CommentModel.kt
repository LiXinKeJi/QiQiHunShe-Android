package com.lxkj.qiqihunshe.app.ui.mine.model

import com.lxkj.qiqihunshe.app.base.BaseModel

/**
 * Created by Slingge on 2019/2/25
 */
class CommentModel : BaseModel() {


    var totalPage = 1

    var commentCount = 0

    var dataList = ArrayList<dataModel>()

    class dataModel {
        var icon = ""
        var nickname = ""
        var sex = ""
        var age = ""
        var content = ""

        var distance = ""
        var adtime = ""
    }


}