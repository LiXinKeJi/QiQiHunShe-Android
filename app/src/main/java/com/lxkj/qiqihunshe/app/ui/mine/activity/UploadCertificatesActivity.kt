package com.lxkj.qiqihunshe.app.ui.mine.activity

import android.content.Intent
import android.content.pm.PackageManager
import android.text.TextUtils
import android.view.View
import com.bumptech.glide.Glide
import com.luck.picture.lib.PictureSelector
import com.lxkj.qiqihunshe.R
import com.lxkj.qiqihunshe.app.base.BaseActivity
import com.lxkj.qiqihunshe.app.ui.dialog.PermissionsDialog
import com.lxkj.qiqihunshe.app.ui.mine.viewmodel.UploadCertificatesViewModel
import com.lxkj.qiqihunshe.app.util.*
import com.lxkj.qiqihunshe.databinding.ActivityUploadCertificatesBinding
import kotlinx.android.synthetic.main.activity_upload_certificates.*
import kotlinx.android.synthetic.main.include_title.*
import java.io.File

/**
 * Created by Slingge on 2019/3/2
 */
class UploadCertificatesActivity :
    BaseActivity<ActivityUploadCertificatesBinding, UploadCertificatesViewModel>(), View.OnClickListener {


    private var flag = -1//0学历，1房产，2车，3薪资，4身份证

    override fun getBaseViewModel() = UploadCertificatesViewModel()

    override fun getLayoutId() = R.layout.activity_upload_certificates

    override fun init() {

        flag = intent.getIntExtra("flag", -1)

        when (flag) {
            0 -> {
                initTitle("上传学历证")
                tv_is.text = "学历"
                tv_tip.visibility = View.VISIBLE
            }
            1 -> {
                initTitle("上传房产证")
                tv_is.text = "是否有房："
            }
            2 -> {
                initTitle("上传车产证")
                tv_is.text = "是否有车："
            }
            3 -> {
                initTitle("上传工作证")
                tv_is.text = "薪资范围："
            }
            4 -> {
                initTitle("上传身份证")
                tv_is.text = "身份证号："
                et_id.visibility = View.VISIBLE
                iv_upload1.visibility = View.VISIBLE
                iv_upload1.setOnClickListener(this)
                sp_province.visibility = View.GONE
            }
        }

        tv_right.visibility = View.VISIBLE
        tv_right.text = "保存"
        tv_right.setOnClickListener(this)
        iv_upload.setOnClickListener(this)

        viewModel?.let {
            binding.viewmodel=it
            it.flag = flag
            it.bind = binding
            it.getLable()
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.tv_right -> {

                if (flag == 4) {
                    viewModel?.notiId()
                    if (TextUtils.isEmpty(viewModel?.idNum)) {
                        ToastUtil.showTopSnackBar(this, "请输入身份证号码")
                        return
                    }
                    if (TextUtils.isEmpty(viewModel!!.url1) || TextUtils.isEmpty(viewModel!!.url1)) {
                        ToastUtil.showTopSnackBar(this, "请选择身份证照")
                        return
                    }
                } else {
                    if (TextUtils.isEmpty(viewModel!!.select)) {
                        ToastUtil.showTopSnackBar(this, "请选择${AbStrUtil.tvTostr(tv_is)}")
                        return
                    }
                    if (TextUtils.isEmpty(viewModel!!.url1)) {
                        ToastUtil.showTopSnackBar(this, "请选择证件照")
                        return
                    }
                }

                viewModel?.back()
            }
            R.id.iv_upload -> {
                if (PermissionUtil.ApplyPermissionAlbum(this, 0)) {
                    SelectPictureUtil.selectPicture(this, 1, 0, false)
                }
            }
            R.id.iv_upload1 -> {
                if (PermissionUtil.ApplyPermissionAlbum(this, 1)) {
                    SelectPictureUtil.selectPicture(this, 1, 1, false)
                }
            }
        }
    }


    /**
     * 申请权限结果回调
     */
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {//询问结果
            SelectPictureUtil.selectPicture(this, 1, requestCode, false)
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
                viewModel?.upFile(PictureSelector.obtainMultipleResult(data)[0].path, 1)
            }
        } else if (requestCode == 1) {
            if (PictureSelector.obtainMultipleResult(data).isNotEmpty()) {
                Glide.with(this).load(File(PictureSelector.obtainMultipleResult(data)[0].path)).into(iv_upload1)
                viewModel?.upFile(PictureSelector.obtainMultipleResult(data)[0].path, 2)
            }
        }
    }


}