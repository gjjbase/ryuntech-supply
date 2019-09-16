package com.ryuntech.saas.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ryuntech.common.utils.Result;
import com.ryuntech.saas.api.dto.Option;
import com.ryuntech.saas.api.model.SysRole;
import com.ryuntech.saas.api.service.ISysRoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.stream.Collectors;

/**
 * created by CaiBaoHong at 2018/4/17 16:41<br>
 * @author EDZ
 */
//@PermInfo(value = "选项模块", pval = "a:option")
@RestController
@RequestMapping("/option")
public class OptionController {

    private static final Logger log = LoggerFactory.getLogger(OptionController.class);

    @Autowired
    private ISysRoleService sysRoleService;

    @GetMapping("/role")
    public Result listRoleOptions() {
        String oper = "list role options";
        log.info(oper);

        QueryWrapper<SysRole> params = new QueryWrapper<>();
        params.select("rid,rname,rval");

        List<SysRole> list = sysRoleService.list(params);
        List<Option> options = list.stream().map(obj -> new Option(obj.getRid(), obj.getRname(),obj.getRval())).collect(Collectors.toList());
        return new Result(options);
    }

}
