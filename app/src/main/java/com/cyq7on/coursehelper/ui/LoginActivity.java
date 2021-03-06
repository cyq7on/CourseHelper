package com.cyq7on.coursehelper.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.cyq7on.coursehelper.R;
import com.cyq7on.coursehelper.base.BaseActivity;
import com.cyq7on.coursehelper.bean.User;
import com.cyq7on.coursehelper.event.FinishEvent;
import com.cyq7on.coursehelper.model.UserModel;
import com.cyq7on.coursehelper.util.SPUtil;

import org.greenrobot.eventbus.Subscribe;

import butterknife.Bind;
import butterknife.OnClick;
import cn.bmob.newim.BmobIM;
import cn.bmob.newim.bean.BmobIMUserInfo;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.LogInListener;

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
        final String pwd = et_password.getText().toString();
        UserModel.getInstance().login(et_username.getText().toString(), pwd, new LogInListener() {

            @Override
            public void done(Object o, BmobException e) {
                if (e == null) {
                    SPUtil.putAndApply(getApplicationContext(),"pwd", pwd);
                    User user =(User)o;
                    BmobIM.getInstance().updateUserInfo(new BmobIMUserInfo(user.getObjectId(), user.getUsername(), user.getAvatar()));
                    startActivity(MainActivity.class, null, true);
                } else {
                    toast(e.getMessage() + "(" + e.getErrorCode() + ")");
                }
            }
        });
    }

    @OnClick(R.id.tv_register)
    public void onRegisterClick(View view){
        startActivity(RegisterActivity.class, null, false);
    }

    @Subscribe
    public void onEventMainThread(FinishEvent event){
        finish();
    }
}
