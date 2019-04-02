package com.lxkj.qiqihunshe.app.ui.xiaoxi.widget

import android.content.Context
import android.view.View
import android.widget.RelativeLayout
import com.lxkj.qiqihunshe.R
import com.lxkj.qiqihunshe.app.MyApplication
import com.lxkj.qiqihunshe.app.ui.model.EventCmdModel
import com.lxkj.qiqihunshe.app.ui.xiaoxi.model.FindUserRelationshipModel
import com.lxkj.qiqihunshe.app.ui.xiaoxi.model.MessageModel
import com.lxkj.qiqihunshe.app.util.GlideUtil
import kotlinx.android.synthetic.main.item_message.view.*
import org.greenrobot.eventbus.EventBus

/**
 * Created by Slingge on 2019/2/21
 */
class MessageItemView : RelativeLayout {


    constructor(context: Context) : super(context)

    /**
     * 初始化方法
     * */
    init {
        View.inflate(context, R.layout.item_message, this)
    }

    fun setData(bean: FindUserRelationshipModel.dataModel, position: Int) {

        GlideUtil.glideHeaderLoad(context, bean.icon, header)

        tv_title.text = bean.nickname

        tv_age.visibility = View.INVISIBLE

        when (bean.relationship) {// 0:临时，1:相识，2:约会,3:牵手,4:拉黑
            "0" -> tv_type.text = "临时消息"
            "1" -> tv_type.text = "相识"
            "2" -> tv_type.text = "约会"
            "3" -> tv_type.text = "牵手"
        }

        if (bean.yuejian == "1") {
            tv_type.text = "约见中"
        } else {
            tv_type.text = ""
        }


        btnDelete.setOnClickListener {
            val model = EventCmdModel("DelMsg", position.toString())
            model.lat = bean.userId
            EventBus.getDefault().post(model)
        }

        cl_main.setOnClickListener {
            val model = EventCmdModel("item", position.toString())
            model.lat = bean.userId
            model.res=position.toString()
            EventBus.getDefault().post(model)
        }

    }


}