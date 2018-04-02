package com.cyq7on.dap.ui.fragment;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.cyq7on.dap.R;
import com.cyq7on.dap.adapter.OnRecyclerViewListener;
import com.cyq7on.dap.adapter.UserAdapter;
import com.cyq7on.dap.adapter.base.IMutlipleItem;
import com.cyq7on.dap.bean.User;
import com.cyq7on.dap.event.UserEvent;
import com.cyq7on.dap.model.UserModel;
import com.orhanobut.logger.Logger;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by cyq7on on 18-4-2.
 */

public class ReceiverFragment extends DialogFragment {

    @Bind(R.id.rc_view)
    RecyclerView rcView;
    private UserAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_receiver_list, container, false);
        ButterKnife.bind(this, view);
        IMutlipleItem<User> iMutlipleItem = new IMutlipleItem<User>() {
            @Override
            public int getItemLayoutId(int viewtype) {
                return R.layout.item_contact;
            }

            @Override
            public int getItemViewType(int postion, User info) {
                return 0;
            }

            @Override
            public int getItemCount(List<User> list) {
                return list.size();
            }
        };
        adapter = new UserAdapter(getActivity(),iMutlipleItem,null);
        rcView.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        rcView.setLayoutManager(layoutManager);
        adapter.setOnRecyclerViewListener(new OnRecyclerViewListener() {
            @Override
            public void onItemClick(int position) {
                UserEvent event = new UserEvent(adapter.getItem(position));
                EventBus.getDefault().post(event);
                dismiss();
            }

            @Override
            public boolean onItemLongClick(int position) {
                return false;
            }
        });
        //查询教师
        UserModel.getInstance().queryUsers("role", 1, 100, new FindListener<User>() {
            @Override
            public void onSuccess(List<User> list) {
                if(list.size() == 0){
                    Toast.makeText(getActivity(),"当前没有可选教师",Toast.LENGTH_SHORT).show();
                    return;
                }
                adapter.bindDatas(list);
            }

            @Override
            public void onError(int i, String s) {
                Toast.makeText(getActivity(),"教师信息获取失败",Toast.LENGTH_SHORT).show();
                Logger.d(i + s);
            }
        });
        Logger.d("view");
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
