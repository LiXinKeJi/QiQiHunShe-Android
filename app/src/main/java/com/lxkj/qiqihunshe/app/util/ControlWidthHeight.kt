package com.lxkj.huaihuatransit.app.util


import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.GridView
import android.widget.ListView
import android.widget.TextView

/**
 * textView、edittext等控件宽度获取设置
 * Created by Slingge on 2016/10/14 0014.
 */

object ControlWidthHeight {

    /**
     * 获取textview宽度
     */
    fun getWidth(tv: TextView): Int {
        val w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
        val h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
        tv.measure(w, h)
        return tv.measuredWidth
    }


    /**
     * 获取View高度
     */
    fun OutputView(view: View): Int {
        val w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
        val h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
        view.measure(w, h)
        return view.measuredHeight
    }


    /**
     * 获取View宽度
     */
    fun OutputViewWidth(view: View): Int {
        val w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
        val h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
        view.measure(w, h)
        return view.measuredWidth
    }


    /**
     * 设置textview宽度
     */
    fun input(width: Int, tv: View) {
        val para = tv.layoutParams
        para.width = width
        tv.layoutParams = para
    }

    /**
     * 设置View高度
     */
    fun inputhigh(width: Int, tv: View) {
        val para = tv.layoutParams
        para.height = width
        tv.layoutParams = para
    }


    /**
     * 动态设置GridView的高度
     * @param listView
     */
    fun setListViewHeightBasedOnChildren(listView: GridView) {
        // 获取listview的adapter
        val listAdapter = listView.adapter ?: return
// 固定列宽，有多少列
        val col = 3// listView.getNumColumns();
        var totalHeight = 0
        // i每次加4，相当于listAdapter.getCount()小于等于4时 循环一次，计算一次item的高度，
        // listAdapter.getCount()小于等于8时计算两次高度相加
        var i = 0
        while (i < listAdapter.count) {
            // 获取listview的每一个item
            val listItem = listAdapter.getView(i, null, listView)
            listItem.measure(0, 0)
            // 获取item的高度和
            totalHeight += listItem.measuredHeight
            i += col
        }

        // 获取listview的布局参数
        val params = listView.layoutParams
        // 设置高度
        params.height = totalHeight
        // 设置margin
        (params as ViewGroup.MarginLayoutParams).setMargins(10, 10, 10, 10)
        // 设置参数
        listView.layoutParams = params
    }


    /**
     * dp转px
     */
    fun dip2px(context: Context, dpValue: Int): Int {
        val scale = context.resources.displayMetrics.density
        return (dpValue * scale + 0.5f).toInt()
    }

    /** px转换dip  */
    fun px2dip(context: Context,px: Int): Int {
        val scale =context.resources.displayMetrics.density
        return (px / scale + 0.5f).toInt()
    }


    fun getTotalHeightofListView(listView: ListView) {
        val mAdapter = listView.adapter ?: return
        var totalHeight = 0
        for (i in 0 until mAdapter.count) {
            val mView = mAdapter.getView(i, null, listView)
            mView.measure(
                    View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                    View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED))
            //mView.measure(0, 0);
            totalHeight += mView.measuredHeight
        }
        val params = listView.layoutParams
        params.height = totalHeight + listView.dividerHeight * (mAdapter.count - 1)
        listView.layoutParams = params
        listView.requestLayout()
    }


}
