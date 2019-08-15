package com.ryuntech.admin.biz.service;

/*
用户登录名称 msg@1746465591239783.onaliyun.com
        AccessKey ID LTAI7bXgddnt5tUv
        AccessKeySecret NRiktAo46MpyemRj1Houeh94BhrMH0*/
public interface MessageSendService {
    void sendSms();
    void querySendDetails();
    void sendBatchSms();
}
