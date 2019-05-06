package com.lxkj.qiqihunshe.app.ui.quyu

import android.Manifest
import android.content.Intent
import android.net.Uri
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
import com.zhy.m.permission.MPermissions
import com.zhy.m.permission.PermissionDenied
import com.zhy.m.permission.PermissionGrant
import android.os.Bundle
import android.view.View
import com.baidu.mapapi.map.*
import com.baidu.mapapi.search.core.SearchResult
import com.baidu.mapapi.search.geocode.*
import com.google.gson.Gson
import com.lxkj.qiqihunshe.app.MyApplication
import com.lxkj.qiqihunshe.app.retrofitnet.bindLifeCycle
import com.lxkj.qiqihunshe.app.rongrun.RongYunUtil
import com.lxkj.qiqihunshe.app.ui.dialog.AqxzDialog
import com.lxkj.qiqihunshe.app.ui.dialog.FwwdDialog
import com.lxkj.qiqihunshe.app.ui.dialog.SayHolleDialog
import com.lxkj.qiqihunshe.app.ui.quyu.activity.DdtjActivity
import com.lxkj.qiqihunshe.app.ui.quyu.activity.FwqyActivity
import com.lxkj.qiqihunshe.app.util.*


/**
 * Created by Slingge on 2019/2/16
 */
