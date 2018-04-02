package com.cyq7on.dap.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.cyq7on.dap.R;
import com.cyq7on.dap.base.ParentWithNaviActivity;
import com.cyq7on.dap.bean.StudentTaskInfo;
import com.cyq7on.dap.bean.TeacherTaskInfo;
import com.orhanobut.logger.Logger;

import butterknife.Bind;
import butterknife.OnClick;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by cyq7on on 18-4-2.
 */

public class LookUpTaskActivity extends ParentWithNaviActivity {
    @Bind(R.id.tvTitle)
    EditText tvTitle;
    @Bind(R.id.tvContent)
    EditText tvContent;
    @Bind(R.id.tvScore)
    EditText tvScore;
    @Bind(R.id.btn_publish)
    Button btnPublish;
    @Bind(R.id.btn_edit)
    Button btnEdit;
    @Bind(R.id.rlScore)
    RelativeLayout rlScore;
    private StudentTaskInfo studentTaskInfo;
    private TeacherTaskInfo teacherTaskInfo;

    @Override
    protected String title() {
        return "提交任务";
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish);
        initNaviView();
        Bundle bundle = getBundle();
        if (bundle == null) {
            btnEdit.setVisibility(View.GONE);
        } else {
            btnPublish.setVisibility(View.GONE);
            if (user.getRole() == 0) {
                //学生查看教师任务
                if (bundle.getBoolean("flag")) {
                    tvTitle.setEnabled(false);
                    tvContent.setEnabled(false);
                    teacherTaskInfo = (TeacherTaskInfo) bundle.getSerializable("info");
                    tvTitle.setText(teacherTaskInfo.title);
                    rlScore.setVisibility(View.GONE);
                    tvContent.setText(teacherTaskInfo.content);
                    btnEdit.setVisibility(View.GONE);
                } else {
                    studentTaskInfo = (StudentTaskInfo) bundle.getSerializable("info");
                    tvScore.setEnabled(false);
                    tvTitle.setText(studentTaskInfo.title);
                    tvScore.setText(studentTaskInfo.score);
                    tvContent.setText(studentTaskInfo.content);
                }

            } else {
                //教师批阅学生作业
                if (bundle.getBoolean("flag")) {
                    studentTaskInfo = (StudentTaskInfo) bundle.getSerializable("info");
                    tvTitle.setText(studentTaskInfo.title);
                    tvScore.setText(studentTaskInfo.score);
                    tvContent.setText(studentTaskInfo.content);
                } else {//教师查看自己发布的任务
                    teacherTaskInfo = (TeacherTaskInfo) bundle.getSerializable("info");
                    tvTitle.setText(teacherTaskInfo.title);
                    rlScore.setVisibility(View.GONE);
                    tvContent.setText(teacherTaskInfo.content);
                }

            }
        }

    }

    @OnClick(R.id.btn_publish)
    public void onPublishClick(View view) {
        if (studentTaskInfo != null) {
            studentTaskInfo.title = tvTitle.getText().toString();
            studentTaskInfo.content = tvContent.getText().toString();
            studentTaskInfo.score = tvScore.getText().toString();
            studentTaskInfo.save(getApplicationContext(), new SaveListener() {
                @Override
                public void onSuccess() {
                    toast("发布成功");
                }

                @Override
                public void onFailure(int i, String s) {
                    toast("发布失败");
                    Logger.d(i + s);
                }
            });
        } else if (teacherTaskInfo != null) {
            teacherTaskInfo.title = tvTitle.getText().toString();
            teacherTaskInfo.content = tvContent.getText().toString();
            teacherTaskInfo.save(getApplicationContext(), new SaveListener() {
                @Override
                public void onSuccess() {
                    toast("发布成功");
                }

                @Override
                public void onFailure(int i, String s) {
                    toast("发布失败");
                    Logger.d(i + s);
                }
            });
        }
    }

    @OnClick(R.id.btn_edit)
    public void onUpdateClick(View view) {
        if (studentTaskInfo != null) {
            studentTaskInfo.title = tvTitle.getText().toString();
            studentTaskInfo.content = tvContent.getText().toString();
            studentTaskInfo.score = tvScore.getText().toString();
            studentTaskInfo.update(getApplicationContext(), new UpdateListener() {
                @Override
                public void onSuccess() {
                    toast("更新成功");
                }

                @Override
                public void onFailure(int i, String s) {
                    toast("更新失败");
                    Logger.d(i + s);
                }
            });
        } else if (teacherTaskInfo != null) {
            teacherTaskInfo.title = tvTitle.getText().toString();
            teacherTaskInfo.content = tvContent.getText().toString();
            teacherTaskInfo.update(getApplicationContext(), new UpdateListener() {
                @Override
                public void onSuccess() {
                    toast("更新成功");
                }

                @Override
                public void onFailure(int i, String s) {
                    toast("更新失败");
                    Logger.d(i + s);
                }
            });
        }
    }
}
