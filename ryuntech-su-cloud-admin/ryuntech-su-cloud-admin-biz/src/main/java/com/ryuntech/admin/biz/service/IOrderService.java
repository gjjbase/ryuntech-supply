package com.ryuntech.admin.biz.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ryuntech.admin.api.entity.Order;
import com.ryuntech.common.utils.QueryPage;
import com.ryuntech.common.utils.Result;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author antu
 * @since 2019-08-14
 */
public interface IOrderService extends IBaseService<Order> {
    Result<IPage<Order>> pageList(Order order, QueryPage queryPage);
    Result<IPage<Order>> selectPageList(  Order order,QueryPage queryPage);

    Order settlement(String orderid);
    Order loan(Order order);
    Order refuse(String orderid);
}
