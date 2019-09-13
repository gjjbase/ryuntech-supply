package com.ryuntech.saas.controller;


import com.alibaba.fastjson.JSON;
import com.ryuntech.common.constant.PermInfo;
import com.ryuntech.saas.api.model.SysPerm;
import com.ryuntech.saas.api.service.ISysPermService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.spring.web.json.Json;

import java.util.Date;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author antu
 * @since 2019-09-12
 */
@PermInfo(value = "系统权限模块")
@RestController
@RequestMapping("/rolePerm")
@Api(value = "SysUserController", tags = {"系统权限管理接口"})
public class SysRolePermController {
    @Autowired
    private ISysPermService permService;

   /* @PostMapping
    public Json add(@RequestBody String body) {

        String oper = "add permission";
        SysPerm perm = JSON.parseObject(body, SysPerm.class);

        if (StringUtils.isEmpty(perm.getPval())) {
            return Json.fail(oper, "权限值不能为空");
        }

        EntityWrapper<SysPerm> params = new EntityWrapper<>();
        params.eq("pval", perm.getPval());
        params.setSqlSelect("pname,pval");
        SysPerm permDB = permService.selectOne(params);

        if (permDB != null) {
            return Json.fail(oper, "权限值已存在：" + permDB.getPname() + "（" + perm.getPval() + "）");
        }

        //保存
        perm.setCreated(new Date());
        boolean success = permService.insert(perm);
        return Json.result(oper, success)
                .data("created", perm.getCreated());
    }*/
}
