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
    @TableName("sys_role_perm")
    public class SysRolePerm extends BaseModel {

    private static final long serialVersionUID = 1L;

    private String roleId;

    private String permVal;

    private Integer permType;

    public SysRolePerm(String roleId, String permVal,Integer permType) {
        this.roleId = roleId;
        this.permVal = permVal;
        this.permType = permType;
    }
}
