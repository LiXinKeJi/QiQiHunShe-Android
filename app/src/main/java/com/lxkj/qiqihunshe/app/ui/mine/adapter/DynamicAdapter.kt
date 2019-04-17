package com.lxkj.qiqihunshe.app.ui.mine.adapter

import android.app.Activity
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.DecelerateInterpolator
import android.widget.ImageView
import android.widget.TextView
import com.lxkj.qiqihunshe.R
import com.lxkj.qiqihunshe.app.MyApplication
import com.lxkj.qiqihunshe.app.base.animators.ItemAnimator
import com.lxkj.qiqihunshe.app.base.animators.ScaleInItemAnimator
import com.lxkj.qiqihunshe.app.customview.CircleImageView
import com.lxkj.qiqihunshe.app.ui.dialog.DaShangDialog
import com.lxkj.qiqihunshe.app.ui.mine.activity.PersonalInfoActivity
import com.lxkj.qiqihunshe.app.ui.mine.model.SpaceDynamicModel
import com.lxkj.qiqihunshe.app.ui.mine.widget.LoadMoreView
import com.lxkj.qiqihunshe.app.ui.model.EventCmdModel
import com.lxkj.qiqihunshe.app.util.*
import org.greenrobot.eventbus.EventBus

/**
 * Created by Slingge on 2019/2/21
 */
