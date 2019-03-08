package com.lxkj.qiqihunshe.app.ui.mine.widget

import android.app.Activity
import android.content.Context
import android.view.View
import android.widget.RelativeLayout
import com.lxkj.qiqihunshe.R
import com.lxkj.qiqihunshe.app.ui.dialog.DaShangDialog
import com.lxkj.qiqihunshe.app.ui.mine.model.DynamicModel
import kotlinx.android.synthetic.main.activity_mydynamic.view.*

/**
 * Created by Slingge on 2019/2/21
 */
class PersonDynamicItemView(activity: Activity, context: Context?) : RelativeLayout(context) {

    private var activity: Activity? = null


    init {
        this.activity = activity

    }

    /**
     * 初始化方法
     * */
    init {
        View.inflate(context, R.layout.item_person_dynamic, this)
    }

    fun setData(bean: DynamicModel) {

        tv_reward.setOnClickListener {
            DaShangDialog.show(activity!!,object :DaShangDialog.DaShangCallBack{
                override fun dashang(money: String) {

                }

            })
        }

    }


}