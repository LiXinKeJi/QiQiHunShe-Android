package com.lxkj.qiqihunshe.app.rongrun.message;

import android.os.Parcel;
import android.util.Log;
import io.rong.common.ParcelUtils;
import io.rong.common.RLog;
import io.rong.imlib.MessageTag;
import io.rong.imlib.model.MessageContent;
import io.rong.imlib.model.UserInfo;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 显示消费划分金额
 * Created by Beyond on 2016/12/5.
 */

@MessageTag(value = "RCD:meetPay", flag = MessageTag.ISCOUNTED | MessageTag.ISPERSISTED)
public class CustomizeMessage7 extends MessageContent {
    private static final String TAG = "CustomizeMessage7";

    private String price;
    private String yuejianId;

    public CustomizeMessage7() {

    }


    public CustomizeMessage7(String price, String lat, String lon, String yuejianId) {
        this.price = price;
        this.yuejianId = yuejianId;
    }

    public static CustomizeMessage7 obtain(String price, String lat, String lon, String yuejianId) {
        return new CustomizeMessage7(price, lat, lon, yuejianId);
    }

    public static final Creator<CustomizeMessage7> CREATOR = new Creator<CustomizeMessage7>() {
        @Override
        public CustomizeMessage7 createFromParcel(Parcel source) {
            return new CustomizeMessage7(source);
        }

        @Override
        public CustomizeMessage7[] newArray(int size) {
            return new CustomizeMessage7[size];
        }
    };

    @Override
    public byte[] encode() {
        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("price", getPrice());
            jsonObject.put("yuejianId", getYuejianId());
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }

        try {
            return jsonObject.toString().getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public CustomizeMessage7(byte[] data) {
        String jsonStr = null;
        try {
            jsonStr = new String(data, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        try {
            JSONObject jsonObj = new JSONObject(jsonStr);

            if (jsonObj.has("price"))
                setPrice(jsonObj.optString("price"));
            if (jsonObj.has("yuejianId"))
                setYuejianId(jsonObj.optString("yuejianId"));
        } catch (JSONException e) {
            RLog.e(TAG, "JSONException " + e.getMessage());
        }
    }

    public CustomizeMessage7(Parcel in) {
        price = in.readString();
        yuejianId = in.readString();
        setUserInfo(ParcelUtils.readFromParcel(in, UserInfo.class));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(price);
        dest.writeString(yuejianId);
        ParcelUtils.writeToParcel(dest, getUserInfo());
    }

    private String getEmotion(String content) {

        Pattern pattern = Pattern.compile("\\[/u([0-9A-Fa-f]+)\\]");
        Matcher matcher = pattern.matcher(content);

        StringBuffer sb = new StringBuffer();

        while (matcher.find()) {
            int inthex = Integer.parseInt(matcher.group(1), 16);
            matcher.appendReplacement(sb, String.valueOf(Character.toChars(inthex)));
        }

        matcher.appendTail(sb);

        return sb.toString();
    }


    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getYuejianId() {
        return yuejianId;
    }

    public void setYuejianId(String yuejianId) {
        this.yuejianId = yuejianId;
    }
}