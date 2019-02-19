package com.lxkj.qiqihunshe.app.ui.entrance

import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Paint
import android.view.View
import com.bumptech.glide.Glide
import com.luck.picture.lib.PictureSelector
import com.lxkj.qiqihunshe.R
import com.lxkj.qiqihunshe.app.MyApplication
import com.lxkj.qiqihunshe.app.base.BaseActivity
import com.lxkj.qiqihunshe.app.ui.dialog.PermissionsDialog
import com.lxkj.qiqihunshe.app.ui.entrance.model.PerfectInfoModel
import com.lxkj.qiqihunshe.app.ui.entrance.viewmodel.PerfectInfoViewModel
import com.lxkj.qiqihunshe.app.util.PermissionUtil
import com.lxkj.qiqihunshe.app.util.SelectPictureUtil
import com.lxkj.qiqihunshe.databinding.ActivityPerfectInfoBinding
import kotlinx.android.synthetic.main.activity_perfect_info.*
import java.io.File


/**
 * Created by Slingge on 2019/2/19
 */
class PerfectInfoActivitiy :
    BaseActivity<ActivityPerfectInfoBinding, PerfectInfoViewModel>(), View.OnClickListener {


    private val model by lazy { PerfectInfoModel() }

    override fun getBaseViewModel() = PerfectInfoViewModel()

    override fun getLayoutId() = R.layout.activity_perfect_info

    override fun init() {
        initTitle("完善资料")
        WhiteStatusBar()

        tv_agree.paint.flags = Paint.UNDERLINE_TEXT_FLAG //下划线
        tv_agree.paint.isAntiAlias = true//抗锯齿

        tv_agree.setOnClickListener(this)
        rl_header.setOnClickListener(this)
        rl_mytype.setOnClickListener(this)

        viewModel?.let {
            binding.viewmodel = it
            binding.model = model
        }

    }


    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.tv_agree -> {

            }
            R.id.rl_header -> {
                if (PermissionUtil.ApplyPermissionAlbum(this, 0)) {
                    SelectPictureUtil.selectPicture(this, 1, 0, false)
                }
            }
            R.id.rl_mytype -> {
                MyApplication.openActivity(this, MyTypeActivity::class.java)
            }
        }
    }


    /**
     * 申请权限结果回调
     */
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED && requestCode == 0) {//询问结果
            SelectPictureUtil.selectPicture(this, 1, 0, true)
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
                val file = File(PictureSelector.obtainMultipleResult(data)[0].compressPath)
                //加载图片
                Glide.with(this).load(file).into(iv_header)
            }
        }
    }


}