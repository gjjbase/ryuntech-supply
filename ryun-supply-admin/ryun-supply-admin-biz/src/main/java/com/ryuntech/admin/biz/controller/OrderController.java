package com.ryuntech.admin.biz.controller;


import io.swagger.annotations.Api;
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
@RestController
@RequestMapping("/order")
@Api(value = "OrderController", tags = {"推荐订单管理接口"})
public class OrderController {

}
