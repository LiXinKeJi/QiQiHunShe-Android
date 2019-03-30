package com.lxkj.qiqihunshe.app.ui.xiaoxi.fragment

import android.os.Bundle
import android.view.View
import com.google.gson.Gson
import com.lxkj.qiqihunshe.R
import com.lxkj.qiqihunshe.app.MyApplication
import com.lxkj.qiqihunshe.app.base.BaseFragment
import com.lxkj.qiqihunshe.app.retrofitnet.bindLifeCycle
import com.lxkj.qiqihunshe.app.ui.dialog.AgeSectionDialog
import com.lxkj.qiqihunshe.app.ui.mine.model.MyPermissionModel
import com.lxkj.qiqihunshe.app.ui.xiaoxi.activity.LookupResultActivity
import com.lxkj.qiqihunshe.app.ui.xiaoxi.model.MapData
import com.lxkj.qiqihunshe.app.ui.xiaoxi.viewmodel.LookupEconomicsViewModel
import com.lxkj.qiqihunshe.app.util.StaticUtil
import com.lxkj.qiqihunshe.app.util.ToastUtil
import com.lxkj.qiqihunshe.databinding.FragmentLookupEconomicsBinding
import kotlinx.android.synthetic.main.fragment_lookup_economics.*

/**
 * 经济查找
 * Created by Slingge on 2019/3/1
 */
class LookupEconomicsFragment : BaseFragment<FragmentLookupEconomicsBinding, LookupEconomicsViewModel>(),
    View.OnClickListener {

    override fun getBaseViewModel() = LookupEconomicsViewModel()

    override fun getLayoutId() = R.layout.fragment_lookup_economics

    override fun init() {
        viewModel?.let {
            binding.viewmodel = it
            it.bind = binding
        }
        tv_search.setOnClickListener {
            viewModel!!.getPermission().bindLifeCycle(this).subscribe({
                val list = Gson().fromJson(it, MyPermissionModel::class.java).dataList
                for (str in list) {
                    if (str.type == "3") {
                        search()
                        return@subscribe
                    }
                }
                ToastUtil.showTopSnackBar(activity!!, "请先购买经济查找权限")
            }, { toastFailure(it) })

        }

        rl_sex.setOnClickListener(this)
        rl_age.setOnClickListener(this)
        rl_income.setOnClickListener(this)
        rl_car.setOnClickListener(this)
        rl_room.setOnClickListener(this)
        rl_education.setOnClickListener(this)
        rl_emotion.setOnClickListener(this)
        rl_state.setOnClickListener(this)

    }


    fun search() {
        params["uid"] = StaticUtil.uid
        params["sex"] = viewModel?.model?.sex.toString()
        params["age"] = viewModel?.model?.age.toString()
        params["salary"] = viewModel?.model?.salary.toString()
        params["car"] = viewModel?.model?.car.toString()
        params["house"] = viewModel?.model?.house.toString()
        params["education"] = viewModel?.model?.education.toString()
        params["marriage"] = viewModel?.model?.marriage.toString()
        params["plan"] = viewModel?.model?.plan.toString()
        var map = MapData()
        map.map = params
        val bundle = Bundle()
        bundle.putString("title", "经济查找")
        bundle.putSerializable("map", map)
        MyApplication.openActivity(activity, LookupResultActivity::class.java, bundle)
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
            R.id.rl_emotion -> {
                viewModel?.getEmotionalPlanning()
            }
            R.id.rl_income -> {
                viewModel?.getIncome()
            }
            R.id.rl_car -> {
                viewModel?.getCar()
            }
            R.id.rl_room -> {
                viewModel?.getHouse()
            }
            R.id.rl_education -> {
                viewModel?.getEdu()
            }
        }
    }


}