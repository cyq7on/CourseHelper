package com.cyq7on.dap.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.cyq7on.dap.R;
import com.cyq7on.dap.base.ParentWithNaviFragment;
import com.cyq7on.dap.bean.MySubject;
import com.cyq7on.dap.util.MySubjectModel;
import com.zhuangfei.timetable.core.OnSubjectBindViewListener;
import com.zhuangfei.timetable.core.OnSubjectItemClickListener;
import com.zhuangfei.timetable.core.OnSubjectItemLongClickListener;
import com.zhuangfei.timetable.core.SubjectBean;
import com.zhuangfei.timetable.core.SubjectUtils;
import com.zhuangfei.timetable.core.TimetableView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by cyq7on on 18-3-31.
 */

public class TimetableFragment extends ParentWithNaviFragment implements OnSubjectItemClickListener,
        View.OnClickListener, OnSubjectBindViewListener, OnSubjectItemLongClickListener {

    @Bind(R.id.timetableView)
    TimetableView timetableView;
//    @Bind(R.id.sw_refresh)
//    SwipeRefreshLayout swRefresh;
    private int curWeek = 1;
    private List<SubjectBean> subjectBeans;

    @Override
    protected String title() {
        return "课程表";
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_timetable, container, false);
        initNaviView();
        ButterKnife.bind(this, rootView);
        subjectBeans = transform(MySubjectModel.loadDefaultSubjects());

        timetableView.setDataSource(subjectBeans)
                .setCurTerm("大三上学期")
                .setCurWeek(curWeek)
//                .bindTitleView(mTitleTextView)
                .setOnSubjectBindViewListener(this)
                .setOnSubjectItemClickListener(this)
                .setOnSubjectItemLongClickListener(this)
                .showTimetableView();

        //调用过showSubjectView后需要调用changWeek()
        //第二个参数为true时在改变课表布局的同时也会将第一个参数设置为当前周
        //第二个参数为false时只改变课表布局
        timetableView.changeWeek(curWeek, true);
//        swRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                swRefresh.setRefreshing(false);
//            }
//        });
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    /**
     * 自定义转换规则,将自己的课程对象转换为所需要的对象集合
     *
     * @param mySubjects
     * @return
     */
    public List<SubjectBean> transform(List<MySubject> mySubjects) {
        //待返回的集合
        List<SubjectBean> subjectBeans = new ArrayList<>();

        //保存课程名、颜色的对应关系
        Map<String, Integer> colorMap = new HashMap<>();
        int colorCount = 1;

        //开始转换
        for (int i = 0; i < mySubjects.size(); i++) {
            MySubject mySubject = mySubjects.get(i);
            //计算课程颜色
            int color;
            if (colorMap.containsKey(mySubject.getName())) {
                color = colorMap.get(mySubject.getName());
            } else {
                colorMap.put(mySubject.getName(), colorCount);
                color = colorCount;
                colorCount++;
            }
            //转换
            subjectBeans.add(new SubjectBean(mySubject.getName(), mySubject.getRoom(), mySubject.getTeacher(), mySubject.getWeekList(),
                    mySubject.getStart(), mySubject.getStep(), mySubject.getDay(), color, mySubject.getTime()));
        }
        return subjectBeans;
    }

    protected String showSubjects(List<SubjectBean> beans) {
        String subjectStr="";
        for (SubjectBean bean : beans) {
            subjectStr += bean.getName() + "\n";
            subjectStr += "上课周次:" + bean.getWeekList().toString() + "\n\n";
        }

        return subjectStr;
    }

    //显示周一课程
    protected void showTodaySubjects() {
        //0表示周一，依次类推，6代表周日
        List<SubjectBean> beans = SubjectUtils.getTodaySubjects(subjectBeans, curWeek, 0);
        String subjectStr=showSubjects(beans);
        Toast.makeText(getActivity(), "周一有" + beans.size() + "门课要上\n\n" + subjectStr, Toast.LENGTH_SHORT).show();
    }

    //显示周一所有课程
    protected void showTodayAllSubjects() {
        List<SubjectBean> beans = SubjectUtils.getTodayAllSubjects(subjectBeans, 0);
        String subjectStr=showSubjects(beans);
        Toast.makeText(getActivity(), "周一共有" + beans.size() + "门课\n\n" + subjectStr, Toast.LENGTH_SHORT).show();
    }

    //随机切换周次
    protected void changeWeekByRandom() {
        int week = (int) (Math.random() * 20) + 1;
        timetableView.changeWeek(week, true);
    }

    //重置状态
    protected void resetStatus() {
        curWeek = 1;
        subjectBeans = transform(MySubjectModel.loadDefaultSubjects());
        timetableView.setDataSource(subjectBeans)
                .setCurTerm("大三上学期")
                .setCurWeek(curWeek)
                .showTimetableView();
    }

    //删除课程
    protected void deleteSubject() {
        int pos = (int) (Math.random() * subjectBeans.size());
        if (subjectBeans.size() > 0) {
            subjectBeans.remove(pos);
            timetableView.notifyDataSourceChanged();
        } else {
            Toast.makeText(getActivity(), "没有课程啦！", Toast.LENGTH_SHORT).show();
        }

    }

    //添加课程
    protected void addSubject() {
        int pos = (int) (Math.random() * subjectBeans.size());
        subjectBeans.add(subjectBeans.get(pos));
        timetableView.notifyDataSourceChanged();
    }

    @Override
    public void onClick(View view) {
        
    }

    @Override
    public void onItemLongClick(View view, int day, int start) {

    }

    /**
     * 绑定TitleView，你可以自定义一个文本的填充规则，
     * 当文本需要变化时将由系统回调
     * <p>
     * 当有以下事件时将会触发该函数
     * 1.setCurWeek(...)被调用
     * 2.setCurTerm(...)被调用
     * 3.notifyDataSourceChanged()被调用
     * 4.showTimetableView()被调用
     * 5.changeWeek(...)被调用
     *
     * @param titleTextView 绑定的TextView
     */
    @Override
    public void onBindTitleView(TextView titleTextView, int curWeek, String curTerm, List<SubjectBean> subjectBeans) {
        String text = "第" + curWeek + "周" + ",共" + subjectBeans.size() + "门课";
        titleTextView.setText(text);
        this.curWeek=curWeek;
    }

    @Override
    public void onItemClick(View v, List<SubjectBean> subjectList) {
        int size = subjectList.size();
        String subjectStr = "";

        for (int i = 0; i < size; i++) {
            SubjectBean bean = subjectList.get(i);
            subjectStr += bean.getName() + "\n";
            subjectStr += "上课周次:" + bean.getWeekList().toString() + "\n";
            subjectStr += "时间:周" + bean.getDay() + "," + bean.getStart() + "至" + (bean.getStart() + bean.getStep() - 1) + "节上";
            if (i != (size - 1)) {
                subjectStr += "\n########\n";
            }
        }
        subjectStr += "\n";

        Toast.makeText(getActivity(), "该时段有" + size + "门课\n\n" + subjectStr, Toast.LENGTH_SHORT).show();
    }
}
