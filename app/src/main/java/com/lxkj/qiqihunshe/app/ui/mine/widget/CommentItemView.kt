package com.lxkj.qiqihunshe.app.ui.mine.widget

import android.content.Context
import android.view.View
import android.widget.RelativeLayout
import com.lxkj.qiqihunshe.R
import com.lxkj.qiqihunshe.app.ui.mine.model.CommentModel
import com.lxkj.qiqihunshe.app.util.AbStrUtil
import com.lxkj.qiqihunshe.app.util.DoubleCalculationUtil
import com.lxkj.qiqihunshe.app.util.GlideUtil
import kotlinx.android.synthetic.main.item_comment.view.*
import android.databinding.adapters.TextViewBindingAdapter.setText
import zhanghuan.cn.emojiconlibrary.FaceConversionUtil



/**
 * 评论
 * Created by Slingge on 2019/2/21
 */
class CommentItemView : RelativeLayout {


    constructor(context: Context) : super(context)

    /**
     * 初始化方法
     * */
    init {
        View.inflate(context, R.layout.item_comment, this)
    }

    fun setData(bean: CommentModel.dataModel) {
        GlideUtil.glideHeaderLoad(context, bean.icon, iv_header)

        tv_name.text = bean.nickname

        tv_age.text = bean.age
        if (bean.sex == "0") {//0女 1男
            tv_age.setBackgroundResource(R.drawable.bg_girl)
            tv_age.setTextColor(context.resources.getColor(R.color.girl))
            AbStrUtil.setDrawableLeft(context, R.drawable.ic_girl, tv_age, 3)
        } else {
            tv_age.setBackgroundResource(R.drawable.thems_bg35)
            tv_age.setTextColor(context.resources.getColor(R.color.colorThemes))
            AbStrUtil.setDrawableLeft(context, R.drawable.ic_boy, tv_age, 3)
        }

        val spannableString = FaceConversionUtil.getInstace().getExpressionString(context, bean.content)
        tv_comment.text = spannableString

        tv_juli.text = DoubleCalculationUtil.mTokm(bean.distance)

        tv_time.text = bean.adtime
    }


}