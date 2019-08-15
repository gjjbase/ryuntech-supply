package com.ryuntech.admin.biz.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ryuntech.admin.api.entity.PartnerStaffUser;
import com.ryuntech.admin.biz.mapper.PartnerStaffUserMapper;
import com.ryuntech.admin.biz.service.IPartnerStaffUserService;
import com.ryuntech.common.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author antu
 * @since 2019-08-12
 */
@Service
public class PartnerStaffUserServiceImpl extends ServiceImpl<PartnerStaffUserMapper, PartnerStaffUser> implements IPartnerStaffUserService {

    @Autowired
    PartnerStaffUserMapper partnerStaffUserMapper;

    @Override
    public Result<IPage<PartnerStaffUser>> pages(Page<PartnerStaffUser> page) {
        IPage<PartnerStaffUser> userIPage = partnerStaffUserMapper.Pages(page);
        return new Result(userIPage);
    }

    @Override
    public Result<IPage<PartnerStaffUser>> pageList(Page<PartnerStaffUser> page) {
        IPage<PartnerStaffUser> userIPage = partnerStaffUserMapper.pageList(page);
        return new Result(userIPage);
    }
}
