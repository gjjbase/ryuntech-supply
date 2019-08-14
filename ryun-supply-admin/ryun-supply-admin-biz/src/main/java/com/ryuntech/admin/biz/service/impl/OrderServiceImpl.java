package com.ryuntech.admin.biz.service.impl;

import com.ryuntech.admin.api.entity.Order;
import com.ryuntech.admin.biz.mapper.OrderMapper;
import com.ryuntech.admin.biz.service.IOrderService;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ryun
 * @since 2019-08-14
 */
@Service
public class OrderServiceImpl extends BaseServiceImpl<OrderMapper, Order> implements IOrderService {

}
