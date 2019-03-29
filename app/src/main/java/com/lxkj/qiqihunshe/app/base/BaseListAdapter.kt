package com.lxkj.qiqihunshe.app.base

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.view.animation.DecelerateInterpolator
import com.lxkj.qiqihunshe.app.base.animators.ItemAnimator
import com.lxkj.qiqihunshe.app.base.animators.ScaleInItemAnimator
import com.lxkj.qiqihunshe.app.ui.mine.widget.LoadMoreView

/**
 * Creater Slingge by 2018/12/26 0026
 */
abstract class BaseListAdapter<ITEMBEAN, ITEMVIEW : View> : RecyclerView.Adapter<BaseListAdapter.ViewHolder>() {

    private var list = ArrayList<ITEMBEAN>()

    abstract fun getitemView(context: Context): ITEMVIEW

    //加载动画
    private var itemAnimator: ItemAnimator? = ScaleInItemAnimator(interpolator = DecelerateInterpolator())
    var showItemAnimator = true
    var isFirstOnly = false
    var mLastPosition = -1


    fun getList(): ArrayList<ITEMBEAN> {
        return list
    }

    fun removeItem(position: Int) {
        list.removeAt(position)
        notifyItemRemoved(position)
    }

    /**
     * 刷新条目
     * */
    abstract fun refreshItemView(view: ITEMVIEW, itembean: ITEMBEAN)

    abstract fun refreshItemView(view: ITEMVIEW, itembean: ITEMBEAN, position: Int)

    var flag = -1//0，最后一页加载完成，1不支持加载更多，隐藏加载（包括加载完成）,2刷新重新显示加载中


    fun upData(list: ArrayList<ITEMBEAN>) {
        this.list.clear()
        this.list.addAll(list)
        notifyItemRangeChanged(0, this.list.size)
    }

    fun loadMore(list: ArrayList<ITEMBEAN>, flag: Int) {
        val size = this.list.size
        this.list.addAll(list)
        this.flag = flag
        notifyItemRangeChanged(size, this.list.size)
    }

    override fun getItemViewType(position: Int): Int {
        if (position == list.size) {//最后一条
            return 1
        } else {
            return 0
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        if (viewType == 0) {
            return ViewHolder(getitemView(parent.context))
        } else {
            return ViewHolder(LoadMoreView(parent.context))
        }
    }

    override fun getItemCount(): Int {
        return list.size + 1
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (position == list.size) {
            val view = holder.itemView as LoadMoreView
            if (list.size == 0) {
                view.Done()
                return
            }
            when (flag) {
                0 -> {
                    view.Loaded()
                }
                1 -> {
                    view.Done()
                }
                2 -> {
                    view.loading()
                }
            }
        } else {
            val view = holder.itemView as ITEMVIEW
            refreshItemView(view, list[position], position)
            refreshItemView(view, list[position])
        }

        holder.itemView.setOnClickListener { v ->
            if (list.size == 0) {
                return@setOnClickListener
            }
            listener?.let {
                it(list[position], position)
            }
        }

        //到达底部加载更多
        if (position == itemCount - 1) {//已经到达列表的底部
            LoadListener?.let {
                if (list.size == 0) {
                    return
                }
                it()
            }
        }

        //加载动画
        itemAnimator?.let {
            if (!showItemAnimator) {
                return@let
            }
            val adapterPosition = holder.adapterPosition
            if (!isFirstOnly || adapterPosition > mLastPosition) {
                if (adapterPosition >= mLastPosition) {
                    it.scrollDownAnim(holder.itemView)
                } else {
//                    it.scrollUpAnim(holder.itemView)
                }
                mLastPosition = adapterPosition
            } else {
                clear(holder.itemView)
            }
        }
    }


    //item点击事件
    var listener: ((itemBean: ITEMBEAN, position: Int) -> Unit)? = null

    fun setMyListener(listener: ((itemBean: ITEMBEAN, position: Int) -> Unit)) {
        this.listener = listener
    }

    //上拉加载事件方法
    var LoadListener: (() -> Unit)? = null

    fun setLoadMore(LoadListener: () -> Unit) {
        this.LoadListener = LoadListener
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    }

    private fun clear(v: View) {
        v.alpha = 1f
        v.scaleY = 1f
        v.scaleX = 1f
        v.translationY = 0f
        v.translationX = 0f
        v.rotation = 0f
        v.rotationX = 0f
        v.rotationY = 0f
        v.pivotX = v.measuredWidth.toFloat() / 2
        v.pivotY = v.measuredHeight.toFloat() / 2
        v.animate().setInterpolator(null).startDelay = 0
    }

    fun addItem(model: ITEMBEAN) {
        list.add(0, model)
        notifyItemInserted(0)
    }


}