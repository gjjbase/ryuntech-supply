package com.ryuntech.saas.api.dto;

import com.ryuntech.saas.api.model.SysRole;
import lombok.Data;

import java.util.List;

/**
 * @author EDZ
 */
@Data
public class RolePermDTO {

    SysRole role;
    List<String> menuPvals;
    List<String> btnPvals;
    List<String> apiPvals;
}
