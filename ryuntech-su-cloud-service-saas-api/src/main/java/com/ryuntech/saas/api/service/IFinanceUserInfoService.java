package com.ryuntech.saas.api.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ryuntech.common.utils.QueryPage;
import com.ryuntech.common.utils.Result;
import com.ryuntech.saas.api.dto.FinanceOrder;
import com.ryuntech.saas.api.model.FinanceUserInfo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author antu
 * @since 2019-08-14
 */
public interface IFinanceUserInfoService extends IBaseService<FinanceUserInfo> {
    Result<IPage<FinanceUserInfo>> pageList(FinanceUserInfo financeUserInfo, QueryPage queryPage);
    Result addFinacneOrder(FinanceOrder financeOrder);
    Result updateFinacneOrder(FinanceUserInfo financeUserInfo);
    FinanceUserInfo selectOne(FinanceOrder financeOrder);


}
