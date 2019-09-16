package com.ryuntech.saas.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ryuntech.common.constant.PermInfo;
import com.ryuntech.common.constant.Root;
import com.ryuntech.common.constant.enums.CommonEnums;
import com.ryuntech.common.utils.QueryPage;
import com.ryuntech.common.utils.Result;
import com.ryuntech.saas.api.helper.RequiresPermissions;
import com.ryuntech.saas.api.helper.SecurityUtils;
import com.ryuntech.saas.api.model.Auth;
import com.ryuntech.saas.api.model.SysPerm;
import com.ryuntech.saas.api.model.SysUser;
import com.ryuntech.saas.api.model.SysUserRole;
import com.ryuntech.saas.api.service.ISysPermService;
import com.ryuntech.saas.api.service.ISysRoleService;
import com.ryuntech.saas.api.service.ISysUserRoleService;
import com.ryuntech.saas.api.service.SysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.spring.web.json.Json;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author antu
 * @date 2019-05-22
 */
@Slf4j
@RestController
@RequestMapping("/user")
@Api(value = "SysUserController", tags = {"用户信息管理接口"})
public class SysUserController extends ModuleBaseController {

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    ISysUserRoleService sysUserRoleService;

    @Autowired
    ISysRoleService sysRoleService;
    @Autowired
    ISysPermService sysPermService;

    /**
     * 获取当前用户信息
     *
     * @return
     */
    @GetMapping("/info")
    @ApiOperation(value = "获取当前授权用户信息", notes = "必须经过了OAuth授权")
    public Result<SysUser> info() {
        String username = getUserName();
        SysUser user = sysUserService.findByName(username);
        if (user == null) {
            return new Result<>(CommonEnums.USER_ERROR);
        }
        if (user != null) {
            //用户对应的角色rids角色Id
            List<SysPerm> permissions = sysPermService.getPermsByUserId(user.getId()+"");
            putAuth(user, permissions);
        }
        return new Result<>(user);
    }

    /**
     * 获取当前用户信息
     *
     * @return
     */
    @GetMapping("/cusinfo")
    @ApiOperation(value = "获取当前授权用户信息", notes = "必须经过了OAuth授权")
    public Result<SysUser> cusinfo() {
        String username = SecurityUtils.getUsername();
        SysUser user = sysUserService.findByName(username);
        if (user == null) {
            return new Result<>(CommonEnums.USER_ERROR);
        }
        if (user != null) {
            //用户对应的角色rids角色Id
            List<SysPerm> permissions = sysPermService.getPermsByUserId(user.getId()+"");
            putAuth(user, permissions);
        }
        return new Result<>(user);
    }

    public static void putAuth(SysUser user, List<SysPerm> permissions) {
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        Set<Auth> perms = new HashSet<>();
        for (SysPerm permission : permissions) {
            if (permission != null && permission.getPval()!=null) {
                GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(permission.getPval());
                //1：此处将权限信息添加到 GrantedAuthority 对象中，在后面进行全权限验证时会使用GrantedAuthority 对象。
                grantedAuthorities.add(grantedAuthority);
                Auth auth = new Auth();
                auth.setName(permission.getPname());
                auth.setVal(permission.getPval());
                perms.add(auth);
            }
        }
        user.setPerms(perms);
        user.setGrantedAuthorities(grantedAuthorities);
    }

    /**
     * 根据用户名查询用户信息
     *
     * @param username
     * @return
     */
    @GetMapping("/info/{username}")
    @ApiOperation(value = "根据用户名查询用户信息")
    @ApiImplicitParam(name = "username", value = "用户名", required = true, dataType = "String")
    public Result<SysUser> info(@PathVariable("username") String username) {
        return new Result<>(sysUserService.findByName(username));
    }

    /**
     * 分页查询列表数据，条件查询
     *
     * @param user
     * @return
     */


