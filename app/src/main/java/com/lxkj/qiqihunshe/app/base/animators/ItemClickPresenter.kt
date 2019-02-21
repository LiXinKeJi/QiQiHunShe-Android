package com.lxkj.qiqihunshe.app.base.animators

import android.view.View


/**
 * 页面描述：
 *
 * Created by ditclear on 2017/9/28.
 */
/*interface  ItemClickPresenter<in Any> {
    fun onItemClick(v: View?=null, item:Any)
}

interface ItemDecorator<T>{
    fun decorator(holder:T?, position: Int, viewType: Int)
}*/

interface ItemAnimator{

    fun scrollUpAnim(v:View)

    fun scrollDownAnim(v: View)
}