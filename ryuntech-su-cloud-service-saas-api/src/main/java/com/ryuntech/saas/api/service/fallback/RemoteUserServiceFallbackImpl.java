package com.ryuntech.saas.api.service.fallback;

import com.ryuntech.common.utils.Result;
import com.ryuntech.saas.api.model.SysUser;
import com.ryuntech.saas.api.service.RemoteUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author antu
 * @date 2019-05-22
 */
@Slf4j
@Component
public class RemoteUserServiceFallbackImpl implements RemoteUserService {

    @Override
    public Result<SysUser> info(String username) {
        log.error("查询用户信息失败，username >> {}", username);
        return null;
    }

    @Override
    public Result<SysUser> cusinfo(String username) {
        log.error("查询用户信息失败，username >> {}", username);
        return null;
    }
}
