package com.cyq7on.coursehelper.bean;

import cn.bmob.v3.BmobObject;

/**
 * Created by cyq7on on 18-3-24.
 */

public class Department extends BmobObject {
    private String dep;
    private int depId;

    public String getDep() {
        return dep;
    }

    public void setDep(String dep) {
        this.dep = dep;
    }

    public int getDepId() {
        return depId;
    }

    public void setDepId(int depId) {
        this.depId = depId;
    }
}
