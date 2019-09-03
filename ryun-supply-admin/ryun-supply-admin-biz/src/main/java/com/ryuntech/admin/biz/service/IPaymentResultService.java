package com.ryuntech.admin.biz.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ryuntech.admin.api.entity.PaymentResult;
import com.ryuntech.common.utils.QueryPage;
import com.ryuntech.common.utils.Result;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author antu
 * @since 2019-08-27
 */
public interface IPaymentResultService extends IBaseService<PaymentResult> {

    Result<IPage<PaymentResult>> pageList(PaymentResult paymentResult, QueryPage queryPage);

    Result<IPage<PaymentResult>> selectPageList(PaymentResult paymentResult, QueryPage queryPage);

    PaymentResult settlementEnter(String paymentSystemId);
}
