package com.ryuntech.saas.api.model;

import com.ryuntech.common.model.BaseModel;
import lombok.Data;

@Data
public class WeChatSession extends BaseModel {

    private String session_key;
    private String openid;
    private String unionid;

}
