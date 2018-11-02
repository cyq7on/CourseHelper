package com.cyq7on.coursehelper.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.cyq7on.coursehelper.Constant;
import com.cyq7on.coursehelper.R;
import com.cyq7on.coursehelper.base.ParentWithNaviActivity;
import com.cyq7on.coursehelper.util.SPUtil;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 注册界面
 *
 */
public class RegisterActivity extends ParentWithNaviActivity {

    @Bind(R.id.et_username)
    EditText et_username;
    @Bind(R.id.et_password)
    EditText et_password;
    @Bind(R.id.btn_register)
    Button btn_register;

    @Bind(R.id.et_password_again)
    EditText et_password_again;
    @Bind(R.id.et_age)
    EditText etAge;


    @Override
    protected String title() {
        return "注册";
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        initNaviView();
    }

    @Override
    protected void initView() {
        super.initView();
    }


    @OnClick(R.id.btn_register)
    public void onRegisterClick(View view) {
        String pwd = et_password.getText().toString();
        String pwdAgain = et_password_again.getText().toString();
        if(!pwd.equals(pwdAgain)){
            toast("两次密码不一致，请重新输入！");
            return;
        }
        SPUtil.putAndApply(getApplicationContext(),Constant.NAME,et_username.getText().toString());
        SPUtil.putAndApply(getApplicationContext(),Constant.PWD, pwd);
        SPUtil.putAndApply(getApplicationContext(),Constant.AGE,etAge.getText().toString());
        SPUtil.putAndApply(getApplicationContext(),Constant.FLAG,true);
        startActivity(MainActivity.class, null, true);
    }

}
