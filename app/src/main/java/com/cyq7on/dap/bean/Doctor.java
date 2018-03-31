package com.cyq7on.dap.bean;

/**
 * Created by cyq7on on 18-3-22.
 */
@Deprecated
public class Doctor extends User {

    private String age;
    private int sex;
    //科室
    private String department;

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

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }
}
