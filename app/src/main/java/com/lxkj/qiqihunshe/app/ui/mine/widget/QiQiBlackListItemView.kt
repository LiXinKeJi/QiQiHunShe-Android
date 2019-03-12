package com.lxkj.qiqihunshe.app.ui.mine.widget

import android.content.Context
import android.view.View
import android.widget.RelativeLayout
import com.lxkj.qiqihunshe.R
import com.lxkj.qiqihunshe.app.ui.mine.model.ActivityRecordModel
import com.lxkj.qiqihunshe.app.ui.mine.model.CommentRecordModel
import com.lxkj.qiqihunshe.app.ui.mine.model.QiQiBlackListModel
import com.lxkj.qiqihunshe.app.util.GlideUtil
import kotlinx.android.synthetic.main.item_blacklist.view.*

/**
 * Created by Slingge on 2019/2/21
 */
class QiQiBlackListItemView : RelativeLayout {


    constructor(context: Context) : super(context)

    /**
     * 初始化方法
     * */
    init {
        View.inflate(context, R.layout.item_blacklist, this)
    }

    fun setData(bean: QiQiBlackListModel.dataModel, position: Int) {

        GlideUtil.glideHeaderLoad(context, bean.icon, iv_header)
        tv_name.text = bean.nickname

        tv_realname.text = bean.realname
        tv_hometown.text = bean.birthplace

        tv_hometown.text = "家乡：" + bean.birthplace
        tv_current.text = "家乡：" + bean.residence
        tv_id.text = "身份证号：" + bean.idnumber
        tv_phone.text = "手机号：" + bean.phone
    }


}