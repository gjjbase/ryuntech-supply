package com.ryuntech.admin.biz.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ryuntech.admin.api.entity.FinanceUserInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
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
public interface FinanceUserInfoMapper extends BaseMapper<FinanceUserInfo> {
    IPage<FinanceUserInfo> Pages(@Param("page") Page<FinanceUserInfo> page);

    IPage<FinanceUserInfo> pageList(@Param("page") Page<FinanceUserInfo> page);
}
