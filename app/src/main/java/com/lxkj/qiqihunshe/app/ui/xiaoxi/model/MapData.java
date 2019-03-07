package com.lxkj.qiqihunshe.app.ui.xiaoxi.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by kxn on 2019/3/6 0006.
 */
public class MapData implements Serializable {
    private HashMap<String,String> map;

    public HashMap<String, String> getMap() {
        return map;
    }

    public void setMap(HashMap<String, String> map) {
        this.map = map;
    }

}