    @PostMapping("/list")
    @ApiOperation(value = "分页、条件查询用户列表信息")
    @ApiImplicitParam(name = "user", value = "查询条件", required = true, dataType = "SysUser", paramType = "body")
    public Result<IPage<SysUser>> list(SysUser user, QueryPage queryPage) {
        log.info("order.getOrderid()"+user.getUsername());
        log.info("order.getOrderid()"+user.getId());
        return  new Result<>(sysUserService.selectUsersRoleById(user, queryPage));
//        return new Result<Map>(this.selectByPageNumSize(queryPage, () -> sysUserService.list(user)));
    }
    /*@PostMapping("/list")
    @ApiOperation(value = "分页、条件查询用户列表信息")
    @ApiImplicitParam(name = "user", value = "查询条件", required = true, dataType = "SysUser", paramType = "body")
    public Result<Map> list(SysUser user, QueryPage queryPage) {
        log.info("order.getOrderid()"+user.getUsername());
        log.info("order.getOrderid()"+user.getId());
        return  new Result<>(sysUserService.selectUsersRoleById(user, queryPage));
//        return new Result<Map>(this.selectByPageNumSize(queryPage, () -> sysUserService.list(user)));
    }*/


    /**
     * 根据ID查询用户信息
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    @ApiOperation(value = "查询详细用户信息", notes = "id存在且大于0")
    @ApiImplicitParam(name = "id", value = "用户编号", required = true, dataType = "Long")
    public Result<SysUser> findById(@PathVariable Long id) {
        if (id == null || id == 0) {
            return new Result<>();
        } else {
            return new Result<>(sysUserService.getById(id));
        }
    }

    /**
     * 添加用户信息
     *
     * @param user
     * @return
     */
    @PostMapping
    @ApiOperation(value = "添加用户")
    @ApiImplicitParam(name = "user", value = "用户实体信息", required = true, dataType = "SysUser", paramType = "body")
    public Result add(@RequestBody SysUser user) {
        sysUserService.create(user);
        return new Result();
    }

    /**
     * 删除用户信息
     *
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    @ApiOperation(value = "删除用户")
    @ApiImplicitParam(name = "id", value = "用户编号", required = true, dataType = "Long")
    public Result delete(@PathVariable Long id) {
        sysUserService.removeById(id);
        return new Result();
    }

    /**
     * 更新用户信息
     *
     * @param user
     * @return
     */
    @PutMapping("/edit")
    @ApiOperation(value = "更新用户")
    @ApiImplicitParam(name = "user", value = "用户实体信息", required = true, dataType = "SysUser", paramType = "body")
    public Result edit(@RequestBody SysUser user) {
        sysUserService.update(user);
        return new Result();
    }

    /**
     * 修改密码
     *
     * @param user
     * @return
     */
    @PostMapping("/changePass")
    @ApiOperation(value = "修改密码")
    @ApiImplicitParam(name = "user", value = "用户实体信息", required = true, dataType = "SysUser", paramType = "body")
    public Result changePass(@RequestBody SysUser user) {
        sysUserService.changePass(user);
        return new Result();
    }


    @PermInfo("更新系统用户的角色")
//    @RequiresPermissions("a:sys:user:role:update")
    @PatchMapping("/role")
    public Result updateUserRole(@RequestBody String body) {

        String oper = "update user's roles";
        log.info("{}, body: {}",oper,body);

        JSONObject json = JSON.parseObject(body);
        final String uid = json.getString("uid");

        List<String> rids = json.getJSONArray("rids").toJavaList(String.class);

        //检查：不能含有管理员角色
        boolean containRoot = sysRoleService.checkRidsContainRval(rids, Root.ROLE_VAL);
        if (containRoot){
            return new Result<>(CommonEnums.PARAM_ERROR,"不能给非管理员用户赋予管理员角色");
        }

        //删除：原来绑定的角色
        boolean deleteSucc = sysUserRoleService.remove(new QueryWrapper<SysUserRole>().eq("user_id", uid));
        if (!deleteSucc) {
            return new Result<>(CommonEnums.PARAM_ERROR,"无法解除原来的用户-角色关系");
        }

        //更新：绑定新的角色
        List<SysUserRole> list = rids.stream().map(roleId -> new SysUserRole(uid, roleId)).collect(Collectors.toList());

        if (!rids.isEmpty()){
            boolean addSucc = sysUserRoleService.saveBatch(list);
            return new Result<>(addSucc);
        }
        return new Result<>();
    }

}
