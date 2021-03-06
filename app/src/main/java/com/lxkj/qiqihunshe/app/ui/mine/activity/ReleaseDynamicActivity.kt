package com.lxkj.qiqihunshe.app.ui.mine.activity

import android.content.Intent
import android.content.pm.PackageManager
import android.text.TextUtils
import android.view.View
import com.baidu.mapapi.search.core.PoiInfo
import com.luck.picture.lib.PictureSelector
import com.lxkj.qiqihunshe.R
import com.lxkj.qiqihunshe.app.MyApplication
import com.lxkj.qiqihunshe.app.base.BaseActivity
import com.lxkj.qiqihunshe.app.interf.UpLoadFileCallBack
import com.lxkj.qiqihunshe.app.retrofitnet.UpFileUtil
import com.lxkj.qiqihunshe.app.retrofitnet.bindLifeCycle
import com.lxkj.qiqihunshe.app.ui.dialog.PermissionsDialog
import com.lxkj.qiqihunshe.app.ui.map.activity.ChooseAddressActivity
import com.lxkj.qiqihunshe.app.ui.mine.viewmodel.ReleaseDynamicViewModel
import com.lxkj.qiqihunshe.app.util.ProgressDialogUtil
import com.lxkj.qiqihunshe.app.util.SelectPictureUtil
import com.lxkj.qiqihunshe.app.util.ToastUtil
import com.lxkj.qiqihunshe.databinding.ActivityReleaseDynamicBinding
import kotlinx.android.synthetic.main.activity_release_dynamic.*

/**
 * Created by Slingge on 2019/2/25
 */
class ReleaseDynamicActivity : BaseActivity<ActivityReleaseDynamicBinding, ReleaseDynamicViewModel>(),
    View.OnClickListener, UpLoadFileCallBack {

    private var flag = -1//0普通动态，1情感动态

    override fun getBaseViewModel() = ReleaseDynamicViewModel()

    override fun getLayoutId() = R.layout.activity_release_dynamic

    private val upload by lazy { UpFileUtil(this, this) }

    override fun init() {
        initTitle("发布动态")
        flag = intent.getIntExtra("flag", -1)
        viewModel?.let {
            binding.viewmodel = it
            it.model.type=flag.toString()
            binding.model = it.model
            it.bind = binding
            it.initViewModel()
        }

        tv_address.setOnClickListener(this)
        tv_send.setOnClickListener(this)
    }


    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.tv_address -> {
                MyApplication.openActivityForResult(this, ChooseAddressActivity::class.java, 1)
            }
            R.id.tv_send -> {
                viewModel?.let {
                    it.model.notiCinten()
                    if (TextUtils.isEmpty(it.model.content)) {
                        ToastUtil.showTopSnackBar(this, "请输入动态内容")
                        return
                    }

                    if (it.ablumList.size>1) {
                        val fileList = ArrayList<String>()
                        for (i in 0 until it.ablumList.size - 1) {//移除最后一个添加占位的图片
                            fileList.add(it.ablumList[i].path)
                        }
                        ProgressDialogUtil.showProgressDialog(this)
                        upload.setListPath(fileList)
                    } else {
                        viewModel!!.fendDynamic().bindLifeCycle(this).subscribe({}, { toastFailure(it) })
                    }
                }
            }
        }
    }


    override fun uoLoad(url: List<String>) {
        val sb = StringBuffer()
        for (i in 0 until url.size) {
            sb.append(url[i] + "|")
        }
        viewModel!!.model.images = sb.toString().substring(0, sb.toString().length - 1)
        viewModel!!.fendDynamic().bindLifeCycle(this).subscribe({}, { toastFailure(it) })
    }

    override fun uoLoad(url: String) {

    }


    /**
     * 申请权限结果回调
     */
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED && requestCode == 0) {//询问结果
            SelectPictureUtil.selectPicture(this, 9, 0, false)
        } else {//禁止使用权限，询问是否设置允许
            PermissionsDialog.dialog(this, "需要访问内存卡和拍照权限")
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data == null) {
            return
        }
        if (requestCode == 0) {
            if (PictureSelector.obtainMultipleResult(data).isNotEmpty()) {
                viewModel?.setImage(PictureSelector.obtainMultipleResult(data))
            }
        }

        if (requestCode == 1) {
            var poi = data.getParcelableExtra("poi") as PoiInfo
            if (null != poi) {


                binding.model?.lat = poi.location.latitude.toString()
                binding.model?.lon = poi.location.longitude.toString()
                binding.model?.location = poi.name
                tv_address.text = poi.name
            }
        }
    }


}