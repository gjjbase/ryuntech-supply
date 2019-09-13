package com.ryuntech.saas.api.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ryuntech.saas.api.model.SysPerm;

import java.util.List;

/**
 * <p>
 * 权限 服务类
 * </p>
 *
 * @author antu
 * @since 2019-09-12
 */
public interface ISysPermService extends IBaseService<SysPerm> {
    List<SysPerm> getPermsByRoleId(String roleId);
}
