package com.cyq7on.dap.adapter;

import android.content.Context;

import com.cyq7on.dap.adapter.base.BaseRecyclerAdapter;
import com.cyq7on.dap.adapter.base.BaseRecyclerHolder;
import com.cyq7on.dap.adapter.base.IMutlipleItem;
import com.cyq7on.dap.bean.TeacherTaskInfo;
import com.cyq7on.dap.bean.User;

import java.util.Collection;

/**
 * Created by cyq7on on 18-3-22.
 */

public class TeacherTaskAdapter extends BaseRecyclerAdapter<TeacherTaskInfo> {
    private User user;
    /**
     * 支持一种或多种Item布局
     *
     * @param context
     * @param items
     * @param datas
     */
    public TeacherTaskAdapter(Context context, IMutlipleItem<TeacherTaskInfo> items, Collection<TeacherTaskInfo> datas) {
        super(context, items, datas);
        user = User.getCurrentUser(context,User.class);
    }

    @Override
    public void bindView(BaseRecyclerHolder holder, TeacherTaskInfo teacherTaskInfo, int position) {
        User user = teacherTaskInfo.teacher;
        holder.setImageView(user.getAvatar(), com.cyq7on.dap.R.mipmap.head, com.cyq7on.dap.R.id.iv_recent_avatar);
        String msg;
        if(this.user.getRole() == 0){
            msg = String.format("来自%s：%s",teacherTaskInfo.teacher.getUsername(),teacherTaskInfo.title);
        }else {
            msg = String.format("已发送%s",teacherTaskInfo.title);
        }
        holder.setText(com.cyq7on.dap.R.id.tv_recent_name,msg);
    }
}
