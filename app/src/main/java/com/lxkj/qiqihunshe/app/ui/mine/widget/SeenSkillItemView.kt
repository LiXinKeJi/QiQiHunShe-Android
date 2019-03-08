package com.lxkj.qiqihunshe.app.ui.mine.widget

import android.content.Context
import android.view.View
import android.widget.RelativeLayout
import com.lxkj.qiqihunshe.R
import com.lxkj.qiqihunshe.app.ui.mine.model.SeenSkillModel
import com.lxkj.qiqihunshe.app.ui.mine.model.SpaceDynamicModel
import com.lxkj.qiqihunshe.app.util.GlideUtil
import com.lxkj.qiqihunshe.app.util.ToastUtil
import kotlinx.android.synthetic.main.item_person_skill.view.*
import kotlinx.android.synthetic.main.item_space_dynamic.view.*

/**
 * Created by Slingge on 2019/2/21
 */
class SeenSkillItemView : RelativeLayout {


    constructor(context: Context) : super(context)

    /**
     * 初始化方法
     * */
    init {
        View.inflate(context, R.layout.item_person_skill, this)
    }

    fun setData(bean: SeenSkillModel.dataModel) {

        GlideUtil.glideLoad(context, bean.image, iv_image)

        tv_name.text = bean.title
        tv_num.text = "播放量：${bean.count}"
    }


}