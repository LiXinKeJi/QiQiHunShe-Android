package com.lxkj.qiqihunshe.app.ui.mine.model

import com.lxkj.qiqihunshe.app.base.BaseModel

/**
 * Created by Slingge on 2019/2/25
 */
class SpaceSkillModel:BaseModel() {

    var totalPage=1

    var dataList=ArrayList<dataModel>()


    class dataModel{
        var caiyiId=""
        var title=""
        var image=""
        var adtime=""
        var count=""

        var video=""
    }


}