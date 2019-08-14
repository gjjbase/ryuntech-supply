package com.ryuntech.admin.biz.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ryuntech.common.utils.Result;

public interface IBaseService<T> extends IService<T> {
    Result<IPage<T>> pages(Page<T> page);

    Result<IPage<T>> pageList(Page<T> page);
}
