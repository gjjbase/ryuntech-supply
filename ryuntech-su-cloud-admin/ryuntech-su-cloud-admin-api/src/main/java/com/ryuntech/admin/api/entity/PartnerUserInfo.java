package com.ryuntech.admin.api.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;

/**
* <p>
    *
    * </p>
*
* @author antu
* @since 2019-08-22
*/
    @Data
        @EqualsAndHashCode(callSuper = false)
    @Accessors(chain = true)
    @TableName("ryn_partner_user_info")
    public class PartnerUserInfo extends BaseModel {

    private static final long serialVersionUID = 1L;

            /**
            * 主键，会员编号
            */
            @TableId("USERID")
    private String userid;

            /**
            * 合伙人姓名
            */
        @TableField("OPERATORNAME")
    private String operatorname;

            /**
            * 账号
            */
        @TableField("ACCOUNT")
    private String account;

            /**
            * 手机
            */
        @TableField("MOBILE")
    private String mobile;

            /**
            * 邮箱
            */
        @TableField("EMAIL")
    private String email;

            /**
            * 角色
            */
        @TableField("ROLE")
    private String role;

            /**
            * 创建时间
            */
        @TableField("CREATETIME")
    private Date createtime;

            /**
            * 状态 0未认证 1 已认证
            */
        @TableField("STATUS")
    private String status;

            /**
            * 性别
            */
        @TableField("OPERATOR_SEX")
    private String operatorSex;

            /**
            * 证件类型
            */
        @TableField("ID_TYPE")
    private String idType;

            /**
            * 证件号码
            */
        @TableField("ID_NO")
    private String idNo;

            /**
            * 座机
            */
        @TableField("OPERATOR_TEL")
    private String operatorTel;

            /**
            * 是否第一次登录
            */
        @TableField("IS_FIRST_LOGIN")
    private String isFirstLogin;

            /**
            * 昵称
            */
        @TableField("NICKNAME")
    private String nickname;

            /**
            * 推荐码
            */
        @TableField("RECOMMEND")
    private String recommend;


}
