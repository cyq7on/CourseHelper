package com.cyq7on.coursehelper.bean;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by cyq7on on 18-3-31.
 */

public class StudentTaskInfo extends BmobObject {
    public String title;
    public String content;
    public User stu;
    public User teacher;
    public String score;
    //附件
    public BmobFile bmobFile;
}
