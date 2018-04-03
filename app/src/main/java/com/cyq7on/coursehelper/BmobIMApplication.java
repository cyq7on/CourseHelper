package com.cyq7on.coursehelper;

import android.app.Application;

import com.cyq7on.coursehelper.base.UniversalImageLoader;
import com.orhanobut.logger.Logger;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import cn.bmob.newim.BmobIM;
import cn.bmob.push.BmobPush;

/**
 * @author :smile
 * @project:BmobIMApplication
 * @date :2016-01-13-10:19
 */
public class BmobIMApplication extends Application{

    private static BmobIMApplication INSTANCE;
    public static BmobIMApplication INSTANCE(){
        return INSTANCE;
    }
    private void setInstance(BmobIMApplication app) {
        setBmobIMApplication(app);
    }
    private static void setBmobIMApplication(BmobIMApplication a) {
        BmobIMApplication.INSTANCE = a;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        setInstance(this);
        //初始化
        Logger.init("smile");
        //只有主进程运行的时候才需要初始化
        if (getApplicationInfo().packageName.equals(getMyProcessName())){
            //im初始化
            BmobIM.init(this);
            //注册消息接收器
            BmobIM.registerDefaultMessageHandler(new DemoMessageHandler(this));
            /*BmobInstallationManager.getInstance().initialize(new InstallationListener<BmobInstallation>() {
                @Override
                public void done(BmobInstallation bmobInstallation, BmobException e) {
                    if (e == null) {
                        Logger.i(bmobInstallation.getObjectId() + "-" + bmobInstallation.getInstallationId());
                    } else {
                        Logger.e(e.getMessage());
                    }
                }
            });*/
            // 启动推送服务
            BmobPush.startWork(this);
        }
        //uil初始化
        UniversalImageLoader.initImageLoader(this);
    }

    /**
     * 获取当前运行的进程名
     * @return
     */
    public static String getMyProcessName() {
        try {
            File file = new File("/proc/" + android.os.Process.myPid() + "/" + "cmdline");
            BufferedReader mBufferedReader = new BufferedReader(new FileReader(file));
            String processName = mBufferedReader.readLine().trim();
            mBufferedReader.close();
            return processName;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
