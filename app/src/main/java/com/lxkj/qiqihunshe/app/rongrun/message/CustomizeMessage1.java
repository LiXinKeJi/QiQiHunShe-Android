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

@MessageTag(value = "RCD:AboutToMeet", flag = MessageTag.ISCOUNTED | MessageTag.ISPERSISTED)
public class CustomizeMessage1 extends MessageContent {
    private static final String TAG = "CustomizeMessage1";

    private String reject;
    private String content;

    private String type;//0未操作，1已同意，2已拒绝

    public CustomizeMessage1() {

    }


    public CustomizeMessage1(String reject, String content) {
        this.reject = reject;
        this.content = content;
    }

    public static CustomizeMessage1 obtain(String reject, String content) {
        return new CustomizeMessage1(reject, content);
    }

    public static final Creator<CustomizeMessage1> CREATOR = new Creator<CustomizeMessage1>() {
        @Override
        public CustomizeMessage1 createFromParcel(Parcel source) {
            return new CustomizeMessage1(source);
        }

        @Override
        public CustomizeMessage1[] newArray(int size) {
            return new CustomizeMessage1[size];
        }
    };

    @Override
    public byte[] encode() {
        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("reject", getReject());
            jsonObject.put("content", getContent());

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

    public CustomizeMessage1(byte[] data) {
        String jsonStr = null;
        try {
            jsonStr = new String(data, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        try {
            JSONObject jsonObj = new JSONObject(jsonStr);

            if (jsonObj.has("reject"))
                setReject(jsonObj.optString("reject"));
            if (jsonObj.has("content"))
                setContent(jsonObj.optString("content"));
        } catch (JSONException e) {
            RLog.e(TAG, "JSONException " + e.getMessage());
        }
    }

    public CustomizeMessage1(Parcel in) {
        reject = in.readString();
        content = in.readString();
        setUserInfo(ParcelUtils.readFromParcel(in, UserInfo.class));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(reject);
        dest.writeString(content);
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

    public String getReject() {
        return reject;
    }

    public void setReject(String reject) {
        this.reject = reject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}