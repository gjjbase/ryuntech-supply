package com.ryuntech.saas.api.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ryuntech.common.mapper.IBaseMapper;
import com.ryuntech.saas.api.model.SysPerm;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 权限 Mapper 接口
 * </p>
 *
 * @author antu
 * @since 2019-09-12
 */
public interface SysPermMapper extends IBaseMapper<SysPerm> {
    /**
     * @param roleId
     * @return
     */
    List<SysPerm> getPermsByRoleId(@Param("roleId") String roleId);

    /**
     * @param userId
     * @return
     */
    List<SysPerm> getPermsByUserId(@Param("userId") String userId);
}
