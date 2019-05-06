package com.lxkj.qiqihunshe.app.ui.quyu.viewmodel

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import com.baidu.mapapi.map.*
import com.baidu.mapapi.model.LatLng
import com.baidu.mapapi.search.core.PoiInfo
import com.baidu.mapapi.utils.DistanceUtil
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.lxkj.qiqihunshe.R
import com.lxkj.qiqihunshe.app.MyApplication
import com.lxkj.qiqihunshe.app.base.BaseViewModel
import com.lxkj.qiqihunshe.app.retrofitnet.SingleCompose
import com.lxkj.qiqihunshe.app.retrofitnet.SingleObserverInterface
import com.lxkj.qiqihunshe.app.retrofitnet.async
import com.lxkj.qiqihunshe.app.ui.quyu.activity.ShopDetailActivity
import com.lxkj.qiqihunshe.app.ui.quyu.model.DataListModel
import com.lxkj.qiqihunshe.app.ui.quyu.model.QuYuModel
import com.lxkj.qiqihunshe.databinding.ActivityDdtjBinding
import io.reactivex.Single
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.widget.LinearLayout
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.lxkj.qiqihunshe.app.util.*
import kotlinx.android.synthetic.main.layout_add_tuijian.view.*


/**
 * Created by kxn on 2019/3/1 0001.
 */
class DdtjViewModel : BaseViewModel() {
    var bind: ActivityDdtjBinding? = null

    var flag = -1

    private var list = ArrayList<DataListModel>()

    val mMapView by lazy { bind?.bmapView?.map }

    //获取推荐地点
    fun getTuiJian(json: String): Single<String> =
        retrofit.getData(json)
            .async()
            .compose(SingleCompose.compose(object : SingleObserverInterface {
                override fun onSuccess(response: String) {
                    val model = Gson().fromJson(response, QuYuModel::class.java)
                    list = model.dataList
                    for (i in 0 until model.dataList.size) {
                        addOverlay(model.dataList[i], i)
                    }
                }
            }, activity))


