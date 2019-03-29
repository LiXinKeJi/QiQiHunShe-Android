package com.lxkj.qiqihunshe.app.ui.shouye.widget

import android.content.Context
import android.view.View
import android.widget.RelativeLayout
import com.lxkj.qiqihunshe.R
import com.lxkj.qiqihunshe.app.ui.model.EventCmdModel
import com.lxkj.qiqihunshe.app.ui.shouye.model.SetupProblemModel
import kotlinx.android.synthetic.main.item_check.view.*
import org.greenrobot.eventbus.EventBus

/**
 * Created by Slingge on 2019/2/21
 */
class CheckBoxItemView : RelativeLayout {


    constructor(context: Context) : super(context) {

    }

    /**
     * 初始化方法
     * */
    init {
        View.inflate(context, R.layout.item_check, this)
    }

    fun setData(bean: SetupProblemModel.answerModel, position: Int) {

        checkbox.isChecked = bean.isSelect
        checkbox.text = bean.content

        checkbox.setOnCheckedChangeListener { buttonView, isChecked ->
            EventBus.getDefault().post(EventCmdModel(isChecked.toString(), position.toString()))
        }
    }


}