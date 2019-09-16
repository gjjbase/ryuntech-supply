package com.ryuntech.saas.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ryuntech.common.service.impl.BaseServiceImpl;
import com.ryuntech.common.utils.QueryPage;
import com.ryuntech.common.utils.Result;
import com.ryuntech.saas.api.mapper.SysRoleMapper;
import com.ryuntech.saas.api.model.FinanceUserInfo;
import com.ryuntech.saas.api.model.SysRole;
import com.ryuntech.saas.api.service.ISysRoleService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author antu
 * @since 2019-09-12
 */
@Service
public class SysRoleServiceImpl extends BaseServiceImpl<SysRoleMapper, SysRole> implements ISysRoleService {

    @Override
    public Result<IPage<SysRole>> pageList(SysRole sysRole, QueryPage queryPage) {
        Page<SysRole> page = new Page<>(queryPage.getPageCode(), queryPage.getPageSize());
        if (sysRole.getRname()!=null) {
            queryWrapper.eq("rname", sysRole.getRname());
        }
        return super.pageList(queryWrapper,page);
    }


    @Override
    public List<String> getRoleIdsByUserId(String userId) {
        return baseMapper.getRoleIdsByUserId(userId);
    }
}
