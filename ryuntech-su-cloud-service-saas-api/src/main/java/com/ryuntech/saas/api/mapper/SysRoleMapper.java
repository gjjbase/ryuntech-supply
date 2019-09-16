package com.ryuntech.saas.api.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ryuntech.saas.api.model.SysRole;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author antu
 * @since 2019-09-12
 */
public interface SysRoleMapper extends IBaseMapper<SysRole> {

    List<String> getRoleIdsByUserId(@Param("userId") String userId);
}
