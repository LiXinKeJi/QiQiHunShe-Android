package com.lxkj.qiqihunshe.app.ui.mine.model

import com.lxkj.qiqihunshe.app.base.BaseModel

/**
 * Created by Slingge on 2019/2/21
 */
class QiQiRuleModel:BaseModel() {
//    result":"0","resultNote":"获取七七规则成功","dataList":[{"title":"规则1","type":"1","url":

    var dataList:ArrayList<DataInfor>?=null


    class DataInfor{
        var title=""
        var type=""
        var url=""
    }

}