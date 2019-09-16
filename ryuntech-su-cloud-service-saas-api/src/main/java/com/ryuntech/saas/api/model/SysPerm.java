package com.ryuntech.saas.api.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
* <p>
    * 权限
    * </p>
*
* @author antu
* @since 2019-09-12
*/
    @Data
        @EqualsAndHashCode(callSuper = false)
    @Accessors(chain = true)
    @TableName("sys_perm")
    public class SysPerm extends BaseModel {

    private static final long serialVersionUID = 1L;

            /**
            * 权限值，shiro的权限控制表达式
            */
            @TableId(type = IdType.INPUT)
    private String pval;

            /**
            * 父权限id
            */
    private String parent;

            /**
            * 权限名称
            */
    private String pname;

            /**
            * 权限类型：1.菜单 2.按钮 3.接口 4.特殊
            */
    private Integer ptype;

            /**
            * 是否叶子节点
            */
    private Boolean leaf;

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
            @Column(name = "created")
    private Date updated;



    @TableField(exist = false)
    private List<SysPerm> children = new ArrayList<>();

    @TableField(exist = false)
    Map<Integer, List<SysPerm>> permMap;
    @TableField(exist = false)
    Map<String, List<SysPerm>> btnPermMap;


}
