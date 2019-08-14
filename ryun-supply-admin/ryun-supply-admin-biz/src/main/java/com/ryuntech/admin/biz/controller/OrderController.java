package com.ryuntech.admin.biz.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ryuntech.admin.api.entity.Order;
import com.ryuntech.admin.biz.service.IOrderService;
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
 *  前端控制器
 * </p>
 *
 * @author ryun
 * @since 2019-08-14
 */
@RestController
@RequestMapping("/order")
@Api(value = "OrderController", tags = {"推荐订单管理接口"})
public class OrderController {
    @Autowired
    private IOrderService iOrderService;

    /**
     * 分页查询列表数据，条件查询
     *
     * @param order
     * @return
     */
    @PostMapping("/list")
    @ApiOperation(value = "分页、条件查询用户列表信息")
    @ApiImplicitParam(name = "order", value = "查询条件", required = true, dataType = "Order", paramType = "body")
    public Result<IPage<Order>> list(Order order, QueryPage queryPage) {
        Page<Order> page = new Page<>(queryPage.getPageCode(), queryPage.getPageSize());
        return iOrderService.pageList(page);
    }

    /**
     * 根据ID查询用户信息
     *
     * @param orderId
     * @return
     */
    @GetMapping("/{orderId}")
    @ApiOperation(value = "查询详细融资客户信息", notes = "orderId存在")
    @ApiImplicitParam(name = "orderId", value = "用户编号", required = true, dataType = "String")
    public Result<Order> findById(@PathVariable String orderId) {
        if (StringUtils.isBlank(orderId)) {
            return new Result<>();
        } else {
            return new Result<>(iOrderService.getById(orderId));
        }
    }

}
