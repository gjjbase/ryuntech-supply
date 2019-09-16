package com.ryuntech.common.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ryuntech.common.utils.Result;

/**
 * @author EDZ
 */
public interface IBaseService<T> extends IService<T> {


    Result<IPage<T>> pageList(Page<T> page);

    /**
     * 根据条件分页查询
     * @param queryWrapper
     * @param page
     * @return
     */
    Result<IPage<T>> pageList(QueryWrapper<T> queryWrapper, Page<T> page);

    T saveOrUpdateReturn(T t, QueryWrapper<T> queryWrapper);
}
