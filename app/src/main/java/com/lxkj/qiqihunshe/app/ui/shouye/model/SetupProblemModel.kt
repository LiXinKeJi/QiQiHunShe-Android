package com.lxkj.qiqihunshe.app.ui.shouye.model

import com.lxkj.qiqihunshe.app.base.BaseModel

/**
 * Created by Slingge on 2019/2/26
 */
class SetupProblemModel : BaseModel() {

    var dataList = ArrayList<dataModel>()

    class dataModel {

        var qid = ""
        var title = ""
        var answerList = ArrayList<answerModel>()
    }

    class answerModel {
        var aid = ""// 答案ID
        var content = ""// 答案内容

        var isSelect=false
    }

}