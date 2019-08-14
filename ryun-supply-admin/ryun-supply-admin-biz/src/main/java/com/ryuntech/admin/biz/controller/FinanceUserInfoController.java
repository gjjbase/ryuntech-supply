package com.ryuntech.admin.biz.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ryuntech.admin.api.entity.FinanceUserInfo;
import com.ryuntech.admin.biz.service.IFinanceUserInfoService;
import com.ryuntech.common.controller.BaseController;
import com.ryuntech.common.utils.QueryPage;
import com.ryuntech.common.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author ryun
 * @since 2019-08-14
 */
@Slf4j
@RestController
@RequestMapping("/financeUserInfo")
@Api(value = "FinanceUserInfoController", tags = {"融资客户信息管理接口"})
public class FinanceUserInfoController extends BaseController {
    @Autowired
    private IFinanceUserInfoService iFinanceUserInfoService;

    /**
     * 分页查询列表数据，条件查询
     *
     * @param financeUserInfo
     * @return
     */
    @PostMapping("/list")
    @ApiOperation(value = "分页、条件查询用户列表信息")
    @ApiImplicitParam(name = "financeUserInfo", value = "查询条件", required = true, dataType = "SysUser", paramType = "body")
    public Result<IPage<FinanceUserInfo>> list(FinanceUserInfo financeUserInfo, QueryPage queryPage) {
        Page<FinanceUserInfo> page = new Page<>(queryPage.getPageCode(), queryPage.getPageSize());
        return iFinanceUserInfoService.pageList(page);
    }
}
