package com.lxkj.qiqihunshe.app.rongrun

import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.support.v4.app.Fragment
import io.rong.imkit.R
import io.rong.imkit.RongExtension
import io.rong.imkit.plugin.IPluginModule
import com.lxkj.qiqihunshe.app.MyApplication
import com.lxkj.qiqihunshe.app.ui.map.activity.ChooseAddressActivity


/**
 * Created by Slingge on 2019/3/30
 */
class LocationPlugin : IPluginModule {

    override fun onClick(p0: Fragment?, p1: RongExtension?) {
        MyApplication.openActivityForResult(p0!!.activity, ChooseAddressActivity::class.java, 403)
    }

    override fun obtainDrawable(p0: Context?): Drawable {
        return p0!!.resources.getDrawable(R.drawable.rc_ext_plugin_location_selector)
    }

    override fun obtainTitle(p0: Context?): String {
        return "位置"
    }

    override fun onActivityResult(p0: Int, p1: Int, p2: Intent?) {

    }


}
