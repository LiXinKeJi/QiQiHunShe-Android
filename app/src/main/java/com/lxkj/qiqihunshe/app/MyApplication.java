package com.lxkj.qiqihunshe.app;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.multidex.MultiDexApplication;
import android.text.TextUtils;
import android.view.View;
import cn.bingoogolapple.badgeview.BGABadgeTextView;
import com.lxkj.qiqihunshe.R;
import com.baidu.mapapi.CoordType;
import com.baidu.mapapi.SDKInitializer;
import com.lxkj.qiqihunshe.app.rongrun.RongCloudEvent;
import com.lxkj.qiqihunshe.app.rongrun.plugin.MyExtensionModule;
import com.lxkj.qiqihunshe.app.rongrun.RongYunUtil;
import com.lxkj.qiqihunshe.app.rongrun.message.*;
import com.lxkj.qiqihunshe.app.util.StaticUtil;
import com.lxkj.qiqihunshe.app.util.ToastUtil;
import com.lxkj.qiqihunshe.app.util.SharedPreferencesUtil;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;
import com.tencent.smtt.sdk.QbSdk;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;
import io.rong.imkit.DefaultExtensionModule;
import io.rong.imkit.IExtensionModule;
import io.rong.imkit.RongExtensionManager;
import io.rong.imkit.RongIM;
import zhanghuan.cn.emojiconlibrary.FaceConversionUtil;

import java.util.List;

/**
 * Created by Slingge on 2017/1/6 0006.
 */

public class MyApplication extends MultiDexApplication {


    public static String uId = "";
    public static Context CONTEXT;

    //String json = "{\"cmd\":\"getMsg\"" + "}";
//    val json = "{\"cmd\":\"upPrize\",\"prizeId\":\"" + prizeId  + "\",\"userNme\":\"" + MyApplication.getUserName() + "\"}"


    private static MyApplication myApplication;

    public static MyApplication getInstance() {
        // if语句下是不会走的，Application本身已单例
        if (myApplication == null) {
            synchronized (MyApplication.class) {
                if (myApplication == null) {
                    myApplication = new MyApplication();
                }
            }
        }
        return myApplication;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        myApplication = this;
        CONTEXT = getApplicationContext();

        SharedPreferences sp = this
                .getSharedPreferences(SharedPreferencesUtil.NAME, 0);
        FaceConversionUtil.getInstace().getFileText(CONTEXT);
        uId = sp.getString("uid", "");
        StaticUtil.INSTANCE.setUid(uId);
        StaticUtil.INSTANCE.setRytoken(sp.getString("rytoken", ""));
        StaticUtil.INSTANCE.setHeaderUrl(sp.getString("userIcon", ""));
        StaticUtil.INSTANCE.setNickName(sp.getString("nickName", ""));
        StaticUtil.INSTANCE.setReal(sp.getString("isAuth", ""));

        Logger.addLogAdapter(new AndroidLogAdapter());

        initTBS();

        //在使用SDK各组件之前初始化context信息，传入ApplicationContext
        SDKInitializer.initialize(this);
        //自4.3.0起，百度地图SDK所有接口均支持百度坐标和国测局坐标，用此方法设置您使用的坐标类型.
        //包括BD09LL和GCJ02两种坐标，默认是BD09LL坐标。
        SDKInitializer.setCoordType(CoordType.BD09LL);


        UMShareAPI.get(this);
        PlatformConfig.setWeixin(StaticUtil.INSTANCE.getWeixin_Appid(), StaticUtil.INSTANCE.getWeixin_AppSecret());
        PlatformConfig.setQQZone("1106937627", "KePldFLgZzyUZ47F");


        RongIM.init(this, "3argexb63qm0e");
//        RongIM.setOnReceiveMessageListener(new MyReceiveMessageListener(CONTEXT));
        RongCloudEvent.INSTANCE.init();
        RongIM.getInstance().setMessageAttachedUserInfo(true);
        RongIM.registerMessageType(CustomizeMessage1.class);//注册自定义消息
        RongIM.registerMessageTemplate(new CustomizeMessageItemProvider1(this));
        RongIM.registerMessageType(CustomizeMessage2.class);//注册自定义消息
        RongIM.registerMessageTemplate(new CustomizeMessageItemProvider2(this));
        RongIM.registerMessageType(CustomizeMessage3.class);//注册自定义消息
        RongIM.registerMessageTemplate(new CustomizeMessageItemProvider3(this));
        RongIM.registerMessageType(CustomizeMessage4.class);//注册自定义消息
        RongIM.registerMessageTemplate(new CustomizeMessageItemProvider4(this));
        RongIM.registerMessageType(CustomizeMessage5.class);//注册自定义消息
        RongIM.registerMessageTemplate(new CustomizeMessageItemProvider5(this));
        RongIM.registerMessageType(CustomizeMessage6.class);//注册自定义消息
        RongIM.registerMessageTemplate(new CustomizeMessageItemProvider6(this));
        RongIM.registerMessageType(CustomizeMessage7.class);//注册自定义消息
        RongIM.registerMessageTemplate(new CustomizeMessageItemProvider7(this));
        if (!TextUtils.isEmpty(StaticUtil.INSTANCE.getRytoken())) {
            RongYunUtil.INSTANCE.initService();
        }
        List<IExtensionModule> moduleList = RongExtensionManager.getInstance().getExtensionModules();
        IExtensionModule defaultModule = null;
        if (moduleList != null) {
            for (IExtensionModule module : moduleList) {
                if (module instanceof DefaultExtensionModule) {
                    defaultModule = module;
                    break;
                }
            }
            if (defaultModule != null) {
                RongExtensionManager.getInstance().unregisterExtensionModule(defaultModule);
                RongExtensionManager.getInstance().registerExtensionModule(new MyExtensionModule(1));
            }
        }
    }


