package com.ryuntech.common.constant.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author antu
 * @date 2019-05-22
 */
@Getter
@AllArgsConstructor
public enum OrderEnums {

    LOGIN("01", "待处理(合伙人推荐成功)"),
    PARAM_ERROR("02", "资质不符合(橙势审核不符)"),
    USER_ERROR("03", "资金方审核(提交银行渠道)"),
    LOGOUT_ERROR("04", "已放款(银行渠道已放款，交易完成)"),
    SYSTEM_ERROR("05", "已拒款(交易失败)");


    private final String code;
    private final String msg;

}
