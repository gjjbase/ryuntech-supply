package com.ryuntech.saas.api.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ryuntech.saas.api.model.PaymentResult;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author antu
 * @since 2019-08-27
 */
public interface PaymentResultMapper extends IBaseMapper<PaymentResult> {
    IPage<PaymentResult> selectPageList(@Param("pg") Page<PaymentResult> page, @Param("paymentResult") PaymentResult paymentResult);


    /*统计金额*/
    BigDecimal censusOrderByPartnerId(@Param("paymentResult") PaymentResult paymentResult);

    /*统计数量*/
    Integer censusOrderCountByPartnerId(@Param("paymentResult") PaymentResult paymentResult);
}
