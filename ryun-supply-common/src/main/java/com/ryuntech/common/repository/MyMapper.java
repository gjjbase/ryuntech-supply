package com.ryuntech.common.repository;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
 * 通用Mapper
 *
 * @author antu
 * @date 2019-05-22
 */
public interface MyMapper<T> extends Mapper<T>, MySqlMapper<T> {
}
