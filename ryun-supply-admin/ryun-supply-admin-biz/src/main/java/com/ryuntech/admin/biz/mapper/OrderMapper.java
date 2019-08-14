package com.ryuntech.admin.biz.mapper;

import com.ryuntech.admin.api.entity.Order;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author ryun
 * @since 2019-08-14
 */
@Mapper
@Repository
public interface OrderMapper extends IBaseMapper<Order> {

}
