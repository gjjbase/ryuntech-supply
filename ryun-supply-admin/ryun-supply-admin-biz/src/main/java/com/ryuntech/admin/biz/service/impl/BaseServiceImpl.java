package com.ryuntech.admin.biz.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ryuntech.admin.biz.mapper.IBaseMapper;
import com.ryuntech.admin.biz.service.IBaseService;
import com.ryuntech.common.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;

public    class BaseServiceImpl <M extends IBaseMapper<T>, T> extends ServiceImpl<M, T>  implements IBaseService<T> {

    @Autowired
    M m;
    QueryWrapper<T> queryWrapper =new QueryWrapper<>();

    @Override
    public Result<IPage<T>> pageList(Page<T> page) {
        IPage<T> userIPage =   m.Pages(page);
        return new Result(userIPage);
    }


    @Override
    public Result<IPage<T>> pageList(QueryWrapper<T> queryWrapper, Page<T> page) {
        IPage<T> iPage = m.selectPage(page, queryWrapper);
        return new Result(iPage);
    }

}
