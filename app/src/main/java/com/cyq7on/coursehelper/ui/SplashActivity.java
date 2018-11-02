package com.cyq7on.coursehelper.ui;

import android.os.Bundle;

import com.cyq7on.coursehelper.Constant;
import com.cyq7on.coursehelper.base.BaseActivity;
import com.cyq7on.coursehelper.util.SPUtil;

/**启动界面
 */
public class SplashActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.cyq7on.coursehelper.R.layout.activity_splash);
        boolean isLogin = (boolean) SPUtil.get(getApplicationContext(),Constant.FLAG,false);
        if(isLogin){
            startActivity(MainActivity.class, null, true);
        }else {
            startActivity(LoginActivity.class, null, true);
        }

    }
}
