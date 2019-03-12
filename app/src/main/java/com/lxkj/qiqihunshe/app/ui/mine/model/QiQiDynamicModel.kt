package com.lxkj.qiqihunshe.app.ui.mine.model

import com.lxkj.qiqihunshe.app.base.BaseModel
import java.io.Serializable

/**
 * Created by Slingge on 2019/2/21
 */
class QiQiDynamicModel : BaseModel(), Serializable {


    var totalPage = 1

    var dataList = ArrayList<dataModel>()

    class dataModel : Serializable {

        var activityId = ""
        var image = ""
        var title = ""
        var introduction = ""

        var status = "" // 0待审核 1审核通过 2审核拒绝
        var adtime = "" // 报名时间

    }

}