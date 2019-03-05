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
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import com.baidu.mapapi.map.*
import com.baidu.mapapi.map.MarkerOptions
import com.baidu.mapapi.map.BitmapDescriptorFactory
import com.google.gson.Gson
import com.lxkj.qiqihunshe.app.MyApplication
import com.lxkj.qiqihunshe.app.retrofitnet.RetrofitService
import com.lxkj.qiqihunshe.app.retrofitnet.RetrofitUtil
import com.lxkj.qiqihunshe.app.retrofitnet.bindLifeCycle
import com.lxkj.qiqihunshe.app.ui.dialog.AqxzDialog
import com.lxkj.qiqihunshe.app.ui.dialog.FwwdDialog
import com.lxkj.qiqihunshe.app.ui.dialog.SayHolleDialog
import com.lxkj.qiqihunshe.app.ui.map.activity.SelectAddressMapActivity
import com.lxkj.qiqihunshe.app.ui.quyu.activity.DdtjActivity
import com.lxkj.qiqihunshe.app.ui.quyu.activity.FwqyActivity
import com.lxkj.qiqihunshe.app.util.StaticUtil
import com.lxkj.qiqihunshe.app.util.abLog
import kotlinx.android.synthetic.main.activity_mybill.view.*
import kotlinx.android.synthetic.main.layout_infowindow_qy.view.*
import kotlin.math.ln


/**
 * Created by Slingge on 2019/2/16
 */
class QuYuFragment : BaseFragment<FragmentQuyuBinding, QuYuViewModel>(), View.OnClickListener,
    BaiduMap.OnMapStatusChangeListener {



    val mMapView by lazy { bmapView.map }
    val mLocationClient by lazy { LocationClient(context) }
    var isFirst = true

    var lat: Double = 0.0
    var lng: Double = 0.0


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
        mMapView.setOnMapStatusChangeListener(this)
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
        option.setScanSpan(10000)//10秒定位一次
        //可选，设置是否需要地址信息，默认不需要
        option.setIsNeedAddress(true)
        //可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        option.setIsNeedLocationDescribe(true)
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




    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.iv_fwqy -> {
                val bundle = Bundle()
                bundle.putDouble("lat", lat)
                bundle.putDouble("lng", lng)
                MyApplication.openActivity(activity, FwqyActivity::class.java, bundle)
                MyApplication.openActivity(activity, SelectAddressMapActivity::class.java, bundle)
            }
            R.id.iv_sayHi -> {
                SayHolleDialog.show(activity!!)
            }

            R.id.tv_aqxz -> {
                AqxzDialog.show(activity!!)
            }

            R.id.tv_fwwd -> {
                FwwdDialog.show(activity!!)
            }

            R.id.tv_ddtj -> {
                val bundle = Bundle()
                bundle.putDouble("lat", lat)
                bundle.putDouble("lng", lng)
                MyApplication.openActivity(activity, DdtjActivity::class.java, bundle)
            }
        }

    }

    fun getData() {
        params.put("cmd", "serviceArea")
        params.put("uid", StaticUtil.uid)
        params.put("lon", lng.toString())
        params.put("lat", lat.toString())
        viewModel!!.getServiceArea(Gson().toJson(params)).bindLifeCycle(this).subscribe()
    }

    inner class MyLocationListener : BDAbstractLocationListener() {
        override fun onReceiveLocation(location: BDLocation?) {
            //mapView 销毁后不在处理新接收的位置
            if (location == null || mMapView == null) {
                return
            }
            lat = location.getLatitude()
            lng = location.getLongitude()
            tv_address.text = (location.addrStr)
            tv_toMyLocation.text = (location.addrStr)

            getData()

            if (isFirst) {
                val ll = LatLng(location.getLatitude(), location.getLongitude())
                val builder = MapStatus.Builder()
                builder.target(ll).zoom(18.0f)
                mMapView.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()))
                val position = LatLng(location.latitude + 0.01, location.getLongitude() + 0.01)
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

    override fun onMapStatusChangeStart(p0: MapStatus?) {
    }

    override fun onMapStatusChangeStart(p0: MapStatus?, p1: Int) {
    }

    override fun onMapStatusChange(p0: MapStatus?) {
    }

    override fun onMapStatusChangeFinish(mapStatus: MapStatus?) {

        mapStatus?.bound?.center?.latitude
        mapStatus?.bound?.center?.latitude

        abLog.e2(mapStatus?.bound?.center?.latitude.toString() + "--->" + mapStatus?.bound?.center?.latitude)
    }


}