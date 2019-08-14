package com.ryuntech.admin.biz.service.impl;

import com.ryuntech.admin.api.entity.FinanceUserInfo;
import com.ryuntech.admin.biz.mapper.FinanceUserInfoMapper;
import com.ryuntech.admin.biz.service.IFinanceUserInfoService;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ryun
 * @since 2019-08-14
 */
@Service
public class FinanceUserInfoServiceImpl extends BaseServiceImpl<FinanceUserInfoMapper, FinanceUserInfo> implements IFinanceUserInfoService {
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
