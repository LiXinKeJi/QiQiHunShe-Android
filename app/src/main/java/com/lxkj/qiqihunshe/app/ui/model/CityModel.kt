package com.lxkj.qiqihunshe.app.ui.model

/**
 * Created by Slingge on 2018/1/18 0018.
 */
class CityModel {

    var areaId: String? = null
    var areaName: String? = null
    var cities: List<citiesBean>? = null

    inner class citiesBean {
        var areaId: String? = null
        var areaName: String? = null
        var counties: List<countiesBean>? = null
    }

    inner class countiesBean {
        var areaId: String? = null
        var areaName: String? = null
    }


}