    /**
     * 初始化TBS浏览服务X5内核
     */
    private void initTBS() {
        //搜集本地tbs内核信息并上报服务器，服务器返回结果决定使用哪个内核。
        QbSdk.setDownloadWithoutWifi(true);//非wifi条件下允许下载X5内核
        QbSdk.PreInitCallback cb = new QbSdk.PreInitCallback() {

            @Override
            public void onViewInitFinished(boolean arg0) {
                //x5內核初始化完成的回调，为true表示x5内核加载成功，否则表示x5内核加载失败，会自动切换到系统内核。
            }

            @Override
            public void onCoreInitFinished() {
            }
        };
        //x5内核初始化接口
        QbSdk.initX5Environment(getApplicationContext(), cb);
    }

    /**
     * 检查是否已经登录 true 已登录
     *
     * @return
     */
    public static boolean isLogined() {
        return !(uId.equals(""));
    }

    /**
     * 是否登陆提示
     */
    public static boolean isLoginToa() {
        boolean b = !(SharedPreferencesUtil.getSharePreStr(CONTEXT, "uid")
                .equals(""));
        if (b) {
            return true;
        } else {
            ToastUtil.INSTANCE.showToast("请登录");
            return false;
        }
    }


    /**
     * 通过类名启动Activity
     *
     * @param targetClass
     */
    public static void openActivity(Context context, Class<?> targetClass) {
        openActivity(context, targetClass, null);
    }

    /**
     * 通过类名启动Activity，并且含有Bundle数据
     *
     * @param targetClass
     * @param extras
     */
    public static void openActivity(Context context, Class<?> targetClass,
                                    Bundle extras) {
        Intent intent = new Intent(context, targetClass);
        if (extras != null) {
            intent.putExtras(extras);
        }
        context.startActivity(intent);
    }

    public static void openActivityForResult(Activity activity,
                                             Class<?> targetClass, Bundle extras, int requestCode) {
        Intent intent = new Intent(activity, targetClass);
        if (extras != null) {
            intent.putExtras(extras);
        }
        activity.startActivityForResult(intent, requestCode);
    }


    /**
     * Fragment中无效
     */
    public static void openActivityForResult(Activity activity,
                                             Class<?> targetClass, int requestCode) {
        openActivityForResult(activity, targetClass, null, requestCode);
    }


    public static void setRedNum(BGABadgeTextView badgeTextView, int MsgNum) {
        badgeTextView.getBadgeViewHelper().setBadgeTextSizeSp(9);
        badgeTextView.getBadgeViewHelper().setBadgeTextColorInt(CONTEXT.getResources().getColor(R.color.white));
        badgeTextView.getBadgeViewHelper().setBadgeBgColorInt(CONTEXT.getResources().getColor(R.color.red));
        badgeTextView.getBadgeViewHelper().setDragable(true);
        badgeTextView.getBadgeViewHelper().setBadgePaddingDp(4);
        badgeTextView.getBadgeViewHelper().setBadgeBorderWidthDp(0);
        badgeTextView.getBadgeViewHelper().setBadgeBorderColorInt(CONTEXT.getResources().getColor(R.color.red));
        badgeTextView.showCirclePointBadge();
        //注意带上这个显示数字，否则将变成空
        if (MsgNum > 99) {
            badgeTextView.showTextBadge("...");
            badgeTextView.setVisibility(View.VISIBLE);
        } else if (MsgNum <= 0) {
            badgeTextView.setVisibility(View.GONE);
        } else {
            badgeTextView.setVisibility(View.VISIBLE);
            badgeTextView.showTextBadge(MsgNum + "");
        }
    }


}
