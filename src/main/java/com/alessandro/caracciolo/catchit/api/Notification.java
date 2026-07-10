package com.alessandro.caracciolo.catchit.api;

import com.alessandro.caracciolo.catchit.bean.RiderBean;

public interface Notification {
    void sendNotification(RiderBean rider, String message);
}
