package com.lxkj.qiqihunshe.app.ui.mine.activity

import android.content.Intent
import android.content.pm.PackageManager
import android.view.View
import com.luck.picture.lib.PictureSelector
import com.lxkj.qiqihunshe.R
import com.lxkj.qiqihunshe.app.base.BaseActivity
import com.lxkj.qiqihunshe.app.ui.dialog.PermissionsDialog
import com.lxkj.qiqihunshe.app.ui.mine.model.ReleaseDynamicModel
import com.lxkj.qiqihunshe.app.ui.mine.viewmodel.ReleaseDynamicViewModel
import com.lxkj.qiqihunshe.app.util.PermissionUtil
import com.lxkj.qiqihunshe.app.util.SelectPictureUtil
import com.lxkj.qiqihunshe.databinding.ActivityReleaseDynamicBinding
import kotlinx.android.synthetic.main.activity_release_dynamic.*

/**
 * Created by Slingge on 2019/2/25
 */
class ReleaseDynamicActivity : BaseActivity<ActivityReleaseDynamicBinding, ReleaseDynamicViewModel>(),
    View.OnClickListener {


    override fun getBaseViewModel() = ReleaseDynamicViewModel()

    override fun getLayoutId() = R.layout.activity_release_dynamic

    private val model by lazy { ReleaseDynamicModel() }


    override fun init() {
        initTitle("发布动态")
        viewModel?.let {
            binding.viewmodel = it
            binding.model = model
            it.bind = binding
            it.initViewModel()
        }

    }


    override fun onClick(v: View?) {
        when (v?.id) {

        }
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
    }


}