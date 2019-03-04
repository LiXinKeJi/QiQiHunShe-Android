package com.lxkj.qiqihunshe.app.util;

import android.content.Context;
import com.lxkj.qiqihunshe.R;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;


public class AppJsonFileReader {

    private static int fileName[] = {R.raw.city, R.raw.nation};

    public static String getJsons(Context context, int i) {
        String text = null;
        try {
            InputStream is = context.getResources().openRawResource(fileName[i]);
            text = readTextFromSDcard(is);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return text;
    }


    private static String readTextFromSDcard(InputStream is) throws Exception {
        InputStreamReader reader = new InputStreamReader(is);
        BufferedReader bufferedReader = new BufferedReader(reader);
        StringBuffer buffer = new StringBuffer("");
        String str;
        while ((str = bufferedReader.readLine()) != null) {
            buffer.append(str);
            buffer.append("\n");
        }
        return buffer.toString();
    }


}
