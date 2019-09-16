package com.ryuntech.saas.api.model;

import com.alibaba.fastjson.JSON;
import lombok.Data;

import java.util.Objects;

/**
 * created by CaiBaoHong at 2018/5/14 16:14<br>
 *     用于存储角色或权限的值
 * @author EDZ
 */
@Data
public class Auth {

    /**显示名*/
    private String name;
    /**值*/
    private String val;
    public Auth() {

    }
    public Auth(String name, String val) {
        this.name = name;
        this.val = val;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Auth authVo = (Auth) o;
        return Objects.equals(val, authVo.val);
    }

    @Override
    public int hashCode() {
        return Objects.hash(val);
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
