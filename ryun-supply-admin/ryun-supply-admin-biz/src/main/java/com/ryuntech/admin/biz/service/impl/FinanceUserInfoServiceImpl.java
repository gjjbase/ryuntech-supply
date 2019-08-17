package com.ryuntech.admin.biz.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ryuntech.admin.api.entity.FinanceUserInfo;
import com.ryuntech.admin.api.entity.Order;
import com.ryuntech.admin.api.vo.FinanceOrder;
import com.ryuntech.admin.biz.mapper.FinanceUserInfoMapper;
import com.ryuntech.admin.biz.mapper.OrderMapper;
import com.ryuntech.admin.biz.service.IFinanceUserInfoService;
import com.ryuntech.common.constant.OrderConstants;
import com.ryuntech.common.constant.PaymentResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

import static com.ryuntech.admin.api.utils.generator.UniqueIdGenerator.generateId;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author antu
 * @since 2019-08-14
 */
@Service
public class FinanceUserInfoServiceImpl extends BaseServiceImpl<FinanceUserInfoMapper, FinanceUserInfo> implements IFinanceUserInfoService {

    @Autowired
    OrderMapper orderMapper;
    @Autowired
    FinanceUserInfoMapper financeUserInfoMapper;

    @Override
    public void addFinacneOrder(FinanceOrder financeOrder) {
        //插入融资客户信息
        String financeId = generateId()+"";
        FinanceUserInfo financeUserInfo = new FinanceUserInfo();

        //如果融资客户存在，则不插入
        financeUserInfo = this.financeUserInfoMapper.selectOne(new QueryWrapper<FinanceUserInfo>().eq("mobile", ""));
        if (financeUserInfo==null){
            //用户不存在，不能插入，插入订单数据
            financeUserInfo.setUserId(financeId);
            financeUserInfo.setSex(financeOrder.getSex());
            financeUserInfo.setMemberBirthday(financeOrder.getMemberBirthday());
            financeUserInfo.setCity(financeOrder.getCity());
            //纳税金额s
            financeUserInfo.setPayTaxes(financeOrder.getPayTaxes());
            //年开发票
            financeUserInfo.setAnnualInvoice(financeOrder.getAnnualInvoice());
            //房产信息 00 按揭房 01全款
            financeUserInfo.setHouseType(financeOrder.getHouseType());
            //00 本地 01外地
            financeUserInfo.setHouseAddressType(financeOrder.getHouseAddressType());

            //房产信息 00 按揭房 01全款
            financeUserInfo.setCarType(financeOrder.getCarType());
            //00 本地 01外地
            financeUserInfo.setCarAddressType(financeOrder.getCarAddressType());

//        营业执照注册时间
            financeUserInfo.setBussinessRegister(financeOrder.getBussinessRegister());
            //身份性质,法人/股东/其他,职业
            financeUserInfo.setAnnualInvoice(financeOrder.getAnnualInvoice());
            financeUserInfoMapper.insert(financeUserInfo);
        }

        //开始插入订单信息  插入订单信息
        //贷款金额
        Order order = new Order();
        String orderPayAmount = financeOrder.getOrderPayAmount();
        String orderId = generateId()+"";
        order.setOrderId(orderId);
        order.setOrderPayAmount(Float.parseFloat(orderPayAmount));
        order.setOrderTime(new Date());
        order.setModifyTime(new Date());
        //融资客户编号
        order.setMemberId(financeUserInfo.getUserId());
        //订单状态
        order.setOrderStatus(OrderConstants.PENDING);
        //佣金阶段状态
        order.setPaymentStatus(PaymentResult.UNLIQUIDATED);
        //申请渠道
        order.setOrderChenel(financeOrder.getOrderChenel());
        orderMapper.insert(order);

    }
  /*  @Autowired
    FinanceUserInfoMapper financeUserInfoMapper;
    @Override
    public Result<IPage<FinanceUserInfo>> pages(Page<FinanceUserInfo> page) {
        IPage<FinanceUserInfo> userIPage = financeUserInfoMapper.Pages(page);
        return new Result(userIPage);
    }

    @Override
    public Result<IPage<FinanceUserInfo>> pageList(Page<FinanceUserInfo> page) {
        IPage<FinanceUserInfo> userIPage = financeUserInfoMapper.pageList(page);
        return new Result(userIPage);
    }*/
}
