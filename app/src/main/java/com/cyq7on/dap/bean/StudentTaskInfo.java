package com.cyq7on.dap.bean;

import cn.bmob.v3.BmobObject;

/**
 * Created by cyq7on on 18-3-31.
 */

public class StudentTaskInfo extends BmobObject {
    public String title;
    public User stu;
    public User teacher;
    public String score;
}
