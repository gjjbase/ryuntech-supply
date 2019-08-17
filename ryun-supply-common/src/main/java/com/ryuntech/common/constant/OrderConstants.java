package com.ryuntech.common.constant;

/**
 * 订单相关常量
 */
public class OrderConstants {

    /**
     * 待处理(合伙人推荐成功)
     */
    public static final String PENDING = "01";
    /*
    * 资质不符合(橙势审核不符)
    * */
    public static final String INCONFORMITY = "02";
    /*
    * 资金方审核(提交银行渠道)
    * */
    public static final String REVIEWING = "03";
    /*
    * 04=已放款(银行渠道已放款，交易完成)
    * */
    public  static final String LENDING = "04";
    /*
    * 已拒款
    * */
    public static final String REFUSAL = "05";
    /*
    * 已删除
    * */
    public static final String DELETED = "06";
}
