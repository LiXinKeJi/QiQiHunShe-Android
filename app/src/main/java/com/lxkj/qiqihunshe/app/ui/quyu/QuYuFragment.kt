package com.lxkj.qiqihunshe.app.ui.quyu

import android.Manifest
import android.os.Build
import com.lxkj.qiqihunshe.R
import com.lxkj.qiqihunshe.app.base.BaseFragment
import com.lxkj.qiqihunshe.app.ui.quyu.viewmodel.QuYuViewModel
import com.lxkj.qiqihunshe.databinding.FragmentQuyuBinding
import kotlinx.android.synthetic.main.fragment_quyu.*
import com.baidu.location.BDLocation
import com.baidu.location.BDAbstractLocationListener
import com.lxkj.qiqihunshe.app.ui.quyu.QuYuFragment.MyLocationListener
import com.baidu.location.LocationClientOption
import com.baidu.location.LocationClient
import com.baidu.mapapi.model.LatLng
import com.lxkj.qiqihunshe.app.AppConsts
import com.lxkj.qiqihunshe.app.util.ToastUtil
import com.zhy.m.permission.MPermissions
import com.zhy.m.permission.PermissionDenied
import com.zhy.m.permission.PermissionGrant
import android.R.attr.button
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import com.baidu.mapapi.map.*
import com.baidu.mapapi.map.MarkerOptions
import com.baidu.mapapi.map.OverlayOptions
import com.baidu.mapapi.map.BitmapDescriptorFactory
import com.baidu.mapapi.map.BitmapDescriptor
import com.lxkj.qiqihunshe.app.util.BitmapUtil
import kotlinx.android.synthetic.main.item_image.view.*


/**
 * Created by Slingge on 2019/2/16
 */
class QuYuFragment : BaseFragment<FragmentQuyuBinding, QuYuViewModel>() {
    val mMapView by lazy { bmapView.map }
    val mLocationClient by lazy { LocationClient(context) }
    var isFirst = true

    override fun getBaseViewModel() = QuYuViewModel()

    override fun getLayoutId() = R.layout.fragment_quyu

    override fun init() {

        mMapView.setMyLocationEnabled(true)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            MPermissions.requestPermissions(
                this, AppConsts.PMS_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
        } else {
            pmsLocationSuccess()
        }
    }

    override fun loadData() {

    }

    @PermissionGrant(AppConsts.PMS_LOCATION)
    fun pmsLocationSuccess() {
        //权限授权成功
        //通过LocationClientOption设置LocationClient相关参数
        val option = LocationClientOption()
        option.isOpenGps = true // 打开gps
        option.setCoorType("bd09ll") // 设置坐标类型
        option.setScanSpan(1000)
        //设置locationClientOption
        mLocationClient.setLocOption(option)
        //注册LocationListener监听器
        val myLocationListener = MyLocationListener()
        mLocationClient.registerLocationListener(myLocationListener)
        //开启地图定位图层
        mLocationClient.start()
    }

    @PermissionDenied(AppConsts.PMS_LOCATION)
    fun pmsLocationError() {
        ToastUtil.showToast("权限被拒绝，定位失败！")
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        MPermissions.onRequestPermissionsResult(this, requestCode, permissions, grantResults)
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }


    override fun onResume() {
        bmapView.onResume()
        super.onResume()
    }

    override fun onPause() {
        bmapView.onPause()
        super.onPause()
    }

    override fun onDestroy() {
        mLocationClient.stop()
        mMapView.setMyLocationEnabled(false)
        super.onDestroy()
    }

    fun addOverlay(point: LatLng) {

        val image = LayoutInflater.from(context).inflate(R.layout.layout_imageview, null)
        image.setDrawingCacheEnabled(true)
        image.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH)
        image.setDrawingCacheBackgroundColor(Color.WHITE)
//构建Marker图标
        val bitmap = BitmapUtil.loadBitmapFromView(image)
        val des = BitmapDescriptorFactory.fromBitmap(bitmap)

//构建MarkerOption，用于在地图上添加Marker
        val option = MarkerOptions()
            .position(point)
            .icon(des)
//在地图上添加Marker，并显示
        val marker = mMapView.addOverlay(option) as Marker



        mMapView.setOnMarkerClickListener (object : BaiduMap.OnMarkerClickListener{
            override fun onMarkerClick(p0: Marker?): Boolean {
                    if (p0 == marker){
                        val view = LayoutInflater.from(context).inflate(com.lxkj.qiqihunshe.R.layout.layout_infowindow_qy, null)
                        val mInfoWindow = InfoWindow(view, point, -100)
                        //使InfoWindow生效
                        mMapView.showInfoWindow(mInfoWindow)
                    }
                return true
            }

        })
    }

    inner class MyLocationListener : BDAbstractLocationListener() {
        override fun onReceiveLocation(location: BDLocation?) {
            //mapView 销毁后不在处理新接收的位置
            if (location == null || mMapView == null) {
                return
            }

            if (isFirst) {
                val ll = LatLng(location.getLatitude(), location.getLongitude())
                val builder = MapStatus.Builder()
                builder.target(ll).zoom(18.0f)
                mMapView.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()))
                addOverlay(ll)


            }
            val locData = MyLocationData.Builder()
                .accuracy(location.radius)
                // 此处设置开发者获取到的方向信息，顺时针0-360
                .direction(location.direction).latitude(location.latitude)
                .longitude(location.longitude).build()
            mMapView.setMyLocationData(locData)
            isFirst = false
        }
    }


}