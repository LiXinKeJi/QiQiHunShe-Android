package com.lxkj.qiqihunshe.app.ui.mine.widget

import android.content.Context
import android.view.View
import android.widget.RelativeLayout
import com.lxkj.qiqihunshe.R
import com.lxkj.qiqihunshe.app.ui.mine.model.QiQiDynamicModel
import com.lxkj.qiqihunshe.app.ui.mine.model.QiQiRuleModel
import com.lxkj.qiqihunshe.app.ui.mine.model.ReputationBaoModel

/**
 * Created by Slingge on 2019/2/21
 */
class QiQiDynamicItemView : RelativeLayout  {


    constructor(context: Context) : super(context)

    /**
     * 初始化方法
     * */
    init {
        View.inflate(context, R.layout.item_qiqi_dynamic, this)
    }

    fun setData(bean: QiQiDynamicModel) {


    }


}