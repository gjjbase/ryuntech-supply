package com.ryuntech.admin.api.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
* <p>
    *
    * </p>
*
* @author antu
* @since 2019-08-15
*/
    @Data
        @EqualsAndHashCode(callSuper = false)
    @Accessors(chain = true)
    @TableName("ryn_partner_login_log_info")
    public class PartnerLoginLogInfo implements Serializable {

    private static final long serialVersionUID = 1L;

            @TableId("LOG_ID")
    private String logId;

        @TableField("SESSION_ID")
    private String sessionId;

        @TableField("LAST_LOGIN_TIME")
    private LocalDateTime lastLoginTime;

        @TableField("CLIENT_IP")
    private String clientIp;

        @TableField("LOGIN_DEVICE_BROWSER")
    private String loginDeviceBrowser;

        @TableField("LOGIN_OS")
    private String loginOs;

        @TableField("USERID")
    private String userid;

        @TableField("LOGIN_NAME")
    private String loginName;

        @TableField("LOGIN_CHANNELS")
    private String loginChannels;

        @TableField("LOGIN_WAY")
    private String loginWay;
         @TableField("CLIENT_MAC")
    private String clientMac;


}
