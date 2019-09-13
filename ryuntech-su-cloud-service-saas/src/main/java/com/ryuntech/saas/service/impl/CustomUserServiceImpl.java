package com.ryuntech.saas.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ryuntech.saas.api.mapper.*;
import com.ryuntech.saas.api.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.security.acl.Permission;
import java.util.ArrayList;
import java.util.List;

/**
 * @author EDZ
 */
@Service
public class CustomUserServiceImpl implements UserDetailsService { //自定义UserDetailsService 接口

    @Autowired
    SysUserMapper sysUserMapper;

    @Autowired
    SysUserRoleMapper sysUserRoleMapper;
    @Autowired
    SysRoleMapper sysRoleMapper;

    @Autowired
    SysRolePermMapper sysRolePermMapper;
    @Autowired
    SysPermMapper sysPermMapper;

    @Override
    public UserDetails loadUserByUsername(String username) {
        SysUser user = sysUserMapper.selectOne(new QueryWrapper<SysUser>().eq("username",username));
        if (user != null) {
            //用户对应的角色
            SysUserRole sysUserRole = sysUserRoleMapper.selectOne(new QueryWrapper<SysUserRole>().eq("user_id", user.getId()));
            List<SysPerm> permissions = sysPermMapper.getPermsByRoleId(sysUserRole.getRoleId());

            List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
            for (SysPerm permission : permissions) {
                if (permission != null && permission.getPval()!=null) {

                    GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(permission.getPval());
                    //1：此处将权限信息添加到 GrantedAuthority 对象中，在后面进行全权限验证时会使用GrantedAuthority 对象。
                    grantedAuthorities.add(grantedAuthority);
                }
            }
            user.setGrantedAuthorities(grantedAuthorities);
            return user;
        } else {
            throw new UsernameNotFoundException("admin: " + username + " do not exist!");
        }
    }

}
