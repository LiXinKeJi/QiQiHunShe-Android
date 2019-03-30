package com.lxkj.qiqihunshe.app.ui.mine.model

import com.lxkj.qiqihunshe.app.base.BaseModel

/**
 * Created by Slingge on 2019/2/21
 */
class ReputationBaoModel : BaseModel() {

    var safe = ""// 安全总值

    var credit = ""// 信誉值

    var polite = ""// 言礼值

    var behavior = "" // 行为值
    var bail = "" // 信誉金 0代表未缴纳
    var refundStatus = ""// 信誉金退还状态 0没有退还申请(用户可申请）1有审核中的退还申请(不可申请)

    var totalPage = 1
    var dataList = ArrayList<dataModel>()

    class dataModel {
        var title = ""//
        var point = ""// 分值
        var status = ""// 0减少 1增加
        var adtime = ""//  日期
    }

}