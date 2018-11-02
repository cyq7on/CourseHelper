package com.cyq7on.coursehelper.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.cyq7on.coursehelper.R;
import com.cyq7on.coursehelper.base.BaseActivity;
import com.cyq7on.coursehelper.base.ParentWithNaviFragment;
import com.cyq7on.coursehelper.ui.fragment.NewsFragment;
import com.cyq7on.coursehelper.ui.fragment.SetFragment;
import com.cyq7on.coursehelper.ui.fragment.TaskFragment;

import butterknife.Bind;

/**
 * @author :smile
 * @project:MainActivity
 */
public class MainActivity extends BaseActivity{

    @Bind(R.id.btn_conversation)
    Button btn_conversation;
    @Bind(R.id.btn_set)
    Button btn_set;

    @Bind(R.id.btn_contact)
    Button btn_contact;

    @Bind(R.id.iv_conversation_tips)
    ImageView iv_conversation_tips;

    @Bind(R.id.iv_contact_tips)
    ImageView iv_contact_tips;

    private Button[] mTabs;
    private ParentWithNaviFragment timeTableFragment;
    private SetFragment setFragment;
    private ParentWithNaviFragment contactFragment;
    private Fragment[] fragments;
    private int index;
    private int currentTabIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        index = getIntent().getIntExtra("page", 0);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void initView() {
        super.initView();
        mTabs = new Button[3];
        mTabs[0] = btn_conversation;
        mTabs[1] = btn_contact;
        mTabs[2] = btn_set;
        mTabs[0].setSelected(true);
        initTab();
    }

    private void initTab() {
        contactFragment = new TaskFragment();
        timeTableFragment = new NewsFragment();
        setFragment = new SetFragment();
        fragments = new Fragment[]{timeTableFragment, contactFragment, setFragment};
        if(index == 0){
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, timeTableFragment).
                    add(R.id.fragment_container, contactFragment)
                    .add(R.id.fragment_container, setFragment)
                    .hide(setFragment).hide(contactFragment)
                    .show(timeTableFragment).commit();
        }else {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, timeTableFragment).
                    add(R.id.fragment_container, contactFragment)
                    .add(R.id.fragment_container, setFragment)
                    .hide(setFragment).hide(timeTableFragment)
                    .show(contactFragment).commit();
        }

    }

    public void onTabSelect(View view) {
        switch (view.getId()) {
            case R.id.btn_conversation:
                index = 0;
                break;
            case R.id.btn_contact:
                index = 1;
                break;
            case R.id.btn_set:
                index = 2;
                break;
        }
        onTabIndex(index);
    }

    private void onTabIndex(int index) {
        if (currentTabIndex != index) {
            FragmentTransaction trx = getSupportFragmentManager().beginTransaction();
            trx.hide(fragments[currentTabIndex]);
            if (!fragments[index].isAdded()) {
                trx.add(R.id.fragment_container, fragments[index]);
            }
            trx.show(fragments[index]).commit();
        }
        mTabs[currentTabIndex].setSelected(false);
        mTabs[index].setSelected(true);
        currentTabIndex = index;
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

}
