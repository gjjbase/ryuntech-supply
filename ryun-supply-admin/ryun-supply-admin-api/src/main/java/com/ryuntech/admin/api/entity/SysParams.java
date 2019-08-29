package com.ryuntech.admin.api.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
* <p>
    *
    * </p>
*
* @author antu
* @since 2019-08-15
*/
    @Data
        @EqualsAndHashCode(callSuper = false)
    @Accessors(chain = true)
    @TableName("ryn_sys_params")
    public class SysParams extends BaseModel {

    private static final long serialVersionUID = 1L;

            /**
            * 内部名key
            */
            @TableId("INNER_NAME")
    private String innerName;

            /**
            * 外部名
            */
        @TableField("OUTER_NAME")
    private String outerName;

            /**
            * 参数值
            */
        @TableField("VALUE")
    private String value;

            /**
            * 参数类型1=第一次登录,0=非第一次登录
            */
        @TableField("TYPE")
    private String type;

            /**
            * 状态 1=是；0=否
            */
        @TableField("STATUS")
    private String status;

            /**
            * 备注
            */
        @TableField("MEMO")
    private String memo;

            /**
            * 流程实例编号
            */
        @TableField("INSTANCE_ID")
    private String instanceId;


}
