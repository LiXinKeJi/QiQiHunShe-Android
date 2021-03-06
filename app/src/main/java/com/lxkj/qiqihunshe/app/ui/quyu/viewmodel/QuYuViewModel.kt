package com.lxkj.qiqihunshe.app.ui.quyu.viewmodel

import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import com.baidu.mapapi.map.*
import com.baidu.mapapi.model.LatLng
import com.google.gson.Gson
import com.lxkj.qiqihunshe.R
import com.lxkj.qiqihunshe.app.base.BaseModel
import com.lxkj.qiqihunshe.app.base.BaseViewModel
import com.lxkj.qiqihunshe.app.retrofitnet.SingleCompose
import com.lxkj.qiqihunshe.app.retrofitnet.SingleObserverInterface
import com.lxkj.qiqihunshe.app.retrofitnet.async
import com.lxkj.qiqihunshe.app.ui.quyu.model.DataListModel
import com.lxkj.qiqihunshe.app.ui.quyu.model.QuYuModel
import com.lxkj.qiqihunshe.app.util.*
import com.lxkj.qiqihunshe.databinding.FragmentQuyuBinding
import io.reactivex.Single
import kotlinx.android.synthetic.main.layout_imageview.view.*
import kotlinx.android.synthetic.main.layout_infowindow_qy.view.*
import com.baidu.mapapi.map.MyLocationData
import com.baidu.mapapi.map.MapStatusUpdateFactory
import com.baidu.mapapi.map.MapStatusUpdate
import com.baidu.mapapi.search.core.RouteNode.location
import com.baidu.mapapi.map.MyLocationConfiguration
import com.baidu.mapapi.map.BitmapDescriptorFactory
import com.baidu.mapapi.map.BitmapDescriptor
import org.json.JSONObject


/**
 * Created by Slingge on 2019/2/16
 */
class QuYuViewModel : BaseViewModel() {

    var bind: FragmentQuyuBinding? = null

    var headOffice = DataListModel() //总公司
    var serviceOffice = DataListModel() //服务网点
    var hiList = ArrayList<String>() //打招呼用语
    var canXz = false //是否可以安全协助

    private var defaultlatlng: LatLng? = null

    //获取服务网点
    fun getServiceArea(json: String): Single<String> =
        retrofit.getData(json)
            .async()
            .compose(SingleCompose.compose(object : SingleObserverInterface {
                override fun onSuccess(response: String) {
                    val model = Gson().fromJson(response, QuYuModel::class.java)

                    if (!StringUtil.isEmpty(model.arrivalTime)) {
                        bind!!.tvTime?.text = ("到场时间：" + model.arrivalTime)
                        canXz = true
                    } else
                        canXz = false

                    var servicePosition = 0
                    for (i in 0 until model.dataList?.size) {
                        if (model.dataList[i].default == ("1")) { //总公司
                            headOffice = model.dataList[i]
                            defaultlatlng = LatLng(model.dataList[i].lat.toDouble(), model.dataList[i].lon.toDouble())
                        }
                        if (i > 0 && model.dataList[i].distance.toDouble() < model.dataList[i - 1].distance.toDouble())
                            servicePosition = i

                    }
                    //最近的服务网点
                    serviceOffice = model.dataList[servicePosition]

                    if (null != serviceOffice) {
                        if (serviceOffice.distance.toInt() > 10000)
                            bind!!.tvNoRange.text = "您不在小七的服务范围哦"
                        else
                            bind!!.tvNoRange.text = "当前位置为小七服务范围，请随时呼叫小七"

                        setData(serviceOffice)
                    } else
                        bind!!.tvNoRange.visibility = VISIBLE

                }
            }, fragment?.activity))

    //获取打招呼用语
    fun getChatList(json: String): Single<String> = retrofit.getData(json)
        .async()
        .compose(SingleCompose.compose(object : SingleObserverInterface {
            override fun onSuccess(response: String) {
                val model = Gson().fromJson(response, QuYuModel::class.java)
                for (i in 0 until model.dataList?.size) {
                    hiList.add(model.dataList[i].content)
                }
            }
        }, fragment?.activity))

    //打招呼
    fun greet(json: String): Single<String> = retrofit.getData(json)
        .async()
        .compose(SingleCompose.compose(object : SingleObserverInterface {
            override fun onSuccess(response: String) {
                val model = Gson().fromJson(response, BaseModel::class.java)
                ToastUtil.showTopSnackBar(fragment!!.activity, model.resultNote)
            }
        }, fragment?.activity))


    fun setData(data: DataListModel) {
        bind?.bmapView?.map!!.clear()
        GlideUtil.glideLoad(fragment!!.activity, data?.logo, bind?.headOfficeIv)
        addOverlay(data)
    }


    fun addOverlay(data: DataListModel) {
        val point = LatLng(data.lat.toDouble(), data.lon.toDouble())
        val image = LayoutInflater.from(fragment?.activity).inflate(R.layout.layout_imageview, null)
        GlideUtil.glideHeaderLoad(fragment?.activity, data?.logo, image?.ivHead)
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
                        LayoutInflater.from(fragment?.context)
                            .inflate(com.lxkj.qiqihunshe.R.layout.layout_infowindow_qy, null)
                    view?.tvAddress?.text = "地址：" + data?.address
                    view?.tvPhone?.text = "电话：" + data?.phone
                    view?.tvDistance?.text = "距离：" + DisplayUtil.distanceFormat(data?.distance.toDouble())

                    view.tvNavigation.setOnClickListener {
                        var mapNavigationUtil = MapNavigationUtil(fragment?.context)
                        mapNavigationUtil.goToBaiduMap(data.lat, data.lon, data.address)
                    }
                    val mInfoWindow = InfoWindow(view, point, -150)
                    //使InfoWindow生效
                    mMapView.showInfoWindow(mInfoWindow)
                }
                return true
            }
        })
    }


    //移动地图到默认服务商

    fun moveMap() {

        defaultlatlng?.let {
            abLog.e("默认金维度", it.latitude.toString() + "," + it.longitude)
            val locationData = MyLocationData.Builder()
                .latitude(it.latitude)
                .longitude(it.longitude)
                .build()
            // 设置定位数据
            bind?.bmapView?.map?.setMyLocationData(locationData)
            // 自定以图表
            // 设置定位图层的配置，设置图标跟随状态（图标一直在地图中心）
            val config = MyLocationConfiguration(
                MyLocationConfiguration.LocationMode.FOLLOWING, true, null
            )
            bind?.bmapView?.map?.setMyLocationConfigeration(config)
        }
    }


    var SignNUm = 0//签到天数
    fun checkIn(): Single<String> {
        val json = "{\"cmd\":\"sign\",\"uid\":\"" + StaticUtil.uid + "\"}"
        return retrofit.getData(json).async()
            .doOnSuccess {
                val obj = JSONObject(it)
                SignNUm = obj.getString("qty").toInt() + 10
                abLog.e("签到", SignNUm.toString())
            }

    }


}