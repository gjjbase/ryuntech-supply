package com.ryuntech.generator.entity;

    import com.baomidou.mybatisplus.annotation.TableName;
    import java.time.LocalDate;
    import com.baomidou.mybatisplus.annotation.TableField;
    import java.io.Serializable;
    import lombok.Data;
    import lombok.EqualsAndHashCode;
    import lombok.experimental.Accessors;

/**
* <p>
    * 
    * </p>
*
* @author ryun
* @since 2019-08-14
*/
    @Data
        @EqualsAndHashCode(callSuper = false)
    @Accessors(chain = true)
    @TableName("ryn_finance_user_info")
    public class FinanceUserInfo implements Serializable {

    private static final long serialVersionUID = 1L;

            /**
            * 主键，融资客户编号
            */
        @TableField("USERID")
    private String userid;

            /**
            * 头像
            */
        @TableField("USER_LOGO")
    private String userLogo;

        @TableField("USER_TYPE")
    private String userType;

            /**
            * 类型
            */
        @TableField("REGISTER_TIME")
    private LocalDate registerTime;

            /**
            * 注册时间
            */
        @TableField("MEMBER_BIRTHDAY")
    private LocalDate memberBirthday;

            /**
            * 生日(出生年月日)
            */
        @TableField("REAL_NAME")
    private String realName;

            /**
            * 真实姓名
            */
        @TableField("EMAIL")
    private String email;

            /**
            * 邮箱
            */
        @TableField("SEX")
    private String sex;

            /**
            * 性别
            */
        @TableField("MOBILE")
    private String mobile;

            /**
            * 手机
            */
        @TableField("ADDRESS")
    private String address;

            /**
            * 地址
            */
        @TableField("ID_TYPE_CODE")
    private String idTypeCode;

            /**
            * 证件类型
            */
        @TableField("ID_NUMBER")
    private String idNumber;

            /**
            * 身份证号
            */
        @TableField("PROVINCE")
    private String province;

            /**
            * 省
            */
        @TableField("CITY")
    private String city;

            /**
            * 市
            */
        @TableField("IS_VISITED_MEMBER")
    private String isVisitedMember;

            /**
            * 是否邀请 0非1是
            */
        @TableField("REGISTER_CHANNELS")
    private String registerChannels;

            /**
            * 申请渠道 1-PC 2-h5 3-小程序 
            */
        @TableField("REGISTER_WAY")
    private String registerWay;

            /**
            * 申请方式
            */
        @TableField("REGISTER_AREA_NUMBER")
    private String registerAreaNumber;

            /**
            * 注册地区
            */
        @TableField("DETAIL_ADDRESS")
    private String detailAddress;

            /**
            * 详细地址
            */
        @TableField("NICKNAME")
    private String nickname;

            /**
            * 昵称
            */
        @TableField("MODIFY_TIME")
    private LocalDate modifyTime;

            /**
            * 修改时间
            */
        @TableField("REFEREE")
    private String referee;

            /**
            * 推荐人编号
            */
        @TableField("OCCUPATION")
    private String occupation;

            /**
            * 职业
            */
        @TableField("IS_PARTENER_WORKER")
    private String isPartenerWorker;

            /**
            * 是否睿云员工 1是 0否
            */
        @TableField("COMPANY_ADDRESS")
    private String companyAddress;

            /**
            * 公司地址
            */
        @TableField("COMPANY_NAME")
    private String companyName;

            /**
            * 公司名称
            */
        @TableField("BUSSINESS_REGISTER")
    private LocalDate bussinessRegister;

            /**
            * 营业执照注册时间
            */
        @TableField("BUSSINESS_LICENSE")
    private String bussinessLicense;

            /**
            * 营业执照编号
            */
        @TableField("PAY_TAXES")
    private String payTaxes;

            /**
            * 纳税额度(每年)
            */
        @TableField("ANNUAL_INVOICE")
    private String annualInvoice;

            /**
            * 开发票(每年)
            */
        @TableField("HOUSE_TYPE")
    private String houseType;

            /**
            * 00 按揭房 01全款 
            */
        @TableField("HOUSE_ADDRESS_TYPE")
    private String houseAddressType;

            /**
            * 00 本地 01外地 
            */
        @TableField("CAR_TYPE")
    private String carType;

            /**
            * 00 按揭车 01全款 
            */
        @TableField("CAR_ADDRESS_TYPE")
    private String carAddressType;


}