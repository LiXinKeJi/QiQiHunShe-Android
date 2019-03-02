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
import com.baidu.location.LocationClientOption
import com.baidu.location.LocationClient
import com.baidu.mapapi.model.LatLng
import com.lxkj.qiqihunshe.app.AppConsts
import com.lxkj.qiqihunshe.app.util.ToastUtil
import com.zhy.m.permission.MPermissions
import com.zhy.m.permission.PermissionDenied
import com.zhy.m.permission.PermissionGrant
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import com.baidu.mapapi.map.*
import com.baidu.mapapi.map.MarkerOptions
import com.baidu.mapapi.map.BitmapDescriptorFactory
import com.lxkj.qiqihunshe.app.MyApplication
import com.lxkj.qiqihunshe.app.ui.dialog.AqxzDialog
import com.lxkj.qiqihunshe.app.ui.dialog.FwwdDialog
import com.lxkj.qiqihunshe.app.ui.dialog.SayHolleDialog
import com.lxkj.qiqihunshe.app.ui.map.activity.SelectAddressMapActivity
import com.lxkj.qiqihunshe.app.ui.quyu.activity.DdtjActivity
import com.lxkj.qiqihunshe.app.ui.quyu.activity.FwqyActivity
import kotlinx.android.synthetic.main.layout_infowindow_qy.view.*


/**
 * Created by Slingge on 2019/2/16
 */
class QuYuFragment : BaseFragment<FragmentQuyuBinding, QuYuViewModel>(), View.OnClickListener {


    val mMapView by lazy { bmapView.map }
    val mLocationClient by lazy { LocationClient(context) }
    var isFirst = true

    var lat : Double = 0.0
    var lng : Double = 0.0




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
        iv_fwqy.setOnClickListener(this)
        iv_sayHi.setOnClickListener(this)
        tv_aqxz.setOnClickListener(this)
        tv_ddtj.setOnClickListener(this)
        tv_fwwd.setOnClickListener(this)
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
        //构建Marker图标
        val des = BitmapDescriptorFactory.fromView(image)
        //构建MarkerOption，用于在地图上添加Marker
        val option = MarkerOptions()
            .position(point)
            .icon(des)
        //在地图上添加Marker，并显示
        val marker = mMapView.addOverlay(option) as Marker



        mMapView.setOnMarkerClickListener(object : BaiduMap.OnMarkerClickListener {
            override fun onMarkerClick(p0: Marker?): Boolean {
                if (p0 == marker) {
                    val view =
                        LayoutInflater.from(context).inflate(com.lxkj.qiqihunshe.R.layout.layout_infowindow_qy, null)
                        view.tvNavigation.setOnClickListener(object : View.OnClickListener{
                       override fun onClick(p0: View?) {
                           ToastUtil.showToast("去导航！")
                       }
                   })
                    val mInfoWindow = InfoWindow(view, point, -100)
                    //使InfoWindow生效
                    mMapView.showInfoWindow(mInfoWindow)
                }
                return true
            }

        })
    }


    override fun onClick(v: View?) {
        when(v?.id){
            R.id.iv_fwqy ->{
                val bundle = Bundle()
                bundle.putDouble("lat",lat)
                bundle.putDouble("lng",lng)
                MyApplication.openActivity(activity, FwqyActivity::class.java,bundle)
                MyApplication.openActivity(activity, SelectAddressMapActivity::class.java,bundle)
            }
            R.id.iv_sayHi ->{
                SayHolleDialog.show(activity!!)
            }

            R.id.tv_aqxz ->{
                AqxzDialog.show(activity!!)
            }

            R.id.tv_fwwd ->{
                FwwdDialog.show(activity!!)
            }

            R.id.tv_ddtj ->{
                val bundle = Bundle()
                bundle.putDouble("lat",lat)
                bundle.putDouble("lng",lng)
                MyApplication.openActivity(activity, DdtjActivity::class.java,bundle)
            }
        }

    }

    inner class MyLocationListener : BDAbstractLocationListener() {
        override fun onReceiveLocation(location: BDLocation?) {
            //mapView 销毁后不在处理新接收的位置
            if (location == null || mMapView == null) {
                return
            }

            lat = location.getLatitude()
            lng = location.getLongitude()

            if (isFirst) {
                val ll = LatLng(location.getLatitude(), location.getLongitude())
                val builder = MapStatus.Builder()
                builder.target(ll).zoom(18.0f)
                mMapView.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()))
                val position = LatLng(location.latitude + 0.01, location.getLongitude() + 0.01)
                addOverlay(position)
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