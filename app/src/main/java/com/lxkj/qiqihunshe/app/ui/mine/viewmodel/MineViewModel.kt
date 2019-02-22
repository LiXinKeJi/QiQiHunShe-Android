package com.lxkj.qiqihunshe.app.ui.mine.viewmodel

import com.lxkj.qiqihunshe.app.base.BaseViewModel
import com.lxkj.qiqihunshe.app.util.ThreadUtil
import com.lxkj.qiqihunshe.databinding.FragmentMineBinding
import com.lxkj.huaihuatransit.app.util.ControlWidthHeight
import android.util.DisplayMetrics
import com.lxkj.qiqihunshe.R
import com.lxkj.qiqihunshe.app.ui.dialog.CategoryPop
import java.util.*


/**
 * Created by Slingge on 2019/2/16
 */
class MineViewModel : BaseViewModel(), CategoryPop.Categoryinterface {


    var bind: FragmentMineBinding? = null


    //假设的总进度，最多为100，可自行调整
    private val status = 30
    //当前进度
    private var currentStatue: Int = 0
    /**
     * 当前位置
     */
    private var currentPosition: Float = 0.toFloat()

    //得到屏幕的总宽度
    private var width: Int = 0
    private var scrollDistance: Float = 0.toFloat()
    private var tvWidth: Int = 0


    fun initAchieve() {
        val outMetrics = DisplayMetrics()
        fragment?.activity!!.windowManager.defaultDisplay.getMetrics(outMetrics)
        width = (outMetrics.widthPixels - (ControlWidthHeight.dip2px(fragment?.context!!, 42))) * status / 100

        //开启分线程
        Thread(Runnable {
            //每一段要移动的距离
            scrollDistance = width.toFloat()
            for (i in 0 until status) {
                ThreadUtil.runOnMainThread(Runnable {
                    // 控制进度条的增长进度
                    bind!!.pbReputation.incrementProgressBy(1)
                    currentStatue++
                    bind!!.tvReputation.text = currentStatue.toString()
                    // 得到字体的宽度
                    tvWidth = bind!!.tvReputation.width
                    currentPosition += scrollDistance
                    //做一个平移动画的效果

                })

                try {
                    Thread.sleep(30)
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
            }
        }).start()
        bind!!.tvReputation.translationX = width.toFloat()
    }


    private var categoryPop: CategoryPop? = null
    private val list by lazy { ArrayList<String>() }
    //选择状态
    fun selectState() {
        if (list.isEmpty()) {
            list.addAll(fragment!!.context!!.resources.getStringArray(R.array.state))
        }
        if (categoryPop == null) {
            categoryPop = CategoryPop(fragment!!.context!!)
            categoryPop?.setCatinterface(this)
        }
        categoryPop!!.screenPopwindow(list, bind!!.tvState)
    }

    // 选中的状态
    override fun category(position: Int) {
        bind!!.tvState.text = list[position]
    }


}