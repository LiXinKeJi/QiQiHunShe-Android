package com.lxkj.qiqihunshe.app.ui.mine

import android.view.View
import com.lxkj.qiqihunshe.R
import com.lxkj.qiqihunshe.app.MyApplication
import com.lxkj.qiqihunshe.app.base.BaseFragment
import com.lxkj.qiqihunshe.app.ui.mine.activity.SetUpActivity
import com.lxkj.qiqihunshe.app.ui.mine.viewmodel.MineViewModel
import com.lxkj.qiqihunshe.databinding.FragmentMineBinding
import kotlinx.android.synthetic.main.fragment_mine.*


/**
 * Created by Slingge on 2019/2/16
 */
class MineFragment : BaseFragment<FragmentMineBinding, MineViewModel>(),View.OnClickListener {


    override fun getBaseViewModel() = MineViewModel()

    override fun getLayoutId() = R.layout.fragment_mine

    override fun init() {

        tv_setup.setOnClickListener(this)


        viewModel?.let {
            binding.viewmodel = it
            it.bind = binding
            it.initAchieve()
        }


    }


    override fun onClick(v: View?) {
        when(v?.id){
            R.id.tv_setup->{
                 MyApplication.openActivity(activity,SetUpActivity::class.java)
            }
        }
    }





    override fun loadData() {
    }


}