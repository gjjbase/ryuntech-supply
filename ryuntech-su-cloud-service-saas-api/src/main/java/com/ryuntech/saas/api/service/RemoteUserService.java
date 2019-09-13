package com.ryuntech.saas.api.service;

import com.ryuntech.common.utils.Result;
import com.ryuntech.saas.api.model.SysUser;
import com.ryuntech.saas.api.service.fallback.RemoteUserServiceFallbackImpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * 用户远程调用接口
 *
 * @author antu
 * @date 2019-05-22
 */
@FeignClient(value = "ryuntech-su-cloud-service-saas", fallback = RemoteUserServiceFallbackImpl.class)
public interface RemoteUserService {

    /**
     * 根据用户名查找用户信息
     *
     * @return
     */
    @GetMapping("/user/info/{username}")
    Result<SysUser> info(@PathVariable("username") String username);

    /**
     * 根据用户名查找用户信息
     *
     * @return
     */
    @GetMapping("/user/cusinfo/{username}")
    Result<SysUser> cusinfo(@PathVariable("username") String username);
}
