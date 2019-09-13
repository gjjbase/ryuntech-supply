package com.ryuntech.saas.service.impl;

import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.google.gson.Gson;
import com.ryuntech.saas.api.dto.Sms;
import com.ryuntech.saas.api.dto.SmsResponse;
import com.ryuntech.saas.api.service.MessageSendService;
import org.springframework.stereotype.Service;

import static com.ryuntech.saas.api.helper.constant.SmsConstants.*;


@Service
public class MessageSendServiceImpl implements MessageSendService {

    @Override
    public SmsResponse sendSms(Sms sms) {
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
        request.putQueryParameter("TemplateCode", TEMPLATECODE);
        request.putQueryParameter("TemplateParam", sms.getContent());

        try {
            CommonResponse response = client.getCommonResponse(request);
            Gson gson = new Gson();
            //解析从微信服务器获得的openid和session_key;
            SmsResponse smsResponse = gson.fromJson(response.getData(),SmsResponse.class);
            return smsResponse;
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }
        return null;
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
