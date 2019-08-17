package com.ryuntech.admin.biz.service;

import com.ryuntech.admin.api.entity.FinanceUserInfo;
import com.ryuntech.admin.api.vo.FinanceOrder;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author antu
 * @since 2019-08-14
 */
public interface IFinanceUserInfoService extends IBaseService<FinanceUserInfo> {
    /*Result<IPage<FinanceUserInfo>> pages(Page<FinanceUserInfo> page);

    Result<IPage<FinanceUserInfo>> pageList(Page<FinanceUserInfo> page);*/

    void addFinacneOrder(FinanceOrder financeOrder);
}
