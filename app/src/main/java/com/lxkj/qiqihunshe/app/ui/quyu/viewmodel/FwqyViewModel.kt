package com.lxkj.qiqihunshe.app.ui.quyu.viewmodel

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import com.baidu.mapapi.map.*
import com.baidu.mapapi.model.LatLng
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.google.gson.Gson
import com.lxkj.qiqihunshe.R
import com.lxkj.qiqihunshe.app.base.BaseViewModel
import com.lxkj.qiqihunshe.app.retrofitnet.SingleCompose
import com.lxkj.qiqihunshe.app.retrofitnet.SingleObserverInterface
import com.lxkj.qiqihunshe.app.retrofitnet.async
import com.lxkj.qiqihunshe.app.ui.quyu.model.DataListModel
import com.lxkj.qiqihunshe.app.ui.quyu.model.QuYuModel
import com.lxkj.qiqihunshe.app.util.DisplayUtil
import com.lxkj.qiqihunshe.app.util.GlideUtil
import com.lxkj.qiqihunshe.app.util.MapNavigationUtil
import com.lxkj.qiqihunshe.databinding.ActivityFwqyBinding
import io.reactivex.Single
import kotlinx.android.synthetic.main.layout_imageview.view.*
import kotlinx.android.synthetic.main.layout_infowindow_qy.view.*

/**
 * Created by kxn on 2019/3/1 0001.
 */
class FwqyViewModel : BaseViewModel() {
    var bind: ActivityFwqyBinding? = null
    //获取服务网点
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

        Glide.with(activity!!)
            .asBitmap()
            .load(data?.logo)
            .into(object : SimpleTarget<Bitmap>() {
                override fun onLoadFailed(errorDrawable: Drawable?) {
                    val image = LayoutInflater.from(activity).inflate(R.layout.layout_imageview, null)
                    //构建Marker图标
                    val des = BitmapDescriptorFactory.fromView(image)
                    //构建MarkerOption，用于在地图上添加Marker
                    val option = MarkerOptions()
                        .position(point)
                        .icon(des)
                    //在地图上添加Marker，并显示
                    var mMapView = bind?.bmapView?.map
                    val marker = mMapView?.addOverlay(option) as Marker

                    //构造CircleOptions对象
                    val mCircleOptions = CircleOptions().center(point)
                        .radius(10000)//单位米
                        .fillColor(0x3315acf5) //填充颜色
                    //在地图上显示圆
                    mMapView.addOverlay(mCircleOptions)


                    mMapView.setOnMarkerClickListener(object : BaiduMap.OnMarkerClickListener {
                        override fun onMarkerClick(p0: Marker?): Boolean {
                            if (p0 == marker) {
                                val view =
                                    LayoutInflater.from(activity)
                                        .inflate(com.lxkj.qiqihunshe.R.layout.layout_infowindow_qy, null)
                                view?.tvAddress?.text = "地址：" + data?.address
                                view?.tvPhone?.text = "电话：" + data?.phone
                                view?.tvDistance?.text = "距离：" + DisplayUtil.distanceFormat(data?.distance.toDouble())

                                view.tvNavigation.setOnClickListener(object : View.OnClickListener {
                                    override fun onClick(p0: View?) {
                                        var mapNavigationUtil = MapNavigationUtil(activity)
                                        mapNavigationUtil?.goToBaiduMap(data.lat, data.lon, data.address)
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
                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    val image = LayoutInflater.from(activity).inflate(R.layout.layout_imageview, null)
                    image.ivHead.setImageBitmap(resource)
                    //构建Marker图标
                    val des = BitmapDescriptorFactory.fromView(image)
                    //构建MarkerOption，用于在地图上添加Marker
                    val option = MarkerOptions()
                        .position(point)
                        .icon(des)
                    //在地图上添加Marker，并显示
                    var mMapView = bind?.bmapView?.map
                    val marker = mMapView?.addOverlay(option) as Marker

                    //构造CircleOptions对象
                    val mCircleOptions = CircleOptions().center(point)
                        .radius(10000)//单位米
                        .fillColor(0x3315acf5) //填充颜色
                    //在地图上显示圆
                    mMapView.addOverlay(mCircleOptions)



                    mMapView.setOnMarkerClickListener(object : BaiduMap.OnMarkerClickListener {
                        override fun onMarkerClick(p0: Marker?): Boolean {
                            if (p0 == marker) {
                                val view =
                                    LayoutInflater.from(activity)
                                        .inflate(com.lxkj.qiqihunshe.R.layout.layout_infowindow_qy, null)
                                view?.tvAddress?.text = "地址：" + data?.address
                                view?.tvPhone?.text = "电话：" + data?.phone
                                view?.tvDistance?.text = "距离：" + DisplayUtil.distanceFormat(data?.distance.toDouble())

                                view.tvNavigation.setOnClickListener(object : View.OnClickListener {
                                    override fun onClick(p0: View?) {
                                        var mapNavigationUtil = MapNavigationUtil(activity)
                                        mapNavigationUtil?.goToBaiduMap(data.lat, data.lon, data.address)
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
            })



    }
}