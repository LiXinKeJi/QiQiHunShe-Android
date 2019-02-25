package com.lxkj.qiqihunshe.app.ui.mine.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Handler
import android.os.Message
import android.view.View
import android.widget.ImageView
import com.luck.picture.lib.PictureSelector
import com.lxkj.qiqihunshe.R
import com.lxkj.qiqihunshe.app.base.BaseActivity
import com.lxkj.qiqihunshe.app.ui.dialog.PermissionsDialog
import com.lxkj.qiqihunshe.app.ui.mine.model.ReleaseSkillModel
import com.lxkj.qiqihunshe.app.ui.mine.viewmodel.ReleaseSkillViewModel
import com.lxkj.qiqihunshe.app.util.ImageUtil
import com.lxkj.qiqihunshe.app.util.SelectPictureUtil
import com.lxkj.qiqihunshe.databinding.ActivityReleaseSkillBinding
import kotlinx.android.synthetic.main.activity_release_skill.*

/**
 * Created by Slingge on 2019/2/25
 */
class ReleaseSkillActivity : BaseActivity<ActivityReleaseSkillBinding, ReleaseSkillViewModel>(),
    View.OnClickListener {


    override fun getBaseViewModel() = ReleaseSkillViewModel()

    override fun getLayoutId() = R.layout.activity_release_skill

    private val model by lazy { ReleaseSkillModel() }


    override fun init() {
        initTitle("发布才艺")

        iv_video.setOnClickListener(this)

        viewModel?.let {
            binding.model = model
            it.bind = binding
            it.init()
        }

    }


    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.iv_video -> {
                SelectPictureUtil.selectVodeoPicture(this, 1, 0)
            }
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


    private var PreviewingBitmap: Bitmap? = null
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data == null) {
            return
        }
        if (requestCode == 0) {
            if (PictureSelector.obtainMultipleResult(data).isNotEmpty()) {
                model.videoPath = PictureSelector.obtainMultipleResult(data)[0].path
                if (model.videoPath.endsWith(".mp4") || model.videoPath.endsWith(".avi") || model.videoPath.endsWith(".mkv")
                    || model.videoPath.endsWith(".FLV") || model.videoPath.endsWith(".MPG") || model.videoPath.endsWith(
                        ".RMVB"
                    )
                    || model.videoPath.endsWith(".3gp") || model.videoPath.endsWith(".WMV")
                    || model.videoPath.endsWith(".MP4") || model.videoPath.endsWith(".AVI") || model.videoPath.endsWith(
                        ".MKV"
                    )
                    || model.videoPath.endsWith(".flv") || model.videoPath.endsWith(".mpg") || model.videoPath.endsWith(
                        ".rmvb"
                    )
                    || model.videoPath.endsWith(".3GP") || model.videoPath.endsWith(".wmv")
                ) {

                    Thread(Runnable {
                        PreviewingBitmap = ImageUtil.getVideoThumbnail(this, model.videoPath)
                        handler.sendEmptyMessage(1)
                    }).start()
                }
            }
        }
    }

    private val handler = @SuppressLint("HandlerLeak")
    object : Handler() {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            when (msg.what) {
                1 -> if (PreviewingBitmap != null) {
                    iv_video.scaleType = ImageView.ScaleType.CENTER_CROP
                    iv_video.setImageBitmap(PreviewingBitmap)
                }
            }
        }
    }

}