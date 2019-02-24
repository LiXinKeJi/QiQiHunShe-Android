package com.lxkj.qiqihunshe.app.ui.mine.viewmodel

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.Settings
import android.support.v4.content.FileProvider
import com.lxkj.qiqihunshe.app.MyApplication
import com.lxkj.qiqihunshe.app.base.BaseViewModel
import com.lxkj.qiqihunshe.app.retrofitnet.RetrofitService
import com.lxkj.qiqihunshe.app.retrofitnet.async
import com.lxkj.qiqihunshe.app.service.NotificationDownApkService
import com.lxkj.qiqihunshe.app.ui.entrance.SginInActivity
import com.lxkj.qiqihunshe.app.util.*
import java.io.File

/**
 * Created by Slingge on 2019/2/19
 */
class SetUpViewModel : BaseViewModel() {


    fun sginout() {
        SharedPreferencesUtil.putSharePre(activity, "uid", "")
        MyApplication.uId = ""
        AppManager.finishAllActivity()
        MyApplication.openActivity(activity, SginInActivity::class.java)
    }


      var isDownApk = false
    fun upData() {
        if (PermissionUtil.ApplyPermissionAlbum(activity, 0)) {
            if (isDownApk) {
                ToastUtil.showTopSnackBar(activity, "正在下载")
                return
            }
            isDownApk = true
            ToastUtil.showTopSnackBar(activity, "开始下载")
            activity!!.startService(Intent(activity, NotificationDownApkService::class.java))
        }
    }



    val REQUEST_INSTALL = 124
    private val apkFile: File by lazy { File(StaticUtil.APKPath) }
    fun initApk() {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        if (Build.VERSION.SDK_INT >= 26) {
            if (activity!!.packageManager.canRequestPackageInstalls()) {//26 版本才有此方法
                //可以安装未知来源应用
                //参数1 上下文, 参数2 在AndroidManifest中的android:authorities值, 参数3  共享的文件
                val apkUri =
                    FileProvider.getUriForFile(activity!!, "com.lxkj.linxintechnologylibrary" + ".provider", apkFile)
                //由于没有在Activity环境下启动Activity,设置下面的标签
                //添加这一句表示对目标应用临时授权该Uri所代表的文件
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                intent.setDataAndType(apkUri, "application/vnd.android.package-archive")
            } else {
                val intent =
                    Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES, Uri.parse("package:" + activity!!.packageName))
                activity?.startActivityForResult(intent, REQUEST_INSTALL)
            }
        } else {
            intent.setDataAndType(Uri.parse("file://$apkFile"), "application/vnd.android.package-archive")
        }
        activity?.startActivity(intent)
    }


}