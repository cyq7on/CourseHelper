package com.cyq7on.coursehelper.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cyq7on.coursehelper.Constant;
import com.cyq7on.coursehelper.R;
import com.cyq7on.coursehelper.base.ParentWithNaviFragment;
import com.cyq7on.coursehelper.ui.EditPwdActivity;
import com.cyq7on.coursehelper.ui.LoginActivity;
import com.cyq7on.coursehelper.ui.UserInfoActivity;
import com.cyq7on.coursehelper.util.SPUtil;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**设置
 */
public class SetFragment extends ParentWithNaviFragment {

    @Bind(R.id.tv_set_name)
    TextView tv_set_name;

    @Bind(R.id.layout_info)
    RelativeLayout layout_info;
    @Bind(R.id.rl_pwd)
    RelativeLayout rl_pwd;

    @Override
    protected String title() {
        return "用户";
    }

    public static SetFragment newInstance() {
        SetFragment fragment = new SetFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public SetFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView= inflater.inflate(R.layout.fragment_set,container, false);
        initNaviView();
        ButterKnife.bind(this, rootView);
        return rootView;
    }


    @Override
    public void onStart() {
        super.onStart();
        String username = (String) SPUtil.get(getActivity(),Constant.NAME,"");
        tv_set_name.setText(TextUtils.isEmpty(username)?"":username);
    }

    @OnClick(R.id.layout_info)
    public void onInfoClick(View view){
        Bundle bundle = new Bundle();
        startActivity(UserInfoActivity.class,bundle);
    }
    @OnClick(R.id.rl_pwd)
    public void onPwdClick(View view){
        startActivity(EditPwdActivity.class,null);
    }

    @OnClick(R.id.btn_logout)
    public void onLogoutClick(View view){
        FragmentActivity activity = getActivity();
        SPUtil.putAndApply(activity,Constant.FLAG,false);
        activity.finish();
        startActivity(LoginActivity.class,null);
    }
}