class DynamicAdapter(val context: Activity, val list: ArrayList<SpaceDynamicModel.dataModel>) :
    RecyclerView.Adapter<DynamicAdapter.ViewHolder>() {


    //加载动画
    private var itemAnimator: ItemAnimator? = ScaleInItemAnimator(interpolator = DecelerateInterpolator())
    var showItemAnimator = true
    var isFirstOnly = false
    var mLastPosition = -1

    var flag = -1//0，最后一页加载完成，1不支持加载更多，隐藏加载（包括加载完成）,2刷新重新显示加载中


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        if (viewType == 0) {
            val view = LayoutInflater.from(context).inflate(R.layout.item_person_dynamic, parent, false)
            return ViewHolder(view)
        } else {
            return ViewHolder(LoadMoreView(parent.context))
        }
    }

    override fun getItemCount(): Int {
        return list.size + 1
    }

    override fun getItemViewType(position: Int): Int {
        if (position == list.size) {//最后一条
            return 1
        } else {
            return 0
        }
    }

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {

        if (p1 == list.size) {
            val view = p0.itemView as LoadMoreView
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
            return
        }

        val bean = list[p1]

        p0.tv_name.text = bean.nickname

        GlideUtil.glideHeaderLoad(context, bean.icon, p0.header)

        if (StaticUtil.uid == bean.userId) {
            p0.tv_reward.visibility = View.GONE
            p0.tv_report.visibility = View.GONE
        } else {
            p0.tv_reward.visibility = View.VISIBLE
            p0.tv_report.visibility = View.VISIBLE
        }

        p0.tv_occupation.text = "职业：" + bean.job
        p0.tv_address.text = "地址：${bean.location}"


        p0.tv_zan.text = bean.zanNum
        if (bean.zan == "0") {
            AbStrUtil.setDrawableLeft(context, R.drawable.ic_zan_nor, p0.tv_zan, 5)
        } else {
            AbStrUtil.setDrawableLeft(context, R.drawable.ic_zan_hl, p0.tv_zan, 5)
        }
        p0.tv_age.text = bean.age
        if (bean.sex == "0") {//0女 1男
            p0.tv_age.setBackgroundResource(R.drawable.bg_girl)
            p0.tv_age.setTextColor(context.resources.getColor(R.color.girl))
            AbStrUtil.setDrawableLeft(context, R.drawable.ic_girl, p0.tv_age, 3)
        } else {
            p0.tv_age.setBackgroundResource(R.drawable.thems_bg35)
            p0.tv_age.setTextColor(context.resources.getColor(R.color.colorThemes))
            AbStrUtil.setDrawableLeft(context, R.drawable.ic_boy, p0.tv_age, 3)
        }

        if (bean.identity == "1") {//1单身 2约会 3牵手
            p0.iv_state.setImageResource(R.drawable.danshen)
        } else if (bean.identity == "2") {
            p0.iv_state.setImageResource(R.drawable.yuehui)
        } else {
            p0.iv_state.setImageResource(R.drawable.qianshou)
        }

        for (i in 0 until bean.permission.size) {
            when (bean.permission[i]) {
                "1" -> p0.iv_v1.visibility = View.VISIBLE
                "2" -> p0.iv_v2.visibility = View.VISIBLE
                "3" -> p0.iv_v3.visibility = View.VISIBLE
                "4" -> p0.iv_v4.visibility = View.VISIBLE
                "5" -> p0.iv_v5.visibility = View.VISIBLE
            }
        }


        p0.tv_time.text = bean.adtime

        p0.tv_content.text = bean.content

        p0.tv_num.text = bean.commentNum

        when (bean.images.size) {
            0 -> {
                p0.iv_1.visibility = View.GONE
                p0.iv_2.visibility = View.GONE
                p0.iv_3.visibility = View.GONE
            }
            1 -> {
                p0.iv_1.visibility = View.VISIBLE
                p0.iv_2.visibility = View.INVISIBLE
                p0.iv_3.visibility = View.INVISIBLE
                GlideUtil.glideLoad(context, bean.images[0], p0.iv_1)
            }
            2 -> {
                p0.iv_1.visibility = View.VISIBLE
                p0.iv_2.visibility = View.VISIBLE
                p0.iv_3.visibility = View.INVISIBLE
                GlideUtil.glideLoad(context, bean.images[0], p0.iv_1)
                GlideUtil.glideLoad(context, bean.images[1], p0.iv_2)
            }
            3 -> {
                p0.iv_1.visibility = View.VISIBLE
                p0.iv_2.visibility = View.VISIBLE
                p0.iv_3.visibility = View.VISIBLE
                GlideUtil.glideLoad(context, bean.images[0], p0.iv_1)
                GlideUtil.glideLoad(context, bean.images[1], p0.iv_2)
                GlideUtil.glideLoad(context, bean.images[2], p0.iv_3)
            }
            else -> {
                p0.iv_1.visibility = View.VISIBLE
                p0.iv_2.visibility = View.VISIBLE
                p0.iv_3.visibility = View.VISIBLE
                GlideUtil.glideLoad(context, bean.images[1], p0.iv_1)
                GlideUtil.glideLoad(context, bean.images[2], p0.iv_2)
                GlideUtil.glideLoad(context, bean.images[3], p0.iv_3)
            }
        }

        p0.header.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("userId", bean.userId)
            MyApplication.openActivity(context, PersonalInfoActivity::class.java, bundle)
        }

        p0.tv_zan.setOnClickListener {
            EventBus.getDefault().post(EventCmdModel(EventBusCmd.DianZan, (p1).toString()))
            zanlistener?.let {
                it(p1, p0.tv_zan)
            }
        }
        p0.tv_report.setOnClickListener {
            EventBus.getDefault().post(EventCmdModel(EventBusCmd.JuBao, (p1).toString()))
        }
        p0.tv_reward.setOnClickListener {
            EventBus.getDefault().post(EventCmdModel(EventBusCmd.dashang, (p1).toString()))
        }

        p0.tv_share.setOnClickListener {
            EventBus.getDefault().post(EventCmdModel(EventBusCmd.fenxaing, (p1).toString()))
        }


        p0.iv_1.setOnClickListener {
            SeePhotoViewUtil.toPhotoView(context, bean.images, 0)
        }
        p0.iv_2.setOnClickListener {
            SeePhotoViewUtil.toPhotoView(context, bean.images, 1)
        }
        p0.iv_3.setOnClickListener {
            SeePhotoViewUtil.toPhotoView(context, bean.images, 2)
        }


        p0.itemView.setOnClickListener { v ->
            if (list.size == 0) {
                return@setOnClickListener
            }
            listener?.let {
                it(list[p1], p1)
            }
        }



        abLog.e("count", p1.toString() + "," + itemCount)
        //到达底部加载更多
        if (p1 == itemCount - 2) {//已经到达列表的底部
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
            val adapterPosition = p0.adapterPosition
            if (!isFirstOnly || adapterPosition > mLastPosition) {
                if (adapterPosition >= mLastPosition) {
                    it.scrollDownAnim(p0.itemView)
                } else {
//                    it.scrollUpAnim(holder.itemView)
                }
                mLastPosition = adapterPosition
            } else {
                clear(p0.itemView)
            }
        }


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


    fun getAdapterList(): ArrayList<SpaceDynamicModel.dataModel> {
        return list
    }

    fun removeItem(position: Int) {
        list.removeAt(position)
        notifyItemRemoved(position)
    }


    //item点击事件
    var listener: ((itemBean: SpaceDynamicModel.dataModel, position: Int) -> Unit)? = null

    fun setMyListener(listener: ((itemBean: SpaceDynamicModel.dataModel, position: Int) -> Unit)) {
        this.listener = listener
    }

    //item点击事件
    var zanlistener: ((position: Int, tv: TextView) -> Unit)? = null

    fun setZanListener(listener: ((position: Int, tv: TextView) -> Unit)) {
        this.zanlistener = listener
    }

    //上拉加载事件方法
    var LoadListener: (() -> Unit)? = null

    fun setLoadMore(LoadListener: () -> Unit) {
        this.LoadListener = LoadListener
    }


    fun upZan(num: String, tv_zan: TextView, flag: Int) {//1加，0减
        if (flag == 1) {
            AbStrUtil.setDrawableLeft(context, R.drawable.ic_zan_hl, tv_zan, 5)
        } else {
            AbStrUtil.setDrawableLeft(context, R.drawable.ic_zan_nor, tv_zan, 5)
        }
        tv_zan.text = num
    }


    /**
     * 刷新条目
     * */
    fun refreshItemView(view: SpaceDynamicModel.dataModel, itembean: SpaceDynamicModel.dataModel, position: Int) {}


    fun upData(lists: ArrayList<SpaceDynamicModel.dataModel>) {
        this.list.clear()
        this.list.addAll(lists)
        notifyItemRangeChanged(0, this.list.size)
    }

    fun loadMore(list: ArrayList<SpaceDynamicModel.dataModel>, flag: Int) {
        val size = this.list.size
        this.list.addAll(list)
        this.flag = flag
        notifyItemRangeChanged(size, this.list.size)
    }


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tv_reward = view.findViewById<TextView>(R.id.tv_reward)
        val tv_occupation = view.findViewById<TextView>(R.id.tv_occupation)
        val tv_address = view.findViewById<TextView>(R.id.tv_address)
        val tv_zan = view.findViewById<TextView>(R.id.tv_zan)
        val tv_age = view.findViewById<TextView>(R.id.tv_age)
        val iv_state = view.findViewById<ImageView>(R.id.iv_state)
        val tv_time = view.findViewById<TextView>(R.id.tv_time)

        val tv_content = view.findViewById<TextView>(R.id.tv_content)
        val iv_1 = view.findViewById<ImageView>(R.id.iv_1)
        val iv_2 = view.findViewById<ImageView>(R.id.iv_2)
        val iv_3 = view.findViewById<ImageView>(R.id.iv_3)
        val tv_report = view.findViewById<ImageView>(R.id.tv_report)
        val tv_share = view.findViewById<TextView>(R.id.tv_share)

        val iv_v1 = view.findViewById<ImageView>(R.id.iv_v1)
        val iv_v2 = view.findViewById<ImageView>(R.id.iv_v2)
        val iv_v3 = view.findViewById<ImageView>(R.id.iv_v3)
        val iv_v4 = view.findViewById<ImageView>(R.id.iv_v4)
        val iv_v5 = view.findViewById<ImageView>(R.id.iv_v5)

        val tv_num = view.findViewById<TextView>(R.id.tv_num)

        val header = view.findViewById<CircleImageView>(R.id.header)

        val tv_name = view.findViewById<TextView>(R.id.tv_name)

    }


}