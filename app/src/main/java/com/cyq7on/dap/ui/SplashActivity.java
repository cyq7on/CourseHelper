package com.cyq7on.dap.ui;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;

import com.cyq7on.dap.base.BaseActivity;
import com.cyq7on.dap.bean.User;
import com.cyq7on.dap.event.FinishEvent;
import com.cyq7on.dap.model.UserModel;
import com.cyq7on.dap.util.SPUtil;
import com.orhanobut.logger.Logger;

import org.greenrobot.eventbus.EventBus;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.LogInListener;

/**启动界面
 * @author :smile
 * @project:SplashActivity
 * @date :2016-01-15-18:23
 */
public class SplashActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.cyq7on.dap.R.layout.activity_splash);
        Handler handler =new Handler(Looper.getMainLooper());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                User user = UserModel.getInstance().getCurrentUser();
                String pwd = (String) SPUtil.get(getApplicationContext(),"pwd","");
                if (user == null || TextUtils.isEmpty(pwd)) {
                    startActivity(LoginActivity.class,null,true);
                }else{
                    Logger.d("pwd:" + pwd);
                    UserModel.getInstance().login(user.getUsername(), pwd, new LogInListener() {
                        @Override
                        public void done(Object o, BmobException e) {
                            if(e == null){
                                EventBus.getDefault().post(new FinishEvent());
                                startActivity(MainActivity.class, null, true);
                            }else {
                                toast("登录失败");
                                startActivity(LoginActivity.class, null, true);
                                Logger.d(e.getMessage());
                            }
                        }
                    });
                }
            }
        },1000);

    }
}
