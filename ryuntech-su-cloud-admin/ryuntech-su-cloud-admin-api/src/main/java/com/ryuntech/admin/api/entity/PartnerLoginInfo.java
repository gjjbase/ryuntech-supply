package com.ryuntech.admin.api.entity;

    import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

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
    @TableName("ryn_partner_login_info")
    public class PartnerLoginInfo extends BaseModel {

    private static final long serialVersionUID = 1L;

            /**
            * 用户ID
            */
            @TableId("USERID")
    private String userid;

            /**
            * 最后登录会话ID
            */
        @TableField("SESSION_ID")
    private String sessionId;

            /**
            * 最后登录时间
            */
        @TableField("LAST_LOGIN_TIME")
    private LocalDateTime lastLoginTime;

            /**
            * 登录IP地址
            */
        @TableField("CLIENT_IP")
    private String clientIp;

            /**
            * 最后修改密码时间
            */
        @TableField("LAST_MODIFY_PWD_DATE")
    private LocalDateTime lastModifyPwdDate;

            /**
            * 错误次数
            */
        @TableField("ERROR_TIMES")
    private Float errorTimes;

            /**
            * 最后修改登录名时间
            */
        @TableField("LAST_MOD_LOGIN_NAME_TIME")
    private LocalDateTime lastModLoginNameTime;

            /**
            * 最后登录操作系统
            */
        @TableField("LAST_LOGIN_DEVICE_BROWSER")
    private String lastLoginDeviceBrowser;

            /**
            * 用户的操作系统名
            */
        @TableField("LAST_LOGIN_OS")
    private String lastLoginOs;

            /**
            * 锁定时间
            */
        @TableField("LOCK_TIME")
    private LocalDateTime lockTime;

            /**
            * 客户端MAC地址
            */
        @TableField("CLIENT_MAC")
    private String clientMac;

            /**
            * 登录渠道
            */
        @TableField("LAST_LOGIN_CHANNELS")
    private String lastLoginChannels;

            /**
            * 登录方式
            */
        @TableField("LAST_LOGIN_WAY")
    private String lastLoginWay;


}
