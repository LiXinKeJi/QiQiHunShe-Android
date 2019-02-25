package com.lxkj.qiqihunshe.app.ui.mine.viewmodel

import android.support.v7.widget.GridLayoutManager
import com.luck.picture.lib.entity.LocalMedia
import com.lxkj.qiqihunshe.app.base.BaseViewModel
import com.lxkj.qiqihunshe.app.ui.mine.adapter.ReleaseAdapter
import com.lxkj.qiqihunshe.databinding.ActivityReleaseInvitationBinding

/**
 *  发布邀约
 * Created by Slingge on 2019/2/25
 */
class ReleaseInvitationViewModel : BaseViewModel(), ReleaseAdapter.ImageRemoveCallback {


    var bind: ActivityReleaseInvitationBinding? = null

    val ablumList by lazy { ArrayList<LocalMedia>() }
    var imageAdapter: ReleaseAdapter? = null


    fun initViewModel() {
        ablumList.add(LocalMedia())
        imageAdapter = ReleaseAdapter(activity!!, ablumList, 9, this)
        imageAdapter?.setFlag(1)
        bind!!.rvAlbum.layoutManager = GridLayoutManager(activity, 3)
        bind!!.rvAlbum.adapter = imageAdapter

    }

    fun setImage(images: List<LocalMedia>) {
        for (i in 0 until images.size) {
            ablumList.add(ablumList.size - 1, images[i])
        }
        imageAdapter!!.notifyDataSetChanged()
    }

    override fun imageRemove(i: Int) {
        ablumList.removeAt(i)
        imageAdapter!!.notifyDataSetChanged()

    }

}