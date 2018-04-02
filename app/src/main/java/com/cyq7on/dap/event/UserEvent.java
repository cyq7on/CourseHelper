package com.cyq7on.dap.event;

import com.cyq7on.dap.bean.User;

/**
 * Created by cyq7on on 18-4-2.
 */

public class UserEvent {
    public User user;

    public UserEvent(User user) {
        this.user = user;
    }
}
