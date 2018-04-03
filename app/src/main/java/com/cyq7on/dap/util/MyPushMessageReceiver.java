package com.cyq7on.dap.util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.orhanobut.logger.Logger;

import java.util.Objects;

import cn.bmob.push.PushConstants;

/**
 * Created by cyq7on on 18-4-3.
 */

public class MyPushMessageReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO Auto-generated method stub
        if(PushConstants.ACTION_MESSAGE.equals(intent.getAction())){
            Logger.d("客户端收到推送内容："+intent.getStringExtra("msg"));
        }
    }

}