class QuYuFragment : BaseFragment<FragmentQuyuBinding, QuYuViewModel>(), View.OnClickListener,
    BaiduMap.OnMapStatusChangeListener, FwwdDialog.OnTellListener, AqxzDialog.OnTellListener,
    SayHolleDialog.OnSayHiListener, OnGetGeoCoderResultListener {


    val mMapView by lazy { bmapView.map }
    val mLocationClient by lazy { LocationClient(context) }
    var isFirst = true

    var lat: Double = 0.0
    var lng: Double = 0.0

    var currentPosition: LatLng? = null
    var phoneNum: String? = null

    var mCoder = GeoCoder.newInstance()

    override fun getBaseViewModel() = QuYuViewModel()

    override fun getLayoutId() = R.layout.fragment_quyu

    override fun init() {

        viewModel?.bind = binding
        viewModel!!.checkIn().bindLifeCycle(this).subscribe({},{toastFailure(it)})

        mMapView.isMyLocationEnabled = true
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
        tv_toMyLocation.setOnClickListener(this)
        iv_close.setOnClickListener(this)
        tv_Qdh.setOnClickListener(this)
        tv_Lxkf.setOnClickListener(this)

        params.clear()
        params.put("cmd", "getChatList")
        viewModel?.getChatList(Gson().toJson(params))?.bindLifeCycle(this)?.subscribe({ }, { toastFailure(it) })

        mCoder.setOnGetGeoCodeResultListener(this)
        headOfficeIv.setOnClickListener(this)
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
        mLocationClient.locOption = option
        //注册LocationListener监听器
        val myLocationListener = MyLocationListener()
        mLocationClient.registerLocationListener(myLocationListener)
        //开启地图定位图层
        mLocationClient.start()
    }


    @PermissionDenied(AppConsts.PMS_LOCATION)
    fun pmsLocationError() {
        ToastUtil.showTopSnackBar(activity, "权限被拒绝，定位失败！")
    }

    @PermissionGrant(AppConsts.PMS_CALL)
    fun pmsCallSuccess() {
        //权限授权成功
        val intent = Intent(Intent.ACTION_CALL, Uri.parse("tel:$phoneNum"))
        startActivity(intent)
    }

    @PermissionDenied(AppConsts.PMS_CALL)
    fun pmsCallError() {
        ToastUtil.showTopSnackBar(activity, "权限被拒绝，无法使用该功能！")
    }

    override fun onSayHi(phoneNum: String) {
        params.clear()
        params.put("cmd", "greet")
        params.put("uid", StaticUtil.uid)
        params.put("lon", lng.toString())
        params.put("lat", lat.toString())
        params.put("content", phoneNum)
        abLog.e("打招呼...........", Gson().toJson(params))
        viewModel?.greet(Gson().toJson(params))?.bindLifeCycle(this)?.subscribe({ }, { toastFailure(it) })

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
        mMapView.isMyLocationEnabled = false
        super.onDestroy()
    }


    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.iv_fwqy -> {
                val bundle = Bundle()
                bundle.putDouble("lat", lat)
                bundle.putDouble("lng", lng)
                bundle.putString("address", tv_address.text.toString())
                MyApplication.openActivity(activity, FwqyActivity::class.java, bundle)
            }
            R.id.iv_sayHi -> {
                if (viewModel!!.hiList.isEmpty()) {
                    ToastUtil.showTopSnackBar(activity, "暂无打招呼内容")
                    return
                }
                SayHolleDialog.show(activity!!, viewModel!!.hiList,viewModel!!.SignNUm)
                SayHolleDialog.setListener(this)
            }

            R.id.tv_aqxz -> {
                if (viewModel?.canXz!!) {
                    AqxzDialog.show(activity!!, viewModel!!.serviceOffice)
                    AqxzDialog.setListener(this)
                } else
                    ToastUtil.showTopSnackBar(activity, "没有约见信息，暂不可用")

            }

            R.id.tv_fwwd -> {
                FwwdDialog.show(activity!!, viewModel!!.serviceOffice)
                FwwdDialog.setListener(this)
            }


            R.id.headOfficeIv -> {//默认服务商
                viewModel?.moveMap()
            }

            R.id.tv_ddtj -> {
                val bundle = Bundle()
                bundle.putDouble("lat", lat)
                bundle.putDouble("lng", lng)
                MyApplication.openActivity(activity, DdtjActivity::class.java, bundle)
            }

            R.id.tv_toMyLocation -> {
                val builder = MapStatus.Builder()
                builder.target(currentPosition)
                mMapView.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()))
            }

            R.id.tv_Qdh -> {
                var mapNavigationUtil = MapNavigationUtil(context)
                mapNavigationUtil.goToBaiduMap(
                    viewModel?.serviceOffice?.lat,
                    viewModel?.serviceOffice?.lon,
                    viewModel?.serviceOffice?.address
                )
            }

            R.id.iv_close -> {
                ll_hint.visibility = View.GONE
            }
            R.id.tv_Lxkf -> RongYunUtil.toService(activity)
        }

    }

    override fun onTell(phoneNum: String) {
        this.phoneNum = phoneNum
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            MPermissions.requestPermissions(
                this, AppConsts.PMS_CALL,
                Manifest.permission.CALL_PHONE
            )
        } else {
            pmsCallSuccess()
        }
    }


    fun getData() {
        params.clear()
        params.put("cmd", "serviceArea")
        params.put("uid", StaticUtil.uid)
        params.put("lon", lng.toString())
        params.put("lat", lat.toString())
        viewModel!!.getServiceArea(Gson().toJson(params)).bindLifeCycle(this).subscribe({ }, { toastFailure(it) })
    }

    inner class MyLocationListener : BDAbstractLocationListener() {
        override fun onReceiveLocation(location: BDLocation?) {
            //mapView 销毁后不在处理新接收的位置
            if (location == null || mMapView == null) {
                return
            }
            lat = location.latitude
            lng = location.longitude
            StaticUtil.lat = lat.toString()
            StaticUtil.lng = lng.toString()
            SharePrefUtil.saveString(context, AppConsts.LAT, lat.toString())
            SharePrefUtil.saveString(context, AppConsts.LNG, lng.toString())

            tv_address.text = (location.addrStr)
            tv_toMyLocation.text = (location.addrStr)

            if (isFirst) {
                currentPosition = LatLng(location.latitude, location.longitude)

                val ll = LatLng(location.latitude, location.longitude)
                val builder = MapStatus.Builder()
                builder.target(ll).zoom(13.0f)
                mMapView.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()))
                getData()
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
        lat = mapStatus?.bound?.center?.latitude!!
        lng = mapStatus.bound?.center?.longitude!!
        getData()
        mCoder.reverseGeoCode(
            ReverseGeoCodeOption()
                .location(mapStatus?.bound?.center)
                .radius(500)
        )
    }

    override fun onGetGeoCodeResult(p0: GeoCodeResult?) {
    }

    override fun onGetReverseGeoCodeResult(reverseGeoCodeResult: ReverseGeoCodeResult?) {
        if (reverseGeoCodeResult == null || reverseGeoCodeResult.error != SearchResult.ERRORNO.NO_ERROR) {
            //没有找到检索结果
            return
        } else {
            //详细地址
            var address = reverseGeoCodeResult.address
            tv_toMyLocation.text = address
        }
    }


}