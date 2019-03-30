package com.lxkj.qiqihunshe.app.ui.fujin.model

import com.lxkj.qiqihunshe.app.base.BaseModel

/**
 * Created by kxn on 2019/3/7 0007.
 */
class FuJinModel : BaseModel(){
    var totalPage = 1//总页数

    var commentCount=0
    var dataList = ArrayList<DataListModel> ()
}