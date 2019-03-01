package com.lxkj.qiqihunshe.app.ui.quyu.activity

import android.view.LayoutInflater
import android.view.View
import com.baidu.mapapi.map.*
import com.baidu.mapapi.model.LatLng
import com.lxkj.qiqihunshe.R
import com.lxkj.qiqihunshe.app.base.BaseActivity
import com.lxkj.qiqihunshe.app.ui.quyu.viewmodel.FwqyViewModel
import com.lxkj.qiqihunshe.app.util.ToastUtil
import com.lxkj.qiqihunshe.databinding.ActivityFwqyBinding
import kotlinx.android.synthetic.main.activity_fwqy.*
import kotlinx.android.synthetic.main.layout_infowindow_fwqy.view.*

/**
 * Created by kxn on 2019/3/1 0001.
 */
class FwqyActivity : BaseActivity<ActivityFwqyBinding,FwqyViewModel>(){
    var lat : Double = 0.0
    var lng : Double = 0.0
    val mMapView by lazy { bmapView.map }

    override fun getBaseViewModel(): FwqyViewModel = FwqyViewModel()

    override fun getLayoutId(): Int {
       return R.layout.activity_fwqy
    }


    override fun init() {
        initTitle("商都世贸中心D座")
        lat = intent.getDoubleExtra("lat",0.0)
        lng = intent.getDoubleExtra("lng",0.0)

        val ll = LatLng(lat,lng)
        val builder = MapStatus.Builder()
        builder.target(ll).zoom(15.0f)
        mMapView.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()))
        val position = LatLng(lat, lng)

        addOverlay(position)
        addOverlay( LatLng(lat + 0.01, lng -0.01))
        addOverlay( LatLng(lat - 0.01, lng + 0.01))
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
                    view.tvNavigation.setOnClickListener(object : View.OnClickListener{
                        override fun onClick(p0: View?) {
                            ToastUtil.showToast("去导航！")
                        }
                    })
                    val mInfoWindow = InfoWindow(view,point , -100)
                    //使InfoWindow生效
                    mMapView.showInfoWindow(mInfoWindow)
                }
                return true
            }

        })

    }



}