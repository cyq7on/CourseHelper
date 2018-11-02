package com.cyq7on.coursehelper.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.cyq7on.coursehelper.Constant;
import com.cyq7on.coursehelper.R;
import com.cyq7on.coursehelper.base.ImageLoaderFactory;
import com.cyq7on.coursehelper.base.ParentWithNaviActivity;
import com.cyq7on.coursehelper.util.SPUtil;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 用户资料
 */
public class UserInfoActivity extends ParentWithNaviActivity {

    @Bind(com.cyq7on.coursehelper.R.id.iv_avator)
    ImageView iv_avator;
    @Bind(com.cyq7on.coursehelper.R.id.tv_name)
    TextView tv_name;

    @Bind(R.id.tv_age)
    EditText tv_age;

    @Bind(R.id.btn_edit)
    Button btn_edit;

    @Override
    protected String title() {
        return "个人资料";
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        initNaviView();
        ImageLoaderFactory.getLoader().loadAvator(iv_avator, null, com.cyq7on.coursehelper.R.mipmap.head);
        String name = (String) SPUtil.get(getApplicationContext(),Constant.NAME,"");
        String age = (String) SPUtil.get(getApplicationContext(),Constant.AGE,"");
        tv_name.setText(name);
        tv_age.setText(age);
    }


    @OnClick(R.id.btn_edit)
    public void onEditClick(View view) {
        SPUtil.putAndApply(getApplicationContext(),Constant.NAME,tv_name.getText().toString());
        SPUtil.putAndApply(getApplicationContext(),Constant.AGE,tv_age.getText().toString());
        toast("修改资料成功");
        finish();
    }

}
