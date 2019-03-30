package com.lxkj.qiqihunshe.app.ui.shouye.model

import com.lxkj.qiqihunshe.app.base.BaseModel

/**
 * Created by kxn on 2019/3/8 0008.
 */
class ShouYeModel : BaseModel(){
    var userId = ""
    var totalPage = ""
    var answer = "0"// 0未回答 1已回答
    var dataList = ArrayList<DataListModel>()

  var  nickname=""
}