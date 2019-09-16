package com.ryuntech.saas.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ryuntech.common.service.impl.BaseServiceImpl;
import com.ryuntech.common.utils.QueryPage;
import com.ryuntech.common.utils.Result;
import com.ryuntech.saas.api.helper.constant.OrderConstants;
import com.ryuntech.saas.api.helper.constant.PayResultConstant;
import com.ryuntech.saas.api.mapper.OrderMapper;
import com.ryuntech.saas.api.mapper.PaymentResultMapper;
import com.ryuntech.saas.api.model.Order;
import com.ryuntech.saas.api.model.PaymentResult;
import com.ryuntech.saas.api.service.IOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;

import static com.ryuntech.common.constant.generator.UniqueIdGenerator.generateId;


/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author antu
 * @since 2019-08-14
 */
@Slf4j
@Service
public class OrderServiceImpl extends BaseServiceImpl<OrderMapper, Order> implements IOrderService {
    @Autowired
    PaymentResultMapper resultMapper;


    @Override
    public Result<IPage<Order>> pageList(Order order, QueryPage queryPage) {
        Page<Order> page = new Page<>(queryPage.getPageCode(), queryPage.getPageSize());
        if (order.getOrderid()!=null) {
            queryWrapper.eq("order_id", order.getOrderid());
        }
        return super.pageList(queryWrapper,page);
    }

    @Override
    public Result<IPage<Order>> selectPageList(Order order,QueryPage queryPage) {
        Page<Order> page = new Page<>(queryPage.getPageCode(), queryPage.getPageSize());
        return new Result(m.selectPageList(page,order));
    }

    /*
    * 发起结算
    * */
    @Override
    public Order settlement(String orderid) {
        //查询订单数据是否已经结算
        PaymentResult payment = resultMapper.selectOne(new QueryWrapper<PaymentResult>().eq("order_id", orderid));
        if (payment!=null){
            log.info("订单号:"+orderid+"已结算");
            return null;
        }
        //开始结算
        //查询订单是否已经放款
        Order order1 = m.selectById(orderid);
        if (!order1.getOrderStatus().equals(OrderConstants.LENDING)){
            log.info("订单号:"+orderid+"未放款");
            return null;
        }
        Order order = new Order();
        order.setOrderid(orderid);
        //更新状态为结算中，修改结算金额，修改手续费为0元
        order.setPaymentFee(new BigDecimal(0));
        order.setPaymentStatus(PayResultConstant.SETTLEING);
        int i = m.updateById(order);
        if (i>0){
            //开始插入结算结果表数据
            PaymentResult paymentResult = new PaymentResult();
            paymentResult.setOrderId(orderid);
            paymentResult.setPaymentTime(new Date());
//            放款金额 setOrderFactPayAmount
            paymentResult.setPayAmt(order.getOrderFactPayAmount());
//            统一设置为0元 手续费设置，修改手续费为0
            paymentResult.setPaymentFee(new BigDecimal(0));
            paymentResult.setPaymentStatus(PayResultConstant.SETTLEING);
            String paymentId = generateId()+"";
            paymentResult.setPaymentSystemId(paymentId);
            resultMapper.insert(paymentResult);
        }
        return m.selectById(orderid);
    }

    @Override
    public Order loan(Order order) {
        //判断订单数据是否已经打款
        Order orderload = m.selectById(order.getOrderid());
        if (orderload.getOrderStatus().equals(OrderConstants.LENDING)){
            log.info("订单号:"+orderload.getOrderid()+"已放款");
            return null;
        }
        //开始执行放款操作
        orderload.setOrderFactPayAmount(order.getOrderFactPayAmount());
        orderload.setOrderStatus(OrderConstants.LENDING);
        orderload.setModifyTime(new Date());
        orderload.setOrderPayTime(new Date());
        int i = m.updateById(orderload);
        if (i>0){
            return orderload;
        }
        return null;
    }

    @Override
    public Order refuse(String orderid) {
        //判断订单数据是否已经打款
        Order order = m.selectById(orderid);
        if (order.getOrderStatus().equals(OrderConstants.REFUSAL)){
            log.info("订单号:"+orderid+"已拒款");
            return null;
        }
        //开始执行放款操作
        order.setOrderStatus(OrderConstants.REFUSAL);
        order.setModifyTime(new Date());
        order.setOrderPayTime(new Date());
        int i = m.updateById(order);
        if (i>0){
            return order;
        }
        return null;
    }

}
