package com.ryuntech.admin.api.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

/**
* <p>
    *
    * </p>
*
* @author antu
* @since 2019-08-14
*/
    @Data
        @EqualsAndHashCode(callSuper = false)
    @Accessors(chain = true)
    @TableName("ryn_order")
    public class Order extends BaseModel {

    private static final long serialVersionUID = 1L;

            /**
            * 主键，订单编号
            */
            @TableId("ORDER_ID")
    private String orderid;

            /**
            * 融资客户Id
            */
        @TableField("MEMBER_ID")
        /*
        * 忽略json字段的输出
        * */
        @JsonIgnore
    private String memberId;

    @TableField("MEMBER_NAME")
    private String memberName;

    @TableField("COMPANY_NAME")
    private String companyName;

            /**
            * 下单渠道 0-小程序 1-PC 2-手机 4-微信
            */
        @TableField("ORDER_CHENEL")
    private String orderChenel;

            /**
            * 申请时间
            */
        @TableField("ORDER_TIME")
        @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date orderTime;

            /**
            * 是否产生佣金(0 无佣金 1有佣金)
            */
        @TableField("IS_ROY")
    private String isRoy;

            /**
            * 佣金结算状态(00-未结算，01已结算，02结算中，03可结算)
            */
        @TableField("PAYMENT_STATUS")
    private String paymentStatus;

            /**
            * "订单状态:01=待处理(合伙人推荐成功),02=资质不符合(橙势审核不符),03=资金方审核(提交银行渠道) 04=已放款(银行渠道已放款，交易完成) 05=已拒款(交易失败) 06=已删除"
            */
        @TableField("ORDER_STATUS")
    private String orderStatus;

            /**
            * 订单类型  01-普通订单
            */
        @TableField("ORDER_TYPE")
    private String orderType;

            /**
            * 订单关闭时间
            */
        @TableField("ORDER_CLOSE_TIME")
        @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date orderCloseTime;

            /**
            * 订单失效时间
            */
        @TableField("ORDER_INVALID_TIME")
        @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date orderInvalidTime;

            /**
            * 合伙人编号
            */
        @TableField("PARTNER_ID")
    private String partnerId;

            /**
            * 合伙人备注
            */
        @TableField("ORDER_MERCHANT_MEMO")
    private String orderMerchantMemo;

            /**
            * 推荐人编号
            */
        @TableField("ORDER_MEMO")
    private String orderMemo;

            /**
            * 佣金提成金额
            */
        @TableField("PAYMENT_FEE")
    private BigDecimal paymentFee;

            /**
            * 修改时间
            */
        @TableField("MODIFY_TIME")
        @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date modifyTime;

            /**
            * 订单删除状态 0:订单标记为未删除  1：订单标记为已删除
            */
        @TableField("IS_DELETE")
    private String isDelete;

            /**
            * 订单实际到账金额
            */
        @TableField("ORDER_FACT_PAY_AMOUNT")
    private BigDecimal orderFactPayAmount;

            /**
            * 订单申请金额
            */
        @TableField("ORDER_PAY_AMOUNT")
    private String orderPayAmount;

            /**
            * 到账时间
            */
        @TableField("ORDER_PAY_TIME")
        @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date orderPayTime;

            /**
            * 订单生成方式 00=直接申请
            */
        @TableField("ORDER_CREATE_TYPE")
    private String orderCreateType;

        @TableField("REFERRAL_CODE")
    private String referralCode;
    @TableField("city")
    private String city;


    @TableField(exist = false)
        private FinanceUserInfo financeUserInfo;


}
