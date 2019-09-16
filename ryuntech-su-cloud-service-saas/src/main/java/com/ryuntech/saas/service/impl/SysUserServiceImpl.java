package com.ryuntech.saas.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ryuntech.common.service.impl.BaseServiceImpl;
import com.ryuntech.common.utils.QueryPage;
import com.ryuntech.saas.api.helper.SecurityUtils;
import com.ryuntech.saas.api.mapper.SysUserMapper;
import com.ryuntech.saas.api.model.Order;
import com.ryuntech.saas.api.model.SysUser;
import com.ryuntech.saas.api.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author antu
 * @date 2019-05-22
 */
@Service
public class SysUserServiceImpl  extends BaseServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    @Autowired
    private SysUserMapper sysUserMapper;

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public SysUser findByName(String username) {
        SysUser sysUser = sysUserMapper.selectOne(new QueryWrapper<SysUser>().eq("username", username));
        return sysUser;
    }

    @Override
    public List<SysUser> list(SysUser user) {
        QueryWrapper queryWrapper=null;
        if (user.getUsername() != null && !user.getUsername().isEmpty()) {
             queryWrapper= new QueryWrapper<SysUser>().eq("username", user.getUsername());
        }
        //插入权限数据
        return sysUserMapper.selectList(queryWrapper);
    }

    @Override
    public void create(SysUser user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        this.save(user);
    }

    @Override
    public void update(SysUser user) {
        user.setPassword(null);
        sysUserMapper.updateById(user);
    }

    @Override
    public void changePass(SysUser user) {
        SysUser entity = new SysUser();
        SysUser sysUser = this.findByName(SecurityUtils.getUsername());
        if (sysUser != null) {
            entity.setId(sysUser.getId());
            entity.setPassword(passwordEncoder.encode(user.getPassword()));
            sysUserMapper.updateById(entity);
        }
    }

    @Override
    public SysUser selectUserRoleById(SysUser user) {
        return sysUserMapper.selectUserRoleById(user);
    }

    @Override
    public IPage<SysUser> selectUsersRoleById(SysUser user, QueryPage queryPage) {

        Page<SysUser> page = new Page<>(queryPage.getPageCode(), queryPage.getPageSize());
        if (user.getId()!=null) {
            queryWrapper.eq("id", user.getId());
        }
        if (user.getUsername()!=null) {
            queryWrapper.eq("username", user.getUsername());
        }
        return sysUserMapper.selectUsersRoleById(page,user);
    }
}
