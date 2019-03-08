package com.lxkj.qiqihunshe.app.ui.mine.widget

import android.content.Context
import android.view.View
import android.widget.RelativeLayout
import com.lxkj.qiqihunshe.R
import com.lxkj.qiqihunshe.app.ui.mine.model.QiQiRuleModel
import com.lxkj.qiqihunshe.app.ui.mine.model.ReputationBaoModel
import kotlinx.android.synthetic.main.item_qiqirule.view.*

/**
 * Created by Slingge on 2019/2/21
 */
class QiQiRuleItemView : RelativeLayout  {


    constructor(context: Context) : super(context)

    /**
     * 初始化方法
     * */
    init {
        View.inflate(context, R.layout.item_qiqirule, this)
    }

    fun setData(bean: QiQiRuleModel) {
        tv_problem.setText(bean.title)


    }


}