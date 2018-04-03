package com.cyq7on.coursehelper.adapter;

import android.content.Context;
import android.text.TextUtils;

import com.cyq7on.coursehelper.adapter.base.BaseRecyclerAdapter;
import com.cyq7on.coursehelper.adapter.base.BaseRecyclerHolder;
import com.cyq7on.coursehelper.adapter.base.IMutlipleItem;
import com.cyq7on.coursehelper.bean.StudentTaskInfo;
import com.cyq7on.coursehelper.bean.User;

import java.util.Collection;

/**
 * Created by cyq7on on 18-3-22.
 */

public class StudentTaskAdapter extends BaseRecyclerAdapter<StudentTaskInfo> {
    private User user;

    /**
     * 支持一种或多种Item布局
     *
     * @param context
     * @param items
     * @param datas
     */
    public StudentTaskAdapter(Context context, IMutlipleItem<StudentTaskInfo> items, Collection<StudentTaskInfo> datas) {
        super(context, items, datas);
        user = User.getCurrentUser(context, User.class);
    }

    @Override
    public void bindView(BaseRecyclerHolder holder, StudentTaskInfo studentTaskInfo, int position) {
        User stu = studentTaskInfo.stu;
        holder.setImageView(stu.getAvatar(), com.cyq7on.coursehelper.R.mipmap.head, com.cyq7on.coursehelper.R.id.iv_recent_avatar);
        String msg;
        if (this.user.getRole() == 0) {
            String score;
            if (TextUtils.isEmpty(studentTaskInfo.score)) {
                score = "教师未批阅";
            } else {
                score = String.format("得分：%s", studentTaskInfo.score);
            }
            msg = String.format("发送给%s：%s \n%s", studentTaskInfo.teacher.getUsername(), studentTaskInfo.title, score);
        } else {
            msg = String.format("来自%s：%s", studentTaskInfo.stu.getUsername(), studentTaskInfo.title);
        }
        holder.setText(com.cyq7on.coursehelper.R.id.tv_recent_name, msg);
    }
}
