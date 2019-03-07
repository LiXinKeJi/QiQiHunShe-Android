package com.lxkj.qiqihunshe.app.ui.mine.activity

import com.lxkj.qiqihunshe.R
import com.lxkj.qiqihunshe.app.base.BaseActivity
import com.lxkj.qiqihunshe.app.interf.ShadowTransformer
import com.lxkj.qiqihunshe.app.ui.mine.adapter.CardPagerAdapter
import com.lxkj.qiqihunshe.app.ui.mine.viewmodel.PermissionButVeiwModel
import com.lxkj.qiqihunshe.app.ui.model.PermissionBuyModel
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

        val mCardAdapter = CardPagerAdapter()

        for (i in 0..9) {
            mCardAdapter.addCarditem(PermissionBuyModel())
        }
        val mCardShadowTransformer = ShadowTransformer(viewPager, mCardAdapter)
        viewPager.adapter = mCardAdapter

        mCardShadowTransformer.enableScaling(true)
    }


}