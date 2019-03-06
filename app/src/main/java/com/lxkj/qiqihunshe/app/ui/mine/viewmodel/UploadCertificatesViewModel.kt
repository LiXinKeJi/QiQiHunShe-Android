package com.lxkj.qiqihunshe.app.ui.mine.viewmodel

import android.content.Intent
import android.databinding.Bindable
import android.databinding.ObservableField
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.lxkj.qiqihunshe.BR
import com.lxkj.qiqihunshe.R
import com.lxkj.qiqihunshe.app.base.BaseViewModel
import com.lxkj.qiqihunshe.app.interf.UpLoadFileCallBack
import com.lxkj.qiqihunshe.app.retrofitnet.GetTagUtil
import com.lxkj.qiqihunshe.app.retrofitnet.UpFileUtil
import com.lxkj.qiqihunshe.app.ui.mine.activity.UploadCertificatesActivity
import com.lxkj.qiqihunshe.app.util.ToastUtil
import com.lxkj.qiqihunshe.databinding.ActivityUploadCertificatesBinding
import kotlinx.android.synthetic.main.activity_upload_certificates.*

/**
 * 上传证件
 * Created by Slingge on 2019/3/2
 */
class UploadCertificatesViewModel : BaseViewModel(), AdapterView.OnItemSelectedListener, UpLoadFileCallBack {

    var flag = -1//0学历，1房产，2车，3薪资，4身份证

    private var tag = -1

    @Bindable
    var idNum = ""

    var url1 = ""
    var url2 = ""

    private var type = 0//1 image1,2 image2

    private val upload by lazy { UpFileUtil(activity!!, this) }

    var bind: ActivityUploadCertificatesBinding? = null

    var select = ""

    private val lable by lazy { ArrayList<String>() }


    fun getLable() {
        when (flag) {
            0 -> tag = 8
            1 -> tag = 7
            2 -> tag = 6
            3 -> tag = 5
        }

        if (flag != 4) {
            GetTagUtil(activity!!, object : GetTagUtil.TagListCallback {
                override fun TagList(tagList: ArrayList<String>) {
                    lable.addAll(tagList)
                    val adapter = ArrayAdapter(activity, R.layout.item_spinner_text, lable)
                    bind!!.spProvince.adapter = adapter

                    bind!!.spProvince.onItemSelectedListener = this@UploadCertificatesViewModel
                }
            }).getTag("0", tag.toString())
        }
    }

    fun notiId() {
        notifyPropertyChanged(BR.idNum)
    }

    //flag 1 iamge1,2 image2
    fun upFile(path: String, type: Int) {
        this.type = type
        upload.upLoad(path)
    }


    override fun uoLoad(url: List<String>) {

    }

    override fun uoLoad(url: String) {
        if (type == 1) {
            url1 = url
        } else {
            url2 = url
        }
    }


    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        select = lable[position]
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {

    }

    fun back() {
        val intent = Intent()
        if (flag == 4) {
            intent.putExtra("lable", idNum)
        } else {
            intent.putExtra("lable", select)
        }
        intent.putExtra("url", url1)
        intent.putExtra("url2", url2)
        activity!!.setResult(0, intent)
        activity!!.finish()
    }


}