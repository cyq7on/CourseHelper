package com.cyq7on.coursehelper.ui;

import android.os.Bundle;
import android.text.TextUtils;
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
        String spPwd = (String) SPUtil.get(getApplicationContext(),Constant.PWD,"");
        if(!pwd.equals(spPwd)){
            toast("原密码不正确");
            return;
        }
        toast("修改密码成功");
        SPUtil.putAndApply(getApplicationContext(),Constant.PWD, pwdNew);
        finish();
    }

}
