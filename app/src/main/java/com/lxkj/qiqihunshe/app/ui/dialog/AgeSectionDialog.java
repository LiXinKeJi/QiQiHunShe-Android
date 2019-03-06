package com.lxkj.qiqihunshe.app.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import com.bigkoo.pickerview.adapter.ArrayWheelAdapter;
import com.contrarywind.listener.OnItemSelectedListener;
import com.contrarywind.view.WheelView;
import com.lxkj.qiqihunshe.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kxn on 2018/10/27 0027.
 */

public class AgeSectionDialog extends Dialog {
    WheelView wheelView1, wheelView2;
    TextView tvCancle;
    TextView tvSure;
    private Context context;
    private String title;
    OnItemClick onItemClick;
    private int position1, position2;

    public AgeSectionDialog(Context context, String title, OnItemClick onItemClick) {
        super(context, R.style.MyDialogStyle);
        this.context = context;
        this.title = title;
        this.onItemClick = onItemClick;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        init();
    }

    public void init() {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.dialog_age_section, null);
        setContentView(view);
        setCancelable(true);
        setCanceledOnTouchOutside(true);
        final List<String> ages = new ArrayList<>();
        final List<String> ageSecond = new ArrayList<>();
        for (int i = 18; i < 91; i++) {
            ages.add(i + "");
        }

        ageSecond.add("不限");
        ageSecond.addAll(ages.subList(1, ages.size()));


        TextView tvTitle = (TextView) view.findViewById(R.id.tv_title);
        wheelView1 = (WheelView) view.findViewById(R.id.wheelView1);
        wheelView2 = (WheelView) view.findViewById(R.id.wheelView2);
        tvCancle = (TextView) view.findViewById(R.id.tv_cancle);
        tvSure = (TextView) view.findViewById(R.id.tv_sure);
        wheelView1.setTextSize(16);
        wheelView2.setTextSize(16);
        wheelView1.setCyclic(false);
        wheelView2.setCyclic(false);

        tvTitle.setText(title);
        final ArrayWheelAdapter adapter1 = new ArrayWheelAdapter(ages);
        final ArrayWheelAdapter adapter2 = new ArrayWheelAdapter(ageSecond);
        wheelView1.setAdapter(adapter1);
        wheelView1.setCurrentItem(0);
        wheelView1.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
                position1 = index;
                ageSecond.clear();
                ageSecond.add("不限");
                ageSecond.addAll(ages.subList(index + 1, ages.size()));
                wheelView2.setAdapter(new ArrayWheelAdapter(ageSecond));
                wheelView2.setCurrentItem(0);
                position2 = 0;
            }
        });

        wheelView2.setAdapter(adapter2);
        wheelView2.setCurrentItem(0);
        wheelView2.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
                position2 = index;
            }
        });

        tvSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClick.onItemClick(ages.get(position1) + "-" + ageSecond.get(position2));
                dismiss();
            }
        });
        tvCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        Window dialogWindow = getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        DisplayMetrics d = context.getResources().getDisplayMetrics(); // 获取屏幕宽、高用
        lp.width = (int) (d.widthPixels * 0.8); // 宽带度设置为屏幕的0.8
        dialogWindow.setAttributes(lp);
    }

    public interface OnItemClick {
        void onItemClick(String age);
    }


}
