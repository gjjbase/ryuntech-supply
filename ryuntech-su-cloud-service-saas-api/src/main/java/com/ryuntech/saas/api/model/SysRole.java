package com.ryuntech.saas.api.model;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

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
    @TableName("sys_role")
    public class SysRole extends BaseModel{

    private static final long serialVersionUID = 1L;

            /**
            * 角色id
            */
            @Id
            @TableId("rid")
    private String rid;

            /**
            * 角色名，用于显示
            */
            @TableId("rname")
    private String rname;

            /**
            * 角色描述
            */
            @TableId("rdesc")
    private String rdesc;

            /**
            * 角色值，用于权限判断
            */
            @TableId("rval")
    private String rval;

            /**
            * 创建时间
            */
            @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
            @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
            @Column(name = "created")
    private Date created;

            /**
            * 修改时间
            */
            @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
            @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
            @Column(name = "updated")
    private Date updated;


}
