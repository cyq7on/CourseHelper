package com.cyq7on.coursehelper.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cyq7on.coursehelper.R;
import com.cyq7on.coursehelper.base.ParentWithNaviActivity;
import com.cyq7on.coursehelper.bean.PushMessage;
import com.cyq7on.coursehelper.bean.StudentTaskInfo;
import com.cyq7on.coursehelper.bean.TeacherTaskInfo;
import com.cyq7on.coursehelper.bean.User;
import com.cyq7on.coursehelper.event.UserEvent;
import com.cyq7on.coursehelper.model.UserModel;
import com.cyq7on.coursehelper.ui.fragment.ReceiverFragment;
import com.google.gson.Gson;
import com.orhanobut.logger.Logger;

import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.newim.BmobIM;
import cn.bmob.newim.bean.BmobIMConversation;
import cn.bmob.newim.bean.BmobIMMessage;
import cn.bmob.newim.bean.BmobIMUserInfo;
import cn.bmob.newim.core.BmobIMClient;
import cn.bmob.newim.listener.MessageSendListener;
import cn.bmob.v3.datatype.BmobRelation;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by cyq7on on 18-4-2.
 */

public class LookUpTaskActivity extends ParentWithNaviActivity {
    @Bind(R.id.tvTitle)
    EditText tvTitle;
    @Bind(R.id.tvContent)
    EditText tvContent;
    @Bind(R.id.tvScore)
    EditText tvScore;
    @Bind(R.id.btn_publish)
    Button btnPublish;
    @Bind(R.id.btn_edit)
    Button btnEdit;
    @Bind(R.id.rlScore)
    RelativeLayout rlScore;
    @Bind(R.id.tvTarget)
    TextView tvTarget;
    @Bind(R.id.rlTarget)
    RelativeLayout rlTarget;
    private StudentTaskInfo studentTaskInfo;
    private TeacherTaskInfo teacherTaskInfo;

