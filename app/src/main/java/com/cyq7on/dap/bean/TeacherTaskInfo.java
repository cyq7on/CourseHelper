package com.cyq7on.dap.bean;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobRelation;

/**
 * Created by cyq7on on 18-3-31.
 */

public class TeacherTaskInfo extends BmobObject {
    public String title;
    public BmobRelation stu;
    public User teacher;
}
