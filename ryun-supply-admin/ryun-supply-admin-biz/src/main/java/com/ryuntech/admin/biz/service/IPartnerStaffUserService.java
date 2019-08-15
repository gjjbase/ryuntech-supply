package com.ryuntech.admin.biz.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ryuntech.admin.api.entity.PartnerStaffUser;
import com.ryuntech.common.utils.Result;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author antu
 * @since 2019-08-12
 */
public interface IPartnerStaffUserService extends IService<PartnerStaffUser> {
    Result<IPage<PartnerStaffUser>> pages(Page<PartnerStaffUser> page);

    Result<IPage<PartnerStaffUser>> pageList(Page<PartnerStaffUser> page);
}
