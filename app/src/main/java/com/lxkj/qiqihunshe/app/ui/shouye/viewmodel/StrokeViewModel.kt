package com.lxkj.qiqihunshe.app.ui.shouye.viewmodel

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.linyuzai.likeornot.BaseAdapter
import com.linyuzai.likeornot.LikeOrNotView
import com.lxkj.qiqihunshe.R
import com.lxkj.qiqihunshe.app.base.BaseViewModel
import com.lxkj.qiqihunshe.app.util.ToastUtil
import com.lxkj.qiqihunshe.databinding.ActivityStrokeBinding

/**
 * Created by Slingge on 2019/2/26
 */
class StrokeViewModel : BaseViewModel() {


    var bind: ActivityStrokeBinding? = null


    fun initViewModel() {

        bind!!.likeOrNot.adapter = object : BaseAdapter() {

            override fun getCount() = 10

            override fun getView(convertView: View?, parent: ViewGroup, position: Int): View {
                var convertView = convertView
                if (convertView == null)
                    convertView = LayoutInflater.from(parent.context).inflate(R.layout.item_like_or_not, parent, false)


                return convertView!!
            }
        }

    }


}