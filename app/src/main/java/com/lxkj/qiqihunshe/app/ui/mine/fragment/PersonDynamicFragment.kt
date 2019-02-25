package com.lxkj.qiqihunshe.app.ui.mine.fragment

import android.view.View
import com.lxkj.qiqihunshe.R
import com.lxkj.qiqihunshe.app.base.BaseFragment
import com.lxkj.qiqihunshe.app.ui.mine.viewmodel.PersonDynamicViewModel
import com.lxkj.qiqihunshe.app.util.ToastUtil
import com.lxkj.qiqihunshe.databinding.FragmentPersonDynamicBinding
import kotlinx.android.synthetic.main.activity_recyvlerview.*

/**
 * Created by Slingge on 2019/2/21
 */
class PersonDynamicFragment : BaseFragment<FragmentPersonDynamicBinding, PersonDynamicViewModel>() {


    override fun getBaseViewModel() = PersonDynamicViewModel()

    override fun getLayoutId() = R.layout.fragment_person_dynamic

    override fun init() {
    }

    override fun loadData() {
        viewModel?.let {
            binding.viewmodel=it
            it.bind = binding
            it.initViewModel()
        }

    }


}