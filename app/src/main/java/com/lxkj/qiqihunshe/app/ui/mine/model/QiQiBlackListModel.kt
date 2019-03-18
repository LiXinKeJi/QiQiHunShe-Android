package com.lxkj.qiqihunshe.app.ui.mine.model

import com.lxkj.qiqihunshe.app.base.BaseModel
import java.util.ArrayList

/**
 * Created by Slingge on 2019/2/26
 */
class QiQiBlackListModel : BaseModel() {
//    "":"",// 头像
//    "sex":"0",// 0女 1男
//    "":"昵称",
//    "":"姓名",
//    "":"20",// 年龄
//    "":"河南郑州",// 家乡
//    "":"河南开封",// 现居
//    "":"410100********1234",// 身份证号
//    "":"131****5678"// 手机号


    var totalPage = 1

    var dataList = ArrayList<dataModel>()

    class dataModel {

        var icon = ""
        var sex = ""
        var nickname = ""
        var realname = ""
        var age = ""
        var birthplace = ""// 家乡
        var residence = ""// 现居
        var idnumber = ""// 身份证号
        var phone = ""



    }


}