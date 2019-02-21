package com.lxkj.qiqihunshe.app.ui.mine.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.lxkj.qiqihunshe.R
import com.lxkj.qiqihunshe.app.base.BaseFragment
import com.lxkj.qiqihunshe.app.ui.mine.viewmodel.PersonDataViewModel
import com.lxkj.qiqihunshe.app.util.ToastUtil
import com.lxkj.qiqihunshe.databinding.FragmentPersonDataBinding

/**
 * Created by Slingge on 2019/2/21
 */
class PersonDataFragment : BaseFragment<FragmentPersonDataBinding, PersonDataViewModel>() {


    override fun getBaseViewModel() = PersonDataViewModel()

    override fun getLayoutId() = R.layout.fragment_person_data

    override fun init() {
    }

    override fun loadData() {
        ToastUtil.showToast("资料")

        viewModel?.let {
            binding.viewmodel=it
        }

    }


}