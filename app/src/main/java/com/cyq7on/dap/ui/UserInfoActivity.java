package com.cyq7on.dap.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.cyq7on.dap.R;
import com.cyq7on.dap.base.ImageLoaderFactory;
import com.cyq7on.dap.base.ParentWithNaviActivity;
import com.cyq7on.dap.bean.AddFriendMessage;
import com.cyq7on.dap.bean.User;
import com.orhanobut.logger.Logger;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.OnClick;
import cn.bmob.newim.BmobIM;
import cn.bmob.newim.bean.BmobIMConversation;
import cn.bmob.newim.bean.BmobIMMessage;
import cn.bmob.newim.bean.BmobIMUserInfo;
import cn.bmob.newim.core.BmobIMClient;
import cn.bmob.newim.listener.MessageSendListener;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

/**
 * 用户资料
 */
public class UserInfoActivity extends ParentWithNaviActivity {

    @Bind(com.cyq7on.dap.R.id.iv_avator)
    ImageView iv_avator;
    @Bind(com.cyq7on.dap.R.id.tv_name)
    TextView tv_name;

    @Bind(R.id.tv_sex)
    TextView tv_sex;
    @Bind(R.id.tv_age)
    EditText tv_age;
    @Bind(R.id.tv_other)
    EditText tv_other;
    @Bind(R.id.tv_other_title)
    TextView tv_other_title;

//    @Bind(com.cyq7on.dap.R.id.btn_add_friend)
//    Button btn_add_friend;
    @Bind(com.cyq7on.dap.R.id.btn_chat)
    Button btn_chat;

    @Bind(R.id.btn_edit)
    Button btn_edit;

    User user;
    BmobIMUserInfo info;
    @Override
    protected String title() {
        return "个人资料";
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.cyq7on.dap.R.layout.activity_user_info);
        initNaviView();
        user=(User)getBundle().getSerializable("u");
        if(user.getObjectId().equals(getCurrentUid())){
//            btn_add_friend.setVisibility(View.GONE);
            btn_chat.setVisibility(View.GONE);
            btn_edit.setVisibility(View.VISIBLE);
        }else{
//            btn_add_friend.setVisibility(View.VISIBLE);
            btn_chat.setVisibility(View.VISIBLE);
            btn_edit.setVisibility(View.GONE);
        }
        //构造聊天方的用户信息:传入用户id、用户名和用户头像三个参数
        info = new BmobIMUserInfo(user.getObjectId(),user.getUsername(),user.getAvatar());
        ImageLoaderFactory.getLoader().loadAvator(iv_avator,user.getAvatar(), com.cyq7on.dap.R.mipmap.head);
        tv_name.setText(user.getUsername());
        tv_sex.setText(user.getSex() == 0 ? "男" : "女");
        tv_age.setText(user.getAge());
    }

//    @OnClick(com.cyq7on.dap.R.id.btn_add_friend)
//    public void onAddClick(View view){
//        sendAddFriendMessage();
//    }

    /**
     * 发送添加好友的请求
     */
    private void sendAddFriendMessage(){
        //启动一个会话，如果isTransient设置为true,则不会创建在本地会话表中创建记录，
        //设置isTransient设置为false,则会在本地数据库的会话列表中先创建（如果没有）与该用户的会话信息，且将用户信息存储到本地的用户表中
        BmobIMConversation c = BmobIM.getInstance().startPrivateConversation(info, true,null);
        //这个obtain方法才是真正创建一个管理消息发送的会话
        BmobIMConversation conversation = BmobIMConversation.obtain(BmobIMClient.getInstance(), c);
        AddFriendMessage msg =new AddFriendMessage();
        User currentUser = BmobUser.getCurrentUser(this,User.class);
        msg.setContent("很高兴认识你，可以加个好友吗?");//给对方的一个留言信息
        Map<String,Object> map =new HashMap<>();
        map.put("name", currentUser.getUsername());//发送者姓名，这里只是举个例子，其实可以不需要传发送者的信息过去
        map.put("avatar",currentUser.getAvatar());//发送者的头像
        map.put("uid",currentUser.getObjectId());//发送者的uid
        msg.setExtraMap(map);
        conversation.sendMessage(msg, new MessageSendListener() {
            @Override
            public void done(BmobIMMessage msg, BmobException e) {
                if (e == null) {//发送成功
                    toast("好友请求发送成功，等待验证");
                } else {//发送失败
                    toast("发送失败:" + e.getMessage());
                }
            }
        });
    }

    @OnClick(com.cyq7on.dap.R.id.btn_chat)
    public void onChatClick(View view){
        //启动一个会话，设置isTransient设置为false,则会在本地数据库的会话列表中先创建（如果没有）与该用户的会话信息，且将用户信息存储到本地的用户表中
        BmobIMConversation c = BmobIM.getInstance().startPrivateConversation(info,false,null);
        Bundle bundle = new Bundle();
        bundle.putSerializable("c", c);
        startActivity(ChatActivity.class, bundle, false);
    }

    @OnClick(R.id.btn_edit)
    public void onEditClick(View view){
        user.setUsername(tv_name.getText().toString());
        user.setAge(tv_age.getText().toString());

        user.update(getApplicationContext(),user.getObjectId(), new UpdateListener() {
            @Override
            public void onSuccess() {
                toast("修改资料成功");
                finish();
            }

            @Override
            public void onFailure(int i, String s) {
                toast("修改资料失败");
                Logger.d(i + "\n" +s);
            }
        });
    }

}