    @Override
    protected String title() {
        return "提交任务";
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish);
        ButterKnife.bind(this);
        initNaviView();
        /*rlTarget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ReceiverFragment receiverFragment = new ReceiverFragment();
                receiverFragment.show(getFragmentManager(), ReceiverFragment.class.getSimpleName());
            }
        });*/
        Bundle bundle = getBundle();
        //新建任务
        if (bundle == null) {
            btnEdit.setVisibility(View.GONE);
            rlScore.setVisibility(View.GONE);
            if (user.getRole() == 0) {

            } else {
                rlTarget.setVisibility(View.GONE);
            }
        } else {
            btnPublish.setVisibility(View.GONE);
            if (user.getRole() == 0) {
                //学生查看教师任务
                if (bundle.getBoolean("flag")) {
                    tvTitle.setEnabled(false);
                    tvContent.setEnabled(false);
                    teacherTaskInfo = (TeacherTaskInfo) bundle.getSerializable("info");
                    tvTitle.setText(teacherTaskInfo.title);
                    rlScore.setVisibility(View.GONE);
                    tvContent.setText(teacherTaskInfo.content);
                    btnEdit.setVisibility(View.GONE);
                    rlTarget.setVisibility(View.GONE);
                } else {
                    studentTaskInfo = (StudentTaskInfo) bundle.getSerializable("info");
                    tvScore.setEnabled(false);
                    tvTitle.setText(studentTaskInfo.title);
                    tvScore.setText(studentTaskInfo.score);
                    tvContent.setText(studentTaskInfo.content);
                    tvTarget.setText(studentTaskInfo.teacher.getUsername());
                }

            } else {
                rlTarget.setVisibility(View.GONE);
                //教师批阅学生作业
                if (bundle.getBoolean("flag")) {
                    studentTaskInfo = (StudentTaskInfo) bundle.getSerializable("info");
                    tvTitle.setText(studentTaskInfo.title);
                    tvScore.setText(studentTaskInfo.score);
                    tvContent.setText(studentTaskInfo.content);
                } else {//教师查看自己发布的任务
                    teacherTaskInfo = (TeacherTaskInfo) bundle.getSerializable("info");
                    tvTitle.setText(teacherTaskInfo.title);
                    rlScore.setVisibility(View.GONE);
                    tvContent.setText(teacherTaskInfo.content);
                }

            }
        }

    }


    @Subscribe
    public void onEventMainThread(UserEvent userEvent) {
        Logger.d(userEvent.user.toString());
        if (studentTaskInfo == null) {
            studentTaskInfo = new StudentTaskInfo();
        }
        studentTaskInfo.teacher = userEvent.user;
        tvTarget.setText(userEvent.user.getUsername());
    }

    @OnClick(R.id.btn_publish)
    public void onPublishClick(View view) {
        if (user.getRole() == 0) {
            if (studentTaskInfo == null) {
                studentTaskInfo = new StudentTaskInfo();
            }
            studentTaskInfo.title = tvTitle.getText().toString();
            studentTaskInfo.content = tvContent.getText().toString();
            studentTaskInfo.score = tvScore.getText().toString();
            studentTaskInfo.stu = user;
            studentTaskInfo.save(getApplicationContext(), new SaveListener() {
                @Override
                public void onSuccess() {
                    toast("发布成功");
                    finish();
                }

                @Override
                public void onFailure(int i, String s) {
                    toast("发布失败");
                    Logger.d(i + s);
                }
            });

        } else {
            if (teacherTaskInfo == null) {
                teacherTaskInfo = new TeacherTaskInfo();
            }
            teacherTaskInfo.title = tvTitle.getText().toString();
            teacherTaskInfo.content = tvContent.getText().toString();
            teacherTaskInfo.teacher = user;
            UserModel.getInstance().queryUsers("role", 0, 100, new FindListener<User>() {
                @Override
                public void onSuccess(final List<User> list) {
                    BmobRelation relation = new BmobRelation();
                    for (User user : list) {
                        relation.add(user);
                    }
                    teacherTaskInfo.stu = relation;
                    teacherTaskInfo.save(getApplicationContext(), new SaveListener() {
                        @Override
                        public void onSuccess() {
                            toast("发布成功");
                            String json = new Gson().toJson(teacherTaskInfo);
                            for (User user : list) {
                                sendMsg(json, user);
                            }
                            finish();
                        }

                        @Override
                        public void onFailure(int i, String s) {
                            toast("发布失败");
                            Logger.d(i + s);
                        }
                    });
                }

                @Override
                public void onError(int i, String s) {
                    toast("发布失败");
                    Logger.d(i + s);
                }
            });
        }
    }

    private void sendMsg(String content, User user) {
        BmobIMUserInfo info = new BmobIMUserInfo(user.getObjectId(), user.getUsername(), user.getAvatar());
        //启动一个会话，如果isTransient设置为true,则不会创建在本地会话表中创建记录，
        //设置isTransient设置为false,则会在本地数据库的会话列表中先创建（如果没有）与该用户的会话信息，且将用户信息存储到本地的用户表中
        BmobIMConversation c = BmobIM.getInstance().startPrivateConversation(info, true, null);
        //这个obtain方法才是真正创建一个管理消息发送的会话
        BmobIMConversation conversation = BmobIMConversation.obtain(BmobIMClient.getInstance(), c);
        PushMessage msg = new PushMessage();
        msg.setContent(content);
       /* //可设置额外信息
        Map<String,Object> map =new HashMap<>();
        map.put("level", "1");//随意增加信息
        msg.setExtraMap(map);*/
        conversation.sendMessage(msg, new MessageSendListener() {
            @Override
            public void done(BmobIMMessage bmobIMMessage, BmobException e) {
                if (e == null) {
                    Logger.d("send success:" + bmobIMMessage.getContent());
                    return;
                }
                Logger.d(e.getMessage() + e.getErrorCode());
            }
        });
    }

    @OnClick(R.id.btn_edit)
    public void onUpdateClick(View view) {
        if (studentTaskInfo != null) {
            studentTaskInfo.title = tvTitle.getText().toString();
            studentTaskInfo.content = tvContent.getText().toString();
            studentTaskInfo.score = tvScore.getText().toString();
            studentTaskInfo.update(getApplicationContext(), new UpdateListener() {
                @Override
                public void onSuccess() {
                    toast("更新成功");
                    finish();
                }

                @Override
                public void onFailure(int i, String s) {
                    toast("更新失败");
                    Logger.d(i + s);
                }
            });
        } else if (teacherTaskInfo != null) {
            teacherTaskInfo.title = tvTitle.getText().toString();
            teacherTaskInfo.content = tvContent.getText().toString();
            teacherTaskInfo.update(getApplicationContext(), new UpdateListener() {
                @Override
                public void onSuccess() {
                    toast("更新成功");
                    finish();
                }

                @Override
                public void onFailure(int i, String s) {
                    toast("更新失败");
                    Logger.d(i + s);
                }
            });
        }
    }

    @OnClick(R.id.rlTarget)
    public void onSelectClick(View view) {
        new ReceiverFragment().show(getFragmentManager(), ReceiverFragment.class.getSimpleName());
        ;
    }

}
