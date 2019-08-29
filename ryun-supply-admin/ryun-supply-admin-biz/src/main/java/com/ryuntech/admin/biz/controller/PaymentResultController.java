package com.ryuntech.admin.biz.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ryuntech.admin.api.entity.PaymentResult;
import com.ryuntech.admin.biz.service.IPaymentResultService;
import com.ryuntech.common.constant.PayResultConstant;
import com.ryuntech.common.utils.QueryPage;
import com.ryuntech.common.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author antu
 * @since 2019-08-27
 */
@Slf4j
@RestController
@RequestMapping("/paymentResult")
@Api(value = "PaymentResultController", tags = {"佣金结算接口"})
public class PaymentResultController {
    @Autowired
    private IPaymentResultService iPaymentResultService;


    /**
     * 分页查询列表数据，条件查询
     *
     * @param paymentResult
     * @return
     */
    @PostMapping("/list")
    @ApiOperation(value = "分页、条件查询佣金结算列表信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "paymentResult", value = "查询条件", dataType = "PaymentResult", paramType = "body"),
            @ApiImplicitParam(name="queryPage",value="分页信息",dataType="QueryPage", paramType = "body")
    })
    public Result<IPage<PaymentResult>> list(@RequestBody PaymentResult paymentResult, QueryPage queryPage) {
        return iPaymentResultService.selectPageList(paymentResult,queryPage);
    }



    /**
     * 根据ID更新融结算结果信息
     *
     * @param paymentSystemId
     * @return
     */
    @GetMapping("/updateById/{paymentSystemId}")
    @ApiOperation(value = "更新融资客户信息", notes = "paymentSystemId存在")
    @ApiImplicitParam(name = "paymentSystemId", value = "结算结果编号", required = true, dataType = "String")
    public Result  updateById(@PathVariable String paymentSystemId) {
        log.info("updateById"+paymentSystemId);
        if (StringUtils.isBlank(paymentSystemId)) {
            return new Result();
        } else {
            PaymentResult paymentResult =new PaymentResult();
            paymentResult.setPaymentSystemId(paymentSystemId);
            paymentResult.setPaymentStatus(PayResultConstant.SETTLE);
            return new Result(iPaymentResultService.updateById(paymentResult),"结算成功");
        }
    }
}
