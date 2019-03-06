package com.lxkj.qiqihunshe.app.ui.quyu.model

import com.lxkj.qiqihunshe.app.base.BaseModel

/**
 * Created by kxn on 2019/3/1 0001.
 */
class ShopDetailModel : BaseModel() {
    var banner = ArrayList<String>()// 轮播图
    var shopName = "" //店铺名称",
    var price = ""// 价格
    var address = ""// 地址
    var lon = ""    // 经度
    var lat = ""// 纬度
    var url = ""// 详情 富文本链接

}