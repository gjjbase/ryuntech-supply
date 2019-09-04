package common;

import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import org.junit.Test;
/*
用户登录名称 msg@1746465591239783.onaliyun.com
        AccessKey ID LTAI7bXgddnt5tUv
        AccessKeySecret NRiktAo46MpyemRj1Houeh94BhrMH0*/
public class SendSmsApplicationTests {
    @Test
    public void sendSms() {
        DefaultProfile profile = DefaultProfile.getProfile("default", "LTAI7bXgddnt5tUv", "NRiktAo46MpyemRj1Houeh94BhrMH0");
        IAcsClient client = new DefaultAcsClient(profile);

        CommonRequest request = new CommonRequest();
        request.setMethod(MethodType.POST);
        request.setDomain("dysmsapi.aliyuncs.com");
        request.setVersion("2017-05-25");
        request.setAction("SendSms");
        request.putQueryParameter("RegionId", "default");
        request.putQueryParameter("PhoneNumbers", "18518215883");
        request.putQueryParameter("SignName", "睿云");
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
