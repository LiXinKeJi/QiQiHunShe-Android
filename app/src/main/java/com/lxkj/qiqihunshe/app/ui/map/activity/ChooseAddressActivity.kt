package com.lxkj.qiqihunshe.app.ui.map.activity

import android.content.Intent
import android.graphics.Bitmap
import android.view.View
import android.widget.AdapterView
import com.baidu.mapapi.cloud.*
import com.lxkj.qiqihunshe.R
import com.lxkj.qiqihunshe.app.base.BaseActivity
import com.lxkj.qiqihunshe.app.ui.map.viewmodel.ChooseAddressViewModel
import com.lxkj.qiqihunshe.databinding.ActivityChooseAddressBinding
import kotlinx.android.synthetic.main.activity_choose_address.*
import com.baidu.mapapi.search.core.RouteNode.location
import com.baidu.mapsdkplatform.comapi.map.ak
import com.baidu.mapapi.cloud.CloudRgcInfo
import com.baidu.mapapi.map.*
import com.baidu.mapapi.model.LatLng
import com.baidu.mapapi.search.core.PoiInfo
import com.baidu.mapapi.search.core.SearchResult
import com.baidu.mapapi.search.geocode.GeoCodeResult
import com.baidu.mapapi.search.geocode.GeoCoder
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption
import com.lxkj.qiqihunshe.app.ui.map.adapter.PoiAdapter
import com.lxkj.qiqihunshe.app.util.FileUtil
import com.lxkj.qiqihunshe.app.util.StaticUtil
import com.lxkj.qiqihunshe.app.util.ToastUtil


/**
 * Created by kxn on 2019/3/2 0002.
 * 选择位置信息
 */
class ChooseAddressActivity : BaseActivity<ActivityChooseAddressBinding, ChooseAddressViewModel>(),
    BaiduMap.OnMapStatusChangeListener, OnGetGeoCoderResultListener, BaiduMap.OnMapClickListener {


    val mMapView by lazy { bmapView.map }
    val mCoder by lazy { GeoCoder.newInstance(); }
    var list = ArrayList<PoiInfo>()
    var adapter: PoiAdapter? = null

    override fun getBaseViewModel(): ChooseAddressViewModel = ChooseAddressViewModel()

    override fun getLayoutId(): Int = R.layout.activity_choose_address

    override fun init() {
        initTitle("选择地址")
        mMapView.setOnMapStatusChangeListener(this)
        mCoder.setOnGetGeoCodeResultListener(this)
        mMapView.showMapIndoorPoi(false)
        mMapView.setOnMapClickListener(this)

        val ll = LatLng(StaticUtil.lat.toDouble(), StaticUtil.lng.toDouble())
        mCoder.reverseGeoCode(
            ReverseGeoCodeOption()
                .location(ll)
                .pageSize(30)
                .radius(500)
        )
        val builder = MapStatus.Builder()
        builder.target(ll).zoom(18.0f)
        mMapView.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()))
        adapter = PoiAdapter(this, list)
        lvPoi.adapter = adapter
        lvPoi?.setOnItemClickListener { p0, p1, p2, p3 ->

            mMapView.snapshot {
                FileUtil.saveFile(this@ChooseAddressActivity, "screenshot", it)
            }


            var intent = Intent()
            intent.putExtra("poi", list[p2])
            setResult(1, intent)
            finish()
        }
    }

    override fun onMapStatusChangeStart(p0: MapStatus?) {

    }

    override fun onMapStatusChangeStart(p0: MapStatus?, p1: Int) {

    }

    override fun onMapStatusChange(p0: MapStatus?) {

    }

    override fun onMapStatusChangeFinish(mapStatus: MapStatus?) {
        mCoder.reverseGeoCode(
            ReverseGeoCodeOption()
                .location(mapStatus?.target)
                // POI召回半径，允许设置区间为0-1000米，超过1000米按1000米召回。默认值为1000
                .radius(500)
        )


    }

    override fun onGetGeoCodeResult(p0: GeoCodeResult?) {
    }

    override fun onGetReverseGeoCodeResult(reverseGeoCodeResult: ReverseGeoCodeResult?) {
        if (reverseGeoCodeResult == null || reverseGeoCodeResult.error !== SearchResult.ERRORNO.NO_ERROR) {
            //没有找到检索结果
            ToastUtil.showTopSnackBar(this, "没有找到检索结果")
            return
        } else {
            if (null != reverseGeoCodeResult.poiList) {
                //详细地址
                list.clear()
                list.addAll(reverseGeoCodeResult.poiList)
                adapter?.notifyDataSetChanged()
            }
        }
    }


    //地图点击事件
    override fun onMapClick(p0: LatLng?) {
        p0?.let {
            val ll = LatLng(it.latitude, it.longitude)
            mCoder.reverseGeoCode(
                ReverseGeoCodeOption()
                    .location(ll)
                    .pageSize(2)
                    .radius(500)
            )
            val builder = MapStatus.Builder()
            builder.target(ll).zoom(18.0f)
            mMapView.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()))
        }

    }

    override fun onMapPoiClick(p0: MapPoi?): Boolean {
        return true
    }


}