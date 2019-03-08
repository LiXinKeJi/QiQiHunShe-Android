package com.lxkj.qiqihunshe.app.ui.shouye.model

/**
 * Created by kxn on 2019/3/8 0008.
 */
class DataListModel{
    var caiyiId = ""// 才艺ID
    var userId = ""// 发布人ID
    var userName = ""// 发布人昵称
    var image = ""// 封面图
    var videoUrl = ""// 视频地址
    var icon = ""// 发布人头像
    var voice = ""// 语音费用
    var video = ""// 视频费用
    var title = ""//标题",
    var content = ""//内容",
    var adtime = ""// 发布时间
    var count = ""// 播放量
    var location = ""// 发布位置

    var comment = ""//评论内容",
    var sex = ""// 0女 1男
    var nickname = ""//昵称",
    var age = ""// 年龄
    var job = ""// 职业
    var plan = ""//情感计划",
    var introduction = ""//个性签名",
    var safe = ""// 安全总值
    var credit = ""// 信誉值
    var polite = ""// 言礼值
    var identity = ""// 1单身 2约会 3牵手
    var distance = ""//距离
    var qid = ""// 问题ID
    var answerList = ArrayList<AnswerListModel>()
}