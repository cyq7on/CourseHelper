package com.cyq7on.coursehelper.ui;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.cyq7on.coursehelper.R;
import com.cyq7on.coursehelper.base.ParentWithNaviActivity;
import com.cyq7on.coursehelper.util.SPUtil;
import com.orhanobut.logger.Logger;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.UpdateListener;

/**
 * @Description: 修改密码
 * @author: cyq7on
 * @date: 18-3-23 下午8:04
 * @version: V1.0
 */
public class EditPwdActivity extends ParentWithNaviActivity {

    @Bind(R.id.et_password)
    EditText etPassword;
    @Bind(R.id.et_password_new)
    EditText etPasswordNew;
    @Bind(R.id.btn_edit_pwd)
    Button btnEditPwd;

    @Override
    protected String title() {
        return getString(R.string.set_pwd);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_pwd);
        ButterKnife.bind(this);
        initNaviView();
    }

    @OnClick(R.id.btn_edit_pwd)
    public void onClick(View view) {
        String pwd = etPassword.getText().toString();
        final String pwdNew = etPasswordNew.getText().toString();
        if(TextUtils.isEmpty(pwd)){
            toast("请输入原密码");
            return;
        }
        if(TextUtils.isEmpty(pwdNew)){
            toast("请输入新密码");
            return;
        }
        BmobUser.updateCurrentUserPassword(getApplicationContext(), pwd, pwdNew, new UpdateListener() {
            @Override
            public void onSuccess() {
                toast("修改密码成功");
                SPUtil.putAndApply(getApplicationContext(),"pwd", pwdNew);
                finish();
            }

            @Override
            public void onFailure(int i, String s) {
                toast("修改密码失败");
                Logger.d(i + s);
            }
        });
    }

}
