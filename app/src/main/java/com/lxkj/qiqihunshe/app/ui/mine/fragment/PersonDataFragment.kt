package com.lxkj.qiqihunshe.app.ui.mine.fragment

import com.lxkj.qiqihunshe.R
import com.lxkj.qiqihunshe.app.base.BaseFragment
import com.lxkj.qiqihunshe.app.retrofitnet.exception.bindLifeCycle
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
        viewModel?.let {
            binding.viewmodel = it
            it.bind = binding
            it.userId = arguments!!.getString("id")
        }
    }

    override fun loadData() {
        viewModel!!.getPersonData().bindLifeCycle(this).subscribe({}, { toastFailure(it) })
    }


}