package com.lxkj.qiqihunshe.app.ui.quyu.activity

import android.os.Build
import android.view.View
import com.google.gson.Gson
import com.lxkj.qiqihunshe.R
import com.lxkj.qiqihunshe.app.base.BaseActivity
import com.lxkj.qiqihunshe.app.retrofitnet.bindLifeCycle
import com.lxkj.qiqihunshe.app.ui.quyu.viewmodel.ShopDetailViewModel
import com.lxkj.qiqihunshe.app.util.AbStrUtil
import com.lxkj.qiqihunshe.app.util.MapNavigationUtil
import com.lxkj.qiqihunshe.app.util.StatusBarUtil
import com.lxkj.qiqihunshe.databinding.ActivityShopDetailBinding
import kotlinx.android.synthetic.main.activity_shop_detail.*
import kotlinx.android.synthetic.main.include_title.*

/**
 * Created by kxn on 2019/3/1 0001.
 * 店铺详情界面
 */
class ShopDetailActivity : BaseActivity<ActivityShopDetailBinding, ShopDetailViewModel>(), View.OnClickListener {

    var id: String? = null
    override fun getBaseViewModel(): ShopDetailViewModel = ShopDetailViewModel()

    override fun getLayoutId(): Int = R.layout.activity_shop_detail

    override fun init() {
        viewModel?.bind = binding
        initTitle("")
        isWhiteStatusBar = false
        if (Build.VERSION.SDK_INT > 19) {
            StatusBarUtil.immersiveStatusBar(this, 0f)
            view_staus.visibility = View.VISIBLE
            StatusBarUtil.setStutaViewHeight(this, view_staus)
        }
        rl_include.setBackgroundColor(resources.getColor(R.color.transparent))
        tv_title.setTextColor(resources.getColor(R.color.white))
        tv_title.setBackgroundColor(resources.getColor(R.color.transparent))
        AbStrUtil.setDrawableLeft(this, R.drawable.ic_back_w, tv_title, 10)
        id = intent.getStringExtra("id")
        params.put("cmd", "shopDetail")
        if (null != id)
            params.put("shopId", id!!)

        viewModel?.getshopDetail(Gson().toJson(params))?.bindLifeCycle(this)?.subscribe({ }, { toastFailure(it) })

        navigationIv.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.navigationIv -> {
                var mapNavigationUtil = MapNavigationUtil(this)
                mapNavigationUtil?.goToBaiduMap(viewModel?.model?.lat, viewModel?.model?.lon,viewModel?.model?.address)
            }
        }
    }

}