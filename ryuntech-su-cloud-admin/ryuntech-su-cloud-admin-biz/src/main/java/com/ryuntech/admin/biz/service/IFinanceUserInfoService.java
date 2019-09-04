package com.ryuntech.admin.biz.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ryuntech.admin.api.entity.FinanceUserInfo;
import com.ryuntech.admin.api.vo.FinanceOrder;
import com.ryuntech.common.utils.QueryPage;
import com.ryuntech.common.utils.Result;

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
    void addFinacneOrder(FinanceOrder financeOrder);
}
