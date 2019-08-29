package com.ryuntech.admin.biz.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ryuntech.admin.api.entity.Order;
import com.ryuntech.admin.biz.mapper.OrderMapper;
import com.ryuntech.admin.biz.service.IOrderService;
import com.ryuntech.common.utils.QueryPage;
import com.ryuntech.common.utils.Result;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author antu
 * @since 2019-08-14
 */
@Service
public class OrderServiceImpl extends BaseServiceImpl<OrderMapper, Order> implements IOrderService {
    @Override
    public Result<IPage<Order>> pageList(Order order, QueryPage queryPage) {
        Page<Order> page = new Page<>(queryPage.getPageCode(), queryPage.getPageSize());
        if (order.getOrderid()!=null)
            queryWrapper.eq("order_id", order.getOrderid());
        return super.pageList(queryWrapper,page);
    }

    @Override
    public Result<IPage<Order>> selectPageList(Order order,QueryPage queryPage) {
        Page<Order> page = new Page<>(queryPage.getPageCode(), queryPage.getPageSize());
        return new Result(m.selectPageList(page,order));
    }

}
