package com.zhuangfei.timetable.core;

import android.view.View;

public interface OnSubjectItemLongClickListener {

	public void onItemLongClick(View view,int day,int start);
	public void onItemLongClick(View view,SubjectBean subjectBean);

}
