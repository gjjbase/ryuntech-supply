package com.ryuntech.admin.biz.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ryuntech.admin.api.entity.Order;
import com.ryuntech.admin.api.entity.PaymentResult;
import com.ryuntech.admin.biz.mapper.OrderMapper;
import com.ryuntech.admin.biz.mapper.PaymentResultMapper;
import com.ryuntech.admin.biz.service.IPaymentResultService;
import com.ryuntech.common.constant.OrderConstants;
import com.ryuntech.common.constant.PayResultConstant;
import com.ryuntech.common.utils.QueryPage;
import com.ryuntech.common.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author antu
 * @since 2019-08-27
 */
@Slf4j
@Service
public class PaymentResultServiceImpl extends BaseServiceImpl<PaymentResultMapper, PaymentResult> implements IPaymentResultService {

    @Autowired
    OrderMapper orderMapper;

    @Override
    public Result<IPage<PaymentResult>> pageList(PaymentResult paymentResult, QueryPage queryPage) {
        Page<PaymentResult> page = new Page<>(queryPage.getPageCode(), queryPage.getPageSize());
        if (paymentResult.getOrderId()!=null)
            queryWrapper.eq("order_id", paymentResult.getOrderId());
        return super.pageList(queryWrapper,page);
    }

    @Override
    public Result<IPage<PaymentResult>> selectPageList(PaymentResult paymentResult, QueryPage queryPage) {
        Page<PaymentResult> page = new Page<>(queryPage.getPageCode(), queryPage.getPageSize());
        return new Result(m.selectPageList(page,paymentResult));
    }

    @Override
    public PaymentResult settlementEnter(String paymentSystemId) {
        PaymentResult paymentResult = m.selectById(paymentSystemId);
        if (paymentResult==null){
            log.info("结算单为空，无法结算"+paymentResult.getPaymentSystemId());
            return null;
        }
        if (paymentResult.getPaymentStatus().equals(PayResultConstant.SETTLE)){
            log.info("订单已放款，无法结算"+paymentResult.getPaymentSystemId());
            return null;
        }
        if (paymentResult.getOrderId()==null)
        {
            log.info("不存在订单号，无法结算"+paymentResult.getPaymentSystemId());
            return null;
        }
        Order order = orderMapper.selectById(paymentResult.getOrderId());
        if(order==null){
            log.info("不存在订单，无法结算"+paymentResult.getPaymentSystemId());
            return null;
        }
        if (!order.getOrderStatus().equals(OrderConstants.LENDING)){
            log.info("订单为放款，无法结算"+paymentResult.getPaymentSystemId());
            return null;
        }
        //开始结算
        paymentResult.setPaymentStatus(PayResultConstant.SETTLE);
        paymentResult.setPayedTime(new Date());
        int i = m.updateById(paymentResult);
        //更新订单的结算状态
         order.setPaymentStatus(PayResultConstant.SETTLE);
        int i1 = orderMapper.updateById(order);
        if (i>0&&i1>0) return paymentResult;
        return null;
    }

}
