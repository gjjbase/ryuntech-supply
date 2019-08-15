package com.ryuntech.admin.biz.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ryuntech.admin.api.entity.Partner;
import com.ryuntech.admin.biz.service.IPartnerService;
import com.ryuntech.common.utils.QueryPage;
import com.ryuntech.common.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  合伙人信息前端控制器
 * </p>
 *
 * @author antu
 * @since 2019-08-15
 */

@RestController
@RequestMapping("/partner")
@Api(value = "PartnerController", tags = {"合伙人管理接口"})
public class PartnerController extends BaseController {
    @Autowired
    private IPartnerService iPartnerService;

    /**
     * 分页查询列表数据，条件查询
     *
     * @param partner
     * @return
     */
    @PostMapping("/list")
    @ApiOperation(value = "分页、条件查询用户列表信息")
    @ApiImplicitParam(name = "partner", value = "查询条件", required = true, dataType = "Partner", paramType = "body")
    public Result<IPage<Partner>> list(Partner partner, QueryPage queryPage) {
        Page<Partner> page = new Page<>(queryPage.getPageCode(), queryPage.getPageSize());
        return iPartnerService.pageList(page);
    }

    /**
     * 根据ID查询用户信息
     *
     * @param partnerId
     * @return
     */
    @GetMapping("/{partnerId}")
    @ApiOperation(value = "查询详细融资客户信息", notes = "orderId存在")
    @ApiImplicitParam(name = "partnerId", value = "用户编号", required = true, dataType = "String")
    public Result<Partner> findById(@PathVariable String partnerId) {
        if (StringUtils.isBlank(partnerId)) {
            return new Result<>();
        } else {
            return new Result<>(iPartnerService.getById(partnerId));
        }
    }
/*
    *//**
     * 添加用户信息
     *
     * @param partner
     * @return
     *//*
    @PostMapping
    @ApiOperation(value = "添加用户")
    @ApiImplicitParam(name = "partner", value = "用户实体信息", required = true, dataType = "Partner", paramType = "body")
    public Result add(@RequestBody Partner partner) {
        partner.setPartnerId(generateId()+"");
        iPartnerService.save(partner);
        return new Result();
    }*/


    /**
     * 注册
     *
     * @param partner
     * @return
     */
    @PostMapping
    @ApiOperation(value = "用户在注册")
    @ApiImplicitParam(name = "partner", value = "用户实体信息", required = true, dataType = "Partner", paramType = "body")
    public Result register(@RequestBody Partner partner) {
        partner.setPartnerId(generateId()+"");
        iPartnerService.save(partner);
        return new Result();
    }
}