    private lateinit var ll_details: LinearLayout
    fun addOverlay(data: DataListModel, position: Int) {
        val point = LatLng(data.lat.toDouble(), data.lon.toDouble())

        Glide.with(activity!!)
            .asBitmap()
            .load(data.logo)
            .into(object : SimpleTarget<Bitmap>() {
                override fun onLoadFailed(errorDrawable: Drawable?) {
                }

                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    val view = LayoutInflater.from(activity).inflate(R.layout.layout_add_tuijian, null)

                    view.ivHead.setImageBitmap(resource)
                    //构建Marker图标

                    view.tvAddress.text = "地址：" + data.address
                    view.tvPhone.text = "电话：" + data.phone
                    val latLng = LatLng(data.lat.toDouble(), data.lon.toDouble())
                    val latLng2 = LatLng(StaticUtil.lat.toDouble(), StaticUtil.lng.toDouble())
                    view.tvDistance.text =
                        "距离：" + DoubleCalculationUtil.mTokm(DistanceUtil.getDistance(latLng, latLng2).toString())
                    view.tvNavigation.text = "选择时间"

                    ll_details = view.findViewById(R.id.linearLayout)

                    if (flag == 0) {
                        view.tvNavigation.text = "选择时间"
                    } else {
                        view.tvNavigation.text = "查看详情"
                        ll_details.visibility = View.INVISIBLE
                    }

                    view.ivHead.setOnClickListener {
                        if (ll_details.visibility == View.GONE) {
                            ll_details.visibility = View.VISIBLE
                        } else {
                            ll_details.visibility = View.GONE
                        }
                    }

//                    val mInfoWindow = InfoWindow(view, point, -100)
//                    使InfoWindow生效
//                    mMapView?.showInfoWindow(mInfoWindow)

                    val des = BitmapDescriptorFactory.fromView(view)
                    //构建MarkerOption，用于在地图上添加Marker
                    val option = MarkerOptions()
                        .position(point)
                        .icon(des)
                    //在地图上添加Marker，并显示
                    marker = mMapView?.addOverlay(option) as Marker
                    marker.title = position.toString()
//                    val mCircleOptions = CircleOptions().center(point)
//                        .radius(10000)//单位米
//                        .fillColor(0x3315acf5) //填充颜色
//                    //在地图上显示圆
//                    mMapView?.addOverlay(mCircleOptions)


                    mMapView?.setOnMarkerClickListener(object : BaiduMap.OnMarkerClickListener {
                        override fun onMarkerClick(p0: Marker?): Boolean {
                            if (p0?.title != position.toString()) {
                                return true
                            }
                            if (flag == 0) {//聊天约见选择地点
                                val intent = Intent()
                                var info = PoiInfo()
                                info.setName(data.title)
                                info.setAddress(data.address)
                                val latLng = LatLng(data.lat.toDouble(), data.lon.toDouble())
                                info.setLocation(latLng)
                                intent.putExtra("poi", info)
                                activity?.setResult(2, intent)
                                activity?.finish()

                            } else {
                                marker.remove()
                                addMark(list[position], position)
                            }
                            return true
                        }
                    })
                }
            })

    }

    lateinit var marker: Marker


    //添加显示详情mark
    fun addMark(data: DataListModel, position: Int) {
        val point = LatLng(data.lat.toDouble(), data.lon.toDouble())

        Glide.with(activity!!)
            .asBitmap()
            .load(data.logo)
            .into(object : SimpleTarget<Bitmap>() {
                override fun onLoadFailed(errorDrawable: Drawable?) {
                }

                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    val view = LayoutInflater.from(activity).inflate(R.layout.layout_add_tuijian, null)

                    view.ivHead.setImageBitmap(resource)
                    //构建Marker图标

                    view.tvAddress.text = "地址：" + data.address
                    view.tvPhone.text = "电话：" + data.phone
                    val latLng = LatLng(data.lat.toDouble(), data.lon.toDouble())
                    val latLng2 = LatLng(StaticUtil.lat.toDouble(), StaticUtil.lng.toDouble())
                    view.tvDistance.text =
                        "距离：" + DoubleCalculationUtil.mTokm(DistanceUtil.getDistance(latLng, latLng2).toString())
                    view.tvNavigation.text = "选择时间"


                    if (flag == 0) {
                        view.tvNavigation.text = "选择时间"
                    } else {
                        view.tvNavigation.text = "查看详情"
                    }


//                    val mInfoWindow = InfoWindow(view, point, -100)
//                    使InfoWindow生效
//                    mMapView?.showInfoWindow(mInfoWindow)

                    val des = BitmapDescriptorFactory.fromView(view)
                    //构建MarkerOption，用于在地图上添加Marker
                    val option = MarkerOptions()
                        .position(point)
                        .icon(des)
                    //在地图上添加Marker，并显示
                    marker = mMapView?.addOverlay(option) as Marker
                    marker.title = position.toString()+"-"
//                    val mCircleOptions = CircleOptions().center(point)
//                        .radius(10000)//单位米
//                        .fillColor(0x3315acf5) //填充颜色
//                    //在地图上显示圆
//                    mMapView?.addOverlay(mCircleOptions)


                    mMapView?.setOnMarkerClickListener(object : BaiduMap.OnMarkerClickListener {
                        override fun onMarkerClick(p0: Marker?): Boolean {
                            if (p0?.title != "$position-") {
                                return true
                            }
                            if (flag == 0) {//聊天约见选择地点
                                val intent = Intent()
                                var info = PoiInfo()
                                info.setName(data.title)
                                info.setAddress(data.address)
                                val latLng = LatLng(data.lat.toDouble(), data.lon.toDouble())
                                info.setLocation(latLng)
                                intent.putExtra("poi", info)
                                activity?.setResult(2, intent)
                                activity?.finish()

                            } else {
                                var bundle = Bundle()
                                bundle.putString("id", data.shopId)
                                MyApplication.openActivity(activity, ShopDetailActivity::class.java, bundle)
                            }
                            return true
                        }
                    })
                }
            })
    }


    //获取一个服务商
    fun getServiceArea(json: String): Single<String> =
        retrofit.getData(json)
            .async()
            .compose(SingleCompose.compose(object : SingleObserverInterface {
                override fun onSuccess(response: String) {
                    val model = Gson().fromJson(response, QuYuModel::class.java)
                    var count = 0
                    var distance = 0.0//显示最近的服务商
                    for (i in 0 until model.dataList.size) {
                        val latlng = LatLng(model.dataList[i].lat.toDouble(), model.dataList[i].lon.toDouble())
                        abLog.e("距离", getDistance(latlng).toString() + "," + i)
                        if (distance == 0.0) {
                            distance = getDistance(latlng)
                        } else {
                            if (getDistance(latlng) < distance) {
                                distance = getDistance(latlng)
                                count = i
                            }
                        }
                    }
                    abLog.e("最终距离", count.toString())
                    addOverlay2(model.dataList[count])
                }
            }, activity))


    private val myLatlng by lazy { LatLng(StaticUtil.lat.toDouble(), StaticUtil.lng.toDouble()) }
    //两点经纬度的距离
    fun getDistance(latlng: LatLng): Double {
        return DistanceUtil.getDistance(myLatlng, latlng)
    }

    fun addOverlay2(data: DataListModel) {
        val point = LatLng(data.lat.toDouble(), data.lon.toDouble())
        val image = LayoutInflater.from(activity).inflate(R.layout.layout_imageview, null)
        GlideUtil.glideHeaderLoad(activity, data.logo, image?.ivHead)

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
