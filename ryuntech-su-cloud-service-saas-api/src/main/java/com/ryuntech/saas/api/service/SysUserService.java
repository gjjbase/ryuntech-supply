package com.ryuntech.saas.api.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ryuntech.common.utils.QueryPage;
import com.ryuntech.saas.api.model.SysUser;

import java.util.List;

/**
 * @author antu
 * @date 2019-05-22
 */
public interface SysUserService  extends IService<SysUser> {

    /**
     * 根据用户名查询用户信息
     *
     * @param username
     * @return
     */
    SysUser findByName(String username);


    /**
     * 条件查询用户
     *
     * @param user
     * @return
     */
    List<SysUser> list(SysUser user);

    /**
     * 新增用户
     *
     * @param user
     */
    void create(SysUser user);

    /**
     * 更新用户
     *
     * @param user
     */
    void update(SysUser user);

    /**
     * 修改密码
     *
     * @param user
     */
    void changePass(SysUser user);

    /**
     * 查询用户信息
     * @param user
     * @return
     */
    SysUser selectUserRoleById(SysUser user);

    /**
     * 分页查看用户信息
     * @param user
     * @param queryPage
     * @return
     */
    IPage<SysUser> selectUsersRoleById(SysUser user, QueryPage queryPage);
}
