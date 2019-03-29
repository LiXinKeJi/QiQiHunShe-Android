package com.lxkj.qiqihunshe.app.ui.shouye.viewmodel

import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.RadioGroup
import com.google.gson.Gson
import com.lxkj.qiqihunshe.R
import com.lxkj.qiqihunshe.app.base.BaseViewModel
import com.lxkj.qiqihunshe.app.retrofitnet.SingleCompose
import com.lxkj.qiqihunshe.app.retrofitnet.SingleObserverInterface
import com.lxkj.qiqihunshe.app.retrofitnet.async
import com.lxkj.qiqihunshe.app.ui.shouye.model.DataListModel
import com.lxkj.qiqihunshe.app.ui.shouye.model.ShouYeModel
import com.lxkj.qiqihunshe.app.util.DisplayUtil
import com.lxkj.qiqihunshe.app.util.StaticUtil
import com.lxkj.qiqihunshe.app.util.StringUtil
import com.lxkj.qiqihunshe.app.util.ToastUtil
import com.lxkj.qiqihunshe.databinding.ActivitySetupProblemBinding
import kotlinx.android.synthetic.main.activity_mybill.view.*
import kotlinx.android.synthetic.main.activity_setup_problem.*
import java.util.*

/**
 * Created by Slingge on 2019/2/26
 */
class SetupProblemViewModel : BaseViewModel() {

    var type = "1"
    var binding: ActivitySetupProblemBinding? = null
    var model = ShouYeModel()
    var page = 0
    var totalPage = 0
    var ids = ArrayList<String>()
    var aid = ""

    fun init() {
        binding?.radio?.setOnCheckedChangeListener { p0, p1 ->
            aid = model.dataList[page].answerList[p1].aid
        }

        binding?.tvNext?.setOnClickListener {
            if (StringUtil.isEmpty(aid)) {
                ToastUtil.showTopSnackBar(activity, "请选择答案")
            } else {
                ids.add(aid)
                aid = ""
                if (page < totalPage) { //下一题
                    page++
                    refreshQuestion()
                    binding?.pbReputation?.progress = page
                    binding?.progress?.text = ("$page/$totalPage")
                    if (page == totalPage)
                        binding?.tvNext!!.text = "完成"
                } else { //提交答案
                    addAnswer()
                }
            }
        }
    }

    /**
     * 获取所有问题
     */
    fun getQuestion() {
        var params = HashMap<String, String>()
        params["cmd"] = "getQuestion"
        params["uid"] = StaticUtil.uid
        retrofit.getData(Gson().toJson(params))
            .async()
            .compose(SingleCompose.compose(object : SingleObserverInterface {
                override fun onSuccess(response: String) {
                    model = Gson().fromJson(response, ShouYeModel::class.java)
                    if (model.dataList.size > 0) {
                        refreshQuestion()
                        totalPage = model.dataList.size - 1
                        binding?.pbReputation?.max = totalPage
                    }else{
                        activity?.finish()
                        ToastUtil.showToast("暂无问题")
                    }

                }
            }, activity)).subscribe({
            }, {
            })
    }


    /**
     * 提交答案
     */
    fun addAnswer() {
        var params = HashMap<String, Any>()
        params["cmd"] = "addAnswer"
        params["uid"] = StaticUtil.uid
        params["ids"] = ids
        retrofit.getData(Gson().toJson(params))
            .async()
            .compose(SingleCompose.compose(object : SingleObserverInterface {
                override fun onSuccess(response: String) {
                    model = Gson().fromJson(response, ShouYeModel::class.java)
                    activity?.finish()
                }
            }, activity)).subscribe({
            }, {
            })
    }


    fun refreshQuestion() {
        var data = model.dataList[page]
        binding?.tvProblem?.text = data.title
        binding?.radio?.removeAllViews()
        for (i in 0 until data.answerList.size) {
            //radioButton
            var radioButton = RadioButton(activity)
            var drawable = activity?.getResources()?.getDrawable(R.drawable.rb_click2)
            radioButton.setCompoundDrawables(drawable, null, null, null)
            var padding = DisplayUtil.dip2px(activity, 15f)
            radioButton.setPadding(padding, padding, padding, padding)
            radioButton.setButtonDrawable(R.drawable.rb_click2)
            radioButton.setText("Button" + i)
            radioButton.setTextColor(activity?.getResources()!!.getColor(R.color.colorSubtitle))
            //必须有ID，否则默认选中的选项会一直是选中状态
            radioButton.setId(i);

            //layoutParams 设置margin值
            var layoutParams = RadioGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            //注意这里addView()里传入layoutParams
            binding?.radio?.addView(radioButton, layoutParams)
        }
    }
}