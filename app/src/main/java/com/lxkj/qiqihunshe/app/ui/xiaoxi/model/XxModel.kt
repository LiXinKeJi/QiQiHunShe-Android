package com.lxkj.qiqihunshe.app.ui.xiaoxi.model

import com.lxkj.qiqihunshe.app.base.BaseModel
import java.io.Serializable

/**
 * Created by kxn on 2019/3/5 0005.
 */
class XxModel : BaseModel(),Serializable{
    var totalPage = 1//总页数
    var dataList = ArrayList<DataListModel> ()
}