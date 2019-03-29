package com.lxkj.qiqihunshe.app.ui.xiaoxi.activity

import android.content.Intent
import android.content.pm.PackageManager
import android.view.View
import com.bumptech.glide.Glide
import com.luck.picture.lib.PictureSelector
import com.lxkj.qiqihunshe.R
import com.lxkj.qiqihunshe.app.base.BaseActivity
import com.lxkj.qiqihunshe.app.ui.dialog.DynamicSignUpAfterDialog
import com.lxkj.qiqihunshe.app.ui.dialog.PermissionsDialog
import com.lxkj.qiqihunshe.app.ui.xiaoxi.viewmodel.UploadMarryViewModel
import com.lxkj.qiqihunshe.app.util.PermissionUtil
import com.lxkj.qiqihunshe.app.util.SelectPictureUtil
import com.lxkj.qiqihunshe.databinding.ActivityUploadMarryBinding
import kotlinx.android.synthetic.main.activity_upload_marry.*
import java.io.File

/**
 * Created by Slingge on 2019/3/1
 */
class UploadMarryActivity : BaseActivity<ActivityUploadMarryBinding, UploadMarryViewModel>(), View.OnClickListener {


    override fun getBaseViewModel() = UploadMarryViewModel()

    override fun getLayoutId() = R.layout.activity_upload_marry

    override fun init() {
        initTitle("上传结婚证")

        iv_upload.setOnClickListener(this)
        tv_enter.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.iv_upload -> {
                if (PermissionUtil.ApplyPermissionAlbum(this, 0)) {
                    SelectPictureUtil.selectPicture(this, 1, 0, false)
                }
            }
            R.id.tv_enter -> {
                DynamicSignUpAfterDialog.sginUpShow(this,"已提交管理员审核，请耐心等待")
            }
        }
    }


    /**
     * 申请权限结果回调
     */
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED && requestCode == 0) {//询问结果
            SelectPictureUtil.selectPicture(this, 10, 0, true)
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
                Glide.with(this).load(File(PictureSelector.obtainMultipleResult(data)[0].path)).into(iv_upload)
                iv_add.visibility=View.INVISIBLE
                tv_add.visibility=View.INVISIBLE
            }
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        DynamicSignUpAfterDialog.diss()
    }

}