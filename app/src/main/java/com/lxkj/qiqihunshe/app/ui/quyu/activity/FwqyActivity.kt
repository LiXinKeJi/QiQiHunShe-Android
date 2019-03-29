package com.lxkj.qiqihunshe.app.ui.quyu.activity

import android.view.LayoutInflater
import android.view.View
import com.baidu.mapapi.map.*
import com.baidu.mapapi.model.LatLng
import com.google.gson.Gson
import com.lxkj.qiqihunshe.R
import com.lxkj.qiqihunshe.app.base.BaseActivity
import com.lxkj.qiqihunshe.app.retrofitnet.bindLifeCycle
import com.lxkj.qiqihunshe.app.ui.quyu.viewmodel.FwqyViewModel
import com.lxkj.qiqihunshe.app.util.StaticUtil
import com.lxkj.qiqihunshe.app.util.ToastUtil
import com.lxkj.qiqihunshe.databinding.ActivityFwqyBinding
import kotlinx.android.synthetic.main.activity_fwqy.*
import kotlinx.android.synthetic.main.layout_infowindow_fwqy.view.*

/**
 * Created by kxn on 2019/3/1 0001.
 */
class FwqyActivity : BaseActivity<ActivityFwqyBinding,FwqyViewModel>(){
    val mMapView by lazy { bmapView.map }

    override fun getBaseViewModel(): FwqyViewModel = FwqyViewModel()

    override fun getLayoutId(): Int {
       return R.layout.activity_fwqy
    }


    override fun init() {

        viewModel?.bind = binding

        lat = intent.getDoubleExtra("lat",0.0).toString()
        lng = intent.getDoubleExtra("lng",0.0).toString()
        initTitle(intent.getStringExtra("address"))

        val ll = LatLng(lat.toDouble(),lng.toDouble())
        val builder = MapStatus.Builder()
        builder.target(ll).zoom(12.0f)
        mMapView.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()))

        params.put("cmd", "serviceArea")
        params.put("uid", StaticUtil.uid)
        params.put("lon", lng)
        params.put("lat", lat)
        viewModel!!.getServiceArea(Gson().toJson(params)).bindLifeCycle(this)?.subscribe({ }, { toastFailure(it) })

    }

    fun addOverlay(point: LatLng) {
        val image = LayoutInflater.from(this).inflate(R.layout.layout_shop_map, null)
        //构建Marker图标
        val des = BitmapDescriptorFactory.fromView(image)
        //构建MarkerOption，用于在地图上添加Marker
        val option = MarkerOptions()
            .position(LatLng(point.latitude-0.002,point.longitude))
            .icon(des)
        //在地图上添加Marker，并显示

        val marker = mMapView.addOverlay(option) as Marker



        mMapView.setOnMarkerClickListener(object : BaiduMap.OnMarkerClickListener {
            override fun onMarkerClick(p0: Marker?): Boolean {
                if (p0 == marker) {
                    val view =
                        LayoutInflater.from(this@FwqyActivity).inflate(com.lxkj.qiqihunshe.R.layout.layout_infowindow_fwqy, null)
                    view.tvNavigation.setOnClickListener {
                        ToastUtil.showToast("去导航！")
                    }
                    val mInfoWindow = InfoWindow(view,point , -100)
                    //使InfoWindow生效
                    mMapView.showInfoWindow(mInfoWindow)
                }
                return true
            }

        })

    }



}