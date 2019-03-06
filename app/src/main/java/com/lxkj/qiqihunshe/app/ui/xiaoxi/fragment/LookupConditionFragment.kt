package com.lxkj.qiqihunshe.app.ui.xiaoxi.fragment

import android.os.Bundle
import android.view.View
import com.lxkj.qiqihunshe.R
import com.lxkj.qiqihunshe.app.MyApplication
import com.lxkj.qiqihunshe.app.base.BaseFragment
import com.lxkj.qiqihunshe.app.ui.dialog.AgeSectionDialog
import com.lxkj.qiqihunshe.app.ui.xiaoxi.activity.LookupResultActivity
import com.lxkj.qiqihunshe.app.ui.xiaoxi.model.MapData
import com.lxkj.qiqihunshe.app.ui.xiaoxi.viewmodel.LookupConditionViewModel
import com.lxkj.qiqihunshe.app.util.StaticUtil
import com.lxkj.qiqihunshe.app.util.ToastUtil
import com.lxkj.qiqihunshe.databinding.FragmentLookupConditionBinding
import kotlinx.android.synthetic.main.fragment_lookup_condition.*

/**
 * 条件查找
 * Created by Slingge on 2019/3/1
 */
class LookupConditionFragment :
    BaseFragment<FragmentLookupConditionBinding, LookupConditionViewModel>(), View.OnClickListener {


    override fun getBaseViewModel() = LookupConditionViewModel()

    override fun getLayoutId() = R.layout.fragment_lookup_condition

    override fun init() {
        viewModel?.let {
            binding.viewmodel = it
            it.bind = binding
        }
        tv_search.setOnClickListener {
            params["uid"] = StaticUtil.uid
            params["sex"] = viewModel?.model?.sex.toString()
            params["age"] = viewModel?.model?.age.toString()
            params["marriage"] = viewModel?.model?.marriage.toString()
            params["residence"] = viewModel?.model?.residence.toString()
            params["birthplace"] = viewModel?.model?.birthplace.toString()
            params["plan"] = viewModel?.model?.plan.toString()
            var map = MapData()
            map.map = params
            val bundle = Bundle()
            bundle.putString("title", "条件查找")
            bundle.putSerializable("map",map)
            MyApplication.openActivity(activity, LookupResultActivity::class.java, bundle)
        }
        rl_sex.setOnClickListener(this)
        rl_age.setOnClickListener(this)
        rl_state.setOnClickListener(this)
        rl_address.setOnClickListener(this)
        rl_hometown.setOnClickListener(this)
        rl_emotion.setOnClickListener(this)
    }

    override fun loadData() {
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.rl_sex -> {
                viewModel?.getSex()
            }
            R.id.rl_age -> {
                val ageSectionDialog = AgeSectionDialog(context, "年龄",
                    AgeSectionDialog.OnItemClick { age ->
                        tv_age.text = age
                        viewModel?.model?.age = age
                        if (age.startsWith("不限"))
                            viewModel?.model?.age = age.replace("不限", "18")
                        else if (age.endsWith("不限"))
                            viewModel?.model?.age = age.replace("不限", "90")
                        else
                            viewModel?.model?.age = age
                    })
                ageSectionDialog.show()
            }
            R.id.rl_state -> {
                viewModel?.getEmotional()
            }
            R.id.rl_address -> {
                viewModel?.showAddress(1)
            }
            R.id.rl_hometown -> {
                viewModel?.showAddress(0)
            }
            R.id.rl_emotion -> {
                viewModel?.getEmotionalPlanning()
            }
        }
    }

}