package com.ryuntech.admin.biz.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ryuntech.admin.api.entity.PaymentResult;
import com.ryuntech.admin.biz.mapper.PaymentResultMapper;
import com.ryuntech.admin.biz.service.IPaymentResultService;
import com.ryuntech.common.utils.QueryPage;
import com.ryuntech.common.utils.Result;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author antu
 * @since 2019-08-27
 */
@Service
public class PaymentResultServiceImpl extends BaseServiceImpl<PaymentResultMapper, PaymentResult> implements IPaymentResultService {

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

}
