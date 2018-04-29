package com.cyq7on.coursehelper.bean;

import com.zhuangfei.timetable.core.SubjectBean;

import java.io.Serializable;
import java.util.List;

public class CacheInfo implements Serializable {
    public List<SubjectBean> subjectBeans;

    public CacheInfo(List<SubjectBean> subjectBeans) {
        this.subjectBeans = subjectBeans;
    }
}
