package com.ryuntech.saas.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ryuntech.common.constant.PermInfo;
import com.ryuntech.common.constant.enums.CommonEnums;
import com.ryuntech.common.utils.Result;
import com.ryuntech.saas.api.helper.RequiresPermissions;
import com.ryuntech.saas.api.helper.constant.PermType;
import com.ryuntech.saas.api.model.SysPerm;
import com.ryuntech.saas.api.service.ISysPermService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.reflect.MethodUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Controller;
import org.springframework.util.ClassUtils;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.spring.web.json.Json;

import java.lang.reflect.Method;
import java.util.*;
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
@PermInfo(value = "系统权限模块")
@RestController
@RequestMapping("/perm")
@Api(value = "SysUserController", tags = {"系统权限管理接口"})
public class SysPermController {
    @Autowired
    private ISysPermService permService;


    @Autowired
    private ApplicationContext context;




    @GetMapping("/list/all")
    @ApiOperation(value = "查询详权限按钮/元数据信息")
    public Result listAllPermission() {
        String oper = "list menu,button,api permissions";
        QueryWrapper<SysPerm> params = new QueryWrapper<>();
        params.in("ptype", new Integer[]{PermType.MENU, PermType.BUTTON, PermType.API});
        List<SysPerm> list = permService.list(params);
        if (list.isEmpty()){
            return new Result();
        }else{
            Map<Integer, List<SysPerm>> permMap = list.stream().collect(Collectors.groupingBy(SysPerm::getPtype));
            List<SysPerm> buttonPermList = permMap.get(PermType.BUTTON);
            Map<String, List<SysPerm>> buttonsGroupedByParent = new HashMap<>();
            if (buttonPermList!=null&&!buttonPermList.isEmpty()){
                buttonsGroupedByParent = buttonPermList.stream().collect(Collectors.groupingBy(SysPerm::getParent));
            }
            SysPerm sysPerm = new SysPerm();
            sysPerm.setPermMap(permMap);
            sysPerm.setBtnPermMap(buttonsGroupedByParent);
            return new Result(sysPerm);
        }
    }


    @DeleteMapping
    public Result delete(@RequestBody SysPerm sysPerm) {
        String oper = "delete permission";
        if (org.apache.commons.lang3.StringUtils.isBlank(sysPerm.getPval())) {
            return new Result(CommonEnums.PARAM_ERROR,"无法删除权限：参数为空（权限值）");
        }
        boolean success = permService.removeById(sysPerm.getPval());
        return new Result(success);
    }

    @GetMapping("/list/btn_perm_map")
    @ApiOperation(value = "查询详权限按钮信息")
    public Result listButtonPermMapGroupByParent() {
        String oper = "list btn perm map group by parent";
        List<SysPerm> buttonPermList = permService.list(new QueryWrapper<SysPerm>().eq("ptype",PermType.BUTTON));

        Map<String, List<SysPerm>> buttonsGroupedByParent = new HashMap<>();
        if (buttonPermList!=null&&!buttonPermList.isEmpty()){
            buttonsGroupedByParent = buttonPermList.stream().collect(Collectors.groupingBy(SysPerm::getParent));
        }
        return new Result<>(buttonsGroupedByParent);
    }



    @PostMapping("/sync/menu")
    public Result syncMenuPermission(@RequestBody String body) {
        String oper = "sync menu permission";
        log.info("{}, body: {}", oper, body);
        List<SysPerm> notSyncedPerms = JSON.parseArray(body, SysPerm.class);
        if (!notSyncedPerms.isEmpty()){
            permService.remove(new QueryWrapper<SysPerm>().eq("ptype",PermType.MENU));
            permService.saveOrUpdateBatch(notSyncedPerms);
        }
        return new Result();
    }


    @PostMapping("/sync/api")
    public Result syncApiPermission(@RequestBody String body) {
        String oper = "sync api permission";
        log.info("{}, body: {}", oper, body);
        List<SysPerm> notSyncedPerms = JSON.parseArray(body, SysPerm.class);
        if (!notSyncedPerms.isEmpty()){
            permService.remove(new QueryWrapper<SysPerm>().eq("ptype",PermType.API));
            permService.saveOrUpdateBatch(notSyncedPerms);
        }
        return new Result();
    }

    @GetMapping("/meta/api")
    public Result listApiPermMetadata() {
        String oper = "list api permission metadata";
        log.info(oper);
        final String basicPackage = ClassUtils.getPackageName(this.getClass());
        Map<String, Object> map = context.getBeansWithAnnotation(Controller.class);
        Collection<Object> beans = map.values();
        List<SysPerm> apiList = beans.stream().filter(b -> StringUtils.equals(basicPackage, ClassUtils.getPackageName(b.getClass())))
                .map(bean -> {
                    Class<?> clz = bean.getClass();
                    SysPerm moduleApiPerm = getModulePerm(clz);
                    List<SysPerm> methodApiPerm = getApiPerm(clz, moduleApiPerm.getPval());
                    moduleApiPerm.getChildren().addAll(methodApiPerm);
                    return moduleApiPerm;
                }).collect(Collectors.toList());

        return new Result<>(apiList);
    }


