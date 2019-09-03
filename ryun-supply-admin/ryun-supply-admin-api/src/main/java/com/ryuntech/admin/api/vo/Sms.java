package com.ryuntech.admin.api.vo;


import com.ryuntech.admin.api.entity.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Sms extends BaseModel {
   /*  request.putQueryParameter("RegionId", "default");
        request.putQueryParameter("PhoneNumbers", "18518215883");
        request.putQueryParameter("SignName", "睿云");
        request.putQueryParameter("TemplateCode", "SMS_172120896");*/
   private String regionId;
   private String phoneNumbers;
   private String signName;
   private String content;
}
