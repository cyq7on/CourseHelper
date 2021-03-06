package com.cyq7on.coursehelper.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

import com.cyq7on.coursehelper.R;
import com.cyq7on.coursehelper.adapter.OnRecyclerViewListener;
import com.cyq7on.coursehelper.adapter.TeacherTaskAdapter;
import com.cyq7on.coursehelper.adapter.base.IMutlipleItem;
import com.cyq7on.coursehelper.base.ParentWithNaviFragment;
import com.cyq7on.coursehelper.bean.TeacherTaskInfo;
import com.cyq7on.coursehelper.bean.User;
import com.cyq7on.coursehelper.ui.LookUpTaskActivity;
import com.orhanobut.logger.Logger;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by cyq7on on 18-3-31.
 * TeacherTaskInfo表里查询学生收到的任务
 */

public class StuReceiveTaskFragment extends ParentWithNaviFragment {
    @Bind(R.id.rc_view)
    RecyclerView rcView;
    @Bind(R.id.sw_refresh)
    SwipeRefreshLayout swRefresh;
    TeacherTaskAdapter adapter;

    @Override
    protected String title() {
        return "";
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
    @Subscribe
    public void onEventMainThread(TeacherTaskInfo teacherTaskInfo) {
        Logger.d("收到teacherTaskInfo");
        query();
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_task_list, container, false);
        ButterKnife.bind(this, rootView);
//        initNaviView();
        IMutlipleItem<TeacherTaskInfo> iMutlipleItem = new IMutlipleItem<TeacherTaskInfo>() {
            @Override
            public int getItemLayoutId(int viewtype) {
                return R.layout.item_contact;
            }

            @Override
            public int getItemViewType(int postion, TeacherTaskInfo info) {
                return 0;
            }

            @Override
            public int getItemCount(List<TeacherTaskInfo> list) {
                return list.size();
            }
        };
        adapter = new TeacherTaskAdapter(getActivity(),iMutlipleItem,null);
        rcView.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        rcView.setLayoutManager(layoutManager);
        swRefresh.setEnabled(true);
        setListener();
        return rootView;
    }

    private void setListener(){
        rootView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                rootView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                swRefresh.setRefreshing(true);
                query();
            }
        });
        swRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                query();
            }
        });
        adapter.setOnRecyclerViewListener(new OnRecyclerViewListener() {
            @Override
            public void onItemClick(int position) {
                Bundle bundle = new Bundle();
                TeacherTaskInfo teacherTaskInfo = adapter.getItem(position);
                bundle.putSerializable("info", teacherTaskInfo);
                bundle.putBoolean("flag",true);
                startActivity(LookUpTaskActivity.class, bundle);
            }

            @Override
            public boolean onItemLongClick(final int position) {
                return false;
            }
        });
    }

    private void query() {
        User user = User.getCurrentUser(getActivity(), User.class);
        BmobQuery<TeacherTaskInfo> query = new BmobQuery<>();
        query.setLimit(100);
        query.order("-createdAt");
        query.include("teacher");
        BmobQuery<User> innerQuery = new BmobQuery<>();
        innerQuery.addWhereEqualTo("objectId",user.getObjectId());
        // 第一个参数为stu字段名
        // 第二个参数为User字段的表名，也可以直接用字符串的形式
        // 第三个参数为内部查询条件
//        query.addWhereRelatedTo("stu", new BmobPointer(post));
        query.addWhereMatchesQuery("stu", "_User", innerQuery);
        query.findObjects(getActivity(), new FindListener<TeacherTaskInfo>() {
            @Override
            public void onSuccess(List<TeacherTaskInfo> infoList) {
                swRefresh.setRefreshing(false);
                if(getUserVisibleHint() && infoList.size() == 0){
                    toast("暂无信息");
                    return;
                }
                for (int i = 0; i < infoList.size(); i++) {
                    Logger.d(infoList.get(i).title);
                }
                adapter.bindDatas(infoList);
            }

            @Override
            public void onError(int i, String s) {
                toast("获取失败");
                swRefresh.setRefreshing(false);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
