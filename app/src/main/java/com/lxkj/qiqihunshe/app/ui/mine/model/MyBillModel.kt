package com.lxkj.qiqihunshe.app.ui.mine.model

import com.lxkj.qiqihunshe.app.base.BaseModel

/**
 * 我的账单
 * Created by Slingge on 2019/2/22
 */
class MyBillModel : BaseModel() {

    var totalPage = 1

    var dataList = ArrayList<dataModel>()


    class dataModel {
        var title = ""
        var money = ""
        var type = ""
        var adtime = ""
    }


}