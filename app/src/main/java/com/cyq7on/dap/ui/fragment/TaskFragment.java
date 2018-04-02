package com.cyq7on.dap.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cyq7on.dap.R;
import com.cyq7on.dap.base.ParentWithNaviActivity;
import com.cyq7on.dap.base.ParentWithNaviFragment;
import com.cyq7on.dap.bean.User;
import com.cyq7on.dap.ui.LookUpTaskActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @Description:
 * @author: cyq7on
 * @date: 18-3-31 下午4:24
 * @version: V1.0
 */
public class TaskFragment extends ParentWithNaviFragment {


    @Bind(R.id.vp)
    ViewPager vp;
    Fragment[] fragments = new Fragment[2];

    @Override
    protected String title() {
        return "已提交任务";
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
                startActivity(new Intent(getActivity(), LookUpTaskActivity.class));
            }
        };
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_task, container, false);
        initNaviView();
        ButterKnife.bind(this, rootView);
        User user = User.getCurrentUser(getActivity(), User.class);
        if(user.getRole() == 0){
            fragments[0] = new StuSendTaskFragment();
            fragments[1] = new StuReceiveTaskFragment();
        }else {
            fragments[0] = new TeacherSendTaskFragment();
            fragments[1] = new TeacherReceiveTaskFragment();
        }


        vp.setAdapter(new FragmentPagerAdapter(getChildFragmentManager()) {
            @Override
            public int getCount() {
                return fragments.length;
            }

            @Override
            public Fragment getItem(int position) {
                return fragments[position];
            }
        });
        vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                tv_title.setText(position == 0 ? "已提交任务" : "已接收任务");
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        return rootView;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
