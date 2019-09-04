package com.ryuntech.common.utils;

import com.ryuntech.common.constant.CommonConstants;
import com.ryuntech.common.constant.enums.CommonEnums;
import lombok.*;

import java.io.Serializable;

/**
 * @author antu
 * @date 2019-05-22
 */
@Data
@Builder
@ToString
@AllArgsConstructor
public class Result<T> implements Serializable {

    private int tcode = CommonConstants.SUCCESS;

    private Object msg = "success";

    private T data;

    public Result() {
        super();
    }

    public Result(T data) {
        super();
        this.data = data;
    }

    public Result(T data, String msg) {
        super();
        this.data = data;
        this.msg = msg;
    }

    public Result(CommonEnums enums) {
        super();
        this.tcode = enums.getCode();
        this.msg = enums.getMsg();
    }

    public Result(T data, CommonEnums enums) {
        super();
        this.data = data;
        this.tcode = enums.getCode();
        this.msg = enums.getMsg();
    }

    public Result(Throwable e) {
        super();
        this.tcode = CommonConstants.ERROR;
        this.msg = e.getMessage();
    }
}
