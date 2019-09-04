package com.ryuntech.admin.api.entity;

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
    @TableName("ryn_partner_staff_user")
    public class PartnerStaffUser extends BaseModel {

    private static final long serialVersionUID = 1L;

            /**
            * 用户名
            */
    private String username;

            /**
            * 密码
            */
    private String password;

            /**
            * 手机号
            */
    private String phone;

            /**
            * 头像
            */
    private String avatar;

            /**
            * 创建时间
            */
    private LocalDateTime createTime;

    private String mobile;

    private String emall;

    private String address;

    private String orgId;

    private String lastModifyBy;

    private LocalDateTime lastModifyTime;

    private String idcard;

    private String empid;

    private String possition;

    private String status;

    private String channelType;

    private String nickname;


}
