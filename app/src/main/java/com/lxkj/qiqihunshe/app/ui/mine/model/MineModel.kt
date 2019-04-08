package com.lxkj.qiqihunshe.app.ui.mine.model

import com.lxkj.qiqihunshe.app.base.BaseModel

/**
 * Created by Slingge on 2019/2/19
 */
class MineModel : BaseModel() {


    var nickname = ""
    var icon = ""
    var identity = ""// 1单身 2约会 3牵手
    var credit = ""// 信誉值

    var polite = ""// 言礼值
    var xiaoqi = ""// 小七推荐数量
    var interact = ""// 互动通知

    var auth = ""// 实名认证状态 0未认证 1待审核 2已认证 3认证失败

    var sex = ""

    var account = ""// 七七账号

    var marriage = ""// 情感状态 0未婚 1已婚 2离异

    var behavior = ""//行为值

    var bail = ""//// 信誉金 0代表未缴纳


}