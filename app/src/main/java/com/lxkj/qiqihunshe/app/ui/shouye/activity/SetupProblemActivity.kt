package com.lxkj.qiqihunshe.app.ui.shouye.activity

import android.os.Bundle
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.RadioGroup
import com.lxkj.qiqihunshe.R
import com.lxkj.qiqihunshe.app.MyApplication
import com.lxkj.qiqihunshe.app.base.BaseActivity
import com.lxkj.qiqihunshe.app.ui.shouye.viewmodel.SetupProblemViewModel
import com.lxkj.qiqihunshe.app.util.DisplayUtil
import com.lxkj.qiqihunshe.app.util.NotificationUtil.context
import com.lxkj.qiqihunshe.databinding.ActivitySetupProblemBinding
import kotlinx.android.synthetic.main.activity_setup_problem.*

/**
 * Created by Slingge on 2019/2/26
 */
class SetupProblemActivity : BaseActivity<ActivitySetupProblemBinding, SetupProblemViewModel>() {


    override fun getBaseViewModel() = SetupProblemViewModel()

    override fun getLayoutId() = R.layout.activity_setup_problem


    override fun init() {
        initTitle("设置问题")

        viewModel?.let {
            it.binding = binding
            it.getQuestion()
        }

        tv_next.setOnClickListener {
            val bundle = Bundle()
            bundle.putInt("flag", 2)
            MyApplication.openActivity(this, MatchingHistoryActivity::class.java, bundle)
        }

        radio.setOnCheckedChangeListener { radioGroup, i ->

        }
        binding?.radio?.removeAllViews()

        for (i in 0 until 6) {
            //radioButton
            var radioButton = RadioButton(this)
            var drawable = getResources().getDrawable(R.drawable.rb_click2)
            radioButton.setCompoundDrawables(drawable, null, null, null)
            var padding = DisplayUtil.dip2px(this,15f)
            radioButton.setPadding(padding,padding,padding,padding)
            radioButton.setButtonDrawable(R.drawable.rb_click2)
            radioButton.setText("Button" + i)
            radioButton.setTextColor(getResources().getColor(R.color.colorSubtitle))
            //必须有ID，否则默认选中的选项会一直是选中状态
            radioButton.setId(i);

            //layoutParams 设置margin值
            var layoutParams = RadioGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            //注意这里addView()里传入layoutParams
            radio.addView(radioButton, layoutParams)
        }


    }
}