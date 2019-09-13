package com.ryuntech.saas.api.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ryuntech.saas.api.model.Order;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author antu
 * @since 2019-08-14
 */
//@Mapper
//@Repository
public interface OrderMapper extends IBaseMapper<Order> {
    IPage<Order> selectPageList(@Param("pg") Page<Order> page, @Param("order") Order order);

}
