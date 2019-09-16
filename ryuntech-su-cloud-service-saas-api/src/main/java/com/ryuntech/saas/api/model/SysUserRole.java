package com.ryuntech.saas.api.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.ryuntech.common.model.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
* <p>
    *
    * </p>
*
* @author antu
* @since 2019-09-12
*/
    @Data
        @EqualsAndHashCode(callSuper = false)
    @Accessors(chain = true)
    @TableName("sys_user_role")
    public class SysUserRole extends BaseModel {

    private static final long serialVersionUID = 1L;

    private String userId;

    private String roleId;
    public SysUserRole(String userId, String roleId) {
        this.userId = userId;
        this.roleId = roleId;
    }

}
