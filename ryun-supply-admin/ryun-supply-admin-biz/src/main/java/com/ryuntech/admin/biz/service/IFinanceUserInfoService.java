package com.ryuntech.admin.biz.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ryuntech.admin.api.entity.FinanceUserInfo;
import com.ryuntech.common.utils.Result;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ryun
 * @since 2019-08-14
 */
public interface IFinanceUserInfoService extends IService<FinanceUserInfo> {
    Result<IPage<FinanceUserInfo>> pages(Page<FinanceUserInfo> page);

    Result<IPage<FinanceUserInfo>> pageList(Page<FinanceUserInfo> page);
}
