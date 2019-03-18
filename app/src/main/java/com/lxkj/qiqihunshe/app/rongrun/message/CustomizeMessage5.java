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
 * 同意定位位置
 * Created by Beyond on 2016/12/5.
 */

@MessageTag(value = "RCD:PositionAddress", flag = MessageTag.ISCOUNTED | MessageTag.ISPERSISTED)
public class CustomizeMessage5 extends MessageContent {
    private static final String TAG = "CustomizeMessage5";

    private String address;
    private String lat;//
    private String lon;
    private String time;
    private String who;//me自己，othe对方
    private String yuejianId;

    public CustomizeMessage5() {

    }


    public CustomizeMessage5(String who, String address, String lat, String lon, String time, String yuejianId) {
        this.who = who;
        this.address = address;
        this.lat = lat;
        this.lon = lon;
        this.time = time;
        this.yuejianId = yuejianId;
    }

    public static CustomizeMessage5 obtain(String who, String address, String lat, String lon, String time, String yuejianId) {
        return new CustomizeMessage5(who, address, lat, lon, time,yuejianId);
    }

    public static final Creator<CustomizeMessage5> CREATOR = new Creator<CustomizeMessage5>() {
        @Override
        public CustomizeMessage5 createFromParcel(Parcel source) {
            return new CustomizeMessage5(source);
        }

        @Override
        public CustomizeMessage5[] newArray(int size) {
            return new CustomizeMessage5[size];
        }
    };

    @Override
    public byte[] encode() {
        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("who", getWho());
            jsonObject.put("address", getAddress());
            jsonObject.put("lat", getLat());
            jsonObject.put("lon", getLon());
            jsonObject.put("time", getTime());
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

    public CustomizeMessage5(byte[] data) {
        String jsonStr = null;
        try {
            jsonStr = new String(data, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        try {
            JSONObject jsonObj = new JSONObject(jsonStr);

            if (jsonObj.has("who"))
                setWho(jsonObj.optString("who"));
            if (jsonObj.has("address"))
                setAddress(jsonObj.optString("address"));
            if (jsonObj.has("lat"))
                setLat(jsonObj.optString("lat"));
            if (jsonObj.has("lon"))
                setLon(jsonObj.optString("lon"));
            if (jsonObj.has("time"))
                setTime(jsonObj.optString("time"));
            if (jsonObj.has("yuejianId"))
                setYuejianId(jsonObj.optString("yuejianId"));
        } catch (JSONException e) {
            RLog.e(TAG, "JSONException " + e.getMessage());
        }
    }

    public CustomizeMessage5(Parcel in) {
        who = in.readString();
        address = in.readString();
        lat = in.readString();
        lon = in.readString();
        time = in.readString();
        yuejianId = in.readString();
        setUserInfo(ParcelUtils.readFromParcel(in, UserInfo.class));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(who);
        dest.writeString(address);
        dest.writeString(lat);
        dest.writeString(lon);
        dest.writeString(time);
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

    public String getWho() {
        return who;
    }

    public void setWho(String who) {
        this.who = who;
    }

    public String getYuejianId() {
        return yuejianId;
    }

    public void setYuejianId(String yuejianId) {
        this.yuejianId = yuejianId;
    }
}