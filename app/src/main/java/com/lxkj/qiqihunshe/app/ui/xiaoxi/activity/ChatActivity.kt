package com.lxkj.qiqihunshe.app.ui.xiaoxi.activity

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.leon.lfilepickerlibrary.utils.Constant
import com.lxkj.qiqihunshe.R
import com.lxkj.qiqihunshe.app.MyApplication
import com.lxkj.qiqihunshe.app.base.BaseActivity
import com.lxkj.qiqihunshe.app.retrofitnet.bindLifeCycle
import com.lxkj.qiqihunshe.app.ui.dialog.PermissionsDialog
import com.lxkj.qiqihunshe.app.ui.dialog.ReportDialog2
import com.lxkj.qiqihunshe.app.ui.fujin.viewmodel.ChatViewModel
import com.lxkj.qiqihunshe.app.ui.mine.activity.PersonalInfoActivity
import com.lxkj.qiqihunshe.app.util.*
import com.lxkj.qiqihunshe.databinding.ActivityChatDetailsBinding
import kotlinx.android.synthetic.main.activity_chat_details.*
import kotlinx.android.synthetic.main.include_title.*

/**
 * Created by Slingge on 2019/3/12
 */
class ChatActivity : BaseActivity<ActivityChatDetailsBinding, ChatViewModel>(), View.OnClickListener {

    override fun getBaseViewModel() = ChatViewModel()

    override fun getLayoutId() = R.layout.activity_chat_details

    override fun init() {

        tv_right.visibility = View.VISIBLE
        tv_right.setOnClickListener(this)
        AbStrUtil.setDrawableLeft(this, R.drawable.yh_ziliao, tv_right, 0)

        iv_del.setOnClickListener(this)
        iv_yuejian.setOnClickListener(this)
        iv_jubao.setOnClickListener(this)
    }


    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.iv_del -> {
                iv_del.visibility = View.GONE
                tv_tip0.visibility = View.GONE
            }
            R.id.iv_yuejian -> {

            }
            R.id.tv_right -> {
                val bundle = Bundle()
                bundle.putString("userId", StaticUtil.uid)
                MyApplication.openActivity(this, PersonalInfoActivity::class.java, bundle)
            }
            R.id.iv_jubao -> {
                if (viewModel!!.JuBaoList.isEmpty()) {
                    viewModel!!.getJuBaoConten().bindLifeCycle(this).subscribe({}, { toastFailure(it) })
                } else {
                    ReportDialog2.show(this, viewModel!!.JuBaoList)
                }
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


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data == null) {
            return
        }
        if (requestCode == 0) {
            val list = data.getStringArrayListExtra(Constant.RESULT_INFO)//文件路径
            Toast.makeText(applicationContext, "选中的路径为" + list[0], Toast.LENGTH_SHORT).show()
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        ReportDialog2.diss()
    }


}