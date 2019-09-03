package com.ryuntech.admin.api.vo;


import lombok.Data;

/*
* {"Message":"OK","RequestId":"8FB14AD1-0D42-45B6-98BF-F34E18DB59BA","BizId":"974716367419173740^0","Code":"OK"}
* */
@Data
public class SmsResponse {
    String Message;
    String RequestId;
    String BizId;
    String Code;
}
