package com.ryuntech.saas.api.model;

import lombok.Data;

@Data
public class WeChatSession {

    private String session_key;
    private String openid;
    private String unionid;

}
