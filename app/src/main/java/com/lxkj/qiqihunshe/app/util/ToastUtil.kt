package com.lxkj.qiqihunshe.app.util

import android.app.Activity
import android.content.Context
import android.support.design.widget.Snackbar
import android.view.View
import android.widget.Toast

import android.graphics.Typeface
import android.support.v4.app.Fragment
import android.view.ViewGroup
import com.irozon.sneaker.Sneaker
import com.lxkj.qiqihunshe.R
import com.lxkj.qiqihunshe.app.MyApplication


/**
 * 单例Toast
 */

object ToastUtil {

    private var mToast: Toast? = null

    fun showToast(text: String?) {
        text?.let {
            if (mToast == null) {
                mToast = Toast.makeText(MyApplication.getInstance(), text, Toast.LENGTH_SHORT)
            }
            mToast!!.setText(text)
            mToast!!.show()
        }
    }


    fun showLongToast(text: String) {
        if (mToast == null) {
            mToast = Toast.makeText(MyApplication.getInstance(), text, Toast.LENGTH_LONG)
        }
        mToast!!.setText(text)
        mToast!!.show()
    }

    /**
     * @param view 最外层布局
     * LENGTH_INDEFINITE	始终显示，点击Snackbar上的按钮才会消失
    LENGTH_SHORT	显示1500毫秒
    LENGTH_LONG
     *  setActionTextColor(ColorStateList colors)
     * */
    fun showSnackBar(view: View, title: String, onClick: View.OnClickListener) {
        Snackbar.make(view, title, Snackbar.LENGTH_SHORT).setAction(
            "隐藏", onClick
        ).setDuration(Snackbar.LENGTH_INDEFINITE).show()
    }

    fun showSnackBar(view: View, title: String) {
        Snackbar.make(view, title, Snackbar.LENGTH_SHORT).setDuration(Snackbar.LENGTH_SHORT).show()
    }


    fun showTopSnackBar(activity: Activity?, msg: String?) {
        msg?.let {
            Sneaker.with(activity!!) // Activity, Fragment or ViewGroup
                .setTitle(it, R.color.white) // Title and title color
//                .setMessage(msg, R.color.white) // Message and message color
                .setDuration(2000) // Time duration to show
                .autoHide(true) // Auto hide Sneaker view
                .setHeight(ViewGroup.LayoutParams.WRAP_CONTENT) // Height of the Sneaker layout
//                .setIcon(R.drawable.ic_no_connection, R.color.white, false) // Icon, icon tint color and circular icon view
                .setTypeface(
                    Typeface.createFromAsset(
                        activity.assets,
                        "font/RobotoCondensed-Regular.ttf"
                    )
                ) // Custom font for title and message
//                .setOnSneakerClickListener(this) // Click listener for Sneaker
//                .setOnSneakerDismissListener(this) // Dismiss listener for Sneaker. - Version 1.0.2
                .setCornerRadius(4, 1) // Radius and margin for round corner Sneaker. - Version 1.0.2
                .sneak(R.color.colorPrimaryDark) // Sneak with background color
        }
    }


    fun showTopSnackBar(activity: Fragment?, msg: String?) {
        msg?.let {
            Sneaker.with(activity!!) // Activity, Fragment or ViewGroup
                .setTitle(it, R.color.white) // Title and title color
//                .setMessage(msg, R.color.white) // Message and message color
                .setDuration(2000) // Time duration to show
                .autoHide(true) // Auto hide Sneaker view
                .setHeight(ViewGroup.LayoutParams.WRAP_CONTENT) // Height of the Sneaker layout
//                .setIcon(R.drawable.ic_no_connection, R.color.white, false) // Icon, icon tint color and circular icon view
                .setTypeface(
                    Typeface.createFromAsset(
                        activity.activity!!.assets,
                        "font/RobotoCondensed-Regular.ttf"
                    )
                ) // Custom font for title and message
//                .setOnSneakerClickListener(this) // Click listener for Sneaker
//                .setOnSneakerDismissListener(this) // Dismiss listener for Sneaker. - Version 1.0.2
                .setCornerRadius(4, 1) // Radius and margin for round corner Sneaker. - Version 1.0.2
                .sneak(R.color.colorPrimaryDark) // Sneak with background color
        }
    }


}
