package com.cyq7on.dap.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

import com.cyq7on.dap.R;
import com.cyq7on.dap.adapter.OnRecyclerViewListener;
import com.cyq7on.dap.adapter.StudentTaskAdapter;
import com.cyq7on.dap.adapter.base.IMutlipleItem;
import com.cyq7on.dap.base.ParentWithNaviFragment;
import com.cyq7on.dap.bean.StudentTaskInfo;
import com.orhanobut.logger.Logger;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by cyq7on on 18-3-31.
 */

public class TeacherTaskFragment extends ParentWithNaviFragment {
    @Bind(R.id.rc_view)
    RecyclerView rcView;
    @Bind(R.id.sw_refresh)
    SwipeRefreshLayout swRefresh;
    StudentTaskAdapter adapter;

    @Override
    protected String title() {
        return "";
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_task_list, container, false);
        ButterKnife.bind(this, rootView);
//        initNaviView();
        IMutlipleItem<StudentTaskInfo> iMutlipleItem = new IMutlipleItem<StudentTaskInfo>() {
            @Override
            public int getItemLayoutId(int viewtype) {
                return R.layout.item_contact;
            }

            @Override
            public int getItemViewType(int postion, StudentTaskInfo info) {
                return 0;
            }

            @Override
            public int getItemCount(List<StudentTaskInfo> list) {
                return list.size();
            }
        };
        adapter = new StudentTaskAdapter(getActivity(),iMutlipleItem,null);
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
                /*User owner = adapter.getItem(position);
                BmobIMUserInfo info = new BmobIMUserInfo(owner.getObjectId(), owner.getUsername(), owner.getAvatar());
                //启动一个会话，实际上就是在本地数据库的会话列表中先创建（如果没有）与该用户的会话信息，且将用户信息存储到本地的用户表中
                BmobIMConversation c = BmobIM.getInstance().startPrivateConversation(info, null);
                Bundle bundle = new Bundle();
                bundle.putSerializable("c", c);
                startActivity(ChatActivity.class, bundle);

                Bundle bundle = new Bundle();
                User user = adapter.getItem(position);
                bundle.putSerializable("u", user);
                startActivity(UserInfoActivity.class, bundle);*/
            }

            @Override
            public boolean onItemLongClick(final int position) {
                return false;
            }
        });
    }

    private void query() {
        BmobQuery<StudentTaskInfo> query = new BmobQuery<>();
//        query.addWhereContains("username", username);
        query.setLimit(100);
        query.order("-createdAt");
//        query.include("");
        query.findObjects(getActivity(), new FindListener<StudentTaskInfo>() {
            @Override
            public void onSuccess(List<StudentTaskInfo> infoList) {
                for (int i = 0; i < infoList.size(); i++) {
                    Logger.d(infoList.get(i).title);
                }
                adapter.bindDatas(infoList);
                swRefresh.setRefreshing(false);
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
