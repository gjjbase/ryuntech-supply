package com.ryuntech.admin.biz.mapper;

import com.ryuntech.admin.api.entity.SysUser;
import com.ryuntech.common.repository.MyMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @author tycoding
 * @date 2019-05-22
 */
@Mapper
@Repository
public interface SysUserMapper extends MyMapper<SysUser> {
}
