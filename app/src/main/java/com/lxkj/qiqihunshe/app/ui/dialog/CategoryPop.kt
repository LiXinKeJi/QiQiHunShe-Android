package com.lxkj.qiqihunshe.app.ui.dialog

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.PopupWindow
import android.widget.TextView
import com.lxkj.qiqihunshe.R
import com.lxkj.qiqihunshe.app.util.RecyclerItemTouchListener

/**
 * 预约时间
 * Created by Slingge on 2017/5/3 0003.
 */

class CategoryPop(val context: Context) {
    private var pop: PopupWindow? = null


    interface Categoryinterface {
        fun category(position: Int)
    }

    var categoryinterface: Categoryinterface? = null

    fun setCatinterface(categoryinterface: Categoryinterface) {
        this.categoryinterface = categoryinterface
    }


    @SuppressLint("ClickableViewAccessibility")
    fun screenPopwindow(list: List<String>, rlTopBar: View) {
        val view = LayoutInflater.from(context).inflate(
            R.layout.popup_category, null
        )

        val rv_screen = view.findViewById<View>(R.id.recyclerView) as RecyclerView
        rv_screen.layoutManager = LinearLayoutManager(context)
        //            rv_screen.addItemDecoration(new DividerItemDecoration(context, DividerItemDecoration.VERTICAL_LIST));
        val adapter = ScreenPopAdapter(list)
        rv_screen.adapter = adapter
        rv_screen.addOnItemTouchListener(object : RecyclerItemTouchListener(rv_screen) {
            override fun onItemClick(vh: RecyclerView.ViewHolder) {
                val position = vh.adapterPosition
                categoryinterface!!.category(position)
                pop!!.dismiss()
            }
        })

        // 创建弹出窗口
        // 窗口内容为layoutLeft，里面包含一个ListView
        // 窗口宽度跟tvLeft一样
        pop = PopupWindow(
            view, rlTopBar.width,
            FrameLayout.LayoutParams.WRAP_CONTENT
        )

        val cd = ColorDrawable(0)
        pop!!.setBackgroundDrawable(cd)
        pop!!.animationStyle = R.style.PopupAnimation
        pop!!.update()
        pop!!.inputMethodMode = PopupWindow.INPUT_METHOD_NEEDED
        pop!!.isTouchable = true // 设置popupwindow可点击
        pop!!.isOutsideTouchable = true // 设置popupwindow外部可点击
        pop!!.isFocusable = true // 获取焦点

        // 设置popupwindow的位置（相对tvLeft的位置）
        pop!!.showAsDropDown(rlTopBar, 0, 0)

        pop!!.setTouchInterceptor(View.OnTouchListener { v, event ->
            // 如果点击了popupwindow的外部，popupwindow也会消失
            if (event.action == MotionEvent.ACTION_OUTSIDE) {
                pop!!.dismiss()
                return@OnTouchListener true
            }
            false
        })
    }


    private inner class ScreenPopAdapter(private val list: List<String>) :
        RecyclerView.Adapter<CategoryPop.MyViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
            val view = LayoutInflater.from(context).inflate(R.layout.item_swipelayout, parent, false)
            return MyViewHolder(view)
        }

        override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
            holder.text.text = list[position]
//            holder.text.setBackgroundColor(context.resources.getColor(R.color.colorWhite))
//            holder.text.setTextColor(context.resources.getColor(R.color.colorTabText))
        }

        override fun getItemCount(): Int {
            return list.size
        }
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var text: TextView = itemView.findViewById(R.id.text)
    }


}
