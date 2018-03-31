package com.cyq7on.dap.bean;

import java.util.ArrayList;

/**
 * Created by cyq7on on 18-3-24.
 */

public class FatherData {
    private String title;
    private ArrayList<User> list;// 二级列表数据
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public ArrayList<User> getList() {
        return list;
    }
    public void setList(ArrayList<User> list) {
        this.list = list;
    }
}

