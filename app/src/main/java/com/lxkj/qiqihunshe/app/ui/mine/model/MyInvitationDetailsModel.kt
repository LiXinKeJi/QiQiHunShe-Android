package com.lxkj.qiqihunshe.app.ui.mine.model

import com.lxkj.qiqihunshe.app.base.BaseModel

/**
 * Created by Slingge on 2019/2/25
 */
class MyInvitationDetailsModel:BaseModel() {



    var icon=""
    var identity=""// 1单身 2约会 3牵手
    var nickname=""

    var sex=""// 0女 1男
    var age=""
    var job=""// 职业

    var title=""
    var content=""
    var starttime=""// 活动时间


    var condition=""
    var fee=""// 消费 0AA 1对方买单 2我买单
    var sexOnly=""// 仅限性别 0女 1男


    var image=ArrayList<String>()
    var address=""
    var adtime=""// 发布时间

    var join=""// 0未报名 1已报名
    var permission=ArrayList<String>()// 权限 1小七推荐 2约见点评 3经济查找 4定制推荐 5牵引安排

    var dataList=ArrayList<dataModel>()

    class dataModel{

        var joinId=""// 报名ID
        var userIcon=""
        var userNickname=""// 活动时间

        var userSex=""// 0女 1男
        var userAge=""// 年龄
        var userJob=""// // 职业

        var userIdentity=""// 1单身 2约会 3牵手
        var userPermission=ArrayList<String>()// 权限 1小七推荐 2约见点评 3经济查找 4定制推荐 5牵引安排
        var plan=""// 情感计划

        var time=""// 报名时间
        var safe=""// 安全值
        var credit=""// 信誉值

        var polite=""// 言礼值,
        var type=""// 状态 0待审核 1同意 2拒绝
        var reason=""// 拒绝原因

        var introduction=""// 个性签名

    }



}