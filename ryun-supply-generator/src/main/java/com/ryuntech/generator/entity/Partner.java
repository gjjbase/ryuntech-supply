package com.ryuntech.generator.entity;

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
* @since 2019-08-19
*/
    @Data
        @EqualsAndHashCode(callSuper = false)
    @Accessors(chain = true)
    @TableName("ryn_partner")
    public class Partner implements Serializable {

    private static final long serialVersionUID = 1L;

            /**
            * 主键，合伙人ID
            */
            @TableId("PARTNERID")
    private String partnerid;

            /**
            * 合伙人名称
            */
        @TableField("PARTNER_NAME")
    private String partnerName;

            /**
            * 公司地址
            */
        @TableField("COMPANY_ADDRESS")
    private String companyAddress;

            /**
            * 公司名称
            */
        @TableField("COMPANY_NAME")
    private String companyName;

            /**
            * 联系人姓名
            */
        @TableField("CONTACT_PERSON")
    private String contactPerson;

            /**
            * 联系方式
            */
        @TableField("CONTACT_TYPE")
    private String contactType;

            /**
            * 性别
            */
        @TableField("SEX")
    private String sex;

            /**
            * 证件类型
            */
        @TableField("IDCART_TYPE")
    private String idcartType;

            /**
            * 证件号码
            */
        @TableField("IDCART_NUMBER")
    private String idcartNumber;

            /**
            * 合伙人详细地址
            */
        @TableField("ADDRESS")
    private String address;

            /**
            * 邮箱
            */
        @TableField("EMAIL")
    private String email;

            /**
            * 创建时间
            */
        @TableField("CREATE_TIME")
    private LocalDateTime createTime;

            /**
            * 状态
            */
        @TableField("STATUS")
    private String status;

            /**
            * 注册手机
            */
        @TableField("REGISTER_MOBILE")
    private String registerMobile;

            /**
            * 支持的支付方式（对公，对私）
            */
        @TableField("MERCHANT_PAYMENT_PATTERN")
    private String merchantPaymentPattern;

            /**
            * 出金账户
            */
        @TableField("ACCNO")
    private String accno;

            /**
            * 出金账户开户行
            */
        @TableField("ACCNO_BANK")
    private String accnoBank;

            /**
            * 省份编号
            */
        @TableField("PROVINCE_NO")
    private String provinceNo;

            /**
            * 市编号
            */
        @TableField("CITY_NO")
    private String cityNo;

            /**
            * 合伙人类型 0 普通类型
            */
        @TableField("MERCHANT_TYPE")
    private String merchantType;

            /**
            * 客户来源,注册0,DEFAULT=0  H5 1 PC
            */
        @TableField("MEMBER_ORIGIN")
    private Float memberOrigin;

            /**
            * 英文名称
            */
        @TableField("LEGAL_PERSON")
    private String legalPerson;


}
