package com.ryuntech.saas.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ryuntech.common.constant.PermInfo;
import com.ryuntech.common.constant.enums.CommonEnums;
import com.ryuntech.common.utils.QueryPage;
import com.ryuntech.common.utils.Result;
import com.ryuntech.saas.api.dto.RolePermDTO;
import com.ryuntech.saas.api.dto.UpdateRolePermDTO;
import com.ryuntech.saas.api.helper.constant.PermType;
import com.ryuntech.saas.api.model.*;
import com.ryuntech.saas.api.service.ISysPermService;
import com.ryuntech.saas.api.service.ISysRolePermService;
import com.ryuntech.saas.api.service.ISysRoleService;
import com.ryuntech.saas.api.service.ISysUserRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author antu
 * @since 2019-09-12
 */
@Slf4j
@PermInfo(value = "系统角色模块")
@RestController
@RequestMapping("/role")
@Api(value = "SysRoleController", tags = {"用户角色管理接口"})
public class SysRoleController extends ModuleBaseController {
    @Autowired
    private ISysRoleService roleService;
    @Autowired
    private ISysPermService permService;
    @Autowired
    private ISysUserRoleService userRoleService;
    @Autowired
    private ISysRolePermService rolePermService;


    @PostMapping
    @ApiOperation(value = "添加权限")
    @ApiImplicitParam(name = "role", value = "用户实体信息", required = true, dataType = "SysRole", paramType = "body")
    public Result add(@RequestBody SysRole role) {


        if (StringUtils.isBlank(role.getRval())) {
            return new Result<SysRole>(CommonEnums.PARAM_ERROR);
        }

        SysRole roleDB = roleService.getOne(new QueryWrapper<SysRole>().eq("rval", role.getRval()));
        if (roleDB != null) {
            return new Result<SysRole>(CommonEnums.PARAM_ERROR,"权限名");
        }
        //保存新用户数据
        role.setCreated(new Date());
        String rid = String.valueOf(generateId());
        role.setRid(rid);
        return new Result(roleService.saveOrUpdateReturn(role,new QueryWrapper<SysRole>().eq("rid", rid)));
    }
    @DeleteMapping
    @ApiOperation(value = "删除权限")
    @ApiImplicitParam(name = "role", value = "权限对象", required = true, dataType = "SysRole")
    public Result delete(@RequestBody SysRole role) {

        String oper = "delete role";
        log.info("{}, body: {}", oper, role);


        if (StringUtils.isBlank(role.getRid())) {
            return new Result<>(CommonEnums.PARAM_ERROR,"权限");
        }
        return new Result(roleService.removeById(role.getRid()));
    }

    @PostMapping("/query")
    @ApiOperation(value = "分页、条件查询角色列表信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sysRole", value = "查询条件", dataType = "SysRole", paramType = "body"),
            @ApiImplicitParam(name="queryPage",value="分页信息",dataType="QueryPage", paramType = "body")
    })
    public Result<IPage<SysRole>> query(@RequestBody SysRole sysRole, QueryPage queryPage) {
        return roleService.pageList(sysRole,queryPage);
    }

    @PatchMapping("/info")
    public Result update(@RequestBody SysRole role) {

        if (StringUtils.isBlank(role.getRval())) {
            return new Result(CommonEnums.PARAM_ERROR);
        }

        SysRole roleDB = roleService.getOne(new QueryWrapper<SysRole>().eq("rval", role.getRval()));
        if (roleDB == null) {
            return new Result(CommonEnums.PARAM_ERROR,"无法更新角色：");
        }
        role.setUpdated(new Date());
        return new Result(roleService.saveOrUpdateReturn(role,new QueryWrapper<SysRole>().eq("rid", role.getRid())));
    }

