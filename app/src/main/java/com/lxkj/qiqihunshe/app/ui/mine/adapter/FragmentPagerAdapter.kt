package com.lxkj.qiqihunshe.app.ui.mine.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager

/**
 * Created by Slingge on 2017/4/22 0022.
 */

class FragmentPagerAdapter : android.support.v4.app.FragmentPagerAdapter {
    private var fragmentLst: List<Fragment>? = null
    private var list_Title: List<String>? = null

    constructor(fm: FragmentManager, fragmentLst: List<Fragment>,list_Title: List<String>) : super(fm) {
        this.fragmentLst = fragmentLst
        this.list_Title=list_Title
    }

    constructor(fm: FragmentManager, fragmentLst: List<Fragment>) : super(fm) {
        this.fragmentLst = fragmentLst
    }

    override fun getItem(position: Int): Fragment {
        return fragmentLst!![position]
    }

    override fun getCount(): Int {
        return fragmentLst!!.size
    }


    override fun getPageTitle(position: Int): CharSequence? {
        return list_Title!![position]
    }

}