    /**
     * 获取控制器上的注释，生成后台接口模块权限的信息
     *
     * @param clz
     * @return
     */
    public SysPerm getModulePerm(Class<?> clz) {
        SysPerm perm = new SysPerm();
        //首选值
        PermInfo piAnno = AnnotationUtils.getAnnotation(clz, PermInfo.class);
        if (piAnno == null) {
            //由于使用了RequiresPermissions注解的类在运行时会使用动态代理，即clz在运行时是一个动态代理，所以需要getSuperClass获取实际的类型
            piAnno = AnnotationUtils.getAnnotation(clz.getSuperclass(), PermInfo.class);
        }
        String pnamePrimary = null;
        String pvalPrimary = null;
        String pvalPrimary2 = null;
        if (piAnno != null && piAnno.value() != null) {
            pnamePrimary = piAnno.value();
            pvalPrimary = piAnno.pval();
        }

        //备选值1
        RequiresPermissions rpAnno = AnnotationUtils.getAnnotation(clz, RequiresPermissions.class);
        if (rpAnno == null) {
            rpAnno = AnnotationUtils.getAnnotation(clz.getSuperclass(), RequiresPermissions.class);
        }
        if (rpAnno != null) {
            pvalPrimary2 = rpAnno.value()[0];
        }

        //备选值2
        String pnameSub = ClassUtils.getShortName(clz);
        RequestMapping rmAnno = AnnotationUtils.getAnnotation(clz, RequestMapping.class);
        if (rmAnno == null) {
            rmAnno = AnnotationUtils.getAnnotation(clz.getSuperclass(), RequestMapping.class);
        }
        String pvalSub = rmAnno.value()[0];
        //赋值
        if (StringUtils.isNotBlank(pnamePrimary)) {
            perm.setPname(pnamePrimary);
        } else {
            perm.setPname(pnameSub);
        }
        if (StringUtils.isNotBlank(pvalPrimary)) {
            perm.setPval(pvalPrimary);
        }else if(StringUtils.isNotBlank(pvalPrimary2)){
            perm.setPval(pvalPrimary2);
        } else {
            perm.setPval("a:"+pvalSub.substring(1).replace("/",":"));
        }
        perm.setPtype(PermType.API);
        return perm;
    }


    /**
     * 获取控制器上的方法上的注释，生成后台接口权限的信息
     *
     * @param clz
     * @return
     */
    private List<SysPerm> getApiPerm(Class<?> clz,final String parentPval) {
        //获取clz类上有RequiresPermissions注解的所有方法
        List<Method> apiMethods = MethodUtils.getMethodsListWithAnnotation(clz.getSuperclass(), RequiresPermissions.class);
        return apiMethods.stream().map(method -> {
            //pname首选
            //获取method方法上的PermInfo注解的元数据
            PermInfo piAnno = AnnotationUtils.getAnnotation(method, PermInfo.class);
            String pnamePrimary = piAnno!=null?piAnno.value():null;
            //pname备选
            String pnameSub = method.getName();
            //pval值
            //获取method方法上的RequiresPermissions注解的元数据
            RequiresPermissions rpAnno = AnnotationUtils.getAnnotation(method, RequiresPermissions.class);
            SysPerm perm = new SysPerm();
            if (StringUtils.isNotBlank(pnamePrimary)){
                perm.setPname(pnamePrimary);
            }else{
                perm.setPname(pnameSub);
            }
            perm.setParent(parentPval);
            perm.setPtype(PermType.API);
            perm.setPval(rpAnno.value()[0]);
            return perm;
        }).collect(Collectors.toList());
    }


    @PostMapping
    public Result add(@RequestBody SysPerm perm) {
        String oper = "add permission";

        if (org.apache.commons.lang3.StringUtils.isEmpty(perm.getPval())) {
            return  new Result(CommonEnums.PARAM_ERROR,"权限值不能为空");
        }

        QueryWrapper<SysPerm> params = new QueryWrapper<>();
        params.eq("pval", perm.getPval());
        SysPerm permDB = permService.getOne(params);

        if (permDB != null) {
            return  new Result(CommonEnums.PARAM_ERROR,"权限值已存在"+ permDB.getPname() + "（" + perm.getPval() + "）");
        }

        //保存
        perm.setCreated(new Date());
        boolean success = permService.saveOrUpdate(perm);
        return new Result(success);
    }

}
