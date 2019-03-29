package com.lxkj.qiqihunshe.app.ui.mine.model

import com.lxkj.qiqihunshe.app.base.BaseModel

/**
 * Created by Slingge on 2019/2/25
 */
class SpaceInvitationModel : BaseModel() {


    var totalPage = 1

    var  dataList=ArrayList<dataModel>()

    class dataModel {

        var yaoyueId = ""
        var title = ""

        var content = ""

        var starttime = ""// 活动时间
        var condition = ""

        var fee = ""// 消费 0AA 1对方买单 2我买单
        var sex = ""// 仅限性别 0女 1男

        var image = ArrayList<String>()// 图片
        var address = ""

        var adtime = ""// 发布时间
        var yes = ""// 报名人数

        var wait = ""// 待审核人数
        var no = ""// 拒绝人数

        var age=""
        var nickname=""

        var job=""
    }

}