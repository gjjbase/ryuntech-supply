package com.ryuntech.admin.biz.service.impl;

import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.ryuntech.admin.api.vo.Sms;
import com.ryuntech.admin.biz.service.MessageSendService;
import org.springframework.stereotype.Service;

import static com.ryuntech.common.constant.SmsConstants.*;

@Service
public class MessageSendServiceImpl implements MessageSendService {

    @Override
    public void sendSms(Sms sms) {
        DefaultProfile profile = DefaultProfile.getProfile(PROFILE, ACCESSKEYID, ACCESSSECRET);
        IAcsClient client = new DefaultAcsClient(profile);

        CommonRequest request = new CommonRequest();
        request.setMethod(MethodType.POST);
        request.setDomain(DOMAIN);
        request.setVersion(VERSION);
        request.setAction(ACTION);
        request.putQueryParameter("RegionId", REGIONID);
        request.putQueryParameter("PhoneNumbers", sms.getPhoneNumbers());
        request.putQueryParameter("SignName", SIGNNAME);
        request.putQueryParameter("TemplateCode", sms.getTemplateCode());
        try {
            CommonResponse response = client.getCommonResponse(request);
            System.out.println(response.getData());
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void querySendDetails() {
        DefaultProfile profile = DefaultProfile.getProfile("default", "<accessKeyId>", "<accessSecret>");
        IAcsClient client = new DefaultAcsClient(profile);

        CommonRequest request = new CommonRequest();
        request.setMethod(MethodType.POST);
        request.setDomain("dysmsapi.aliyuncs.com");
        request.setVersion("2017-05-25");
        request.setAction("QuerySendDetails");
        request.putQueryParameter("RegionId", "default");
        request.putQueryParameter("PhoneNumber", "18518215883");
        request.putQueryParameter("SendDate", "20190813");
        request.putQueryParameter("PageSize", "1");
        request.putQueryParameter("CurrentPage", "10");
        try {
            CommonResponse response = client.getCommonResponse(request);
            System.out.println(response.getData());
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void sendBatchSms() {
        DefaultProfile profile = DefaultProfile.getProfile("default", "<accessKeyId>", "<accessSecret>");
        IAcsClient client = new DefaultAcsClient(profile);

        CommonRequest request = new CommonRequest();
        request.setMethod(MethodType.POST);
        request.setDomain("dysmsapi.aliyuncs.com");
        request.setVersion("2017-05-25");
        request.setAction("SendBatchSms");
        request.putQueryParameter("RegionId", "default");
        request.putQueryParameter("PhoneNumberJson", "{}");
        request.putQueryParameter("SignNameJson", "{}");
        request.putQueryParameter("TemplateCode", "SMS_172120896");
        try {
            CommonResponse response = client.getCommonResponse(request);
            System.out.println(response.getData());
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }
    }
}
