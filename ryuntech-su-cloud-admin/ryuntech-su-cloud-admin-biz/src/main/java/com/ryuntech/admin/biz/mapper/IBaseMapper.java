package com.ryuntech.admin.biz.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;


public interface IBaseMapper<T> extends BaseMapper<T> {
    IPage<T> Pages(@Param("page") Page<T> page);

    IPage<T> pageList(@Param("page") Page<T> page);
}
