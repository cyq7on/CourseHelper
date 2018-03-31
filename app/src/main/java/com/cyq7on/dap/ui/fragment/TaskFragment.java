package com.cyq7on.dap.ui.fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

import com.cyq7on.dap.R;
import com.cyq7on.dap.adapter.OnRecyclerViewListener;
import com.cyq7on.dap.adapter.UserAdapter;
import com.cyq7on.dap.adapter.base.IMutlipleItem;
import com.cyq7on.dap.base.ParentWithNaviActivity;
import com.cyq7on.dap.base.ParentWithNaviFragment;
import com.cyq7on.dap.bean.StudentTaskInfo;
import com.cyq7on.dap.bean.User;
import com.cyq7on.dap.model.BaseModel;
import com.cyq7on.dap.model.UserModel;
import com.cyq7on.dap.ui.UserInfoActivity;
import com.orhanobut.logger.Logger;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;

/**联系人界面
 * @author :smile
 * @project:ContactFragment
 * @date :2016-04-27-14:23
 */

/**
 * @Description:
 * @author: cyq7on
 * @date: 18-3-31 下午4:24
 * @version: V1.0
 */
public class TaskFragment extends ParentWithNaviFragment {

    @Bind(R.id.rc_view)
    RecyclerView rc_view;
    @Bind(R.id.sw_refresh)
    SwipeRefreshLayout sw_refresh;
    UserAdapter adapter;
    LinearLayoutManager layoutManager;

    @Override
    protected String title() {
        return "任务";
    }

    @Override
    public Object right() {
        return R.drawable.base_action_bar_add_bg_selector;
    }

    @Override
    public ParentWithNaviActivity.ToolBarListener setToolBarListener() {
        return new ParentWithNaviActivity.ToolBarListener() {
            @Override
            public void clickLeft() {

            }

            @Override
            public void clickRight() {
                /*TeacherTaskInfo teacherTaskInfo = new TeacherTaskInfo();
                User user = User.getCurrentUser(getActivity(),User.class);
                teacherTaskInfo.title = "TeacherTest";
                teacherTaskInfo.teacher = user;
                BmobRelation relation = new BmobRelation();
                //将用户添加到多对多关联中
                User stu1 = new User();
                stu1.setObjectId("5eosSSSe");
                relation.add(stu1);
                User stu2 = new User();
                stu2.setObjectId("e8063bd737");
                relation.add(stu2);
                teacherTaskInfo.stu = relation;
                teacherTaskInfo.save(getActivity(), new SaveListener() {
                    @Override
                    public void onSuccess() {
                        toast("提交成功");
                    }

                    @Override
                    public void onFailure(int i, String s) {
                        Logger.d(i + s);
                    }
                });*/

                StudentTaskInfo studentTaskInfo = new StudentTaskInfo();
                studentTaskInfo.title = "StuTest";
                studentTaskInfo.stu = User.getCurrentUser(getActivity(),User.class);
                User teacher = new User();
                teacher.setObjectId("Ii6CBBBW");
                studentTaskInfo.teacher = teacher;
                studentTaskInfo.save(getActivity(), new SaveListener() {
                    @Override
                    public void onSuccess() {
                        toast("提交成功");
                    }

                    @Override
                    public void onFailure(int i, String s) {
                        Logger.d(i + s);
                    }
                });
            }
        };
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView =inflater.inflate(R.layout.fragment_conversation, container, false);
        initNaviView();
        ButterKnife.bind(this, rootView);

        IMutlipleItem<User> iMutlipleItem = new IMutlipleItem<User>() {
            @Override
            public int getItemLayoutId(int viewtype) {
                return R.layout.item_contact;
            }

            @Override
            public int getItemViewType(int postion, User user) {
                return 0;
            }

            @Override
            public int getItemCount(List<User> list) {
                return list.size();
            }
        };
        adapter = new UserAdapter(getActivity(),iMutlipleItem,null);
        rc_view.setAdapter(adapter);
        layoutManager = new LinearLayoutManager(getActivity());
        rc_view.setLayoutManager(layoutManager);
        sw_refresh.setEnabled(true);
        setListener();
        return rootView;
    }

    private void setListener(){
        rootView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                rootView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                sw_refresh.setRefreshing(true);
                query();
            }
        });
        sw_refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                query();
            }
        });
        adapter.setOnRecyclerViewListener(new OnRecyclerViewListener() {
            @Override
            public void onItemClick(int position) {
                /*User owner = adapter.getItem(position);
                BmobIMUserInfo info = new BmobIMUserInfo(owner.getObjectId(), owner.getUsername(), owner.getAvatar());
                //启动一个会话，实际上就是在本地数据库的会话列表中先创建（如果没有）与该用户的会话信息，且将用户信息存储到本地的用户表中
                BmobIMConversation c = BmobIM.getInstance().startPrivateConversation(info, null);
                Bundle bundle = new Bundle();
                bundle.putSerializable("c", c);
                startActivity(ChatActivity.class, bundle);*/

                Bundle bundle = new Bundle();
                User user = adapter.getItem(position);
                bundle.putSerializable("u", user);
                startActivity(UserInfoActivity.class, bundle);
            }

            @Override
            public boolean onItemLongClick(final int position) {
                return false;
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        sw_refresh.setRefreshing(true);
        query();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }


    /**
      查询本地会话
     */
    public void query(){

        User user = User.getCurrentUser(getActivity(),User.class);
        UserModel.getInstance().queryUsers("role",user.getRole() == 0 ? 1 : 0, BaseModel.DEFAULT_LIMIT, new FindListener<User>() {
            @Override
            public void onSuccess(List<User> list) {
                adapter.bindDatas(list);
                sw_refresh.setRefreshing(false);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onError(int i, String s) {
                sw_refresh.setRefreshing(false);
            }
        });
    }

}
