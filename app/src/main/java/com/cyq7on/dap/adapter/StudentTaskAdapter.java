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
    /**
     * 支持一种或多种Item布局
     *
     * @param context
     * @param items
     * @param datas
     */
    public StudentTaskAdapter(Context context, IMutlipleItem<StudentTaskInfo> items, Collection<StudentTaskInfo> datas) {
        super(context, items, datas);
    }

    @Override
    public void bindView(BaseRecyclerHolder holder, StudentTaskInfo studentTaskInfo, int position) {
        User user = studentTaskInfo.stu;
        holder.setImageView(user.getAvatar(), com.cyq7on.dap.R.mipmap.head, com.cyq7on.dap.R.id.iv_recent_avatar);
        holder.setText(com.cyq7on.dap.R.id.tv_recent_name,studentTaskInfo.title + user.getUsername());
    }
}
