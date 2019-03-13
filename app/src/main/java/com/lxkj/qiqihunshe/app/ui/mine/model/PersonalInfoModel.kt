package com.lxkj.qiqihunshe.app.ui.mine.model

import com.lxkj.qiqihunshe.app.base.BaseModel

/**
 * Created by Slingge on 2019/2/21
 */
class PersonalInfoModel : BaseModel() {

    var icon = ArrayList<String>()

    var video = ""

    var permission = ArrayList<String>()

    var nickname = ""

    var sex = ""// 性别 0女 1男
    var age = ""
    var identity = ""// 1单身 2约会 3牵手
    var distance = ""// 距离
    var safe = ""// 安全总值
    var credit = ""// 信誉值

    var polite = ""// 言礼值
    var love = ""// 是否喜欢 0否 1是

}