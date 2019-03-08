package com.lxkj.qiqihunshe.app.ui.mine.widget

import android.content.Context
import android.view.View
import android.widget.RelativeLayout
import com.lxkj.qiqihunshe.R
import com.lxkj.qiqihunshe.app.ui.mine.model.SpaceSkillModel
import com.lxkj.qiqihunshe.app.ui.model.DelDynamicModel
import com.lxkj.qiqihunshe.app.util.EventBusCmd
import com.lxkj.qiqihunshe.app.util.GlideUtil
import kotlinx.android.synthetic.main.item_space_skill.view.*
import org.greenrobot.eventbus.EventBus

/**
 * Created by Slingge on 2019/2/21
 */
class SpaceSkillItemView : RelativeLayout {


    constructor(context: Context) : super(context)

    /**
     * 初始化方法
     * */
    init {
        View.inflate(context, R.layout.item_space_skill, this)
    }

    fun setData(bean: SpaceSkillModel.dataModel, position: Int) {

        GlideUtil.glideLoad(context, bean.image, iv_0)

        tv_time.text = bean.adtime
        tv_content.text = bean.title

        iv_del.setOnClickListener {
            if (cv_del.visibility == View.VISIBLE) {
                cv_del.visibility = View.GONE
            } else {
                cv_del.visibility = View.VISIBLE
            }
        }

        cv_del.setOnClickListener {
            EventBus.getDefault().post(DelDynamicModel(EventBusCmd.DelSkill, position))
        }

    }


}