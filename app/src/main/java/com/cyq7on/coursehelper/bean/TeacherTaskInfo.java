package com.cyq7on.coursehelper.bean;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobRelation;

/**
 * Created by cyq7on on 18-3-31.
 */

public class TeacherTaskInfo extends BmobObject {
    public String title;
    public String content;
    public BmobRelation stu;
    public User teacher;
    //附件地址
    public String url;
}
