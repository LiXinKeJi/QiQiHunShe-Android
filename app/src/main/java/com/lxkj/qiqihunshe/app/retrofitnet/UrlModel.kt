package com.lxkj.qiqihunshe.app.retrofitnet

import com.lxkj.qiqihunshe.app.base.BaseModel
import java.util.ArrayList

/**
 * Created by Slingge on 2018/11/12.
 */
open class UrlModel : BaseModel() {


    var error = false

    var results = ArrayList<resultsModel>()

    class resultsModel {

        var url = ""
    }

}