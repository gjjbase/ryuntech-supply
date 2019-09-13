package com.ryuntech.saas.api.entity;

    import java.io.Serializable;
    import lombok.Data;
    import lombok.EqualsAndHashCode;
    import lombok.experimental.Accessors;

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
    public class SysUserRole implements Serializable {

    private static final long serialVersionUID = 1L;

    private String userId;

    private String roleId;


}
