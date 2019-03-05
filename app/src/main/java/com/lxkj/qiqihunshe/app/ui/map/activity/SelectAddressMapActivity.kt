package com.lxkj.qiqihunshe.app.ui.map.activity

import android.view.LayoutInflater
import android.view.View
import com.baidu.mapapi.map.*
import com.baidu.mapapi.model.LatLng
import com.baidu.mapapi.search.core.SearchResult
import com.baidu.mapapi.search.geocode.GeoCodeResult
import com.baidu.mapapi.search.geocode.GeoCoder
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult
import com.lxkj.qiqihunshe.R
import com.lxkj.qiqihunshe.app.base.BaseActivity
import com.lxkj.qiqihunshe.app.ui.dialog.SelectMeetTimeDialog
import com.lxkj.qiqihunshe.app.ui.map.viewmodel.SelectAddressMapViewModel
import com.lxkj.qiqihunshe.databinding.ActivitySelectAddressMapBinding
import kotlinx.android.synthetic.main.activity_select_address_map.*
import kotlinx.android.synthetic.main.layout_infowindow_select_address.view.*
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption
import com.baidu.mapapi.search.core.SearchResult.ERRORNO
import com.lxkj.qiqihunshe.app.util.ToastUtil


/**
 * Created by kxn on 2019/3/1 0001.
 * 地图选择地址 聊天选择位置
 */
class SelectAddressMapActivity : BaseActivity<ActivitySelectAddressMapBinding,SelectAddressMapViewModel>(),
    BaiduMap.OnMapClickListener, OnGetGeoCoderResultListener {



    val mMapView by lazy { bmapView.map }
    val mCoder = GeoCoder.newInstance()

    override fun getBaseViewModel(): SelectAddressMapViewModel = SelectAddressMapViewModel()

    override fun getLayoutId():  Int {
        return R.layout.activity_select_address_map
    }
    override fun init() {
        initTitle("选择地址")
        lat = intent.getDoubleExtra("lat",0.0).toString()
        lng = intent.getDoubleExtra("lng",0.0).toString()

        val ll = LatLng(lat.toDouble(),lng.toDouble())
        val builder = MapStatus.Builder()
        builder.target(ll).zoom(15.0f)
        mMapView.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()))
        val position = LatLng(lat.toDouble(), lng.toDouble())

        addOverlay(position)
        addOverlay( LatLng(lat.toDouble() + 0.01, lng.toDouble() -0.01))
        addOverlay( LatLng(lat.toDouble() - 0.01, lng.toDouble() + 0.01))

        //设置地图单击事件监听
        mMapView.setOnMapClickListener(this)
        mCoder.setOnGetGeoCodeResultListener(this);

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
                        LayoutInflater.from(this@SelectAddressMapActivity).inflate(R.layout.layout_infowindow_select_address, null)
                    view.chooseTimeTv.setOnClickListener(object : View.OnClickListener{
                        override fun onClick(p0: View?) {
                            SelectMeetTimeDialog.show(this@SelectAddressMapActivity)
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

    override fun onDestroy() {
        super.onDestroy()
        mCoder.destroy()
    }

    override fun onMapPoiClick(point: MapPoi?): Boolean {
        return true
    }
    //地图点击回调
    override fun onMapClick(point: LatLng?) {
        mCoder.reverseGeoCode(
            ReverseGeoCodeOption()
                .location(point)
                // POI召回半径，允许设置区间为0-1000米，超过1000米按1000米召回。默认值为1000
                .radius(1000)
        )
    }

    override fun onGetGeoCodeResult(p0: GeoCodeResult?) {

    }

    override fun onGetReverseGeoCodeResult(reverseGeoCodeResult: ReverseGeoCodeResult?) {
        if (reverseGeoCodeResult == null || reverseGeoCodeResult.error !== SearchResult.ERRORNO.NO_ERROR) {
            //没有找到检索结果
            return
        } else {
            //详细地址
            val address = reverseGeoCodeResult.getAddress()
            ToastUtil.showToast(address)

        }
    }



}