package com.lxkj.qiqihunshe.app.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Path;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Slingge on 2018/8/15
 */
public class RoundImageView extends android.support.v7.widget.AppCompatImageView {

    float width, height;

    public RoundImageView(Context context) {
        this(context, null);
    }

    public RoundImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RoundImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        if (Build.VERSION.SDK_INT < 18) {
            setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        width = getWidth();
        height = getHeight();
    }

    @Override
    protected void onDraw(Canvas canvas) {

        if (width > 30 && height > 30) {
            Path path = new Path();
            path.moveTo(30, 0);
            path.lineTo(width - 30, 0);
            path.quadTo(width, 0, width, 30);
            path.lineTo(width, height - 30);
            path.quadTo(width, height, width - 30, height);
            path.lineTo(30, height);
            path.quadTo(0, height, 0, height - 30);
            path.lineTo(0, 30);
            path.quadTo(0, 0, 30, 0);
            canvas.clipPath(path);
        }

        super.onDraw(canvas);
    }


}
