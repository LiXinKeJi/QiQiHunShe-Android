package com.lxkj.qiqihunshe.app.ui.mine.widget

import android.content.Context
import android.view.View
import android.widget.RelativeLayout
import com.lxkj.qiqihunshe.R
import com.lxkj.qiqihunshe.app.ui.mine.model.QiQiDynamicModel
import com.lxkj.qiqihunshe.app.util.GlideUtil
import kotlinx.android.synthetic.main.item_qiqi_dynamic.view.*
import java.text.FieldPosition

/**
 * Created by Slingge on 2019/2/21
 */
class QiQiDynamicSignUpRecordItemView : RelativeLayout {


    constructor(context: Context) : super(context) {
        tv_time.visibility = View.VISIBLE
    }

    /**
     * 初始化方法
     * */
    init {
        View.inflate(context, R.layout.item_qiqi_dynamic, this)
    }

    fun setData(bean: QiQiDynamicModel.dataModel, position: Int) {

        tv_title.text = bean.title
        tv_content.text = bean.introduction
        tv_time.text = bean.adtime
        GlideUtil.glideLoad(context, bean.image, image_view)
    }


}