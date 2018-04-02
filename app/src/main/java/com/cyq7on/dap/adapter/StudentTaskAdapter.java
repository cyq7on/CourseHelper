package com.cyq7on.dap.adapter;

import android.content.Context;

import com.cyq7on.dap.adapter.base.BaseRecyclerAdapter;
import com.cyq7on.dap.adapter.base.BaseRecyclerHolder;
import com.cyq7on.dap.adapter.base.IMutlipleItem;
import com.cyq7on.dap.bean.StudentTaskInfo;
import com.cyq7on.dap.bean.User;

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
        user = User.getCurrentUser(context,User.class);
    }

    @Override
    public void bindView(BaseRecyclerHolder holder, StudentTaskInfo studentTaskInfo, int position) {
        User stu = studentTaskInfo.stu;
        holder.setImageView(stu.getAvatar(), com.cyq7on.dap.R.mipmap.head, com.cyq7on.dap.R.id.iv_recent_avatar);
        String msg;
        if(this.user.getRole() == 0){
            msg = String.format("发送给%s：%s",studentTaskInfo.teacher.getUsername(),studentTaskInfo.title);
        }else {
            msg = String.format("来自%s：%s",studentTaskInfo.stu.getUsername(),studentTaskInfo.title);
        }
        holder.setText(com.cyq7on.dap.R.id.tv_recent_name,msg);
    }
}
