package com.lxkj.qiqihunshe.app;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.multidex.MultiDexApplication;
import com.lxkj.qiqihunshe.app.util.ToastUtil;
import com.lxkj.qiqihunshe.app.util.SharedPreferencesUtil;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

/**
 * Created by Slingge on 2017/1/6 0006.
 */

public class MyApplication extends MultiDexApplication {


    public static String uId = "";
    public static Context CONTEXT;

    //String json = "{\"cmd\":\"getMsg\"" + "}";
// String json = "{\"cmd\":\"upPrize\",\"prizeId\":\"" + prizeId  + "\",\"userNme\":\"" + MyApplication.getUserName() + "\"}";


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

        uId = SharedPreferencesUtil.getSharePreStr(CONTEXT, "uId");


        Logger.addLogAdapter(new AndroidLogAdapter());
    }

    /**
     * 检查是否已经登录 true 已登录
     *
     * @return
     */
    public static boolean isLogined() {
        return !(uId.equals(""))
                && SharedPreferencesUtil.getSharePreBoolean(CONTEXT, "isLogin");
    }

    /**
     * 是否登陆提示
     */
    public static boolean isLoginToa() {
        boolean b = !(SharedPreferencesUtil.getSharePreStr(CONTEXT, "uId")
                .equals(""))
                && SharedPreferencesUtil.getSharePreBoolean(CONTEXT, "isLogin");
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


}
