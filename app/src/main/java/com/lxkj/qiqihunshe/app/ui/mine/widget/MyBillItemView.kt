package com.lxkj.qiqihunshe.app.ui.mine.widget

import android.content.Context
import android.view.View
import android.widget.RelativeLayout
import com.lxkj.qiqihunshe.R
import com.lxkj.qiqihunshe.app.ui.mine.model.MyBillModel
import kotlinx.android.synthetic.main.item_mybill.view.*

/**
 * Created by Slingge on 2019/2/21
 */
class MyBillItemView : RelativeLayout {


    constructor(context: Context) : super(context)

    /**
     * 初始化方法
     * */
    init {
        View.inflate(context, R.layout.item_mybill, this)
    }

    fun setData(bean: MyBillModel.dataModel) {

        tv_title.text = bean.title
        tv_time.text = bean.adtime
        if (bean.type == "0") {//减少
            tv_money.text = "-${bean.money}"
        } else {
            tv_money.text = "+${bean.money}"
        }

    }


}