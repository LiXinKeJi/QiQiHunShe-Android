package com.lxkj.qiqihunshe.app.ui.quyu.viewmodel

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import com.baidu.mapapi.map.*
import com.baidu.mapapi.model.LatLng
import com.baidu.mapapi.search.core.PoiInfo
import com.baidu.mapapi.utils.DistanceUtil
import com.google.gson.Gson
import com.lxkj.qiqihunshe.R
import com.lxkj.qiqihunshe.app.MyApplication
import com.lxkj.qiqihunshe.app.base.BaseViewModel
import com.lxkj.qiqihunshe.app.retrofitnet.SingleCompose
import com.lxkj.qiqihunshe.app.retrofitnet.SingleObserverInterface
import com.lxkj.qiqihunshe.app.retrofitnet.async
import com.lxkj.qiqihunshe.app.ui.quyu.activity.DdtjActivity
import com.lxkj.qiqihunshe.app.ui.quyu.activity.ShopDetailActivity
import com.lxkj.qiqihunshe.app.ui.quyu.model.DataListModel
import com.lxkj.qiqihunshe.app.ui.quyu.model.QuYuModel
import com.lxkj.qiqihunshe.app.util.*
import com.lxkj.qiqihunshe.databinding.ActivityDdtjBinding
import io.reactivex.Single
import kotlinx.android.synthetic.main.layout_imageview.view.*
import kotlinx.android.synthetic.main.layout_infowindow_qy.view.*

/**
 * Created by kxn on 2019/3/1 0001.
 */
class DdtjViewModel : BaseViewModel() {
    var bind: ActivityDdtjBinding? = null

    var flag = -1//0聊天约见选择地点

    //获取推荐地点
    fun getServiceArea(json: String): Single<String> =
        retrofit.getData(json)
            .async()
            .compose(SingleCompose.compose(object : SingleObserverInterface {
                override fun onSuccess(response: String) {
                    val model = Gson().fromJson(response, QuYuModel::class.java)
                    for (i in 0 until model.dataList?.size) {
                        addOverlay(model.dataList[i])
                    }
                }
            }, activity))


    fun addOverlay(data: DataListModel) {
        val point = LatLng(data.lat.toDouble(), data.lon.toDouble())
        val image = LayoutInflater.from(activity).inflate(R.layout.layout_imageview, null)
        GlideUtil.glideHeaderLoad(activity, data?.logo, image?.ivHead)
        //构建Marker图标
        val des = BitmapDescriptorFactory.fromView(image)
        //构建MarkerOption，用于在地图上添加Marker
        val option = MarkerOptions()
            .position(point)
            .icon(des)
        //在地图上添加Marker，并显示
        var mMapView = bind?.bmapView?.map
        val marker = mMapView?.addOverlay(option) as Marker


        mMapView.setOnMarkerClickListener(object : BaiduMap.OnMarkerClickListener {
            override fun onMarkerClick(p0: Marker?): Boolean {
                if (p0 == marker) {
                    if (flag == 0) {//聊天约见选择地点
                        val view =
                            LayoutInflater.from(activity)
                                .inflate(com.lxkj.qiqihunshe.R.layout.layout_infowindow_qy, null)
                        view.tvAddress.text = "地址：" + data.address
                        view.tvPhone.text = "电话：" + data.phone
                        val latLng = LatLng(data.lat.toDouble(), data.lon.toDouble())
                        val latLng2 = LatLng(StaticUtil.lat.toDouble(), StaticUtil.lng.toDouble())
                        view.tvDistance.text =
                            "距离：" + DoubleCalculationUtil.mTokm(DistanceUtil.getDistance(latLng, latLng2).toString())
                        view.tvNavigation.text = "选择时间"
                        view.tvNavigation.setOnClickListener {
                            val intent = Intent()
                            var info = PoiInfo()
                            info.setName(data.title)
                            info.setAddress(data.address)
                            val latLng = LatLng(data.lat.toDouble(), data.lon.toDouble())
                            info.setLocation(latLng)
                            intent.putExtra("poi", info)
                            activity?.setResult(2, intent)
                            activity?.finish()
                        }

                        val mInfoWindow = InfoWindow(view, point, -100)
                        //使InfoWindow生效
                        mMapView.showInfoWindow(mInfoWindow)

                    } else {
                        var bundle = Bundle()
                        bundle.putString("id", data.shopId)
                        MyApplication.openActivity(activity, ShopDetailActivity::class.java, bundle)
                    }
                }
                return true
            }
        })
    }
}