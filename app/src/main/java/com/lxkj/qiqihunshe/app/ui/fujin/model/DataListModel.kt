package com.lxkj.qiqihunshe.app.ui.fujin.model

import java.io.Serializable

/**
 * Created by kxn on 2019/3/7 0007.
 */
class DataListModel : Serializable {
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

    var nickname = ""//昵称",
    var sex = ""// 0女 1男
    var age = ""// 年龄
    var comment = ""//评论内容",
    var distance = ""// 距离

   var love=""// 0不喜欢 1喜欢
}