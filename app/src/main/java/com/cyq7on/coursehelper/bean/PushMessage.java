package com.cyq7on.coursehelper.bean;

import cn.bmob.newim.bean.BmobIMExtraMessage;

/**
 * Created by cyq7on on 18-4-3.
 * 推送有坑，利用消息通道实现推送
 */

public class PushMessage extends BmobIMExtraMessage {
    @Override
    public String getMsgType() {
        return "push";
    }
}
