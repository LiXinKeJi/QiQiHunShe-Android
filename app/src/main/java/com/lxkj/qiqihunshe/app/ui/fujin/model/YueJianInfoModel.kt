package com.lxkj.qiqihunshe.app.ui.fujin.model

import com.lxkj.qiqihunshe.app.base.BaseModel

/**
 * 约见信息
 * Created by Slingge on 2019/4/2
 */
class YueJianInfoModel : BaseModel() {

    var yuejianId = ""// 约见ID

    var uid = ""// 发起人Id
    var taid = ""// 受约人ID
    var address = ""// 约见地址
    var lon = ""// 经度
    var lat = ""// 纬度
    var arrivaltime = ""// 到场时间
    var status = ""// 状态 1我方失约 2对方失约 可为空
    var comment = ""//评价 0否 1是
    var amount = ""// 消费划分 1我付 2AA
    var payment = ""// 消费总额

    var money = ""// 对方支付金额
    var paystatus = ""//支付状态 0未支付 1已支付 2拒绝

}