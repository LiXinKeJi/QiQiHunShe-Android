package com.lxkj.qiqihunshe.app.retrofitnet


/**
 * Created by Slingge on 2019/1/9
 */
interface SingleObserverInterface<T> {

    fun  onSuccess(response: T)
}