package com.ryuntech.saas.api.mapper;

import com.ryuntech.saas.api.model.FinanceUserInfo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author antu
 * @since 2019-08-14
 */

public interface FinanceUserInfoMapper extends IBaseMapper<FinanceUserInfo> {
    /*IPage<FinanceUserInfo> Pages(@Param("page") Page<FinanceUserInfo> page);

    IPage<FinanceUserInfo> pageList(@Param("page") Page<FinanceUserInfo> page);*/
}
