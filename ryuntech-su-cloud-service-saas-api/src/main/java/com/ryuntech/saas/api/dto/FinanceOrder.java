package com.ryuntech.saas.api.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ryuntech.saas.api.model.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class FinanceOrder extends BaseModel {
 /*   {
        "realname": "cccc",
            "sex": "1",
            "memberBirthday": "2019-08-19 10:26:20",
            "city": "1",
            "orderPayAmount": "1",
            "occupation": "法人",
            "payTaxes": "1",
            "annualInvoice": "1",
            "houseType": "全款房",
            "houseAddressType": "",
            "carType": "按揭车",
            "bussinessRegister": "2019-08-19 00:00:00",
            "carAddressType": ""
    }
*/

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
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date bussinessRegister;
    /*
    * 渠道
    * */
    private String orderChenel;
    private String referralCode;

    private String mobile;
    private String code;
    private String companyName;

}
