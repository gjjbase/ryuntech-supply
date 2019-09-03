package com.ryuntech.admin.biz.service;

import com.ryuntech.admin.api.vo.Sms;
import com.ryuntech.admin.api.vo.SmsResponse;

/*
用户登录名称 msg@1746465591239783.onaliyun.com
        AccessKey ID LTAI7bXgddnt5tUv
        AccessKeySecret NRiktAo46MpyemRj1Houeh94BhrMH0*/
public interface MessageSendService {
    SmsResponse sendSms(Sms sms);
    void querySendDetails();
    void sendBatchSms();
}
