package com.ryuntech.admin.biz.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ryuntech.admin.api.config.SctUser;
import com.ryuntech.admin.api.entity.Partner;
import com.ryuntech.admin.api.utils.SecurityUtils;
import com.ryuntech.admin.biz.service.IPartnerService;
import com.ryuntech.common.constant.PartnerConstants;
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
        return iPartnerService.pageList(partner,queryPage);

    }

    /**
     * 根据ID查询用户信息
     *
     * @param partnerId
     * @return
     */
    @GetMapping("/{partnerId}")
    @ApiOperation(value = "查询详细融资客户信息", notes = "partnerId存在")
    @ApiImplicitParam(name = "partnerId", value = "用户编号", required = true, dataType = "String")
    public Result<Partner> findById(@PathVariable String partnerId) {
        if (StringUtils.isBlank(partnerId)) {
            return new Result<>();
        } else {
            return new Result<>(iPartnerService.getById(partnerId));
        }
    }

    /**
     * 更新用户信息
     *
     * @param partner
     * @return
     */
    @PostMapping("/edit")
    @ApiOperation(value = "更新合伙人信息")
    @ApiImplicitParam(name = "partner", value = "更新合伙人信息", required = true, dataType = "Partner", paramType = "body")
    public Result edit(@RequestBody Partner partner) {
        iPartnerService.saveOrUpdate(partner);
        return new Result();
    }
    /**
     * 根据ID更新融资客户信息
     *
     * @param partnerId
     * @return
     */
    @GetMapping("/updateById/{partnerId}")
    @ApiOperation(value = "更新融资客户信息", notes = "partnerId存在")
    @ApiImplicitParam(name = "partnerId", value = "用户编号", required = true, dataType = "String")
    public Result  updateById(@PathVariable String partnerId) {
        if (StringUtils.isBlank(partnerId)) {
            return new Result();
        } else {
            Partner partner =new Partner();
            partner.setPartnerId(partnerId);
            SctUser sctUser = SecurityUtils.getUser();
            partner.setReviewerName(sctUser.getUsername());
            partner.setReviewerId(sctUser.getId()+"");
            partner.setStatus(PartnerConstants.AUTHORIZED);
            return new Result(iPartnerService.updateById(partner),"更新成功");
        }
    }
    /**
     * 根据ID查询用户信息
     *
     * @param openId
     * @return
     */
    @GetMapping("/findByOpenId/{openId}")
    @ApiOperation(value = "查询详细融资客户信息", notes = "openId存在")
    @ApiImplicitParam(name = "openId", value = "用户编号", required = true, dataType = "String")
    public Result<Partner> findByOpenId(@PathVariable String openId) {
        if (StringUtils.isBlank(openId)) {
            return new Result<>();
        } else {
            return new Result<>(iPartnerService.findByOpenId(openId));
        }
    }

    /**
     * 注册
     *
     * @param partner
     * @return
     */
    @PostMapping("/outRegister")
    @ApiOperation(value = "用户在注册")
    @ApiImplicitParam(name = "partner", value = "用户实体信息", required = true, dataType = "Partner", paramType = "body")
    public Result register(@RequestBody Partner partner) {
        iPartnerService.register(partner);
        return new Result();
    }
}
