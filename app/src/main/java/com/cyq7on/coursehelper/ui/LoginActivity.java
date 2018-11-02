package com.cyq7on.coursehelper.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.cyq7on.coursehelper.Constant;
import com.cyq7on.coursehelper.R;
import com.cyq7on.coursehelper.base.BaseActivity;
import com.cyq7on.coursehelper.util.SPUtil;

import butterknife.Bind;
import butterknife.OnClick;

/**登陆界面
 * @author :smile
 * @project:LoginActivity
 * @date :2016-01-15-18:23
 */
public class LoginActivity extends BaseActivity {

    @Bind(R.id.et_username)
    EditText et_username;
    @Bind(R.id.et_password)
    EditText et_password;
    @Bind(R.id.btn_login)
    Button btn_login;
    @Bind(R.id.tv_register)
    TextView tv_register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    @OnClick(R.id.btn_login)
    public void onLoginClick(View view){
        String name = et_username.getText().toString();
        String pwd = et_password.getText().toString();
        String spName = (String) SPUtil.get(getApplicationContext(),Constant.NAME,"");
        String spPwd = (String) SPUtil.get(getApplicationContext(),Constant.PWD,"");
        if(name.equals(spName) && pwd.equals(spPwd)){
            startActivity(MainActivity.class, null, true);
            SPUtil.putAndApply(getApplicationContext(),Constant.FLAG,true);
        }else {
            toast("账号或密码错误");
        }
    }

    @OnClick(R.id.tv_register)
    public void onRegisterClick(View view){
        startActivity(RegisterActivity.class, null, false);
    }

}
