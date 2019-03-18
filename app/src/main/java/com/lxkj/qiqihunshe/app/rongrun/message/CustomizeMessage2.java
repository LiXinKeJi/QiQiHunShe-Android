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
 * Created by Beyond on 2016/12/5.
 */

@MessageTag(value = "RCD:RejectMeet", flag = MessageTag.ISCOUNTED | MessageTag.ISPERSISTED)
public class CustomizeMessage2 extends MessageContent {
    private static final String TAG = "CustomizeMessage2";

    private String content;
    private String type;//1见面，2定位，3完成约见，4支付，5拒绝支付，6，拒绝定位
    private String price;//支付金额

    public CustomizeMessage2() {

    }


    public CustomizeMessage2(String content, String type, String price) {
        this.content = content;
        this.type = type;
        this.price = price;
    }

    public static CustomizeMessage2 obtain(String content, String type, String price) {
        return new CustomizeMessage2(content, type, price);
    }

    public static final Creator<CustomizeMessage2> CREATOR = new Creator<CustomizeMessage2>() {
        @Override
        public CustomizeMessage2 createFromParcel(Parcel source) {
            return new CustomizeMessage2(source);
        }

        @Override
        public CustomizeMessage2[] newArray(int size) {
            return new CustomizeMessage2[size];
        }
    };

    @Override
    public byte[] encode() {
        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("content", getContent());
            jsonObject.put("type", getType());
            jsonObject.put("price", getPrice());
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

    public CustomizeMessage2(byte[] data) {
        String jsonStr = null;
        try {
            jsonStr = new String(data, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        try {
            JSONObject jsonObj = new JSONObject(jsonStr);

            if (jsonObj.has("content"))
                setContent(jsonObj.optString("content"));
            if (jsonObj.has("type"))
                setType(jsonObj.optString("type"));
            if (jsonObj.has("price"))
                setPrice(jsonObj.optString("price"));
        } catch (JSONException e) {
            RLog.e(TAG, "JSONException " + e.getMessage());
        }
    }

    public CustomizeMessage2(Parcel in) {
        content = in.readString();
        type = in.readString();
        price = in.readString();
        setUserInfo(ParcelUtils.readFromParcel(in, UserInfo.class));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(content);
        dest.writeString(type);
        dest.writeString(price);
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}