package com.ryuntech.generator.service.impl;

import com.ryuntech.generator.entity.Order;
import com.ryuntech.generator.mapper.OrderMapper;
import com.ryuntech.generator.service.IOrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements IOrderService {

}
