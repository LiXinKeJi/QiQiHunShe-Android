package com.lxkj.qiqihunshe.app.ui.mine.widget

import android.content.Context
import android.view.View
import android.widget.RelativeLayout
import com.lxkj.qiqihunshe.R
import com.lxkj.qiqihunshe.app.ui.mine.model.SpaceDynamicModel
import com.lxkj.qiqihunshe.app.ui.model.DelDynamicModel
import com.lxkj.qiqihunshe.app.util.EventBusCmd
import com.lxkj.qiqihunshe.app.util.GlideUtil
import kotlinx.android.synthetic.main.item_space_dynamic.view.*
import org.greenrobot.eventbus.EventBus

/**
 * Created by Slingge on 2019/2/21
 */
class SpaceDynamicItemView : RelativeLayout {


    constructor(context: Context) : super(context)

    /**
     * 初始化方法
     * */
    init {
        View.inflate(context, R.layout.item_space_dynamic, this)
    }

    fun setData(bean: SpaceDynamicModel.dataModel, position: Int) {

        tv_time.text = bean.adtime

        tv_content.text = bean.content

        when (bean.images.size) {
            0 -> {
                iv_0.visibility = View.INVISIBLE
                iv_1.visibility = View.INVISIBLE
                iv_2.visibility = View.INVISIBLE
                iv_3.visibility = View.INVISIBLE
            }
            1 -> {
                iv_0.visibility = View.VISIBLE
                iv_1.visibility = View.INVISIBLE
                iv_2.visibility = View.INVISIBLE
                iv_3.visibility = View.INVISIBLE
                GlideUtil.glideLoad(context, bean.images[0], iv_0)
            }
            2 -> {
                iv_0.visibility = View.VISIBLE
                iv_1.visibility = View.VISIBLE
                iv_2.visibility = View.INVISIBLE
                iv_3.visibility = View.INVISIBLE
                GlideUtil.glideLoad(context, bean.images[0], iv_0)
                GlideUtil.glideLoad(context, bean.images[1], iv_1)
            }
            3 -> {
                iv_0.visibility = View.VISIBLE
                iv_1.visibility = View.VISIBLE
                iv_2.visibility = View.VISIBLE
                iv_3.visibility = View.INVISIBLE
                GlideUtil.glideLoad(context, bean.images[0], iv_0)
                GlideUtil.glideLoad(context, bean.images[1], iv_1)
                GlideUtil.glideLoad(context, bean.images[2], iv_2)
            }
            4 -> {
                iv_0.visibility = View.VISIBLE
                iv_1.visibility = View.VISIBLE
                iv_2.visibility = View.VISIBLE
                iv_3.visibility = View.VISIBLE
                GlideUtil.glideLoad(context, bean.images[0], iv_0)
                GlideUtil.glideLoad(context, bean.images[1], iv_1)
                GlideUtil.glideLoad(context, bean.images[2], iv_2)
                GlideUtil.glideLoad(context, bean.images[3], iv_3)
            }
            else -> {
                iv_0.visibility = View.VISIBLE
                iv_1.visibility = View.VISIBLE
                iv_2.visibility = View.VISIBLE
                iv_3.visibility = View.VISIBLE
                GlideUtil.glideLoad(context, bean.images[0], iv_0)
                GlideUtil.glideLoad(context, bean.images[1], iv_1)
                GlideUtil.glideLoad(context, bean.images[2], iv_2)
                GlideUtil.glideLoad(context, bean.images[3], iv_3)
            }
        }


        iv_del.setOnClickListener {
            if (cv_del.visibility == View.VISIBLE) {
                cv_del.visibility = View.GONE
            } else {
                cv_del.visibility = View.VISIBLE
            }
        }

        cv_del.setOnClickListener {
            //删除动态
            EventBus.getDefault().post(DelDynamicModel(EventBusCmd.DelDynamic, position))
        }

    }


}