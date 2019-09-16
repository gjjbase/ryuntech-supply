package com.ryuntech.saas.service.impl;

import com.ryuntech.saas.api.mapper.SysPermMapper;
import com.ryuntech.saas.api.model.SysPerm;
import com.ryuntech.saas.api.service.ISysPermService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 权限 服务实现类
 * </p>
 *
 * @author antu
 * @since 2019-09-12
 */
@Service
public class SysPermServiceImpl extends BaseServiceImpl<SysPermMapper, SysPerm> implements ISysPermService {

    @Override
    public List<SysPerm> getPermsByRoleId(String roleId) {
        return baseMapper.getPermsByRoleId(roleId);
    }

    @Override
    public List<SysPerm> getPermsByUserId(String userId) {
        return baseMapper.getPermsByUserId(userId);
    }
}
