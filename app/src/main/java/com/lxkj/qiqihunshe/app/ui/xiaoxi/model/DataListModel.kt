package com.lxkj.qiqihunshe.app.ui.xiaoxi.model

import java.io.Serializable

/**
 * Created by kxn on 2019/3/5 0005.
 */
class DataListModel : Serializable {
    var userId = ""// 好友ID
    var icon = ""// 头像
    var sex = ""// 性别 0女 1男
    var realname = ""// 姓名
    var age = ""// 年龄
    var job = "" // 职业
    var fid = ""// 请求ID
    var nickname = ""//昵称",
    var content = ""//你好", // 打招呼
    var status = ""// 状态 0待审核 1同意 2拒绝
    var adtime = ""// 申请时间
    var permission = ArrayList<String>() //["1","2"],// 权限 1小七推荐 2约见点评 3经济查找 4定制推荐 5牵引安排
    var plan = ""//情感计划",
    var introduction = ""//个性签名",
    var safe = ""// 安全总值
    var credit = ""// 信誉值
    var polite = ""// 言礼值
    var identity = ""// 1单身 2约会 3牵手
    var type = ""// 消息类型 1互动通知 2小七提醒  1我点评的 2对方点评我的
    var count = "" // 未读消息数量
    var msgId = ""// 消息ID
    var title = ""// 标题

    var userIcon = "" // 头像
    var userNickname = "" //昵称",
    var userSex = "" // 0女 1男
    var userAge = "" // 年龄
    var userJob = "" // 职业
    var time = "" // 时间

    var myIcon = ""// 当前用户的头像
    var myNickname = ""// 当前用户的昵称
    var match = ""// 相符值
    var impression = ""// 印象值

    var yaoyueId = ""// 邀约ID
    var category = ""// 类型 0普通邀约 1情感征婚
    var starttime = ""// 活动时间
    var condition = ""//限制范围",
    var fee = ""// 消费 0AA 1对方买单 2我买单
    var image = ArrayList<String>() //":["",""],// 图片
    var yes = ""// 报名人数
    var wait = ""// 待审核人数
    var no = "" // 拒绝人数
    var address = "" //活动地点",

    var birthplace= "" // 家乡
    var residence= "" // 现居
}