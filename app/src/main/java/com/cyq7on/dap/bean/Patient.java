package com.cyq7on.dap.bean;

/**
 * Created by cyq7on on 18-3-22.
 */
@Deprecated
public class Patient extends User {
    private String age;
    private int sex;
    //病历
    private String record;

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getRecord() {
        return record;
    }

    public void setRecord(String record) {
        this.record = record;
    }
}
