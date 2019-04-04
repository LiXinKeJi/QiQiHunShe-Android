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
 * 发送位置
 * Created by Beyond on 2016/12/5.
 */

@MessageTag(value = "RCD:SendMeetAddress", flag = MessageTag.ISCOUNTED | MessageTag.ISPERSISTED)
public class CustomizeMessage4 extends MessageContent {
    private static final String TAG = "CustomizeMessage2";

    private String content;
    private String address;//
    private String lat;
    private String lon;
    private String time;

    public CustomizeMessage4() {

    }


    public CustomizeMessage4(String content, String address, String lat, String lon, String time) {
        this.content = content;
        this.address = address;
        this.lat = lat;
        this.lon = lon;
        this.time = time;
    }

    public static CustomizeMessage4 obtain(String content, String address, String lat, String lon, String time) {
        return new CustomizeMessage4(content, address, lat, lon, time);
    }

    public static final Creator<CustomizeMessage4> CREATOR = new Creator<CustomizeMessage4>() {
        @Override
        public CustomizeMessage4 createFromParcel(Parcel source) {
            return new CustomizeMessage4(source);
        }

        @Override
        public CustomizeMessage4[] newArray(int size) {
            return new CustomizeMessage4[size];
        }
    };

    @Override
    public byte[] encode() {
        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("content", getContent());
            jsonObject.put("address", getAddress());
            jsonObject.put("lat", getLat());
            jsonObject.put("lon", getLon());
            jsonObject.put("time", getTime());
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

    public CustomizeMessage4(byte[] data) {
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
            if (jsonObj.has("address"))
                setAddress(jsonObj.optString("address"));
            if (jsonObj.has("lat"))
                setLat(jsonObj.optString("lat"));
            if (jsonObj.has("lon"))
                setLon(jsonObj.optString("lon"));
            if (jsonObj.has("time"))
                setTime(jsonObj.optString("time"));

        } catch (JSONException e) {
            RLog.e(TAG, "JSONException " + e.getMessage());
        }
    }

    public CustomizeMessage4(Parcel in) {
        content = in.readString();
        address = in.readString();
        lat = in.readString();
        lon = in.readString();
        time = in.readString();
        setUserInfo(ParcelUtils.readFromParcel(in, UserInfo.class));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(content);
        dest.writeString(address);
        dest.writeString(lat);
        dest.writeString(lon);
        dest.writeString(time);
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

}