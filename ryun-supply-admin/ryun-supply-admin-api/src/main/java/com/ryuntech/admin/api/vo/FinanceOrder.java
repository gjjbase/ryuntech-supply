package com.ryuntech.admin.api.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ryuntech.admin.api.entity.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class FinanceOrder extends BaseModel {


    private String realname;
    private String sex;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date memberBirthday;
    private String city;
    private String orderPayAmount;
    private String occupation;
    private String payTaxes;
    private String annualInvoice;
    private String houseType;
    private String houseAddressType;
    private String carType;
    private String carAddressType;
    private Date bussinessRegister;
    /*
    * 渠道
    * */
    private String orderChenel;

}
