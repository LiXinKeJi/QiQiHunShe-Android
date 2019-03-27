package com.lxkj.qiqihunshe.app.ui.quyu.activity

import com.baidu.mapapi.map.*
import com.baidu.mapapi.model.LatLng
import com.lxkj.qiqihunshe.R
import com.lxkj.qiqihunshe.app.base.BaseActivity
import com.lxkj.qiqihunshe.app.ui.quyu.viewmodel.DdtjViewModel
import com.lxkj.qiqihunshe.databinding.ActivityDdtjBinding
import kotlinx.android.synthetic.main.activity_ddtj.*
import com.google.gson.Gson
import com.lxkj.qiqihunshe.app.retrofitnet.bindLifeCycle
import com.lxkj.qiqihunshe.app.util.StaticUtil


/**
 * Created by kxn on 2019/3/1 0001.
 */
class DdtjActivity : BaseActivity<ActivityDdtjBinding,DdtjViewModel>() {

    val mMapView by lazy { bmapView.map }

    override fun getBaseViewModel(): DdtjViewModel = DdtjViewModel()

    override fun getLayoutId(): Int {
        return R.layout.activity_ddtj
    }

    override fun init() {
        viewModel?.bind = binding
        viewModel?.flag=intent.getIntExtra("flag",-1)
        initTitle("地点推荐")
        lat = StaticUtil.lat
        lng = StaticUtil.lng

        val ll = LatLng(lat.toDouble(), lng.toDouble())
        val builder = MapStatus.Builder()
        builder.target(ll).zoom(12.0f)
        mMapView.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()))

        val position = LatLng(lat.toDouble(), lng.toDouble())

        params.put("cmd", "recommendPlace")
        viewModel!!.getServiceArea(Gson().toJson(params)).bindLifeCycle(this).subscribe({ }, { toastFailure(it) })

        //构建Marker图标
        val des = BitmapDescriptorFactory.fromResource(R.mipmap.ic_location_red)
        //构建MarkerOption，用于在地图上添加Marker
        val option = MarkerOptions()
            .position(position)
            .icon(des)

        //在地图上添加Marker(当前位置)，并显示
         mMapView.addOverlay(option)

    }

}
