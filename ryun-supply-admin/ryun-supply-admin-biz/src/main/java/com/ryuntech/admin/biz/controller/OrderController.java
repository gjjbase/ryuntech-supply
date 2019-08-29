package com.ryuntech.admin.biz.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ryuntech.admin.api.entity.Order;
import com.ryuntech.admin.biz.service.IOrderService;
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
 * @since 2019-08-14
 */
@Slf4j
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
    @ApiImplicitParams({
            @ApiImplicitParam(name = "order", value = "查询条件", dataType = "Order", paramType = "body"),
            @ApiImplicitParam(name="queryPage",value="分页信息",dataType="QueryPage", paramType = "body")
    })
    public Result<IPage<Order>> list(@RequestBody Order order, QueryPage queryPage) {
        log.info("order.getOrderid()"+order.getOrderid());
        return  iOrderService.selectPageList(order, queryPage);
    }

    /**
     * 分页查询列表数据，条件查询
     *
     * @param order
     * @return
     */
    @PostMapping("/outList")
    @ResponseBody
    @ApiOperation(value = "分页、条件查询用户列表信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "order", value = "查询条件", dataType = "Order", paramType = "body"),
            @ApiImplicitParam(name="queryPage",value="分页信息",dataType="QueryPage", paramType = "body")
    })
    public Result<IPage<Order>> outList(Order order, QueryPage queryPage) {
        return iOrderService.pageList(order,queryPage);
    }


    /**
     * 删除订单信息
     *
     * @param orderid
     * @return
     */
    @DeleteMapping("/{orderid}")
    @ApiOperation(value = "删除订单信息")
    @ApiImplicitParam(name = "orderid", value = "订单编号", required = true, dataType = "String")
    public Result delete(@PathVariable String orderid) {
        return new Result(iOrderService.removeById(orderid));
    }

    /**
     * 根据ID查询用户信息
     *
     * @param orderid
     * @return
     */
    @GetMapping("/findById/{orderid}")
    @ApiOperation(value = "查询详细融资客户信息", notes = "orderId存在")
    @ApiImplicitParam(name = "orderId", value = "用户编号", required = true, dataType = "String")
    public Result<Order> findById(@PathVariable String orderid) {
        if (StringUtils.isBlank(orderid)) {
            return new Result<>();
        } else {
            return new Result<>(iOrderService.getById(orderid));
        }
    }


    /**
     * 根据ID查询用户信息
     *
     * @param userId
     * @return
     */
    @GetMapping("/findByOrderId/{userId}")
    @ApiOperation(value = "同级合伙人的订单信息", notes = "userId存在")
    @ApiImplicitParam(name = "userId", value = "用户编号", required = true, dataType = "String")
    public Result<Order> findByOrderId(@PathVariable String userId) {
        if (StringUtils.isBlank(userId)) {
            return new Result<>();
        } else {
            return new Result<>(iOrderService.getById(userId));
        }
    }


}
