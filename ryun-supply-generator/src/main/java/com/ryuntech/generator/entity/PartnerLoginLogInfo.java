package com.ryuntech.generator.entity;

    import com.baomidou.mybatisplus.annotation.TableName;
    import com.baomidou.mybatisplus.annotation.TableId;
    import java.time.LocalDateTime;
    import com.baomidou.mybatisplus.annotation.TableField;
    import java.io.Serializable;
    import io.swagger.annotations.ApiModel;
    import io.swagger.annotations.ApiModelProperty;
    import lombok.Data;
    import lombok.EqualsAndHashCode;
    import lombok.experimental.Accessors;

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
    @ApiModel(value="PartnerLoginLogInfo对象", description="")
    public class PartnerLoginLogInfo implements Serializable {

    private static final long serialVersionUID = 1L;

            @ApiModelProperty(value = "主键")
            @TableId("LOG_ID")
    private String logId;

            @ApiModelProperty(value = "会话编号")
        @TableField("SESSION_ID")
    private String sessionId;

            @ApiModelProperty(value = "最后登录时间")
        @TableField("LAST_LOGIN_TIME")
    private LocalDateTime lastLoginTime;

            @ApiModelProperty(value = "客户端ip地址")
        @TableField("CLIENT_IP")
    private String clientIp;

            @ApiModelProperty(value = "使用的浏览器")
        @TableField("LOGIN_DEVICE_BROWSER")
    private String loginDeviceBrowser;

            @ApiModelProperty(value = "使用的操作系统")
        @TableField("LOGIN_OS")
    private String loginOs;

            @ApiModelProperty(value = "会员编号")
        @TableField("USERID")
    private String userid;

            @ApiModelProperty(value = "会员登录名")
        @TableField("LOGIN_NAME")
    private String loginName;

            @ApiModelProperty(value = "会员登录渠道")
        @TableField("LOGIN_CHANNELS")
    private String loginChannels;

            @ApiModelProperty(value = "会员登录方式")
        @TableField("LOGIN_WAY")
    private String loginWay;

            @ApiModelProperty(value = "mac地址")
        @TableField("CLIENT_MAC")
    private String clientMac;


}
