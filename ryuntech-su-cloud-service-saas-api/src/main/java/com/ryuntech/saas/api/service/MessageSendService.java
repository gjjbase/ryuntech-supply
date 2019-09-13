package com.ryuntech.saas.api.service;


import com.ryuntech.saas.api.dto.Sms;
import com.ryuntech.saas.api.dto.SmsResponse;

/*
用户登录名称 msg@1746465591239783.onaliyun.com
        AccessKey ID LTAI7bXgddnt5tUv
        AccessKeySecret NRiktAo46MpyemRj1Houeh94BhrMH0*/
public interface MessageSendService {
    SmsResponse sendSms(Sms sms);
    void querySendDetails();
    void sendBatchSms();
}