/*
    @PatchMapping("/perm")
    public Json updateRolePerm(@RequestBody UpdateRolePermVo vo) {

        String oper = "update role's permissions";

        if (StringUtils.isBlank(vo.getRid())) {
            return Json.fail(oper, "无法更新角色的权限：参数为空（角色id）");
        }
        if (vo.getPtype()==null){
            return Json.fail(oper, "无法更新角色的权限：参数为空（权限类型）");
        }
        final String rid = vo.getRid();
        final Integer ptype = vo.getPtype();
        final List<String> pvals = vo.getPvals();

        Wrapper<SysRolePerm> deleteRelationParam = new EntityWrapper<SysRolePerm>().eq("role_id", rid).eq("perm_type", ptype);
        boolean deleteRelationSucc = rolePermService.delete(deleteRelationParam);
        if (!deleteRelationSucc) return Json.fail(oper, "无法解除原来的角色-权限关系");

        if (!pvals.isEmpty()){
            List<SysRolePerm> list = vo.getPvals().stream().map(pval -> new SysRolePerm(rid, pval,ptype)).collect(Collectors.toList());
            boolean addSucc = rolePermService.insertBatch(list);
            return Json.result(oper, addSucc);
        }
        return Json.succ(oper);
    }

    @DeleteMapping("/perm")
    public Json deletePerm(@RequestBody String body){
        String oper = "delete role's permissions";

        JSONObject json = JSON.parseObject(body);
        String rid = json.getString("rid");
        Integer ptype = json.getInteger("ptype");
        String pval = json.getString("pval");

        Wrapper<SysRolePerm> deleteParam = new EntityWrapper<SysRolePerm>()
                .eq("role_id", rid)
                .eq("perm_val", pval)
                .eq("perm_type", ptype);
        boolean success = rolePermService.delete(deleteParam);
        return Json.succ(oper,success);
    }
*/

    @GetMapping("/{rid}/perms")
    public Result findRolePerms(@PathVariable String rid){
        String oper = "find role perms";
        log.info("{}, rid: {}", oper, rid);
        if (StringUtils.isBlank(rid)){
           return new Result(CommonEnums.PARAM_ERROR,"无法查询当前角色的权限值：");
        }
        SysRole role = roleService.getById(rid);
        List<SysPerm> perms = permService.getPermsByRoleId(rid);
        Map<Integer, List<SysPerm>> permMap = perms.stream().collect(Collectors.groupingBy(SysPerm::getPtype));

        List<String> menuPvals = permMap.getOrDefault(PermType.MENU, new ArrayList<>()).stream()
                .filter(perm->perm.getLeaf()==true).map(SysPerm::getPval).collect(Collectors.toList());

        List<String> btnPvals = permMap.getOrDefault(PermType.BUTTON, new ArrayList<>()).stream()
                .map(SysPerm::getPval).collect(Collectors.toList());

        List<String> apiPvals = permMap.getOrDefault(PermType.API, new ArrayList<>()).stream()
                .filter(perm->perm.getLeaf()==true).map(SysPerm::getPval).collect(Collectors.toList());
        RolePermDTO rolePermDTO = new RolePermDTO();
        rolePermDTO.setRole(role);
        rolePermDTO.setMenuPvals(menuPvals);
        rolePermDTO.setApiPvals(apiPvals);
        rolePermDTO.setBtnPvals(btnPvals);
        return new Result(rolePermDTO);
    }



    @PostMapping("/perm")
    public Result addPerm(@RequestBody SysRolePerm sysRolePerm){
        String oper = "add role's permissions";
        boolean success = rolePermService.saveOrUpdate(sysRolePerm);
        if (success)
        {
            return new Result(sysRolePerm);
        }
        return null;
    }

    @PatchMapping("/perm")
    public Result updateRolePerm(@RequestBody UpdateRolePermDTO dto) {

        String oper = "update role's permissions";

        if (org.apache.commons.lang3.StringUtils.isBlank(dto.getRid())) {
            return new Result<>(CommonEnums.PARAM_ERROR,"无法更新角色的权限：参数为空（角色id）");
        }
        if (dto.getPtype()==null){
            return new Result<>(CommonEnums.PARAM_ERROR,"无法更新角色的权限：参数为空（权限类型）");
        }
        final String rid = dto.getRid();
        final Integer ptype = dto.getPtype();
        final List<String> pvals = dto.getPvals();

        QueryWrapper<SysRolePerm> deleteRelationParam = new QueryWrapper<SysRolePerm>().eq("role_id", rid).eq("perm_type", ptype);
        boolean deleteRelationSucc = rolePermService.remove(deleteRelationParam);
        if (!deleteRelationSucc) {
            return new Result<>(CommonEnums.OPERATE_ERROR,"无法解除原来的角色-权限关系");
        }

        if (!pvals.isEmpty()){
            List<SysRolePerm> list = dto.getPvals().stream().map(pval -> new SysRolePerm(rid, pval,ptype)).collect(Collectors.toList());
            boolean addSucc = rolePermService.saveBatch(list);
            return new Result(addSucc);
        }
        return new Result();
    }
}
