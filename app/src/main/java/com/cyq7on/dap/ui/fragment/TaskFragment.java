package com.cyq7on.dap.ui.fragment;

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
import com.cyq7on.dap.bean.StudentTaskInfo;
import com.cyq7on.dap.bean.User;
import com.orhanobut.logger.Logger;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.bmob.v3.listener.SaveListener;

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
                studentTaskInfo.stu = User.getCurrentUser(getActivity(), User.class);
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
        rootView = inflater.inflate(R.layout.fragment_task, container, false);
        initNaviView();
        ButterKnife.bind(this, rootView);
        fragments[0] = new StudentTaskFragment();
        fragments[1] = new TeacherTaskFragment();
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
