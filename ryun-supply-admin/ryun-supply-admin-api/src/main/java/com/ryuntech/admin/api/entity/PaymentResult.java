package com.ryuntech.admin.api.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
* <p>
    *
    * </p>
*
* @author antu
* @since 2019-08-27
*/
    @Data
        @EqualsAndHashCode(callSuper = false)
    @Accessors(chain = true)
    @TableName("ryn_payment_result")
    public class PaymentResult extends BaseModel {

    private static final long serialVersionUID = 1L;

            /**
            * 结算系统编号
            */
            @TableId("PAYMENT_SYSTEM_ID")
    private String paymentSystemId;

            /**
            * 订单编号
            */
        @TableField("ORDER_ID")
    private String orderId;

            /**
            * 结算付款方式 01 微信 02 银行卡
            */
        @TableField("PAYMENT_TYPE")
    private String paymentType;

            /**
            * 结算时间
            */
        @TableField("PAYMENT_TIME")
    private LocalDateTime paymentTime;

            /**
            * 结算状态(00-未结算，01已结算，02结算中)
            */
        @TableField("PAYMENT_STATUS")
    private String paymentStatus;

            /**
            * 手续费
            */
        @TableField("PAYMENT_FEE")
    private BigDecimal paymentFee;

            /**
            * 结算银行
            */
        @TableField("PAYED_BANK")
    private String payedBank;

            /**
            * 结算卡号
            */
        @TableField("PAY_CARD_NO")
    private String payCardNo;

            /**
            * 结算类型
            */
        @TableField("PAY_CARD_TYPE")
    private String payCardType;

            /**
            * 结算完成时间
            */
        @TableField("PAYED_TIME")
    private LocalDateTime payedTime;

            /**
            * 渠道 01-PC 02-小程序
            */
        @TableField("CHANNEL_TYPE")
    private String channelType;

            /**
            * 结算金额
            */
        @TableField("PAY_AMT")
    private BigDecimal payAmt;

        @TableField(exist = false)
    private Order order;

}
