package com.lxkj.qiqihunshe.app.ui.mine.model

import com.lxkj.qiqihunshe.app.base.BaseModel
import java.io.Serializable

/**
 * Created by Slingge on 2019/2/25
 */
class SpaceDynamicModel : BaseModel(), Serializable {


    var totalPage = 1

    var dataList = ArrayList<dataModel>()

    class dataModel : Serializable {
        var dongtaiId = ""// 动态ID
        var userId = ""// 发布人ID

        var icon = ""// 发布人头像
        var identity = ""// 1单身 2约会 3牵手
        var nickname = ""

        var sex = ""// 0女 1男
        var age = ""// 年龄
        var job = ""// 职业

        var content = ""
        var images = ArrayList<String>()// 图片
        var adtime = ""// 发布时间

        var distance = ""// 距离
        var location = ""// 发布位置
        var zan = ""// 0未点赞 1已点
        var zanNum = ""// 点赞数量

        var commentNum = ""// 评论数量
    }


}