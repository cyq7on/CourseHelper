package com.cyq7on.dap.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.cyq7on.dap.R;
import com.cyq7on.dap.adapter.DepAndDoctorAdapter;
import com.cyq7on.dap.base.ParentWithNaviFragment;
import com.cyq7on.dap.bean.FatherData;
import com.cyq7on.dap.bean.User;
import com.cyq7on.dap.model.UserModel;
import com.cyq7on.dap.ui.UserInfoActivity;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by cyq7on on 18-3-23.
 */

public class DepAndDoctorFragment extends ParentWithNaviFragment {
    @Bind(R.id.expand_list)
    ExpandableListView expandList;
    @Bind(R.id.sw_refresh)
    SwipeRefreshLayout swRefresh;
    private ArrayList<FatherData> list = new ArrayList<>();
    private DepAndDoctorAdapter adapter;

    @Override
    protected String title() {
        return "医生";
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new DepAndDoctorAdapter(getActivity(),list);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_contact_dep, container, false);
        ButterKnife.bind(this, rootView);
        /*new Thread(new Runnable() {
            @Override
            public void run() {
                createData();
            }
        }).start();*/
        createData();
        expandList.setAdapter(adapter);
        expandList.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int i, int i1, long l) {
                Bundle bundle = new Bundle();
                User user = adapter.getChild(i,i1);
                bundle.putSerializable("u", user);
                startActivity(UserInfoActivity.class, bundle);
                return true;
            }
        });
        expandList.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView expandableListView, View view, int i, long l) {
                if(adapter.getChildrenCount(i) == 0){
                    toast("该科室暂时没有医生信息");
                    return true;
                }
                return false;
            }
        });
        swRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                createData();
            }
        });
        return rootView;
    }

    private void createData() {
        swRefresh.setRefreshing(true);
        for (int i = 0; i < DepAndDoctorAdapter.dep.length; i++) {
            FatherData fatherData = new FatherData();
            fatherData.setList(new ArrayList<User>());
            fatherData.setTitle(DepAndDoctorAdapter.dep[i]);
            list.add(fatherData);
        }
        UserModel.getInstance().queryUsers("", 1000, new FindListener<User>() {
            @Override
            public void onSuccess(List<User> users) {
                for (User user : users) {
                    Logger.d(user.getUsername());

                }
                swRefresh.setRefreshing(false);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onError(int i, String s) {
                Logger.d(i + s);
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
