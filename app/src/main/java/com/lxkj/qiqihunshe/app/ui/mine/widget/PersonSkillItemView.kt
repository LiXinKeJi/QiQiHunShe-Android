package com.lxkj.qiqihunshe.app.ui.mine.widget

import android.content.Context
import android.view.View
import android.widget.RelativeLayout
import com.lxkj.qiqihunshe.R
import com.lxkj.qiqihunshe.app.ui.mine.model.SpaceSkillModel
import com.lxkj.qiqihunshe.app.util.GlideUtil
import kotlinx.android.synthetic.main.item_person_skill.view.*

/**
 * Created by Slingge on 2019/2/21
 */
class PersonSkillItemView : RelativeLayout {


    constructor(context: Context) : super(context)

    /**
     * 初始化方法
     * */
    init {
        View.inflate(context, R.layout.item_person_skill, this)
    }

    fun setData(bean: SpaceSkillModel.dataModel, position: Int) {

        GlideUtil.glideLoad(context, bean.image, iv_image)

        tv_name.text = bean.title
        tv_num.text = "播放量：${bean.count}"
    }


}