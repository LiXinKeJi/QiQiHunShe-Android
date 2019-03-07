package com.lxkj.qiqihunshe.app.ui.mine.activity

import com.lxkj.qiqihunshe.R
import com.lxkj.qiqihunshe.app.base.BaseActivity
import com.lxkj.qiqihunshe.app.interf.ShadowTransformer
import com.lxkj.qiqihunshe.app.retrofitnet.bindLifeCycle
import com.lxkj.qiqihunshe.app.ui.mine.adapter.CardPagerAdapter
import com.lxkj.qiqihunshe.app.ui.mine.model.PermissionBuyModel
import com.lxkj.qiqihunshe.app.ui.mine.viewmodel.PermissionButVeiwModel
import com.lxkj.qiqihunshe.databinding.ActivityPermissionBuyBinding
import kotlinx.android.synthetic.main.activity_permission_buy.*

/**
 * Created by Slingge on 2019/3/2
 */
class PermissionBuyActivity : BaseActivity<ActivityPermissionBuyBinding, PermissionButVeiwModel>() {

    override fun getBaseViewModel() = PermissionButVeiwModel()

    override fun getLayoutId() = R.layout.activity_permission_buy

    override fun init() {
        initTitle("权限购买")

        viewModel?.let {
            it.bind = binding
            it.getPermissionList().bindLifeCycle(this).subscribe({}, { toastFailure(it) })
        }

    }


}