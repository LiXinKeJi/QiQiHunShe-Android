package com.lxkj.qiqihunshe.app.ui.mine.widget

import android.content.Context
import android.view.View
import android.widget.RelativeLayout
import com.lxkj.qiqihunshe.R
import com.lxkj.qiqihunshe.app.ui.mine.model.SpaceDynamicModel
import com.lxkj.qiqihunshe.app.util.ToastUtil
import kotlinx.android.synthetic.main.item_space_dynamic.view.*

/**
 * Created by Slingge on 2019/2/21
 */
class SpaceDynamicItemView : RelativeLayout  {


    constructor(context: Context) : super(context)

    /**
     * 初始化方法
     * */
    init {
        View.inflate(context, R.layout.item_space_dynamic, this)
    }

    fun setData(bean: SpaceDynamicModel) {

        iv_del.setOnClickListener {
            ToastUtil.showToast("fe")
            if(cv_del.visibility==View.VISIBLE){
                cv_del.visibility=View.GONE
            }else{
                cv_del.visibility=View.VISIBLE
            }
        }

    }


}