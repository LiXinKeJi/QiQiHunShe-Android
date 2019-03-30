package com.lxkj.qiqihunshe.app.ui.mine.adapter

import android.app.Activity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.lxkj.qiqihunshe.R
import com.lxkj.qiqihunshe.app.ui.mine.model.SpaceSkillModel
import com.lxkj.qiqihunshe.app.util.GlideUtil
import com.lxkj.runproject.app.view.SquareImage

/**
 * Created by Slingge on 2019/2/21
 */
class PersonSkillAdapter(val context: Activity, val list: ArrayList<SpaceSkillModel.dataModel>) :
    RecyclerView.Adapter<PersonSkillAdapter.ViewHolder>() {


    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_person_skill, p0, false))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        val model = list[p1]
        GlideUtil.glideLoad(context, model.image, p0.iv_image)

        p0.tv_name.text = model.title
        p0.tv_num.text = "播放量：${model.count}"



        p0.itemView.setOnClickListener { v ->
            if (list.size == 0) {
                return@setOnClickListener
            }
            listener?.let {
                it(list[p1], p1)
            }
        }

        //到达底部加载更多
        if (p1 == itemCount - 1) {//已经到达列表的底部
            LoadListener?.let {
                if (list.size == 0) {
                    return
                }
                it()
            }
        }
    }


    fun removeItem(position: Int) {
        notifyItemRemoved(position)
    }


    //item点击事件
    var listener: ((itemBean: SpaceSkillModel.dataModel, position: Int) -> Unit)? = null

    fun setMyListener(listener: ((itemBean: SpaceSkillModel.dataModel, position: Int) -> Unit)) {
        this.listener = listener
    }

    //上拉加载事件方法
    var LoadListener: (() -> Unit)? = null

    fun setLoadMore(LoadListener: () -> Unit) {
        this.LoadListener = LoadListener
    }


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val iv_image = view.findViewById<SquareImage>(R.id.iv_image)

        val tv_name = view.findViewById<TextView>(R.id.tv_name)
        val tv_num = view.findViewById<TextView>(R.id.tv_num)
    }

}